package com.tecsup.caserito_api.paq_web.paq_dto;

public record ComentarioResponse(
        Long restauranteId,
        String comentario,
        String username,
        String avatarUser

) {


}
