package org.acme;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import io.truthencode.client.cometd.CometDClient;
import io.truthencode.client.cometd.CometDClientConfig;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

@QuarkusTest
public class ClientTest {

    @Test
    public void testClient() {
        Log.warn("Attempting to start CometD client and attach");
        SampleClient sc = new SampleClient();
        Log.warn(String.format("attempting to handshake / sub %s", sc.cometDUrl()));
        Assertions.assertEquals(config.getCometDUrl(), sc.cometDUrl());
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

        try {
            sc.detach();
        } catch (Exception e) {
            fail("Test failed", e);
        }
    }


    @Inject
    CometDClient client;

    @Inject
    CometDClientConfig config;

    @Test
    public void testClient2() throws Exception {
        Log.warn("Attempting to start CometD client and attach");
        Log.warn("attempting to handshake / sub %s");

        client.subscribe();

        Log.warn("after subscribe call");
    }
}