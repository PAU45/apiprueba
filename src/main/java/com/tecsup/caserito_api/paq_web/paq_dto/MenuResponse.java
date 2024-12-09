package com.tecsup.caserito_api.paq_web.paq_dto;

public record MenuResponse(
        Long menuId,
        String nombre,
        String descripcion,
        String img,
        Long precio
) {
}
