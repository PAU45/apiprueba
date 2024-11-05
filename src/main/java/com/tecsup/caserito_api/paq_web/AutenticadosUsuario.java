package com.tecsup.caserito_api.paq_web;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Usuario;
import com.tecsup.caserito_api.paq_modelo.paq_servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caserito_api/user")
public class AutenticadosUsuario {
    @GetMapping("/home")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Hola Mundo"); // Retorna el mensaje
    }

    // Aquí puedes agregar más endpoints para usuarios autenticados
}
