package com.example.meteoservice.controllers;

import com.example.meteoservice.dto.MeteoResponse;
import com.example.meteoservice.mappers.MeteoMapper;
import com.example.meteoservice.services.MeteoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/vi/meteo")
public class MeteoController {
    private final MeteoService meteoService;
    private final MeteoMapper meteoMapper;

    @GetMapping
    public MeteoResponse getFieldMeteo(@RequestParam Long id, @RequestParam String polygon) {
        return meteoMapper.meteoToMeteoResponse(meteoService.getMeteo(id, polygon));
    }
}
