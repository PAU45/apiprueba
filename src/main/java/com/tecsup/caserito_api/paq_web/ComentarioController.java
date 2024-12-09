package com.tecsup.caserito_api.paq_web;

import com.tecsup.caserito_api.paq_modelo.paq_servicios.ComentarioService;
import com.tecsup.caserito_api.paq_web.paq_dto.ComentarioRequest;
import com.tecsup.caserito_api.paq_web.paq_dto.ComentarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caserito_api/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    // Endpoint para agregar un nuevo comentario
    @PostMapping("/agregar")
    public ResponseEntity<String> agregarComentario(@RequestBody ComentarioRequest comentarioRequest) {
        // Llamar al servicio para agregar el comentario
        boolean success = comentarioService.agregarComentario(comentarioRequest);

        if (success) {
            return new ResponseEntity<>("Comentario agregado con éxito", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Error al agregar comentario", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<ComentarioResponse>> obtenerComentariosPorRestaurante(@PathVariable Long restauranteId) {
        List<ComentarioResponse> comentarios = comentarioService.obtenerComentariosPorRestaurante(restauranteId);
        return ResponseEntity.ok(comentarios);
    }


}