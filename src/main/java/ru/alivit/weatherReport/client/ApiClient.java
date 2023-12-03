package ru.alivit.weatherReport.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.alivit.weatherReport.model.dto.WeatherDTO;
import ru.alivit.weatherReport.service.WeatherService;

@Component
@EnableAsync
@RequiredArgsConstructor
public class ApiClient{

    private final WeatherService service;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.api.url}")
    private String API_URL;

    @Async
    @Scheduled(fixedRateString = "${spring.scheduled.fixed.rate}", initialDelayString = "${spring.scheduled.initial.delay}")
    public void fetchDataAndSave() {
        WeatherDTO weather = restTemplate.getForEntity(API_URL, WeatherDTO.class).getBody();
        service.create(weather);
    }
}
