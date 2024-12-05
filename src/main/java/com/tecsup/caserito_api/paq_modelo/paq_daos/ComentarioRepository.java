package com.tecsup.caserito_api.paq_modelo.paq_daos;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Comentario;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByRestaurante(Restaurante restaurante);
}
