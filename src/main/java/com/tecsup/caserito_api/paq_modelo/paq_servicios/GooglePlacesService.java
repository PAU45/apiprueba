package com.tecsup.caserito_api.paq_modelo.paq_servicios;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GooglePlacesService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    private static final String GOOGLE_PLACES_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
    private static final String GOOGLE_DIRECTIONS_URL = "https://maps.googleapis.com/maps/api/directions/json";

    // Método para obtener restaurantes cercanos
    public String getNearbyRestaurants(double latitude, double longitude, int radius) {
        RestTemplate restTemplate = new RestTemplate();

        String url = UriComponentsBuilder.fromHttpUrl(GOOGLE_PLACES_URL)
                .queryParam("location", latitude + "," + longitude)
                .queryParam("radius", radius)
                .queryParam("type", "restaurant")
                .queryParam("key", apiKey)
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }

    // Método para obtener direcciones desde la ubicación del usuario hasta el restaurante
    public String getDirections(double startLat, double startLng, double endLat, double endLng) {
        RestTemplate restTemplate = new RestTemplate();

        String url = UriComponentsBuilder.fromHttpUrl(GOOGLE_DIRECTIONS_URL)
                .queryParam("origin", startLat + "," + startLng)  // Ubicación del usuario
                .queryParam("destination", endLat + "," + endLng)  // Ubicación del restaurante
                .queryParam("mode", "driving")  // Modo de transporte (puede ser driving, walking, bicycling, transit)
                .queryParam("key", apiKey)
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }
}
