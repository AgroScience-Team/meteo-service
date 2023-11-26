package com.example.meteoservice.controllers;

import com.example.meteoservice.dao.entities.Meteo;
import com.example.meteoservice.dao.repositories.MeteoRepository;
import com.example.meteoservice.dto.CoordinateWithField;
import com.example.meteoservice.dto.MeteoResponse;
import com.example.meteoservice.mappers.MeteoMapper;
import com.example.meteoservice.services.MeteoService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/meteo")
public class MeteoController {
    private final MeteoService meteoService;
    private final MeteoMapper meteoMapper;
    @GetMapping("/{fieldId}")
    public List<MeteoResponse> getMeteo(@PathVariable Long fieldId) {
        return meteoService.getFieldMeteo(fieldId).stream()
                .map(meteoMapper::meteoToMeteoResponse)
                .toList();
    }
}
