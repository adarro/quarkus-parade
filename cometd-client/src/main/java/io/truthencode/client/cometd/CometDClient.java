package io.truthencode.client.cometd;

import io.quarkus.logging.Log;
import io.quarkus.runtime.Shutdown;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.client.BayeuxClient;
import org.cometd.common.TransportException;

import java.util.HashMap;
import java.util.Objects;
import java.util.Stack;

public class CometDClient {

    @Inject
    BayeuxClient client;

    /**
     * Stores the last channel subscribed to.
     */
    Stack<String> lastChannel = new Stack<>();


    /**
     * Connection status of the client.
     */
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private boolean connected = false;


    private static final String CHANNEL = "/information";
    private final ClientSessionChannel.MessageListener defaultListener = new DefaultListener();


    protected void sub(String channel, ClientSessionChannel.MessageListener listener) {
        if (client.isHandshook()) {
            ClientSessionChannel clientSessionChannel = client.getChannel(channel);
            clientSessionChannel.subscribe(listener, subscribeReply -> {
                if (subscribeReply.isSuccessful()) {
                    Log.info(String.format("%s subscribed to %s", client.getId(), channel));
                    if (lastChannel.isEmpty() || !Objects.equals(lastChannel.peek(), channel))
                        lastChannel.push(channel);
                    // TODO: cache the channel and listener for later use.
                } else {
                    Log.warn(String.format("%s Failed to subscribe to %s", client.getId(), channel));
                }
            });
        } else {
            Log.warn("Client must handshake before subscribing to a channel");
        }
    }

    protected void unSub(String channel, ClientSessionChannel.MessageListener listener) {
        if (client.isHandshook()) {
            ClientSessionChannel clientSessionChannel = client.getChannel(channel);
            clientSessionChannel.unsubscribe(listener, unSubscribeReply -> {
                if (unSubscribeReply.isSuccessful()) {
                    Log.info(String.format("%s unsubscribed to %s", client.getId(), channel));
                    if (!lastChannel.isEmpty() && Objects.equals(lastChannel.peek(), channel))
                        lastChannel.pop();
                    // TODO: cache the channel and listener for later use.
                } else {
                    Log.warn(String.format("%s Failed to subscribe to %s", client.getId(), channel));
                }
            });
        } else {
            Log.warn("Client must handshake before unsubscribing to a channel");
        }
    }

    protected void pub(String channel, Object data) {
        if (client.isHandshook()) {
            ClientSessionChannel clientSessionChannel = client.getChannel(channel);
            clientSessionChannel.publish(data, reply -> {
                if (reply.isSuccessful()) {
                    Log.info(String.format("%s published message to %s", client.getId(), channel));
                    // Do we update the last channel? or do we track it separately?
                } else {
                    Log.warn(String.format("%s Failed to publish to %s", client.getId(), channel));
                }
            });
        } else {
            Log.warn("Client must handshake before unsubscribing to a channel");
        }
    }

    void handShakeAsync(MessageAction action, String channel, ClientSessionChannel.MessageListener listener) {
        handShakeAsync(action, channel, listener, null);
    }

    void handShakeAsync(MessageAction action, String channel, ClientSessionChannel.MessageListener listener, Object data) {
        if (!client.isHandshook()) {

            client.handshake(handshakeReply -> {
                Log.info("Attempting Handshake");
                // You can only un/subscribe after a successful handshake.
                if (handshakeReply.isSuccessful()) {
                    setConnected(true);
                    Log.info("Handshake succeeded");
                    Log.info(String.format("subscribing %s from %s ", client.getId(), channel));
                    switch (action) {
                        case SUBSCRIBE:
                            sub(channel, listener);
                            break;
                        case UNSUBSCRIBE:
                            unSub(channel, listener);
                            break;
                        case PUBLISH:
                            pub(channel, data);
                            break;
                        default:
                            Log.warn(String.format("Action %s not supported", action));
                    }

                    // The channel you want to subscribe to.


                } else {
                    setConnected(false);
                    Log.error(String.format("Handshake failed, available keys are: %s", handshakeReply.keySet()));
                    HashMap<String, Object> f = (HashMap<String, Object>) handshakeReply.get("failure");
                    for (String key : f.keySet()) {
                        Log.error(String.format("message k:%s\t v:%s",f, f.get(key)));
                    }
//                    Exception exception = (Exception) f.get("failure");
//                    String message = exception.getMessage();
//                    Log.error("Failure message: %s", message,exception);
//                    TransportException te = (TransportException) f.get("exception");
//                    Log.error("Failure message: %s", te.getMessage(), te);
                }
            });
        }
    }

    /**
     * Subscribes to a channel and registers a listener for messages received on the channel.
     *
     * @param channel  channel to subscribe to.  Supports wildcards.
     * @param listener listener to register.
     */
    public void subscribe(String channel, ClientSessionChannel.MessageListener listener) {
        if (!client.isHandshook()) {
            handShakeAsync(MessageAction.SUBSCRIBE, channel, listener);
        } else {
            sub(channel, listener); // This fires synchronously. :(
        }
    }

    /**
     * Subscribes to the last channel subscribed to or the default channel if none was subscribed to.
     *
     * @param listener MessageListener to be notified of messages.
     */
    public void subscribe(ClientSessionChannel.MessageListener listener) {
        String channel = !lastChannel.empty() ? lastChannel.peek() : CHANNEL;
        subscribe(channel, listener);
    }

    public void subscribe() throws Exception {
        subscribe(defaultListener);
    }

    public void unsubscribe(String channel, ClientSessionChannel.MessageListener listener) {
        if (!client.isHandshook()) {
            handShakeAsync(MessageAction.UNSUBSCRIBE, channel, listener);
        } else {
            unSub(channel, listener); // This fires synchronously. :(
        }
    }

    public void unsubscribe(String channel) {
        unsubscribe(channel, defaultListener);
    }

    public void unsubscribe() {
        String channel = !lastChannel.empty() ? lastChannel.peek() : CHANNEL;
        unsubscribe(channel);
    }

    /**
     * publishes a message to a channel.
     * Valid channel syntax is not currently validated.
     * <p>
     * Currently only supports non-binary data.
     *
     * @param channel channel to publish to.
     * @param data    data to publish.
     */
    public void publish(String channel, Object data) {
        if (!client.isHandshook()) {
            handShakeAsync(MessageAction.PUBLISH, channel, defaultListener, data);
        } else {
            client.getChannel(channel).publish(data);
        }
    }

    public void publish(Object data) {
        String channel = !lastChannel.empty() ? lastChannel.peek() : CHANNEL;
        publish(channel, data);
    }

    @Shutdown
    public void detach() throws Exception {
        if (client.isConnected())
            client.disconnect(disconnectReply -> {
                if (disconnectReply.isSuccessful()) {
                    Log.info("Disconnected from CometD Server");
                }
            });
    }

    private static class DefaultListener implements ClientSessionChannel.MessageListener {
        public void onMessage(ClientSessionChannel channel, Message message) {
            String msg = String.format("Message Received via %s %s", channel.getChannelId(), message.getChannel());
            Log.warn(msg);
        }
    }
}