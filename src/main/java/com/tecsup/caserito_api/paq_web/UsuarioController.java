package com.tecsup.caserito_api.paq_web;

import com.tecsup.caserito_api.paq_config.UserDetailServiceImpl;
import com.tecsup.caserito_api.paq_modelo.paq_daos.UsuarioRepository;
import com.tecsup.caserito_api.paq_modelo.paq_servicios.UsuarioService;
import com.tecsup.caserito_api.paq_web.paq_dto.AuthResponse;
import com.tecsup.caserito_api.paq_web.paq_dto.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/caserito_api/user")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/update-user")
    public ResponseEntity<AuthResponse> updateUser(@Validated @RequestBody UpdateUserRequest updateUserRequest) {
        try {
            AuthResponse authResponse = userDetailService.updateUserDate(updateUserRequest);

            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new AuthResponse(
                    null,
                    "Error inesperado: " + ex.getMessage(),
                    null,
                    null,
                    false
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }









}
