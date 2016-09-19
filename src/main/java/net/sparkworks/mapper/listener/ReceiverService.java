package net.sparkworks.mapper.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class ReceiverService implements MessageListener {
    /**
     * Logger.
     */
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ReceiverService.class);
    
    @Override
    public void onMessage(Message message) {
        String msg = new String(message.getBody());
        LOGGER.info("Received '" + msg + "'");
    }
    
}
