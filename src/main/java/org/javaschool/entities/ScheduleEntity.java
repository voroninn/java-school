package org.javaschool.entities;

import javax.persistence.*;

@Entity
@Table(name = "schedule", schema = "sbb")
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

    public ScheduleEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
