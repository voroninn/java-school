package org.javaschool.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tracks", schema = "sbb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackEntity implements Serializable {

    @Id
    @Column(name = "id")
    private int id;

    @Transient
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "track")
    private Set<TrainEntity> trains;

    @Transient
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "track")
    private Set<MappingEntity> mappings;
}