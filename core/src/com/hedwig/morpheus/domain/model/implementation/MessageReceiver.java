package com.hedwig.morpheus.domain.model.implementation;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by hugo. All rights reserved.
 */
@Component
@Scope("singleton")
public class MessageReceiver implements com.hedwig.morpheus.business.interfaces.IMessageReceiver {

    private final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);

    @Override
    public void processIncomeMessage(String topic, MqttMessage mqttMessage) {
        logger.info("Message received from module: " + new String(mqttMessage.getPayload()));
    }
}
