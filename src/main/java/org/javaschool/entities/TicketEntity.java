package org.javaschool.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tickets", schema = "sbb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketEntity {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "number")
    private String number;

    @ToString.Exclude
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "passenger_id")
    private PassengerEntity passenger;

    @Transient
    @ManyToMany(mappedBy = "tickets")
    private Set<TrainEntity> trains;

    @Column(name = "departure_station")
    private String departureStation;

    @Column(name = "arrival_station")
    private String arrivalStation;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "departure_time")
    private Date departureTime;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "arrival_time")
    private Date arrivalTime;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "date")
    private Date date;

    @Column(name = "price")
    private double price;
}