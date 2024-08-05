package io.truthencode.poc;

import java.util.HashMap;
import java.util.Map;

import org.cometd.bayeux.client.ClientSession;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.client.BayeuxClient;
import org.cometd.client.transport.HttpClientTransport;
import org.eclipse.jetty.client.HttpClient;
//import org.eclipse.jetty.client.HttpClient;
//import org.eclipse.jetty.client.HttpClient;
//import org.eclipse.jetty.client.HttpClientTransport;


public class ExampleClient {
//    // Create (and eventually configure) Jetty's HttpClient.
//HttpClient httpClient = new HttpClient();
//// Here configure Jetty's HttpClient.
//// httpClient.setMaxConnectionsPerDestination(2);
////httpClient.start();
//
//// Prepare the transport.
//Map<String, Object> options = new HashMap<>();
//
//HttpClientTransport transport = httpClient.getTransport();
//
//
//
//// Create the BayeuxClient.
//BayeuxClient bayeuxClient = new BayeuxClient("http://localhost:8080/cometd", transport);
//
//// Here prepare the BayeuxClient, for example:
//// Add the message acknowledgement extension.
//bayeuxClient.addExtension(new AckExtension());
//// Register a listener for channel /service/business.
//ClientSessionChannel channel = bayeuxClient.getChannel("/service/business");
//channel.addListener((ClientSession.MessageListener)(c, message) -> {
//    System.err.printf("Received message on %s: %s%n", c, message);
//});
//
//// Handshake with the server.
//bayeuxClient.handshake();
}
