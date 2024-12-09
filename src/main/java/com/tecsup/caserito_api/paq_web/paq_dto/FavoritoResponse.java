package com.tecsup.caserito_api.paq_web.paq_dto;

import java.time.LocalTime;

public record FavoritoResponse(
        Long favoriteId,
        Long restaurantId,
        String nombre,
        String descripcion,
        String ubicacion,
        String tipo,
        String img,
        LocalTime horaApertura,  // Ahora tipo LocalTime
        LocalTime horaCierre,    // Ahora tipo LocalTime
        String distancia,        // Cambiado a String para distancia
        String tiempo,
        double calificacion
) {
}
