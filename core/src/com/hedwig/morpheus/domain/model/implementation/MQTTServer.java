package com.hedwig.morpheus.domain.model.implementation;

import com.hedwig.morpheus.business.interfaces.IMessageReceiver;
import com.hedwig.morpheus.domain.model.interfaces.IServer;
import com.hedwig.morpheus.security.Securities;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.net.ssl.SSLSocketFactory;
import javax.validation.constraints.NotNull;
import java.nio.file.Path;
import java.nio.file.Paths;

// TODO : Make this code all asynchronous
// TODO : Make a server interface
// TODO : Inversion of control, message manager should have an instance of the server
// TODO : Add logging functionality
// TODO : Add subscription security
// TODO : Add configuration file

/**
 * Created by hugo. All rights reserved.
 */
@Component
@Scope("singleton")
public class MQTTServer implements IServer {
    private final String id;
    private final String name;
    private final int port;

    private final MqttAsyncClient mqttAsyncClient;
    private final MqttConnectOptions mqttConnectOptions;
    private final MemoryPersistence memoryPersistence;

    private final Path caCertificate;
    private final Path serverCertificate;
    private final Path serverKey;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    SSLSocketFactory socketFactory;

    IMqttToken mqttConnectToken;

    private MQTTServer() throws Exception {
        this.caCertificate = Paths.get("/home/hugo/Documents/TCC/dev/mqtt/certificates/ca.crt");
        this.serverCertificate = Paths.get("/home/hugo/Documents/TCC/dev/mqtt/certificates/device001.der");
        this.serverKey = Paths.get("/home/hugo/Documents/TCC/dev/mqtt/certificates/device001.key");

        this.id = "morpheus-localServer";
        this.name =  "localhost";
        this.port = 8883;

        mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
        mqttConnectOptions.setKeepAliveInterval(MqttConnectOptions.KEEP_ALIVE_INTERVAL_DEFAULT);

        memoryPersistence = new MemoryPersistence();

        mqttAsyncClient = new MqttAsyncClient(getConnectionUrl(), id, memoryPersistence);

        socketFactory = Securities.createSocketFactory(this.caCertificate.toString(),
                                                       this.serverCertificate.toString(),
                                                       this.serverKey.toString());

        mqttConnectOptions.setSocketFactory(socketFactory);

        setCallBack();
    }

    private void setCallBack() {
        mqttAsyncClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
               logger.error("Connection was lost. " + cause.getCause());
                System.exit(0);
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                  // TODO : Send arrived messages to the queue

                logger.info("New message received from module: " + new String(mqttMessage.getPayload()));
//                //Sensor sensor = new Sensor("Generic sensor", null, null, null, null);
//                Message_old message = new Message_old();
//                //message.setFrom(sensor);
//                message.setPayload(new String(mqttMessage.getPayload(), "UTF-8"));
//
//                System.out.println("A new message has arrived: " + message.getPayload());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    @Override
    public String getConnectionUrl() {
        return String.format("ssl://%s:%d", name, port);
    }

    @Override
    public void connect() throws MqttException {
        mqttConnectToken = mqttAsyncClient.connect(mqttConnectOptions, null, null);
        mqttConnectToken.waitForCompletion();
    }

    @Override
    public boolean sendMessage(Message message) {
        // TODO : Verify if topic is in subscription list
        MqttMessage mqttMessage = new MqttMessage(message.toString().getBytes());
        try {
            IMqttDeliveryToken deliveryToken = mqttAsyncClient.publish(message.getTopic(), mqttMessage, null, null);
            logger.info("Message sent to module");
            deliveryToken.waitForCompletion();
        } catch (MqttException e) {
            System.err.println("Could not send message.");
            return false;
        }

        return true;
    }

    @Override
    public boolean subscribe(@NotNull String topic) {
        // TODO: This has to be async

        try {
            IMqttToken mqttToken = mqttAsyncClient.subscribe(topic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // TODO: communicate success
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // TODO: Communicate failure
                }
            });

            mqttToken.waitForCompletion();

            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }

    // TODO : Implement unsubscription
    @Override
    public boolean unsubscribe(String topic) {
        throw new NotImplementedException();
    }

    @Override
    public boolean registerMessageReceiver(IMessageReceiver messageReceiver) {
        return false;
    }

//    public static MQTTServer build(String mqttServerHost,
//                            int mqttServerPort,
//                            Path caCertificate,
//                            Path clientCertificate,
//                            Path clientKey,
//                            IMessageManager messageManager) {
//        try {
//            return new MQTTServer(mqttServerHost,
//                                  mqttServerPort,
//                                  caCertificate,
//                                  clientCertificate,
//                                  clientKey,
//                                  messageManager);
//        } catch (Exception e) {
//            throw new IllegalStateException("IServer could not be created.", e);
//        }
//    }
}
