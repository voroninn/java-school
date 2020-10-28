package org.javaschool.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "mappings", schema = "sbb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MappingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id")
    private StationEntity station;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "track_id")
    private TrackEntity track;

    @Column(name = "station_order")
    private int stationOrder;

    public MappingEntity(StationEntity station, TrackEntity track, int stationOrder) {
        this.station = station;
        this.track = track;
        this.stationOrder = stationOrder;
    }
}
