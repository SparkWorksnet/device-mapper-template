package net.sparkworks.mapper.listener;

import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

@Service
public class Receiver {
    /**
     * Logger.
     */
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(Receiver.class);

    public Object handleMessage(Object obj) {
        if (obj instanceof Message) {
            final String message = new String(((Message) obj).getBody());
            LOGGER.info("Received '" + message + "'");
        }
        return null;
    }
}
