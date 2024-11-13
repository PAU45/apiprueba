package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;

import java.util.List;

public interface RestauranteService {

    Restaurante createOrUpdateRestaurante(Restaurante restaurante);

    Restaurante createOrUpdateRestaurante(Restaurante restaurante, String token);

    List<Restaurante> getAllRestaurantes();

    Restaurante getRestauranteById(Long id);

    void deleteRestaurante(Long id);
}
