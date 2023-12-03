package ru.alivit.weatherReport.service.impl;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.alivit.weatherReport.dao.CurrentRepository;
import ru.alivit.weatherReport.dao.WeatherRepository;
import ru.alivit.weatherReport.exception.NotFoundException;
import ru.alivit.weatherReport.exception.ServerErrorException;
import ru.alivit.weatherReport.logging.annotation.Logging;
import ru.alivit.weatherReport.model.dto.WeatherDTO;
import ru.alivit.weatherReport.model.entity.Weather;
import ru.alivit.weatherReport.model.request.PeriodRequest;
import ru.alivit.weatherReport.model.response.AvgTempResponse;
import ru.alivit.weatherReport.service.WeatherService;

@Logging
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private final ModelMapper modelMapper = new ModelMapper();
    private final WeatherRepository weatherRepository;
    private final CurrentRepository currentRepository;

    @Override
    public void create(WeatherDTO weatherDTO) {
        Weather weather = modelMapper.map(weatherDTO, Weather.class);

        try {
            weatherRepository.save(weather);
        }catch (DataIntegrityViolationException ex){
            System.out.println("Error: Duplicate record. A record with such a date already exists!!!");
        }

    }

    @Override
    public WeatherDTO findCurrentWeather() {
        try {
            Weather weather = weatherRepository.findLatestWeather();

            if (weather == null) throw new NoResultException();

            return modelMapper.map(weather, WeatherDTO.class);
        }catch (NoResultException ex){
            throw new NotFoundException("Error: current weather not found");
        }catch (Exception ex){
            throw new ServerErrorException("Error: An error occurred during the search: " + ex);
        }

    }

    @Override
    public AvgTempResponse getAverageTemperature(PeriodRequest period) {
        try {
            double avgTemp = currentRepository
                    .getAverageTemperatureByPeriod(period.getFromDate(), period.getToDate());

            return new AvgTempResponse(avgTemp);
        }catch (NullPointerException ex){
            throw new NotFoundException("Error: temperature not found");
        }catch (Exception ex){
            throw new ServerErrorException("Error: An error occurred during the search: " + ex);
        }

    }

}
