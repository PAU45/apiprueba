package com.tecsup.caserito_api.paq_web.paq_dto;


public record ComentarioRequest(
        Long restauranteId,
        String comentario
) {
}