package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Detalle;
import com.tecsup.caserito_api.paq_web.paq_dto.DetalleResponse;

import java.util.List;

public interface DetalleService {
    void crearDetalle(Long restauranteId, Detalle detalle);
    List<DetalleResponse> obtenerDetallesPorRestaurante(Long restauranteId);

    void eliminarDetalle(Long detalleId);
}
