package com.tecsup.caserito_api.paq_web.paq_dto;

public record CalificacionResponse(
        Long restauranteId,
        Long calificacion,
        String username,
        String avatarUser
) {
}
