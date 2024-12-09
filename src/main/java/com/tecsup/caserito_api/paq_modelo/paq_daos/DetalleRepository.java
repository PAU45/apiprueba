package com.tecsup.caserito_api.paq_modelo.paq_daos;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Detalle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleRepository extends JpaRepository<Detalle, Long> {
}
