package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.tecsup.caserito_api.paq_modelo.paq_daos.RestauranteRepository;
import com.tecsup.caserito_api.paq_modelo.paq_daos.UsuarioRepository;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Usuario;
import com.tecsup.caserito_api.paq_util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteServiceImpl implements RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Restaurante createOrUpdateRestaurante(Restaurante restaurante) {
        return null;
    }

    @Override
    public Restaurante createOrUpdateRestaurante(Restaurante restaurante, String token) {
        // Validar el token
        DecodedJWT decodedJWT = jwtUtils.validateToken(token);
        String username = jwtUtils.extractUsername(decodedJWT);

        // Obtener el usuario a partir del token (username)
        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Asignar el usuario al restaurante
        restaurante.setUsuario(usuario);

        // Procede a crear o actualizar el restaurante
        return saveOrUpdateRestaurante(restaurante);
    }

    @Override
    public List<Restaurante> getAllRestaurantes() {
        return restauranteRepository.findAll(); // Obtiene todos los restaurantes
    }

    @Override
    public Restaurante getRestauranteById(Long id) {
        // Usamos Optional para manejar el caso cuando no se encuentra el restaurante
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));
    }

    @Override
    public void deleteRestaurante(Long id) {
        restauranteRepository.deleteById(id); // Elimina un restaurante por su ID
    }

    // Método para guardar o actualizar el restaurante
    private Restaurante saveOrUpdateRestaurante(Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }
}
