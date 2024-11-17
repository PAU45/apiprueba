package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestauranteService {

    Restaurante createRestaurante(Restaurante restaurante);

    List<Restaurante> getAllRestaurantes();

    Restaurante getRestauranteById(Long id);


    ResponseEntity<?> deleteRestaurante(Long id);


    List<Restaurante> getRestaurantesPorUsuario();

    Restaurante updateRestaurante(Long id, Restaurante restauranteDetalles);
}
