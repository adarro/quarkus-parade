package io.undertow.examples.servlet;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.util.Headers;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Singleton;
import jakarta.servlet.ServletException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static io.quarkus.arc.impl.UncaughtExceptions.LOGGER;

@Singleton
public class CometDUnderTowServer {

    public CometDUnderTowServer() {
        // TODO Auto-generated constructor stub
    }

    private Undertow server;

    @ConfigProperty(name = "cometd.port",defaultValue = "8080")
    int listeningPort;

    void onStart(@Observes StartupEvent ev) throws ServletException {
        LOGGER.info("The CometD Undertow edition application is starting from port:" + listeningPort);
        DeploymentInfo servletBuilder = Servlets.deployment()
                .setClassLoader(ServletServer.class.getClassLoader())
                .setContextPath("/myapp")
                .setDeploymentName("test.war")
                .addServlets(
                        Servlets.servlet("MessageServlet", MessageServlet.class)
                                .addInitParam("message", "Hello World")
                                .addMapping("/*"),
                        Servlets.servlet("MyServlet", MessageServlet.class)
                                .addInitParam("message", "MyServlet")
                                .addMapping("/myservlet"));

        DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
        manager.deploy();
        PathHandler path = Handlers.path(Handlers.redirect("/myapp"))
                .addPrefixPath("/myapp", manager.start());

        Undertow server = Undertow.builder()
                .addHttpListener(listeningPort, "localhost")
                .setHandler(path)
                .build();
        server.start();
//        server = Undertow.builder()
//                .addHttpListener(listeningPort, "localhost")
//                .setHandler(exchange -> {
//                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
//                    exchange.getResponseSender().send("Hello World");
//                }).build();
//        server.start();
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The CometD Undertow edition application is stopping from port:" + listeningPort);
        if (server != null)
            server.stop();
    }

    public static void main(final String[] args) {

    }

}