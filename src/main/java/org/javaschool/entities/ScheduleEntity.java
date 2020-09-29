package org.javaschool.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "schedule", schema = "sbb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntity {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "station_id")
    private Integer stationId;

    @Column(name = "train_id")
    private Integer trainId;

    @Column(name = "time")
    private String time;
}
