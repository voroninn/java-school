package org.javaschool.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private int number;

    @Column(name = "passenger_id")
    private Integer passengerId;

    @Column(name = "train_id")
    private Integer trainId;

    @Column(name = "coach_number")
    private Integer coachNumber;

    @Column(name = "seat_number")
    private Integer seatNumber;

    @Column(name = "departure_station")
    private String departureStation;

    @Column(name = "arrival_station")
    private String arrivalStation;

    @Column(name = "departure_time")
    private String departureTime;

    @Column(name = "arrival_time")
    private String arrivalTime;
}
