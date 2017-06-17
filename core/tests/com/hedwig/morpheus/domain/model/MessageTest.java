package com.hedwig.morpheus.domain.model;

import com.hedwig.morpheus.domain.model.implementation.Message;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by hugo. All rights reserved.
 */
public class MessageTest {

    @Test
    public void payloadConfigurationMessage() {
        // given
        Message message = new Message("hw/kitchen",
                                       Message.MessageType.CONFIGURATION, new Message.MessageBody("new time set"));
        message.addControlParameter(new Message.ControlParameter("ts", new Date(new Long("1497209392924")).toString()));
        message.addControlParameter(new Message.ControlParameter("ty", "timeAddition"));

        // when
        String payload = message.toString();

        //then
        assertEquals("#configuration\n" +
                     "$ts:Sun Jun 11 16:29:52 BRT 2017\n" +
                     "$ty:timeAddition\n" +
                     "@\n" +
                     "new time set\n" +
                     "@\n", payload);
    }
    @Test
    public void payloadConfigurationMessageWithoutControlParameter() {
        // given
        Message message = new Message("hw/kitchen",
                                        Message.MessageType.CONFIGURATION, new Message.MessageBody("new time set"));

        // when
        String payload = message.toString();

        //then
        assertEquals("#configuration\n" +
                     "@\n" +
                     "new time set\n" +
                     "@\n", payload);
    }

    @Test
    public void payloadConfigurationMessageWithoutBody() {
        // given
        Message message = new Message("hw/kitchen", Message.MessageType.CONFIGURATION, new Message.MessageBody(""));

        // when
        String payload = message.toString();

        //then
        assertEquals("#configuration\n" +
                     "@\n\n" +
                     "@\n", payload);
    }
}
