package com.example.meteoservice.dao.repositories;

import com.example.meteoservice.dao.entities.Meteo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeteoRepository extends JpaRepository<Meteo, Long> {
    List<Meteo> findByFieldId(Long fieldId);

    void deleteByFieldId(Long fieldId);
}
