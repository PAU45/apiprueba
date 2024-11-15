package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class GeocodingService {

    @Value("${google.maps.api.key}")
    private String googleApiKey;

    public double[] getCoordinates(String address) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + googleApiKey;
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        JSONObject json = new JSONObject(response);
        if ("OK".equals(json.getString("status"))) {
            JSONObject location = json.getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONObject("geometry")
                    .getJSONObject("location");
            double lat = location.getDouble("lat");
            double lng = location.getDouble("lng");
            return new double[]{lat, lng};
        } else {
            throw new RuntimeException("No se pudieron obtener las coordenadas para la dirección: " + address);
        }
    }
}
