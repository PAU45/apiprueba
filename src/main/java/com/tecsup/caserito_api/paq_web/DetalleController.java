package com.tecsup.caserito_api.paq_web;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.DestallesEnum;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Detalle;
import com.tecsup.caserito_api.paq_modelo.paq_servicios.DetalleServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caserito_api/detalles")
public class DetalleController {

    @Autowired
    private DetalleServicio detalleServicio;

    @PostMapping("/{restauranteId}")
    public ResponseEntity<Detalle> registrarDetalle(@PathVariable Long restauranteId, @RequestBody Detalle detalle) {
        Detalle nuevoDetalle = detalleServicio.registrarDetalle(restauranteId, detalle);
        return ResponseEntity.ok(nuevoDetalle);
    }

    @GetMapping
    public ResponseEntity<List<Detalle>> listarDetalles() {
        List<Detalle> detalles = detalleServicio.listarDetalles();
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Detalle>> listarPorTipo(@PathVariable DestallesEnum tipo) {
        List<Detalle> detalles = detalleServicio.listarDetallesPorTipo(tipo);
        return ResponseEntity.ok(detalles);
    }
}
