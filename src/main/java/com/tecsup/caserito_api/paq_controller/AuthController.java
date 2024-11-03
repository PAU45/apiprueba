package com.tecsup.caserito_api.paq_controller;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Usuario;
import com.tecsup.caserito_api.paq_modelo.paq_servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caserito_api/authentication")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Usuario usuario) {
        try {
            usuarioService.registrarUsuario(usuario);
            return ResponseEntity.ok("Usuario registrado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        try {
            Usuario existingUser = usuarioService.obtenerUsuarioPorNombre(usuario.getUsuario());

            if (passwordEncoder.matches(usuario.getPassword(), existingUser.getPassword())) {
                return ResponseEntity.ok("Login exitoso");
            } else {
                return ResponseEntity.status(401).body("Credenciales incorrectas");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Usuario no encontrado");
        }
    }
}
