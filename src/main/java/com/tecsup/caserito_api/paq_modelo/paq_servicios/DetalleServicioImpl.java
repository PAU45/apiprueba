package com.tecsup.caserito_api.paq_modelo.paq_servicios;
import com.tecsup.caserito_api.paq_modelo.paq_daos.RestauranteRepository;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Detalle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;
import com.tecsup.caserito_api.paq_modelo.paq_daos.DetalleRepository;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.DestallesEnum;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetalleServicioImpl implements DetalleServicio {

    @Autowired
    private DetalleRepository detalleRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Override
    public Detalle registrarDetalle(Long restauranteId, Detalle detalle) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado con id: " + restauranteId));

        detalle.setRestaurante(restaurante);
        return detalleRepository.save(detalle);
    }

    @Override
    public List<Detalle> listarDetalles() {
        return detalleRepository.findAll();
    }

    @Override
    public List<Detalle> listarDetallesPorTipo(DestallesEnum tipo) {
        return detalleRepository.findAll().stream()
                .filter(detalle -> detalle.getTipo() == tipo)
                .collect(Collectors.toList());
    }
}

