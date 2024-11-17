package com.tecsup.caserito_api.paq_web;

import com.tecsup.caserito_api.paq_exception.ErrorResponse;
import com.tecsup.caserito_api.paq_exception.RestauranteExistenteException;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;
import com.tecsup.caserito_api.paq_modelo.paq_servicios.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import java.util.List;

@RestController
@RequestMapping("/caserito_api/restaurante")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    // Endpoint para crear un restaurante
    @PostMapping("/create")
    public Restaurante crearRestaurante(@RequestBody Restaurante restaurante) {
        return restauranteService.createRestaurante(restaurante);
    }


    // Ruta GET de prueba para verificar la conectividad
    @GetMapping("/prueba")
    public String prueba() {
        return "Hola"; // Responde con un mensaje simple
    }


    @GetMapping("/mis-restaurantes")
    public List<Restaurante> obtenerRestaurantesDelUsuario() {
        return restauranteService.getRestaurantesPorUsuario();
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> actualizarRestaurante(@PathVariable Long id, @RequestBody Restaurante restauranteDetalles) {
        try {
            Restaurante updatedRestaurante = restauranteService.updateRestaurante(id, restauranteDetalles);
            return new ResponseEntity<>(updatedRestaurante, HttpStatus.OK);
        } catch (RestauranteExistenteException e) {

            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Manejar otras excepciones y devolver un mensaje de error general
            ErrorResponse errorResponse = new ErrorResponse("Ocurrió un error al actualizar el restaurante: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteRestaurante(@PathVariable Long id) {
        try {
            // Llamar al servicio para eliminar el restaurante
            return restauranteService.deleteRestaurante(id);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(400).body(errorResponse);
        }
    }

}
