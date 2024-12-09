package com.tecsup.caserito_api.paq_web;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Detalle;
import com.tecsup.caserito_api.paq_modelo.paq_servicios.DetalleService;
import com.tecsup.caserito_api.paq_web.paq_dto.DetalleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caserito_api/detalle")
public class DetalleController {
    @Autowired
    private DetalleService detalleService;

    @PostMapping("/{restauranteId}")
    public ResponseEntity<Void> crearDetalle(@PathVariable Long restauranteId, @RequestBody Detalle detalle) {
        // Llamar al servicio para crear el detalle
        detalleService.crearDetalle(restauranteId, detalle);

        // Retornar un código de estado 201 (CREADO)
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/{restauranteId}")
    public ResponseEntity<List<DetalleResponse>> obtenerDetallesPorRestaurante(@PathVariable Long restauranteId) {
        List<DetalleResponse> detalles = detalleService.obtenerDetallesPorRestaurante(restauranteId);
        return detalles.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(detalles);
    }
    @DeleteMapping("/{detalleId}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable Long detalleId) {
        detalleService.eliminarDetalle(detalleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // Retorna un status 204 No Content
    }
}
