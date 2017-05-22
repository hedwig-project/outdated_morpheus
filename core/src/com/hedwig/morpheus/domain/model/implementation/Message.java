package com.hedwig.morpheus.domain.model.implementation;

import com.hedwig.morpheus.domain.model.abstracts.Component;

/**
 * Created by hugo on 22/05/17. All rights reserved.
 */
public class Message {
    private Component to;
    private Component from;
    private String payload;

    public Component getTo() {
        return to;
    }

    public void setTo(Component to) {
        this.to = to;
    }

    public Component getFrom() {
        return from;
    }

    public void setFrom(Component from) {
        this.from = from;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
