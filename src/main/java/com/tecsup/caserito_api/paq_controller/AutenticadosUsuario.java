package com.tecsup.caserito_api.paq_controller;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Usuario;
import com.tecsup.caserito_api.paq_modelo.paq_servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caserito_api/user")
public class AutenticadosUsuario {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/profile")
    public ResponseEntity<Usuario> getProfile(@RequestParam String username) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorNombre(username);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }




    // Aquí puedes agregar más endpoints para usuarios autenticados
}
