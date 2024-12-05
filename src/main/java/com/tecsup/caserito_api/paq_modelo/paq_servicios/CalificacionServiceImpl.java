package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import com.tecsup.caserito_api.paq_exception.CalificacionDuplicadaException;
import com.tecsup.caserito_api.paq_modelo.paq_daos.CalificacionRepository;
import com.tecsup.caserito_api.paq_modelo.paq_daos.RestauranteRepository;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Calificacion;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Usuario;
import com.tecsup.caserito_api.paq_web.paq_dto.CalificacionRequest;
import com.tecsup.caserito_api.paq_web.paq_dto.CalificacionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalificacionServiceImpl implements  CalificacionService{
    @Autowired
    private CalificacionRepository calificacionRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private AuthService authService;

    @Override
    public boolean agregarCalificacion(CalificacionRequest calificacionRequest) {
        // Obtener el usuario autenticado
        Usuario usuario = authService.getAuthenticatedUser();

        // Obtener el restaurante al que se le va a agregar la calificación
        Restaurante restaurante = restauranteRepository.findById(calificacionRequest.restauranteId())
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));

        // Verificar si ya existe una calificación del usuario para este restaurante
        boolean yaCalifico = calificacionRepository.existsByUsuarioAndRestaurante(usuario, restaurante);

        if (yaCalifico) {
            throw new CalificacionDuplicadaException("El usuario ya calificó este restaurante");
        }

        // Crear la nueva calificación
        Calificacion calificacion = new Calificacion();
        calificacion.setCalificacion(calificacionRequest.calificacion());
        calificacion.setUsuario(usuario);
        calificacion.setRestaurante(restaurante);

        // Guardar la calificación
        calificacionRepository.save(calificacion);

        return true;
    }



    @Override
    public List<CalificacionResponse> obtenerCalificacionesPorRestaurante(Long restauranteId) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));

        // Obtener calificaciones y mapearlas a CalificacionResponse
        return calificacionRepository.findByRestaurante(restaurante).stream()
                .map(calificacion -> new CalificacionResponse(
                        restaurante.getPk_restaurante(),
                        calificacion.getCalificacion(),
                        calificacion.getUsuario().getUsuario(),
                        calificacion.getUsuario().getAvatar() // Ajusta según tus entidades
                ))
                .collect(Collectors.toList());
    }
}
