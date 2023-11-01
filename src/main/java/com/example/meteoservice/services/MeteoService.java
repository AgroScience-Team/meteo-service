package com.example.meteoservice.services;

import com.example.meteoservice.dao.repositories.MeteoRepository;
import com.example.meteoservice.dto.Coordinate;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MeteoService {
    private final MeteoRepository meteoRepository;
    private final RestTemplate restTemplate;
    private final String weatherApiUrl = "https://api.openweathermap.org/data/2.5/weather";
    private final String weatherApiKey = "ebf72155b2561f883ea6d0096dd8e402";

    private final String fieldServiceApiUrl = "http://crops-back:8002/api/v1/fields?fieldId=";
// megamarket-back
    public JSONObject getWeatherData(double lat, double lon) {
        String url = weatherApiUrl + "?lat=" + lat + "&lon=" + lon + "&appid=" + weatherApiKey;
        String jsonResponse = restTemplate.getForObject(url, String.class);
        return new JSONObject(jsonResponse);
    }

    public Coordinate getFieldPoint(Long fieldId) {
        String url = fieldServiceApiUrl + fieldId;
        String jsonResponse = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray jsonArray = jsonObject.getJSONObject("geom").getJSONArray("coordinates");
        JSONObject firstCoordinate = jsonArray.getJSONObject(0);
        double longitude = firstCoordinate.getDouble("longitude");
        double latitude = firstCoordinate.getDouble("latitude");
        return new Coordinate(longitude, latitude);
    }
}
