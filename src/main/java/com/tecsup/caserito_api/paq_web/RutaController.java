package com.tecsup.caserito_api.paq_web;

import com.tecsup.caserito_api.paq_modelo.paq_servicios.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/caserito_api/restaurante")
public class RutaController {


    @Autowired
    private RutaService rutaService;

    @GetMapping("/{id}/ruta")
    public ResponseEntity<Map<String, Object>> obtenerRuta(@PathVariable Long id) {
        try {
            Map<String, Object> ruta = rutaService.obtenerRuta(id);
            return ResponseEntity.ok(ruta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}
