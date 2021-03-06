package net.sparkworks.mapper.util;

import net.sparkworks.mapper.service.SenderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MessageSimulator {
    /**
     * LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(MessageSimulator.class);

    @Autowired
    SenderService senderService;

    @Scheduled(fixedDelay = 10000)
    public void sendMeasurement() {
        LOGGER.info("Sending a new dummy message...");
        senderService.sendMeasurement("test/1", Math.random(), System.currentTimeMillis());
    }
}
