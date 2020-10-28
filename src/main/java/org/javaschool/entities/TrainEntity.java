package org.javaschool.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "trains", schema = "sbb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainEntity implements Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Min(value = 10)
    @Max(value = 50)
    @Column(name = "capacity")
    private int capacity;

    @Transient
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "train")
    private Set<ScheduleEntity> schedules;

    @ToString.Exclude
    @Transient
    @ManyToMany(mappedBy = "trains")
    private Set<TicketEntity> tickets;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "track_id")
    private TrackEntity track;
}