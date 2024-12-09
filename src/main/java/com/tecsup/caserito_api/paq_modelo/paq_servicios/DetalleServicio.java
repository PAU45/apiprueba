package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Detalle;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.DestallesEnum;

import java.util.List;

public interface DetalleServicio {
    Detalle registrarDetalle(Long restauranteId, Detalle detalle);
    List<Detalle> listarDetalles();
    List<Detalle> listarDetallesPorTipo(DestallesEnum tipo);
}
