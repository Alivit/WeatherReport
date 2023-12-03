package ru.alivit.weatherReport.http.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.alivit.weatherReport.model.dto.WeatherDTO;
import ru.alivit.weatherReport.model.request.PeriodRequest;
import ru.alivit.weatherReport.model.response.AvgTempResponse;
import ru.alivit.weatherReport.service.impl.WeatherServiceImpl;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherControllerTest {

    @Mock
    private WeatherServiceImpl service;

    @InjectMocks
    private WeatherController controller;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "https://api.weatherapi.com/v1/current.json?key=4418a8f90af74bf5a0d154951233006&q=Minsk&aqi=no";


    @Nested
    class GetCurrentWeather{

        @Test
        void testGetCurrentWeather() {
            WeatherDTO mockedWeatherDTO = restTemplate.getForEntity(API_URL, WeatherDTO.class).getBody();
            when(service.findCurrentWeather()).thenReturn(mockedWeatherDTO);

            ResponseEntity<WeatherDTO> response = controller.getCurrentWeather();

            assertEquals(HttpStatus.FOUND, response.getStatusCode());
            assertEquals(mockedWeatherDTO, response.getBody());

            verify(service, times(1)).findCurrentWeather();
        }

    }


    @Nested
    class testGetAverageTemperature{

        PeriodRequest periodRequest;
        AvgTempResponse expectedResponse;

        @BeforeEach
        public void init(){
            periodRequest = new PeriodRequest(LocalDateTime.now().minusDays(7), LocalDateTime.now());
            expectedResponse = new AvgTempResponse(-5);
        }

        @Test
        void testGetAverageTemperature_ShouldReturnResult() {

            when(service.getAverageTemperature(periodRequest)).thenReturn(expectedResponse);

            ResponseEntity<AvgTempResponse> response = controller.getAverageTemperature(periodRequest);

            assertEquals(HttpStatus.FOUND, response.getStatusCode());
            assertEquals(expectedResponse, response.getBody());

            verify(service, times(1)).getAverageTemperature(periodRequest);
        }

    }

}