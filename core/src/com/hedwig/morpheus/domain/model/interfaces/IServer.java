package com.hedwig.morpheus.domain.model.interfaces;

import com.hedwig.morpheus.business.interfaces.IMessageReceiver;
import com.hedwig.morpheus.domain.model.implementation.Message;
import org.eclipse.paho.client.mqttv3.MqttException;

import javax.validation.constraints.NotNull;

/**
 * Created by hugo. All rights reserved.
 */
public interface IServer {

    String getConnectionUrl();

    void connect() throws MqttException;

    boolean sendMessage(@NotNull Message message);

    boolean subscribe(@NotNull String topic);

    boolean unsubscribe(@NotNull String topic);

    boolean registerMessageReceiver(IMessageReceiver messageReceiver);
}
