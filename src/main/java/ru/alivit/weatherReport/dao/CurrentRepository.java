package ru.alivit.weatherReport.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.alivit.weatherReport.model.entity.Current;

import java.time.LocalDateTime;

public interface CurrentRepository extends JpaRepository<Current, Long> {

    @Query("SELECT AVG(c.tempC) FROM Current c WHERE c.lastUpdated >= :fromDate AND c.lastUpdated <= :toDate")
    Double getAverageTemperatureByPeriod(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );
}
