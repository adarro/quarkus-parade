package io.truthencode.client.cometd;

import io.quarkus.logging.Log;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.WebSocketContainer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.cometd.client.BayeuxClient;
import org.cometd.client.http.jetty.JettyHttpClientTransport;
import org.cometd.client.transport.ClientTransport;
import org.cometd.client.websocket.jakarta.WebSocketTransport;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Deprecated
public class CometDClientConfig {

    /**
     * The context / mount point of the CometD server.
     * For this example, the context here is /cometd-es6 as generated from the mvn archetype selecting the
     * vanilla es6 template.
     */
    @Inject
    @ConfigProperty(name = "cometd.context", defaultValue = "/cometd-es6")
    String context;
    @Inject
    @ConfigProperty(name = "cometd.apiPath", defaultValue = "/cometd")
    String apiPath;
    @Inject
    @ConfigProperty(name = "cometd.host", defaultValue = "http://localhost:8080")
    String host;
    @Inject
    @ConfigProperty(name = "cometd.port", defaultValue = "8080")
    String port;

    @Getter(lazy = true)
    private final String cometDUrl = buildCometDUrl();


    /**
     * Lazy getter to build the CometD URL post injection.
     *
     * @return the CometD URL using supplied properties.
     */
    @NotNull
    private String buildCometDUrl() {
        return String.format("%s:%s%s%s", host, port, context, apiPath);
    }


    private static HttpClient getHttpClient() {
        Log.info("preparing Websocket Transport");
        // Expects a Jakarta WebSocket implementation on your classpath.
        // We are using quarkus-websockets extension, which provides an implementation.
        WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();

        // Create the CometD Jakarta WebSocket transport.
        ClientTransport wsTransport = new WebSocketTransport(null, null, webSocketContainer);

        // Prepare the HTTP transport.
        // Create a Jetty HttpClient instance.
        Log.info("preparing HttpClient");
        HttpClient httpClient = new HttpClient();
        // Add the webSocketContainer as a dependent bean of HttpClient.
        // If webSocketContainer is the Jetty implementation, then
        // webSocketContainer will follow the lifecycle of HttpClient.
        Log.info(("adding websocket transport to Httpclient"));
        httpClient.addBean(webSocketContainer, true);
        Log.info("Starting client");
        try {
            httpClient.start();
        } catch (Exception e) {
            Log.error("failed to start httpclient", e);
            throw new RuntimeException(e);
        }
        Log.info("Client should be started");
        return httpClient;
    }

    @Produces
    public BayeuxClient DefaultClient() {

        Log.info("Getting HttpClient");
        HttpClient httpClient = getHttpClient();
        Log.info("creating long-polling transport");
        // Create the CometD long-polling transport for long-polling fallback.
        ClientTransport httpTransport = new JettyHttpClientTransport(null, httpClient);

        return new BayeuxClient(getCometDUrl(), httpTransport);
    }
}
