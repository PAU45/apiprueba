package com.tecsup.caserito_api.paq_modelo.paq_servicios;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Comentario;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Usuario;
import com.tecsup.caserito_api.paq_modelo.paq_daos.ComentarioRepository;
import com.tecsup.caserito_api.paq_modelo.paq_daos.RestauranteRepository;
import com.tecsup.caserito_api.paq_web.paq_dto.ComentarioRequest;
import com.tecsup.caserito_api.paq_web.paq_dto.ComentarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private AuthService authService;  // Servicio para obtener el usuario autenticado

    @Override
    public boolean agregarComentario(ComentarioRequest comentarioRequest) {
        // Obtener el usuario autenticado
        Usuario usuario = authService.getAuthenticatedUser();

        // Obtener el restaurante al que se le va a hacer el comentario
        Restaurante restaurante = restauranteRepository.findById(comentarioRequest.restauranteId())
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));

        // Crear el nuevo comentario
        Comentario comentario = new Comentario();
        comentario.setComentario(comentarioRequest.comentario());
        comentario.setUsuario(usuario);
        comentario.setRestaurante(restaurante);

        // Guardar el comentario
        comentarioRepository.save(comentario);

        return true;
    }
    @Override
    public List<ComentarioResponse> obtenerComentariosPorRestaurante(Long restauranteId) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));

        // Obtener comentarios y mapearlos a ComentarioResponse
        return comentarioRepository.findByRestaurante(restaurante).stream()
                .map(comentario -> new ComentarioResponse(
                        restaurante.getPk_restaurante(),
                        comentario.getComentario(),
                        comentario.getUsuario().getUsuario(),
                        comentario.getUsuario().getAvatar() // Ajusta según tus entidades
                ))
                .collect(Collectors.toList());
    }
}
