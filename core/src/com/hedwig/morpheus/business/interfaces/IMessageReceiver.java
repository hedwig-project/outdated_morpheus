package com.hedwig.morpheus.business.interfaces;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by hugo. All rights reserved.
 */
public interface IMessageReceiver {
    void processIncomeMessage(String topic, MqttMessage mqttMessage);
}
