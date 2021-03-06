package org.javaschool.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "schedules", schema = "sbb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntity implements Serializable {

    public ScheduleEntity(StationEntity station, TrainEntity train) {
        this.station = station;
        this.train = train;
    }

    public ScheduleEntity(StationEntity station, TrainEntity train, boolean direction) {
        this.station = station;
        this.train = train;
        this.direction = direction;
    }

    public ScheduleEntity(StationEntity station, TrainEntity train, String trainStatus, boolean direction) {
        this.station = station;
        this.train = train;
        this.direction = direction;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "station_id")
    private StationEntity station;

    @Column(name = "train_status")
    private String trainStatus;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "train_id")
    private TrainEntity train;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "arrival_time")
    private Date arrivalTime;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "departure_time")
    private Date departureTime;

    @Column(name = "direction")
    private boolean direction;
}