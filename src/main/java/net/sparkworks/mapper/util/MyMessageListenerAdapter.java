package net.sparkworks.mapper.util;

import net.sparkworks.mapper.listener.Receiver;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class MyMessageListenerAdapter implements MessageListener {
    private final Receiver handler;

    public MyMessageListenerAdapter(Receiver receiver) {
        handler = receiver;
    }

    @Override
    public void onMessage(Message message) {
        handler.handleMessage(message);
    }

}
