package com.example.meteoservice.services;

import com.example.meteoservice.dao.entities.Meteo;
import com.example.meteoservice.dao.repositories.MeteoRepository;
import com.example.meteoservice.dto.CoordinateWithField;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@EnableAsync
@Service
@RequiredArgsConstructor
public class MeteoService {
    private final MeteoRepository meteoRepository;
    private final RestTemplate restTemplate;
    private final String weatherApiUrl = "https://api.openweathermap.org/data/2.5/forecast";
    private final String weatherApiKey = "ebf72155b2561f883ea6d0096dd8e402";
    private final String fieldServiceApiUrl = "http://fields-back:8002/api/v1/fields/meteo/all-coordinates";
// megamarket-back
    public JSONObject getWeatherData(double lat, double lon) {
        String url = weatherApiUrl + "?lat=" + lat + "&lon=" + lon + "&appid=" + weatherApiKey;
        String jsonResponse = restTemplate.getForObject(url, String.class);
        return new JSONObject(jsonResponse);
    }

//    @Async
//    @Scheduled(fixedRate = 1800000)
//    public void getFieldPoint() {
//        String jsonResponse = restTemplate.getForObject(fieldServiceApiUrl, String.class);
//        JSONArray jsonArray = new JSONArray(jsonResponse);
//        List<CoordinateWithField> coordinates = new ArrayList<>();
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject coordinate = jsonArray.getJSONObject(i);
//            double longitude = coordinate.getDouble("longitude");
//            double latitude = coordinate.getDouble("latitude");
//            Long fieldId = coordinate.getLong("id");
//            coordinates.add(new CoordinateWithField(longitude, latitude, fieldId));
//        }
//
//        for (var obj : coordinates) {
//            Double latitude = obj.getLatitude();
//            Double longitude = obj.getLongitude();
//            Long fieldId = obj.getId();
//
//            JSONObject weatherJsonResponse = getWeatherData(latitude, longitude);
//            double humidity = weatherJsonResponse.getJSONObject("main").getDouble("humidity");
//            double temperature = weatherJsonResponse.getJSONObject("main").getDouble("temp");
//            double pressure = weatherJsonResponse.getJSONObject("main").getInt("pressure");
//
//            LocalDateTime now = LocalDateTime.now();
//            now = now.plusHours(3);
//
//            Meteo meteo = new Meteo();
//            meteo.setFieldId(fieldId);
//            meteo.setHumidity(humidity);
//            meteo.setPressure(pressure * 0.75);
//            meteo.setLastUpdate(now);
//            meteo.setTemperature(temperature - 273.15);
//            meteoRepository.save(meteo);
//        }
//    }

    public List<Meteo> getFieldMeteo(Long fieldId) {
        var meteoHistory = meteoRepository.findByFieldId(fieldId);
        return meteoHistory.stream()
                .sorted(Comparator.comparing(Meteo::getLastUpdate))
                .toList();
    }

    @Scheduled(fixedRate = 86400000)
    @Transactional
    public void updateMeteoData() {
        String jsonResponse = restTemplate.getForObject(fieldServiceApiUrl, String.class);
        JSONArray jsonArray = new JSONArray(jsonResponse);
        List<CoordinateWithField> coordinates = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject coordinate = jsonArray.getJSONObject(i);
            double longitude = coordinate.getDouble("longitude");
            double latitude = coordinate.getDouble("latitude");
            Long fieldId = coordinate.getLong("id");
            coordinates.add(new CoordinateWithField(longitude, latitude, fieldId));
        }

        for (var obj : coordinates) {
            Double latitude = obj.getLatitude();
            Double longitude = obj.getLongitude();
            Long fieldId = obj.getId();
            meteoRepository.deleteByFieldId(fieldId);

            JSONObject weatherJsonResponse = getWeatherData(latitude, longitude);
            JSONArray forecast = weatherJsonResponse.getJSONArray("list");
            LocalDate date = LocalDate.now(ZoneId.of("Europe/Moscow"));
            int cnt = 0;
            for (int i = 0; i < 5; i++) {
                JSONObject fc = forecast.getJSONObject(i);
                double humidity = fc.getJSONObject("main").getDouble("humidity");
                double pressure = fc.getJSONObject("main").getDouble("pressure");
                double temperature = fc.getJSONObject("main").getDouble("temp");
                date = date.plusDays(cnt);
                Meteo meteo = new Meteo();
                meteo.setFieldId(fieldId);
                meteo.setHumidity(humidity);
                meteo.setPressure(pressure * 0.75);
                meteo.setLastUpdate(date);
                meteo.setTemperature(temperature - 273.15);
                meteoRepository.save(meteo);
                cnt = 1;
            }
        }
    }
}
