package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.tecsup.caserito_api.paq_modelo.paq_daos.RestauranteRepository;
import com.tecsup.caserito_api.paq_modelo.paq_daos.UsuarioRepository;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Usuario;
import com.tecsup.caserito_api.paq_util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import java.util.List;

@Service
public class RestauranteServiceImpl implements RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${google.maps.api.key}")
    private String GOOGLE_MAPS_API_KEY;

    @Override
    public Restaurante createOrUpdateRestaurante(Restaurante restaurante) {
        // Obtener el usuario desde la autenticación
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // El nombre de usuario es el 'principal'

        // Obtener el usuario desde el repositorio usando el username
        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        System.out.println("El usario es:" + usuario);
        // Asignar el usuario al restaurante
        restaurante.setUsuario(usuario);

        // Obtener las coordenadas en base a la ubicación proporcionada
        double[] coordinates = getCoordinates(restaurante.getUbicacion());
        restaurante.setLatitud(coordinates[0]);
        restaurante.setLongitud(coordinates[1]);

        // Guardar o actualizar el restaurante
        return saveOrUpdateRestaurante(restaurante);
    }


    @Override
    public List<Restaurante> getAllRestaurantes() {
        return restauranteRepository.findAll();
    }

    @Override
    public Restaurante getRestauranteById(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));
    }

    @Override
    public void deleteRestaurante(Long id) {
        restauranteRepository.deleteById(id);
    }

    private Restaurante saveOrUpdateRestaurante(Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }

    // Método para obtener las coordenadas (latitud y longitud) a partir de la ubicación
    private double[] getCoordinates(String ubicacion) {
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
}
