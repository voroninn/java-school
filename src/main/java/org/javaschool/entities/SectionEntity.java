package org.javaschool.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "sections", schema = "sbb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "station_from_id")
    private StationEntity stationFrom;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "station_to_id")
    private StationEntity stationTo;

    @Column(name = "length")
    private double length;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "track_id")
    private TrackEntity track;
}