package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;

import java.util.List;

public interface RestauranteService {

    // Método para crear o actualizar un restaurante, autenticado por token
    Restaurante createOrUpdateRestaurante(Restaurante restaurante, String token);

    // Método para obtener todos los restaurantes
    List<Restaurante> getAllRestaurantes();

    // Método para obtener un restaurante específico por su ID
    Restaurante getRestauranteById(Long id);

    // Método para eliminar un restaurante
    void deleteRestaurante(Long id);
}
