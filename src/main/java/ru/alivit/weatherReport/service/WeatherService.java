package ru.alivit.weatherReport.service;

import ru.alivit.weatherReport.model.dto.WeatherDTO;
import ru.alivit.weatherReport.model.request.PeriodRequest;
import ru.alivit.weatherReport.model.response.AvgTempResponse;

public interface WeatherService {

    public void create(WeatherDTO weatherDTO);

    public WeatherDTO findCurrentWeather();

    public AvgTempResponse getAverageTemperature(PeriodRequest periodRequest);
}
