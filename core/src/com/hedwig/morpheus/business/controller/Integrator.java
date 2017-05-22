package com.hedwig.morpheus.business.controller;

import com.hedwig.morpheus.domain.model.implementation.Broker;
import com.hedwig.morpheus.domain.model.implementation.Sensor;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;

/**
 * Created by hugo. All rights reserved.
 */
public class Integrator {

    private Broker localBroker;
    private Broker remoteBroker;

    private Integrator() {
    }

    private final static class StaticHolder {
        private final static Integrator INSTANCE  = new Integrator();
    }

    public Broker getLocalBroker() {
        return localBroker;
    }

    public Broker getRemoteBroker() {
        return remoteBroker;
    }

    public void startBrokers() {
        try {
            if(localBroker != null) {
                localBroker.connect();
            }

            if(remoteBroker != null) {
                remoteBroker.connect();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void addSensors(List<Sensor> sensorList) {
    }

    public void addActuators() {

    }
}

