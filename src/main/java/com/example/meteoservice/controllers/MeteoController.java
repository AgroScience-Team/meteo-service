package com.example.meteoservice.controllers;

import com.example.meteoservice.dao.entities.Meteo;
import com.example.meteoservice.dao.repositories.MeteoRepository;
import com.example.meteoservice.dto.Coordinate;
import com.example.meteoservice.dto.MeteoResponse;
import com.example.meteoservice.mappers.MeteoMapper;
import com.example.meteoservice.services.MeteoService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/vi/meteo")
public class MeteoController {
    private final MeteoService meteoService;
    private final MeteoMapper meteoMapper;
    private final MeteoRepository repository;

    @GetMapping
    public List<MeteoResponse> getAllFieldMeteo(@RequestParam Long fieldId) {
        return repository.findByFieldId(fieldId)
                .stream()
                .map(meteoMapper::meteoToMeteoResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{fieldId}")
    public MeteoResponse getFieldMeteo(@PathVariable Long fieldId) {

        Coordinate coordinate = meteoService.getFieldPoint(fieldId);
        Double latitude = coordinate.getLatitude();
        Double longitude = coordinate.getLongitude();


        JSONObject weatherJsonResponse = meteoService.getWeatherData(latitude, longitude);

        double humidity = weatherJsonResponse.getJSONObject("main").getDouble("humidity");
        double temperature = weatherJsonResponse.getJSONObject("main").getDouble("temp");
        double pressure = weatherJsonResponse.getJSONObject("main").getInt("pressure");

        LocalDateTime now = LocalDateTime.now();

        Meteo meteo = new Meteo();
        meteo.setFieldId(fieldId);
        meteo.setHumidity(humidity);
        meteo.setPressure(pressure);
        meteo.setLastUpdate(now);
        meteo.setTemperature(temperature);
        repository.save(meteo);

        MeteoResponse response = new MeteoResponse();
        response.setHumidity(humidity);
        response.setTemperature(temperature - 273.15);
        response.setPressure(pressure * 0.75);
        response.setFieldId(fieldId);
        response.setLastUpdate(now);

        return response;
    }
}
