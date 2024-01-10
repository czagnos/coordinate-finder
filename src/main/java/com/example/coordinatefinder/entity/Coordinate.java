package com.example.coordinatefinder.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "coordinate")
public class Coordinate {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"

    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Basic
    @Column(name = "latitude")
    private String latitude;

    @Basic
    @Column(name = "longitude")
    private String longitude;


    @Basic
    @Column(name = "radius")
    private String radius;

    @Lob
    @Column(name = "result", columnDefinition = "CLOB")
    private String result;

}

