package com.tecsup.caserito_api.paq_modelo.paq_daos;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Detalle;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DetalleRepository extends JpaRepository<Detalle, Long> {
    List<Detalle> findByRestaurante(Restaurante restaurante);
}
