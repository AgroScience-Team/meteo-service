package com.example.meteoservice.dao.repositories;

import com.example.meteoservice.dao.entities.Meteo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeteoRepository extends JpaRepository<Meteo, Long> {
    Meteo findByFieldId(Long fieldId);
}
