package com.tecsup.caserito_api.paq_modelo.paq_servicios;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Usuario;

public interface UsuarioService {
    Usuario registrarUsuario(Usuario usuario);
    Usuario obtenerUsuarioPorNombre(String nombre);
    // Cambiado a String para retornar el token JWT
}
