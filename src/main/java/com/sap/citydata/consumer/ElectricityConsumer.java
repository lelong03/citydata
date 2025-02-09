package com.sap.citydata.consumer;

import com.sap.citydata.config.RabbitMQConfig;
import com.sap.citydata.model.Electricity;
import com.sap.citydata.service.ElectricityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ElectricityConsumer {

    private static final Logger log = LoggerFactory.getLogger(ElectricityConsumer.class);

    private final ElectricityService electricityService;

    // Thread-safe buffer for incoming electricity records
    private final List<Electricity> buffer = Collections.synchronizedList(new ArrayList<>());
    // A threshold to flush the buffer
    private static final int BATCH_SIZE = 20;

    @Autowired
    public ElectricityConsumer(ElectricityService electricityService) {
        this.electricityService = electricityService;
    }

    @RabbitListener(queues = RabbitMQConfig.ELECTRICITY_QUEUE)
    public void receiveElectricityData(Electricity electricity) {
        buffer.add(electricity);
        log.info("Buffered electricity data: {}", electricity);

        if (buffer.size() >= BATCH_SIZE) {
            flushBuffer();
        }
    }

    // This method can also be scheduled to flush every few seconds if needed.
    public synchronized void flushBuffer() {
        if (!buffer.isEmpty()) {
            List<Electricity> batch = new ArrayList<>(buffer);
            buffer.clear();
            try {
                electricityService.createAll(batch); // Create a new service method to handle bulk inserts
                log.info("Flushed {} electricity records to the database.", batch.size());
            } catch (Exception e) {
                log.error("Error flushing electricity data: {}", e.getMessage(), e);
                // Optionally, add the batch back to the buffer or handle the error appropriately.
            }
        }
    }
}
