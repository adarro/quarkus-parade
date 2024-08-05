package io.truthencode.poc.cometd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import io.quarkus.test.junit.QuarkusTest;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.LocalSession;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.server.BayeuxServerImpl;
import org.cometd.server.LocalSessionImpl;
import org.cometd.server.ServerMessageImpl;
import org.cometd.server.ServerSessionImpl;
import org.cometd.server.ext.AcknowledgedMessagesExtension;
import org.cometd.server.ext.ActivityExtension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
@QuarkusTest
class HelloServiceDiffblueTest {
    /**
     * Method under test: {@link HelloService#HelloService(BayeuxServer)}
     */
    @Test
    @Disabled("Not yet implemented")
    void testNewHelloService() {
        // Arrange
        BayeuxServerImpl bayeux = new BayeuxServerImpl();

        // Act
        HelloService actualHelloService = new HelloService(bayeux);

        // Assert
        LocalSession localSession = actualHelloService.getLocalSession();
        assertTrue(localSession instanceof LocalSessionImpl);
        ServerSession serverSession = actualHelloService.getServerSession();
        assertTrue(serverSession instanceof ServerSessionImpl);
        assertEquals("hello", actualHelloService.getName());
        assertNull(serverSession.getUserAgent());
        assertNull(((ServerSessionImpl) serverSession).getBrowserId());
        assertNull(serverSession.getServerTransport());
        assertNull(actualHelloService.getThreadPool());
        assertEquals(-1L, serverSession.getInterval());
        assertEquals(-1L, serverSession.getMaxInterval());
        assertEquals(-1L, serverSession.getTimeout());
        List<ServerSession> sessions = bayeux.getSessions();
        assertEquals(1, sessions.size());
        assertFalse(serverSession.isBroadcastToPublisher());
        assertFalse(serverSession.isMetaConnectDeliveryOnly());
        assertFalse(actualHelloService.isSeeOwnPublishes());
        assertFalse(((ServerSessionImpl) serverSession).hasNonLazyMessages());
        assertFalse(((ServerSessionImpl) serverSession).isAllowMessageDeliveryDuringHandshake());
        assertFalse(((ServerSessionImpl) serverSession).isDisconnected());
        assertFalse(((ServerSessionImpl) serverSession).isTerminated());
        assertTrue(((ServerSessionImpl) serverSession).getQueue().isEmpty());
        assertTrue(localSession.getExtensions().isEmpty());
        assertTrue(serverSession.getExtensions().isEmpty());
        assertTrue(((ServerSessionImpl) serverSession).getListeners().isEmpty());
        Set<String> attributeNames = localSession.getAttributeNames();
        assertTrue(attributeNames.isEmpty());
        assertTrue(serverSession.getSubscriptions().isEmpty());
        assertTrue(localSession.isConnected());
        assertTrue(serverSession.isConnected());
        assertTrue(localSession.isHandshook());
        assertTrue(serverSession.isHandshook());
        assertTrue(serverSession.isLocalSession());
        assertSame(bayeux, actualHelloService.getBayeux());
        assertSame(bayeux, ((ServerSessionImpl) serverSession).getBayeuxServer());
        assertSame(attributeNames, serverSession.getAttributeNames());
        assertSame(localSession, serverSession.getLocalSession());
        assertSame(serverSession, sessions.get(0));
        assertSame(serverSession, localSession.getServerSession());
    }

    /**
     * Method under test:
     * {@link HelloService#processHello(ServerSession, ServerMessage)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testProcessHello() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Map.get(Object)" because "that" is null
        //       at io.truthencode.poc.cometd.HelloService.processHello(HelloService.java:19)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        HelloService helloService = new HelloService(new BayeuxServerImpl());
        ServerSessionImpl remote = new ServerSessionImpl(new BayeuxServerImpl());

        // Act
        helloService.processHello(remote, new ServerMessageImpl());
    }

    /**
     * Method under test: {@link HelloService#HelloService(BayeuxServer)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testNewHelloService2() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.cometd.server.BayeuxServerImpl.newLocalSession(String)" because "this._bayeux" is null
        //       at org.cometd.server.AbstractService.<init>(AbstractService.java:90)
        //       at org.cometd.server.AbstractService.<init>(AbstractService.java:77)
        //       at io.truthencode.poc.cometd.HelloService.<init>(HelloService.java:13)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange and Act
        new HelloService(null);
    }

    /**
     * Method under test: {@link HelloService#HelloService(BayeuxServer)}
     */
    @Test
    void testNewHelloService3() {
        // Arrange
        BayeuxServerImpl bayeux = new BayeuxServerImpl();
        bayeux.addExtension(new AcknowledgedMessagesExtension());

        // Act
        HelloService actualHelloService = new HelloService(bayeux);

        // Assert
        LocalSession localSession = actualHelloService.getLocalSession();
        assertTrue(localSession instanceof LocalSessionImpl);
        ServerSession serverSession = actualHelloService.getServerSession();
        assertTrue(serverSession instanceof ServerSessionImpl);
        assertEquals("hello", actualHelloService.getName());
        assertNull(serverSession.getUserAgent());
        assertNull(((ServerSessionImpl) serverSession).getBrowserId());
        assertNull(serverSession.getServerTransport());
        assertNull(actualHelloService.getThreadPool());
        assertEquals(-1L, serverSession.getInterval());
        assertEquals(-1L, serverSession.getMaxInterval());
        assertEquals(-1L, serverSession.getTimeout());
        assertEquals(1, bayeux.getExtensions().size());
        List<ServerSession> sessions = bayeux.getSessions();
        assertEquals(1, sessions.size());
        assertFalse(serverSession.isBroadcastToPublisher());
        assertFalse(serverSession.isMetaConnectDeliveryOnly());
        assertFalse(actualHelloService.isSeeOwnPublishes());
        assertFalse(((ServerSessionImpl) serverSession).hasNonLazyMessages());
        assertFalse(((ServerSessionImpl) serverSession).isAllowMessageDeliveryDuringHandshake());
        assertFalse(((ServerSessionImpl) serverSession).isDisconnected());
        assertFalse(((ServerSessionImpl) serverSession).isTerminated());
        assertTrue(((ServerSessionImpl) serverSession).getQueue().isEmpty());
        assertTrue(localSession.getExtensions().isEmpty());
        assertTrue(serverSession.getExtensions().isEmpty());
        assertTrue(((ServerSessionImpl) serverSession).getListeners().isEmpty());
        Set<String> attributeNames = localSession.getAttributeNames();
        assertTrue(attributeNames.isEmpty());
        assertTrue(serverSession.getSubscriptions().isEmpty());
        assertTrue(localSession.isConnected());
        assertTrue(serverSession.isConnected());
        assertTrue(localSession.isHandshook());
        assertTrue(serverSession.isHandshook());
        assertTrue(serverSession.isLocalSession());
        assertSame(bayeux, actualHelloService.getBayeux());
        assertSame(bayeux, ((ServerSessionImpl) serverSession).getBayeuxServer());
        assertSame(attributeNames, serverSession.getAttributeNames());
        assertSame(localSession, serverSession.getLocalSession());
        assertSame(serverSession, sessions.get(0));
        assertSame(serverSession, localSession.getServerSession());
    }

    /**
     * Method under test: {@link HelloService#HelloService(BayeuxServer)}
     */
    @Test
    void testNewHelloService4() {
        // Arrange
        BayeuxServerImpl bayeux = new BayeuxServerImpl();
        bayeux.addExtension(new AcknowledgedMessagesExtension());
        bayeux.addExtension(new AcknowledgedMessagesExtension());

        // Act
        HelloService actualHelloService = new HelloService(bayeux);

        // Assert
        LocalSession localSession = actualHelloService.getLocalSession();
        assertTrue(localSession instanceof LocalSessionImpl);
        ServerSession serverSession = actualHelloService.getServerSession();
        assertTrue(serverSession instanceof ServerSessionImpl);
        assertEquals("hello", actualHelloService.getName());
        assertNull(serverSession.getUserAgent());
        assertNull(((ServerSessionImpl) serverSession).getBrowserId());
        assertNull(serverSession.getServerTransport());
        assertNull(actualHelloService.getThreadPool());
        assertEquals(-1L, serverSession.getInterval());
        assertEquals(-1L, serverSession.getMaxInterval());
        assertEquals(-1L, serverSession.getTimeout());
        List<ServerSession> sessions = bayeux.getSessions();
        assertEquals(1, sessions.size());
        assertFalse(serverSession.isBroadcastToPublisher());
        assertFalse(serverSession.isMetaConnectDeliveryOnly());
        assertFalse(actualHelloService.isSeeOwnPublishes());
        assertFalse(((ServerSessionImpl) serverSession).hasNonLazyMessages());
        assertFalse(((ServerSessionImpl) serverSession).isAllowMessageDeliveryDuringHandshake());
        assertFalse(((ServerSessionImpl) serverSession).isDisconnected());
        assertFalse(((ServerSessionImpl) serverSession).isTerminated());
        assertTrue(((ServerSessionImpl) serverSession).getQueue().isEmpty());
        assertTrue(localSession.getExtensions().isEmpty());
        assertTrue(serverSession.getExtensions().isEmpty());
        assertTrue(((ServerSessionImpl) serverSession).getListeners().isEmpty());
        Set<String> attributeNames = localSession.getAttributeNames();
        assertTrue(attributeNames.isEmpty());
        assertTrue(serverSession.getSubscriptions().isEmpty());
        assertTrue(localSession.isConnected());
        assertTrue(serverSession.isConnected());
        assertTrue(localSession.isHandshook());
        assertTrue(serverSession.isHandshook());
        assertTrue(serverSession.isLocalSession());
        assertSame(bayeux, actualHelloService.getBayeux());
        assertSame(bayeux, ((ServerSessionImpl) serverSession).getBayeuxServer());
        assertSame(attributeNames, serverSession.getAttributeNames());
        assertSame(localSession, serverSession.getLocalSession());
        assertSame(serverSession, sessions.get(0));
        assertSame(serverSession, localSession.getServerSession());
    }

    /**
     * Method under test: {@link HelloService#HelloService(BayeuxServer)}
     */
    @Test
    void testNewHelloService5() {
        // Arrange
        BayeuxServerImpl bayeux = new BayeuxServerImpl();
        bayeux.addExtension(null);

        // Act
        HelloService actualHelloService = new HelloService(bayeux);

        // Assert
        LocalSession localSession = actualHelloService.getLocalSession();
        assertTrue(localSession instanceof LocalSessionImpl);
        ServerSession serverSession = actualHelloService.getServerSession();
        assertTrue(serverSession instanceof ServerSessionImpl);
        assertEquals("hello", actualHelloService.getName());
        assertNull(serverSession.getUserAgent());
        assertNull(((ServerSessionImpl) serverSession).getBrowserId());
        assertNull(serverSession.getServerTransport());
        assertNull(actualHelloService.getThreadPool());
        assertEquals(-1L, serverSession.getInterval());
        assertEquals(-1L, serverSession.getMaxInterval());
        assertEquals(-1L, serverSession.getTimeout());
        List<ServerSession> sessions = bayeux.getSessions();
        assertEquals(1, sessions.size());
        assertFalse(serverSession.isBroadcastToPublisher());
        assertFalse(serverSession.isMetaConnectDeliveryOnly());
        assertFalse(actualHelloService.isSeeOwnPublishes());
        assertFalse(((ServerSessionImpl) serverSession).hasNonLazyMessages());
        assertFalse(((ServerSessionImpl) serverSession).isAllowMessageDeliveryDuringHandshake());
        assertFalse(((ServerSessionImpl) serverSession).isDisconnected());
        assertFalse(((ServerSessionImpl) serverSession).isTerminated());
        assertTrue(((ServerSessionImpl) serverSession).getQueue().isEmpty());
        assertTrue(localSession.getExtensions().isEmpty());
        assertTrue(serverSession.getExtensions().isEmpty());
        assertTrue(((ServerSessionImpl) serverSession).getListeners().isEmpty());
        Set<String> attributeNames = localSession.getAttributeNames();
        assertTrue(attributeNames.isEmpty());
        assertTrue(serverSession.getSubscriptions().isEmpty());
        assertTrue(localSession.isConnected());
        assertTrue(serverSession.isConnected());
        assertTrue(localSession.isHandshook());
        assertTrue(serverSession.isHandshook());
        assertTrue(serverSession.isLocalSession());
        assertSame(bayeux, actualHelloService.getBayeux());
        assertSame(bayeux, ((ServerSessionImpl) serverSession).getBayeuxServer());
        assertSame(attributeNames, serverSession.getAttributeNames());
        assertSame(localSession, serverSession.getLocalSession());
        assertSame(serverSession, sessions.get(0));
        assertSame(serverSession, localSession.getServerSession());
    }

    /**
     * Method under test: {@link HelloService#HelloService(BayeuxServer)}
     */
    @Test
    void testNewHelloService6() {
        // Arrange
        BayeuxServerImpl bayeux = new BayeuxServerImpl();
        bayeux.addExtension(new ActivityExtension(ActivityExtension.Activity.CLIENT, 1L));

        // Act
        HelloService actualHelloService = new HelloService(bayeux);

        // Assert
        LocalSession localSession = actualHelloService.getLocalSession();
        assertTrue(localSession instanceof LocalSessionImpl);
        ServerSession serverSession = actualHelloService.getServerSession();
        assertTrue(serverSession instanceof ServerSessionImpl);
        List<ServerSession.Extension> extensions = serverSession.getExtensions();
        assertEquals(1, extensions.size());
        ServerSession.Extension getResult = extensions.get(0);
        assertTrue(getResult instanceof ActivityExtension.SessionExtension);
        assertEquals("hello", actualHelloService.getName());
        assertNull(serverSession.getUserAgent());
        assertNull(((ServerSessionImpl) serverSession).getBrowserId());
        assertNull(serverSession.getServerTransport());
        assertNull(actualHelloService.getThreadPool());
        assertEquals(-1L, serverSession.getInterval());
        assertEquals(-1L, serverSession.getMaxInterval());
        assertEquals(-1L, serverSession.getTimeout());
        assertEquals(1, bayeux.getExtensions().size());
        assertEquals(1L, ((ActivityExtension.SessionExtension) getResult).getMaxInactivityPeriod());
        assertFalse(localSession.isConnected());
        assertFalse(serverSession.isConnected());
        assertFalse(localSession.isHandshook());
        assertFalse(serverSession.isHandshook());
        assertFalse(serverSession.isBroadcastToPublisher());
        assertFalse(serverSession.isMetaConnectDeliveryOnly());
        assertFalse(actualHelloService.isSeeOwnPublishes());
        assertFalse(((ServerSessionImpl) serverSession).hasNonLazyMessages());
        assertFalse(((ServerSessionImpl) serverSession).isAllowMessageDeliveryDuringHandshake());
        assertTrue(((ServerSessionImpl) serverSession).getQueue().isEmpty());
        assertTrue(localSession.getExtensions().isEmpty());
        assertTrue(((ServerSessionImpl) serverSession).getListeners().isEmpty());
        Set<String> attributeNames = localSession.getAttributeNames();
        assertTrue(attributeNames.isEmpty());
        assertTrue(serverSession.getSubscriptions().isEmpty());
        assertTrue(serverSession.isLocalSession());
        assertTrue(((ServerSessionImpl) serverSession).isDisconnected());
        assertTrue(((ServerSessionImpl) serverSession).isTerminated());
        assertSame(bayeux, actualHelloService.getBayeux());
        assertSame(bayeux, ((ServerSessionImpl) serverSession).getBayeuxServer());
        assertSame(attributeNames, serverSession.getAttributeNames());
        assertSame(localSession, serverSession.getLocalSession());
        assertSame(serverSession, localSession.getServerSession());
    }
}
