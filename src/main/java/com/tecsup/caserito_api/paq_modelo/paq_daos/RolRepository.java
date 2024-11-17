package com.tecsup.caserito_api.paq_modelo.paq_daos;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolRepository extends JpaRepository<Rol, Long> {
    List<Rol> findRolByRoleEnumIn(List<String> roleNames);
}
