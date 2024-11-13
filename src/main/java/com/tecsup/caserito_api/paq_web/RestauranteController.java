package com.tecsup.caserito_api.paq_web;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;
import com.tecsup.caserito_api.paq_modelo.paq_servicios.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caserito_api/restaurante")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @GetMapping("/hola")
    public String saludo() {
        return "Hola, este es un mensaje de prueba";
    }

    // Crear o actualizar restaurante
    @PostMapping
    public ResponseEntity<Restaurante> createOrUpdateRestaurante(
            @RequestBody Restaurante restaurante,
            @RequestHeader(name = "Authorization") String token) {
        Restaurante savedRestaurante = restauranteService.createOrUpdateRestaurante(restaurante, token);
        return new ResponseEntity<>(savedRestaurante, HttpStatus.CREATED);
    }

    // Obtener todos los restaurantes
    @GetMapping
    public ResponseEntity<List<Restaurante>> getAllRestaurantes() {
        List<Restaurante> restaurantes = restauranteService.getAllRestaurantes();
        return new ResponseEntity<>(restaurantes, HttpStatus.OK);
    }

    // Obtener restaurante por ID
    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> getRestauranteById(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.getRestauranteById(id);
        return new ResponseEntity<>(restaurante, HttpStatus.OK);
    }

    // Eliminar restaurante por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurante(@PathVariable Long id) {
        restauranteService.deleteRestaurante(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Respuesta vacía con estado 204
    }
}
