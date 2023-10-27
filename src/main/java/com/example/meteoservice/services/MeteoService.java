package com.example.meteoservice.services;

import com.example.meteoservice.dao.entities.Meteo;
import com.example.meteoservice.dao.repositories.MeteoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeteoService {
    private final MeteoRepository meteoRepository;

    public Meteo getMeteo(Long fieldId, String polygon) {
        return meteoRepository.findByFieldId(fieldId);
    }
}
