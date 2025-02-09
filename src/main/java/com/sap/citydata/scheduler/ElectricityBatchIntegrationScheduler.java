package com.sap.citydata.scheduler;

import com.sap.citydata.model.Electricity;
import com.sap.citydata.service.ElectricityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final ElectricityService electricityService;
    private final RestTemplate restTemplate;

    // Use the same simulation API for this example, or a different one if needed.
    private static final String SIMULATION_API_URL = "http://localhost:8080/api/simulate/electricity/batch";

    public ElectricityBatchIntegrationScheduler(ElectricityService electricityService, RestTemplate restTemplate) {
        this.electricityService = electricityService;
        this.restTemplate = restTemplate;
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
        ResponseEntity<List<Electricity>> response = restTemplate.exchange(
                SIMULATION_API_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Electricity>>() {}
        );
        List<Electricity> electricityList = response.getBody();
        if (electricityList != null && !electricityList.isEmpty()) {
            for (Electricity e : electricityList) {
                electricityService.create(e);
            }
            log.info("Batch Integrated {} new electricity data records.", electricityList.size());
        } else {
            log.warn("No electricity data received from the batch simulation endpoint.");
        }
    }
}
