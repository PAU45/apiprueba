package com.tecsup.caserito_api.paq_config;

import com.tecsup.caserito_api.paq_modelo.paq_daos.UsuarioRepository;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Obtiene el usuario desde el repositorio
        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));

        // Lista para almacenar las autoridades (roles) del usuario
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        // Itera sobre los roles del usuario y los agrega a authorityList
        usuario.getRoles()
                .forEach(rol -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(rol.getRoleEnum().name())))
                );

        // Crea un UserDetails con el username, contraseña y lista de autoridades
        return new User(
                usuario.getUsuario(),
                usuario.getPassword(),
                usuario.isEnabled(),
                usuario.isAccountNonExpired(),
                usuario.isCredentialsNonExpired(),
                usuario.isAccountNonLocked(),
                authorityList);
    }
}
