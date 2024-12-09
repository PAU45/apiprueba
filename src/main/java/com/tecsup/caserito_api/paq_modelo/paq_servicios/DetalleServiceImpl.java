package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import com.tecsup.caserito_api.paq_modelo.paq_daos.DetalleRepository;
import com.tecsup.caserito_api.paq_modelo.paq_daos.RestauranteRepository;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Detalle;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;
import com.tecsup.caserito_api.paq_web.paq_dto.DetalleResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetalleServiceImpl implements DetalleService {

    private final RestauranteRepository restauranteRepository;
    private final DetalleRepository detalleRepository;

    public DetalleServiceImpl(RestauranteRepository restauranteRepository, DetalleRepository detalleRepository) {
        this.restauranteRepository = restauranteRepository;
        this.detalleRepository = detalleRepository;
    }

    @Override
    public void crearDetalle(Long restauranteId, Detalle detalle) {

        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado con ID: " + restauranteId));


        detalle.setRestaurante(restaurante);


        detalleRepository.save(detalle);
    }
    @Override
    public List<DetalleResponse> obtenerDetallesPorRestaurante(Long restauranteId) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado con ID: " + restauranteId));

        List<Detalle> detalles = detalleRepository.findByRestaurante(restaurante);


        return detalles.stream()
                .map(detalle -> new DetalleResponse(
                        detalle.getPk_detalle(),
                        detalle.getInformacion(),
                        detalle.getTipo().toString()))
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarDetalle(Long detalleId) {
        Detalle detalle = detalleRepository.findById(detalleId)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado con ID: " + detalleId));

        detalleRepository.delete(detalle);
    }

}
