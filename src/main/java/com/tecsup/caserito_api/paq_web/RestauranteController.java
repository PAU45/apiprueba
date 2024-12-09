package com.tecsup.caserito_api.paq_web;

import com.tecsup.caserito_api.paq_exception.ErrorResponse;
import com.tecsup.caserito_api.paq_exception.RestauranteExistenteException;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;
import com.tecsup.caserito_api.paq_modelo.paq_servicios.RestauranteService;
import com.tecsup.caserito_api.paq_web.paq_dto.RestaurantResponse;
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

    @PostMapping("/create")
    public ResponseEntity<String> crearRestaurante(@RequestBody Restaurante restaurante) {
        String mensaje = restauranteService.createRestaurante(restaurante);
        return ResponseEntity.ok(mensaje);
    }


    @GetMapping("/mis-restaurantes")
    public ResponseEntity<List<RestaurantResponse>> obtenerRestaurantesDelUsuario() {
        List<RestaurantResponse> restaurantes = restauranteService.getRestaurantesPorUsuario();
        return ResponseEntity.ok(restaurantes);
    }


    @GetMapping("/all")
    public List<RestaurantResponse> obtenerRestaurantes() {
        return restauranteService.getAllRestaurantes();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> actualizarRestaurante(@PathVariable Long id, @RequestBody Restaurante restauranteDetalles) {
        try {
            // Llamar al servicio de actualización sin esperar un retorno
            restauranteService.updateRestaurante(id, restauranteDetalles);

            // Retornar respuesta sin cuerpo indicando éxito
            return ResponseEntity.noContent().build();
        } catch (RestauranteExistenteException e) {
            // Manejar excepción de restaurante existente
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (RuntimeException e) {
            // Manejar errores de permisos o datos faltantes
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            // Manejar cualquier otro error inesperado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error inesperado: " + e.getMessage()));
        }
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRestaurante(@PathVariable Long id) {
        try {
            restauranteService.deleteRestaurante(id);
            return ResponseEntity.ok().body(new ErrorResponse("Restaurante eliminado con éxito"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al eliminar restaurante: " + e.getMessage()));
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<RestaurantResponse>> buscarRestaurantePorNombre(@RequestParam String nombre) {
        List<RestaurantResponse> restaurantes = restauranteService.getRestauranteByNombre(nombre);
        return ResponseEntity.ok(restaurantes);
    }





}
