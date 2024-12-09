package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;
import com.tecsup.caserito_api.paq_web.paq_dto.RestaurantResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestauranteService {


    String createRestaurante(Restaurante restaurante);

    List<RestaurantResponse> getAllRestaurantes();


    Restaurante getRestauranteById(Long id);


    ResponseEntity<?> deleteRestaurante(Long id);


    List<RestaurantResponse> getRestaurantesPorUsuario();

    Restaurante updateRestaurante(Long id, Restaurante restauranteDetalles);

    List<RestaurantResponse> getRestauranteByNombre(String nombre);
}
