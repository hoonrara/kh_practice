package com.example.kh.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class WeatherService {


    @Value("${weather.api.key}")
    private String weatherKey;

    public String getMoodByCity(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                "&appid=" + weatherKey + "&lang=kr";

        RestTemplate rest = new RestTemplate();
        Map<String, Object> response = rest.getForObject(url, Map.class);

        String main = ((Map<String, Object>) ((java.util.List<Object>) response.get("weather")).get(0)).get("main").toString();

        return switch (main) {
            case "Rain" -> "우울";
            case "Clear" -> "행복";
            case "Clouds" -> "지루함";
            default -> "무난";
        };
    }
}