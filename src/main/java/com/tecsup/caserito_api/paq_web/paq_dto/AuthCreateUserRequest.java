package com.tecsup.caserito_api.paq_web.paq_dto;

import jakarta.validation.constraints.NotBlank;

public record AuthCreateUserRequest(@NotBlank String username, @NotBlank String password, @NotBlank String email, String telefono, String direccion) {
}
