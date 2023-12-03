package ru.alivit.weatherReport.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "locations")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String region;
    private String country;
    private double lat;
    private double lon;

    @Column(name = "local_time")
    private LocalDateTime localTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location location)) return false;
        return Double.compare(location.getLat(), getLat()) == 0 && Double.compare(location.getLon(), getLon()) == 0 && Objects.equals(getId(), location.getId()) && Objects.equals(getName(), location.getName()) && Objects.equals(getRegion(), location.getRegion()) && Objects.equals(getCountry(), location.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getRegion(), getCountry(), getLat(), getLon());
    }
}
