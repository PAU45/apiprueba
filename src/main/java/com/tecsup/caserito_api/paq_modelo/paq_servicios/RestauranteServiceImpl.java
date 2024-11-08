package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import com.tecsup.caserito_api.paq_modelo.paq_daos.RestauranteRepository;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;
import com.tecsup.caserito_api.paq_modelo.paq_servicios.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteServiceImpl implements RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Override
    public Restaurante createOrUpdateRestaurante(Restaurante restaurante) {
        return restauranteRepository.save(restaurante); // Crea o actualiza un restaurante
    }

    @Override
    public List<Restaurante> getAllRestaurantes() {
        return restauranteRepository.findAll(); // Obtiene todos los restaurantes
    }

    @Override
    public Restaurante getRestauranteById(Long id) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);
        return restaurante.orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));
    }

    @Override
    public void deleteRestaurante(Long id) {
        restauranteRepository.deleteById(id); // Elimina un restaurante por su ID
    }
}
