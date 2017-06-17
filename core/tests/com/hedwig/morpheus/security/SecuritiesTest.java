package com.hedwig.morpheus.security;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.junit.Test;

import javax.net.ssl.SSLSocketFactory;
import java.io.File;

/**
 * Created by hugo on 20/05/17. All rights reserved.
 */
public class SecuritiesTest {

    public SecuritiesTest() {
    }

    @Test
    public void establishConnectionTest() {
        try {
            final MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
            mqttConnectOptions.setKeepAliveInterval(MqttConnectOptions.KEEP_ALIVE_INTERVAL_DEFAULT);

            MemoryPersistence memoryPersistence = new MemoryPersistence();

            final String mqttServerHost = "localhost"; // This should be changed to the server IP
            final int mqttServerPort = 8883;
            final String mqttServerURI = String.format("ssl://%s:%d", mqttServerHost, mqttServerPort);

            final String mqttClientId = MqttAsyncClient.generateClientId();
            MqttAsyncClient mqttAsyncClient = new MqttAsyncClient(mqttServerURI, mqttClientId, memoryPersistence);
            final String topic_for_sensor01 = "hw/house001/kitchen/sensors/sensor01";

            final String certificatesPath = "./resources/securities/certificates";
            final String caCertificateFileName = String.join(File.separator, certificatesPath, "ca.crt");

            final String clientCertificateFileName = String.join(File.separator, certificatesPath, "device001.der");

            final String clientKeyFileName = String.join(File.separator, certificatesPath, "device001.key");


            SSLSocketFactory socketFactory;

            try {
                socketFactory = Securities.createSocketFactory(caCertificateFileName,
                                                               clientCertificateFileName,
                                                               clientKeyFileName);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            mqttConnectOptions.setSocketFactory(socketFactory);

            mqttAsyncClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    cause.printStackTrace();
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    if (!topic.equals(topic_for_sensor01)) {
                        return;
                    }

                    String messageText = new String(message.getPayload(), "UTF-8");

                    System.out.println(String.format("Topic: %s. Payload: %s", topic, messageText));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });

            IMqttToken mqttConnectToken = mqttAsyncClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    System.out.println(String.format("Successfully connected"));

                    try {
                        IMqttToken subscribeToken =
                                mqttAsyncClient.subscribe(topic_for_sensor01, 0, null, new IMqttActionListener() {
                                    @Override
                                    public void onSuccess(IMqttToken asyncActionToken) {
                                        System.out.println(String.format("Subscribed to the %s topic with QoS: %d",
                                                                         topic_for_sensor01,
                                                                         asyncActionToken.getGrantedQos()[0]));
                                    }

                                    @Override
                                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                        exception.printStackTrace();
                                    }
                                });
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

}
