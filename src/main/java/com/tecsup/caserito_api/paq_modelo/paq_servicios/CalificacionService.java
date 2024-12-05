package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import com.tecsup.caserito_api.paq_web.paq_dto.CalificacionRequest;
import com.tecsup.caserito_api.paq_web.paq_dto.CalificacionResponse;

import java.util.List;

public interface CalificacionService {
    List<CalificacionResponse> obtenerCalificacionesPorRestaurante(Long restauranteId);
    boolean agregarCalificacion(CalificacionRequest calificacionRequest);
}
