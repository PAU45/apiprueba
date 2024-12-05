package com.tecsup.caserito_api.paq_web.paq_dto;
public record RestaurantResponse(
        Long restaurantId,
        String nombre,
        String descripcion,
        String ubicacion,
        String tipo,
        String img
) {
}
