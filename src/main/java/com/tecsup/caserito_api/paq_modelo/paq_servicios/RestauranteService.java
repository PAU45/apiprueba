package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;

import java.util.List;

public interface RestauranteService {

    Restaurante createOrUpdateRestaurante(Restaurante restaurante);

    List<Restaurante> getAllRestaurantes();

    Restaurante getRestauranteById(Long id);

    void deleteRestaurante(Long id);
}
