package org.javaschool.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "trains", schema = "sbb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "capacity")
    private int capacity;

    @Transient
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "train")
    private Set<ScheduleEntity> schedules;
}