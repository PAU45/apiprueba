package com.tecsup.caserito_api.paq_web.paq_dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonPropertyOrder({"username", "msg", "jwt", "roles", "status"})
public record AuthResponse(
        String username,
        String msg,
        String jwt,
        List<String> roles, // Agregamos el campo para los roles
        boolean status) {
}
