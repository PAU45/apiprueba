package com.tecsup.caserito_api.paq_web;

import com.tecsup.caserito_api.paq_modelo.paq_servicios.FavoritoService;
import com.tecsup.caserito_api.paq_web.paq_dto.FavoritoRequest;
import com.tecsup.caserito_api.paq_web.paq_dto.FavoritoResponse;
import com.tecsup.caserito_api.paq_web.paq_dto.RestaurantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/caserito_api/favorito")
public class FavoritoController {
    @Autowired
    private FavoritoService favoritoService;

    @GetMapping
    public ResponseEntity<List<FavoritoResponse>> obtenerFavoritos() {
        try {
            List<FavoritoResponse> favoritos = favoritoService.getFavoritosDelUsuario();
            return ResponseEntity.ok(favoritos);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<String> agregarFavorito(@RequestBody FavoritoRequest favoritoRequest) {
        boolean succes = favoritoService.agregarFavorito(favoritoRequest);
        if (succes) {
            return ResponseEntity.ok("Favorito agregado correctamente");
        } else {
            return ResponseEntity.badRequest().body("Error al agregar favorito");
        }
    }

    @PostMapping("/eliminar")
    public ResponseEntity<String> eliminarFavorito(@RequestBody Map<String, Long> body) {
        Long favoritoId = body.get("favoritoId");
        if (favoritoId == null) {
            return ResponseEntity.badRequest().body("El ID del favorito es requerido");
        }

        boolean success = favoritoService.eliminarFavorito(favoritoId);
        if (success) {
            return ResponseEntity.ok("Favorito eliminado correctamente");
        } else {
            return ResponseEntity.badRequest().body("Error al eliminar favorito");
        }
    }


}
