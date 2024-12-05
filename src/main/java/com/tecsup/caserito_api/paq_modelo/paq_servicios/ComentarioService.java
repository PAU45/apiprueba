package com.tecsup.caserito_api.paq_modelo.paq_servicios;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Comentario;
import com.tecsup.caserito_api.paq_web.paq_dto.ComentarioRequest;

import java.util.List;

public interface ComentarioService {
    List<Comentario> obtenerComentariosPorRestaurante(Long restauranteId);
    boolean agregarComentario(ComentarioRequest comentarioRequest);
}
