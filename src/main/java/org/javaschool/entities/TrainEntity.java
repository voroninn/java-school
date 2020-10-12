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
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "train")
    private Set<ScheduleEntity> schedules;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "train_tickets",
            joinColumns = @JoinColumn(name = "train_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_id"))
    private Set<TicketEntity> tickets;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "track_id")
    private TrackEntity track;
}