package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleMapsService {

    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;

    private final RestTemplate restTemplate;

    public GoogleMapsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getGeocode(String address) {
        String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s", address, googleMapsApiKey);
        return restTemplate.getForObject(url, String.class);
    }
}
