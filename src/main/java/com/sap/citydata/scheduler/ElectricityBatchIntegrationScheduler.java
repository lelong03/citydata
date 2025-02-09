package com.sap.citydata.scheduler;

import com.sap.citydata.config.RabbitMQConfig;
import com.sap.citydata.model.Electricity;
import com.sap.citydata.service.ElectricityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ElectricityBatchIntegrationScheduler extends BaseIntegrationScheduler {

    private static final Logger log = LoggerFactory.getLogger(ElectricityBatchIntegrationScheduler.class);

    private final RestTemplate restTemplate;
    private final AmqpTemplate amqpTemplate;

    // Use the simulation API that returns a list of Electricity records.
    private static final String SIMULATION_API_URL = "http://localhost:8080/api/simulate/electricity/batch";

    @Autowired
    public ElectricityBatchIntegrationScheduler(RestTemplate restTemplate,
                                                AmqpTemplate amqpTemplate) {
        this.restTemplate = restTemplate;
        this.amqpTemplate = amqpTemplate;
    }

    /**
     * This scheduled method runs every 5 seconds.
     */
    @Scheduled(fixedRate = 5000)
    public void scheduledIntegration() {
        executeIntegration();
    }

    @Override
    protected void fetchDataFromSource() {
        // Fetch a batch (list) of electricity records from the simulation API.
        ResponseEntity<List<Electricity>> response = restTemplate.exchange(
                SIMULATION_API_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Electricity>>() {}
        );
        List<Electricity> electricityList = response.getBody();
        if (electricityList != null && !electricityList.isEmpty()) {
            // Publish each record to the RabbitMQ queue.
            for (Electricity e : electricityList) {
                amqpTemplate.convertAndSend(RabbitMQConfig.ELECTRICITY_QUEUE, e);
            }
            log.info("Published {} electricity data records to the queue.", electricityList.size());
        } else {
            log.warn("No electricity data received from the simulation endpoint.");
        }
    }
}
