package com.hedwig.morpheus.domain.model.implementation;

import com.hedwig.morpheus.domain.model.interfaces.IMessageReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hugo. All rights reserved.
 */
@Component
@Scope("singleton")
public class MessageReceiver implements IMessageReceiver {

    private final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);

    private final MessageQueue messageQueue;
    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    @Autowired
    public MessageReceiver(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    @Override
    public void processIncomeMessage(Message message) {
        try {
            logger.info("Starting message processing");
            Thread.sleep(5000);
            logger.info("Message successfully parsed: " + message.getTopic());
        } catch (InterruptedException e) {

        }
    }

    @Override
    public void processQueue() {
        threadPool.execute(() -> {
            while (true) {
                Message message = messageQueue.pop();
                threadPool.execute(() -> {
                    logger.info("Starting message processing");
                    try {
                        Thread.sleep(10000);
                        logger.info("Message successfully parsed: " + message.getTopic());
                    } catch (InterruptedException e) {
                        logger.error("Error in message processing", e);
                    }
                });
            }
        });
    }
}
