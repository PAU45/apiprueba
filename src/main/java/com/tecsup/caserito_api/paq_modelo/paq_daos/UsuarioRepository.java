package com.tecsup.caserito_api.paq_modelo.paq_daos;

import com.tecsup.caserito_api.paq_modelo.paq_entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar un usuario por su nombre de usuario
    Optional<Usuario> findByUsuario(String usuario);
    Optional<Usuario> findByEmail(String email);

    // Actualizar el rol de un usuario según su nombre
    /*
    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.rol = :rol WHERE u.nombre = :nombre")
    int updateByUserRol(@Param("nombre") String nombre, @Param("rol") String rol);
     */
}
