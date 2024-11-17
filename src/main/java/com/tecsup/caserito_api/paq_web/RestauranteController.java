package com.tecsup.caserito_api.paq_web;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;
import com.tecsup.caserito_api.paq_modelo.paq_servicios.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caserito_api/restaurante")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    // Endpoint para crear un restaurante
    @PostMapping("/create")
    public Restaurante crearRestaurante(@RequestBody Restaurante restaurante) {
        // Llamar al servicio para crear o actualizar el restaurante
        return restauranteService.createOrUpdateRestaurante(restaurante);
    }

    // Ruta GET de prueba para verificar la conectividad
    @GetMapping("/prueba")
    public String prueba() {
        return "Hola"; // Responde con un mensaje simple
    }
}
