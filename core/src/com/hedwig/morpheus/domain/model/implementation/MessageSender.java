package com.hedwig.morpheus.domain.model.implementation;

import com.hedwig.morpheus.domain.model.interfaces.IMessageSender;
import com.hedwig.morpheus.domain.model.interfaces.IServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by hugo. All rights reserved.
 */
@Component
public class MessageSender implements IMessageSender {

    private final IServer server;

    @Autowired
    public MessageSender(IServer server) {
        this.server = server;
    }

    @Override
    public void send(Message message) {
        server.sendMessage(message);
    }
}
