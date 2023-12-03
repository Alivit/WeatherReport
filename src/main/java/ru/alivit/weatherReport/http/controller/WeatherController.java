package ru.alivit.weatherReport.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alivit.weatherReport.logging.annotation.Logging;
import ru.alivit.weatherReport.model.dto.WeatherDTO;
import ru.alivit.weatherReport.model.request.PeriodRequest;
import ru.alivit.weatherReport.model.response.AvgTempResponse;
import ru.alivit.weatherReport.service.WeatherService;

@Logging
@RestController
@RequestMapping("/api/v1/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService service;

    @GetMapping("/current")
    public ResponseEntity<WeatherDTO> getCurrentWeather(){

        return new ResponseEntity<>(
                service.findCurrentWeather(),
                HttpStatus.FOUND
        );
    }

    @GetMapping("/period/avg-temp")
    public ResponseEntity<AvgTempResponse> getAverageTemperature(@RequestBody PeriodRequest periodRequest){

        return new ResponseEntity<>(
                service.getAverageTemperature(periodRequest),
                HttpStatus.FOUND
        );
    }
}
