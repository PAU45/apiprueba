package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class GeocodingService {

    @Value("${google.maps.api.key}")
    private String GOOGLE_MAPS_API_KEY;

    public double[] getCoordinates(String ubicacion) {
        try {
            String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                    ubicacion.replace(" ", "+") + "&key=" + GOOGLE_MAPS_API_KEY;

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            JSONObject jsonObject = new JSONObject(response);
            JSONObject location = jsonObject.getJSONArray("results")
                    .getJSONObject(0)
                    .getJSONObject("geometry")
                    .getJSONObject("location");

            double lat = location.getDouble("lat");
            double lng = location.getDouble("lng");

            return new double[]{lat, lng};
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener coordenadas: " + e.getMessage());
        }
    }

    public String getAddress(double lat, double lng) {
        try {
            String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" +
                    lat + "," + lng + "&key=" + GOOGLE_MAPS_API_KEY;

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            JSONObject jsonObject = new JSONObject(response);
            String address = jsonObject.getJSONArray("results")
                    .getJSONObject(0)
                    .getString("formatted_address");

            return address;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener dirección: " + e.getMessage());
        }
    }

    // Método para obtener la ruta
    public String getDirections(double latOrigen, double lngOrigen, double latDestino, double lngDestino) {
        try {
            // Construir la URL de la API de Directions
            String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + latOrigen + "," + lngOrigen +
                    "&destination=" + latDestino + "," + lngDestino +
                    "&key=" + GOOGLE_MAPS_API_KEY;

            // Crear un objeto RestTemplate para hacer la solicitud
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            // Procesar la respuesta de la API
            JSONObject jsonObject = new JSONObject(response);

            // Verificar si la respuesta contiene rutas
            if (jsonObject.getJSONArray("routes").length() == 0) {
                throw new RuntimeException("No se encontró ninguna ruta.");
            }

            // Obtener la primera ruta
            JSONObject route = jsonObject.getJSONArray("routes").getJSONObject(0);
            JSONObject legs = route.getJSONArray("legs").getJSONObject(0);
            String distance = legs.getJSONObject("distance").getString("text");
            String duration = legs.getJSONObject("duration").getString("text");

            // Devolver la distancia y la duración
            return "Distancia: " + distance + ", Duración: " + duration;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la ruta: " + e.getMessage());
        }
    }

}


