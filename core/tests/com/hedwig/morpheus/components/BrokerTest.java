package com.hedwig.morpheus.components;

import com.hedwig.morpheus.domain.model.implementation.Broker;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by hugo on 10/06/17. All rights reserved.
 */
public class BrokerTest {

    private Broker localBroker;
    private Broker remoteBroker;

    private Path caCertificate;

    private Path localBrokerCertificate;
    private Path localBrokerKey;

    @Before
    public void setup() {
        caCertificate = Paths.get("/home/hugo/Documents/TCC/dev/mqtt/certificates/ca.crt");

        localBrokerCertificate = Paths.get("/home/hugo/Documents/TCC/dev/mqtt/certificates/device001.der");
        localBrokerKey = Paths.get("/home/hugo/Documents/TCC/dev/mqtt/certificates/device001.key");

        localBroker = Broker.build("localhost", 8883, caCertificate, localBrokerCertificate, localBrokerKey);
        remoteBroker = null;
    }

    @Test
    public void brokerConnectionTest() {
        try {
            localBroker.connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
