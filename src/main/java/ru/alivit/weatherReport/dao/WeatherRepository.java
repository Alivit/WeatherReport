package ru.alivit.weatherReport.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.alivit.weatherReport.model.entity.Weather;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    @Query("SELECT w FROM Weather w " +
            "JOIN FETCH w.current c " +
            "JOIN FETCH w.location l " +
            "JOIN FETCH c.condition co " +
            "ORDER BY c.lastUpdated DESC LIMIT 1")
    Weather findLatestWeather();

}
