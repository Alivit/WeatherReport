package ru.alivit.weatherReport.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.client.RestTemplate;
import ru.alivit.weatherReport.dao.CurrentRepository;
import ru.alivit.weatherReport.dao.WeatherRepository;
import ru.alivit.weatherReport.exception.NotFoundException;
import ru.alivit.weatherReport.exception.ServerErrorException;
import ru.alivit.weatherReport.model.dto.WeatherDTO;
import ru.alivit.weatherReport.model.entity.Weather;
import ru.alivit.weatherReport.model.request.PeriodRequest;
import ru.alivit.weatherReport.model.response.AvgTempResponse;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {

    @Mock
    private CurrentRepository currentRepository;

    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private WeatherServiceImpl service;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ModelMapper modelMapper = new ModelMapper();
    private final String API_URL = "https://api.weatherapi.com/v1/current.json?key=4418a8f90af74bf5a0d154951233006&q=Minsk&aqi=no";



    @Nested
    class createTest{

        @Test
        void create_ShouldNotThrowException_WhenSaveSuccessful() {
            WeatherDTO weatherDTO = restTemplate.getForEntity(API_URL, WeatherDTO.class).getBody();
            Weather weather = modelMapper.map(weatherDTO, Weather.class);

            when(weatherRepository.save(any(Weather.class))).thenReturn(weather);

            assertDoesNotThrow(() -> service.create(weatherDTO));

            verify(weatherRepository, times(1)).save(any(Weather.class));
        }

        @Test
        void create_ShouldPrintErrorMessage_WhenDuplicateRecord() {
            WeatherDTO weatherDTO = restTemplate.getForEntity(API_URL, WeatherDTO.class).getBody();

            when(weatherRepository.save(any(Weather.class))).thenThrow(new DataIntegrityViolationException("Duplicate record"));

            assertDoesNotThrow(() -> service.create(weatherDTO));

            verify(weatherRepository, times(1)).save(any(Weather.class));
        }
    }

    @Nested
    class findCurrentWeatherTest {

        @Test
        void findCurrentWeather_ShouldReturnWeatherDTO_WhenWeatherExists() {

            WeatherDTO mockWeather = restTemplate.getForEntity(API_URL, WeatherDTO.class).getBody();
            Weather weather = modelMapper.map(mockWeather, Weather.class);

            when(weatherRepository.findLatestWeather()).thenReturn(weather);

            WeatherDTO result = service.findCurrentWeather();

            assertNotNull(result);
        }

        @Test
        void findCurrentWeather_ShouldThrowNotFoundException_WhenNoWeatherFound() {
            when(weatherRepository.findLatestWeather()).thenReturn(null);

            NotFoundException exception = assertThrows(NotFoundException.class,
                    () -> service.findCurrentWeather());

            assertEquals("Error: current weather not found", exception.getMessage());
        }

        @Test
        void findCurrentWeather_ShouldThrowServerErrorException_WhenErrorOccurs() {
            when(weatherRepository.findLatestWeather()).thenThrow(new RuntimeException("Some error"));

            ServerErrorException exception = assertThrows(ServerErrorException.class,
                    () -> service.findCurrentWeather());

            assertEquals("Error: An error occurred during the search: java.lang.RuntimeException: Some error", exception.getMessage());
        }
    }

    @Nested
    class getAverageTemperatureTest {

        LocalDateTime fromDate;
        LocalDateTime toDate;

        @BeforeEach
        public void init(){
            fromDate = LocalDateTime.now().minusDays(7);
            toDate = LocalDateTime.now();
        }

        @Test
        void getAverageTemperature_ShouldReturnAvgTemp_WhenValidPeriod() {
            double expectedAvgTemp = -5.0;

            when(currentRepository.getAverageTemperatureByPeriod(fromDate, toDate)).thenReturn(expectedAvgTemp);

            AvgTempResponse result = service.getAverageTemperature(new PeriodRequest(fromDate, toDate));

            assertEquals(expectedAvgTemp, result.getAvgTemp());
            verify(currentRepository, times(1)).getAverageTemperatureByPeriod(fromDate, toDate);
        }

        @Test
        void getAverageTemperature_ShouldThrowNotFoundException_WhenTemperatureNotFound() {

            when(currentRepository.getAverageTemperatureByPeriod(fromDate, toDate)).thenReturn(null);

            // Act & Assert
            NotFoundException exception = assertThrows(NotFoundException.class,
                    () -> service.getAverageTemperature(new PeriodRequest(fromDate, toDate)));

            assertEquals("Error: temperature not found", exception.getMessage());
            verify(currentRepository, times(1)).getAverageTemperatureByPeriod(fromDate, toDate);
        }

        @Test
        void getAverageTemperature_ShouldThrowServerErrorException_WhenExceptionOccurs() {

            when(currentRepository.getAverageTemperatureByPeriod(fromDate, toDate)).thenThrow(new RuntimeException("Some error"));

            ServerErrorException exception = assertThrows(ServerErrorException.class,
                    () -> service.getAverageTemperature(new PeriodRequest(fromDate, toDate)));

            assertEquals("Error: An error occurred during the search: java.lang.RuntimeException: Some error", exception.getMessage());
            verify(currentRepository, times(1)).getAverageTemperatureByPeriod(fromDate, toDate);
        }
    }

}