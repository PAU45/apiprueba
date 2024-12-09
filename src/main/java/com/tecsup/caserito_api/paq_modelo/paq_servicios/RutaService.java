package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Usuario;
import com.tecsup.caserito_api.paq_modelo.paq_daos.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RutaService {

    @Autowired
    private GeocodingService geocodingService;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private AuthService authService;

    @Value("${google.maps.api.key}")
    private String GOOGLE_MAPS_API_KEY;

    public Map<String, Object> obtenerRuta(Long restauranteId) {
        try {
            // Obtener el usuario autenticado
            Usuario usuario = authService.getAuthenticatedUser();

            // Obtener el restaurante por ID
            Restaurante restaurante = restauranteRepository.findById(restauranteId)
                    .orElseThrow(() -> new RuntimeException("Restaurante no encontrado con el id: " + restauranteId));

            // Obtener las coordenadas del usuario y del restaurante
            double latUsuario = usuario.getLatitud();
            double lngUsuario = usuario.getLongitud();
            double latRestaurante = restaurante.getLatitud();
            double lngRestaurante = restaurante.getLongitud();

            // Preparar respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("origin", Map.of("lat", latUsuario, "lng", lngUsuario));
            response.put("destination", Map.of("lat", latRestaurante, "lng", lngRestaurante));
            response.put("apiKey", GOOGLE_MAPS_API_KEY); // Puedes externalizar la clave si lo prefieres

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la ruta: " + e.getMessage());
        }
    }

}
