package com.tecsup.caserito_api.paq_web.paq_dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AuthCreateUserRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@tecsup\\.edu\\.pe$", message = "El correo debe ser de tipo @tecsup.edu.pe") String email,
        String telefono,
        String direccion,
        String avatar
) {}
