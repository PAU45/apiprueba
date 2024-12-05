package com.tecsup.caserito_api.paq_web;

import com.tecsup.caserito_api.paq_exception.CalificacionDuplicadaException;
import com.tecsup.caserito_api.paq_modelo.paq_servicios.CalificacionService;
import com.tecsup.caserito_api.paq_web.paq_dto.CalificacionRequest;
import com.tecsup.caserito_api.paq_web.paq_dto.CalificacionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/caserito_api/calificacion/")
public class CalificacionController {
    @Autowired
    private CalificacionService calificacionService;

    // Endpoint para agregar una nueva calificación
    @PostMapping("/agregar")
    public ResponseEntity<String> agregarCalificacion(@RequestBody CalificacionRequest calificacionRequest) {
        try {
            boolean success = calificacionService.agregarCalificacion(calificacionRequest);

            if (success) {
                return new ResponseEntity<>("Calificación agregada con éxito", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Error al agregar calificación", HttpStatus.BAD_REQUEST);
            }
        } catch (CalificacionDuplicadaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Ocurrió un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // Endpoint para obtener las calificaciones por restaurante
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<CalificacionResponse>> obtenerCalificacionesPorRestaurante(@PathVariable Long restauranteId) {
        List<CalificacionResponse> calificaciones = calificacionService.obtenerCalificacionesPorRestaurante(restauranteId);
        return ResponseEntity.ok(calificaciones);
    }
}
