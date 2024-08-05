package io.truthencode.poc.cometd;

import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.client.BayeuxClient;
import org.cometd.client.ext.AckExtension;
import org.cometd.client.http.jetty.JettyHttpClientTransport;
import org.cometd.client.transport.ClientTransport;
import org.eclipse.jetty.client.HttpClient;

import java.util.HashMap;
import java.util.Map;

public class ClientFromDoc {
    void current8() throws Exception {
        // Create (and eventually configure) Jetty's HttpClient.
        HttpClient httpClient = new HttpClient();
// Here configure Jetty's HttpClient.
// httpClient.setMaxConnectionsPerDestination(2);
        httpClient.start();

// Prepare the transport.
        Map<String, Object> options = new HashMap<>();
        ClientTransport transport = new JettyHttpClientTransport(options, httpClient);

// Create the BayeuxClient.
        //ClientSession bayeuxClient = new BayeuxClient("http://localhost:8080/cometd", transport);
        // upcasting until we find needed clientsession lib
        BayeuxClient bayeuxClient = new BayeuxClient("http://localhost:8080/cometd", transport);

// Here prepare the BayeuxClient, for example:
// Add the message acknowledgement extension.
        bayeuxClient.addExtension(new AckExtension());
// Register a listener for channel /service/business.
        ClientSessionChannel channel = bayeuxClient.getChannel("/service/business");
        channel.addListener((ClientSessionChannel.MessageListener)(c, message) -> {
            System.err.printf("Received message on %s: %s%n", c, message);
        });

// Handshake with the server.
        bayeuxClient.handshake();
    }
//    void doStuff() {
//        // Setup Jetty's HttpClient.
//        HttpClient httpClient = new HttpClient();
//        httpClient.start();
//
//        // Handshake
//        String url = "http://localhost:8080/cometd";
//        BayeuxClient client = new BayeuxClient(url, new JettyHttpClientTransport(null, httpClient));
//        client.handshake();
//        client.waitFor(1000, BayeuxClient.State.CONNECTED);
//
//        // Subscription to channels
//        ClientSessionChannel channel = client.getChannel("/foo");
//        channel.subscribe((channel, message) -> {
//            // Handle the message
//        });
//
//        // Publishing to channels
//        Map<String, Object> data = new HashMap<>();
//        data.put("bar", "baz");
//        channel.publish(data);
//
//        // Disconnecting
//        client.disconnect();
//        client.waitFor(1000, BayeuxClient.State.DISCONNECTED);
//    }

//    void current4() {
//        // Handshake
//        String url = "http://localhost:8080/cometd";
//        BayeuxClient client = new BayeuxClient(url, LongPollingTransport.create(null));
//        client.handshake();
//        client.waitFor(1000, BayeuxClient.State.CONNECTED);
//
//        // Subscription to channels
//        ClientSessionChannel channel = client.getChannel("/foo");
//        channel.subscribe(new ClientSessionChannel.MessageListener()
//        {
//            public void onMessage(ClientSessionChannel channel, Message message)
//            {
//                // Handle the message
//            }
//        });
//
//        // Publishing to channels
//        Map<String, Object> data = new HashMap<String, Object>();
//        data.put("bar", "baz");
//        channel.publish(data);
//
//        // Disconnecting
//        client.disconnect();
//        client.waitFor(1000, BayeuxClient.State.DISCONNECTED);
//    }
}
