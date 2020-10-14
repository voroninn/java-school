package org.javaschool.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "stations", schema = "sbb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_endpoint")
    private boolean isEndpoint;

    @Column(name = "is_breakpoint")
    private boolean isBreakpoint;

    @Transient
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "station")
    private Set<ScheduleEntity> schedules;

    @Transient
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "station")
    private Set<MappingEntity> mappings;
}