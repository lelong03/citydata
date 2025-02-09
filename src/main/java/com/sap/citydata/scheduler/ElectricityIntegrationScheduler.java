//package com.sap.citydata.scheduler;
//
//import com.sap.citydata.model.Electricity;
//import com.sap.citydata.service.ElectricityService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//@Slf4j
//@Component
//public class ElectricityIntegrationScheduler extends BaseIntegrationScheduler {
//    private final ElectricityService electricityService;
//    private final RestTemplate restTemplate;
//
//    // URL of the simulated electricity integration API.
//    private static final String SIMULATION_API_URL = "http://localhost:8080/api/simulate/electricity";
//
//    public ElectricityIntegrationScheduler(ElectricityService electricityService, RestTemplate restTemplate) {
//        this.electricityService = electricityService;
//        this.restTemplate = restTemplate;
//    }
//
//    /**
//     * This scheduled method runs every 10 seconds.
//     */
//    @Scheduled(fixedRate = 10000)
//    public void scheduledIntegration() {
//        executeIntegration();
//    }
//
//    @Override
//    protected void fetchDataFromSource() {
//        Electricity electricity = restTemplate.getForObject(SIMULATION_API_URL, Electricity.class);
//        if (electricity != null) {
//            electricityService.create(electricity);
//            System.out.println("Integrated new electricity data: " + electricity);
//        }
//    }
//}
//
