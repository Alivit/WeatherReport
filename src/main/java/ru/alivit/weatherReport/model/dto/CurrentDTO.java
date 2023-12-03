package ru.alivit.weatherReport.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentDTO {

    @JsonProperty("temp_c")
    private double tempC;

    @JsonProperty("wind_mph")
    private double windMph;

    @JsonProperty("pressure_mb")
    private double pressureMb;

    @JsonProperty("humidity")
    private double humidity;

    @JsonFormat(pattern = "yyyy-MM-dd H:mm")
    @JsonProperty("last_updated")
    private LocalDateTime lastUpdated;

    @JsonProperty("condition")
    private ConditionDTO condition;
}
