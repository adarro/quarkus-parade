package org.acme;

import io.quarkus.logging.Log;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.WebSocketContainer;
import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.client.BayeuxClient;
import org.cometd.client.http.jetty.JettyHttpClientTransport;
import org.cometd.client.transport.ClientTransport;
import org.cometd.client.websocket.jakarta.WebSocketTransport;
import org.cometd.common.TransportException;
import org.eclipse.jetty.client.HttpClient;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SampleClient implements QuarkusApplication {

    /**
     * The context / mount point of the CometD server.
     * For this example, the context here is /cometd-es6 as generated from the mvn archetype selecting the
     * vanilla es6 template.
     *
     */
    private final String context; // = "/cometd-es6";
    private final String apiPath; // = "/cometd";
    private final String host; // = "http://localhost";
    private final String port; // = "8080";

    private static final String DEFAULT_CONTEXT = "/cometd-server";
    private static final String DEFAULT_PATH = "/cometd";
    private static final String DEFAULT_HOST = "http://localhost";
    private static final String DEFAULT_PORT = "8080";

    final String cometDUrl() { return  String.format("%s:%s%s%s", host, port, context, apiPath);}

    public SampleClient(String host, String port, String context, String apiPath) {
        this.context = context;
        this.apiPath = apiPath;
        this.host = host;
        this.port = port;
    }

    public SampleClient() {
        this.context = DEFAULT_CONTEXT;
        this.apiPath = DEFAULT_PATH;
        this.host = DEFAULT_HOST;
        this.port = DEFAULT_PORT;
    }

    @Override
    public int run(String... args) throws Exception {
        Log.warn("Attempting to start CometD client and attach");
        SampleClient sc = new SampleClient();
        Log.warn(String.format("attempting to handshake / sub %s", cometDUrl()));
        sc.attach();
        Log.warn("sleeping 15 seconds");
        int i = 0;
        while (i < 14) {
            i++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.warn("sleep loop interrupted");
            }
        }
        Log.warn("attempting detach");
        sc.detach();
        Quarkus.waitForExit();
        return 0;
    }

    private static final String CHANNEL = "/foo";
    private final ClientSessionChannel.MessageListener fooListener = new FooListener();
    Set<String> attachedClients = new HashSet<>();

    public void attach() throws TransportException {
        Log.info("Getting HttpClient");
        HttpClient httpClient = getHttpClient();
        Log.info("creating long-polling transport");
        // Create the CometD long-polling transport for long-polling fallback.
        ClientTransport httpTransport = new JettyHttpClientTransport(null, httpClient);

        BayeuxClient client = new BayeuxClient(cometDUrl(), httpTransport);
        Log.warn("Attempting attach");
        client.handshake(handshakeReply -> {
            Log.info("Attempting Handshake");
            // You can only un/subscribe after a successful handshake.
            if (handshakeReply.isSuccessful()) {
                Log.info("Handshake succeeded");
                Log.warn(String.format("subscribing %s from %s ", client.getId(), CHANNEL));
                // The channel you want to subscribe to.
                ClientSessionChannel channel = client.getChannel(CHANNEL);
                if (client.getChannel(CHANNEL).subscribe(fooListener)) {
                    Log.warn("subscribe may have succeeded");
                    attachedClients.add(client.getId());
                } else {
                    Log.error("Failed to subscribe");
                }

                channel.subscribe(fooListener);
            } else {
                Log.error(String.format("Handshake failed, available keys are: %s", handshakeReply.keySet()));
                HashMap<String, Object> f = (HashMap<String, Object>) handshakeReply.get("failure");
                TransportException te = (TransportException) f.get("exception");
                Log.error("Failure message: %s", te.getMessage(), te);
                throw te;

            }
        });
    }

    public void detach() throws Exception {
        HttpClient httpClient = getHttpClient();
        // Create the CometD long-polling transport for long-polling fallback.
        ClientTransport httpTransport = new JettyHttpClientTransport(null, httpClient);

        BayeuxClient client = new BayeuxClient(cometDUrl(), httpTransport);
        client.handshake(handshakeReply -> {
            // You can only un/subscribe after a successful handshake.
            if (handshakeReply.isSuccessful()) {
                Log.warn(String.format("Unsubscribing %s from %s ", client.getId(), CHANNEL));
                // The channel you want to subscribe to.
                ClientSessionChannel channel = client.getChannel(CHANNEL);
                channel.unsubscribe(fooListener);
            }
        });
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

    private static class FooListener implements ClientSessionChannel.MessageListener {
        public void onMessage(ClientSessionChannel channel, Message message) {
            String msg = String.format("Message Received via %s %s", channel.getChannelId(), message.getChannel());
            Log.warn(msg);
        }
    }
}