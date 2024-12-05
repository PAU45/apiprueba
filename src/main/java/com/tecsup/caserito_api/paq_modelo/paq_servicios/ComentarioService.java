package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import com.tecsup.caserito_api.paq_web.paq_dto.ComentarioRequest;
import com.tecsup.caserito_api.paq_web.paq_dto.ComentarioResponse;

import java.util.List;

public interface ComentarioService {
    List<ComentarioResponse> obtenerComentariosPorRestaurante(Long restauranteId);
    boolean agregarComentario(ComentarioRequest comentarioRequest);
}
