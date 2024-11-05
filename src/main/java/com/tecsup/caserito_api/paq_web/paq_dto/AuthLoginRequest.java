package com.tecsup.caserito_api.paq_web.paq_dto;

import jakarta.validation.constraints.NotNull;

public record AuthLoginRequest(@NotNull String username,@NotNull String password) {
}
