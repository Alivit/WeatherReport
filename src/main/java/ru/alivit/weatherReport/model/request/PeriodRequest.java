package ru.alivit.weatherReport.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodRequest {

    @JsonFormat(pattern = "yyyy-MM-dd H:mm")
    @JsonProperty("from")
    LocalDateTime fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd H:mm")
    @JsonProperty("to")
    LocalDateTime toDate;
}
