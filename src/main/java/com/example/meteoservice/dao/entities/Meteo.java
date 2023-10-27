package com.example.meteoservice.dao.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table
@Data
public class Meteo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "field_id", nullable = false)
    private Long fieldId;

    @Column(name = "last_update")
    private Date lastUpdate;
}
