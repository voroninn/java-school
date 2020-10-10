package org.javaschool.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "passengers", schema = "sbb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "passport_number")
    private int passportNumber;

    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Transient
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "passenger")
    private Set<TicketEntity> tickets;
}