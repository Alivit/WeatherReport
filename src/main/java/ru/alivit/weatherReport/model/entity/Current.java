package ru.alivit.weatherReport.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "currents")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Current{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "temp_c")
    private double tempC;

    @Column(name = "wind_mph")
    private double windMph;

    @Column(name = "pressure_mb")
    private double pressureMb;

    private double humidity;

    @Column(name = "last_updated", unique = true)
    private LocalDateTime lastUpdated;

    @OneToOne(cascade = CascadeType.ALL)
    private Condition condition;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Current current)) return false;
        return Double.compare(current.getTempC(), getTempC()) == 0 && Double.compare(current.getWindMph(), getWindMph()) == 0 && Double.compare(current.getPressureMb(), getPressureMb()) == 0 && Double.compare(current.getHumidity(), getHumidity()) == 0 && Objects.equals(getId(), current.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTempC(), getWindMph(), getPressureMb(), getHumidity());
    }
}
