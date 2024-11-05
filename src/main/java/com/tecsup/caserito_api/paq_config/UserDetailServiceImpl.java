package com.tecsup.caserito_api.paq_config;

import com.tecsup.caserito_api.paq_modelo.paq_daos.UsuarioRepository;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Usuario;
import com.tecsup.caserito_api.paq_util.JwtUtils;
import com.tecsup.caserito_api.paq_web.paq_dto.AuthLoginRequest;
import com.tecsup.caserito_api.paq_web.paq_dto.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar el usuario en el repositorio
        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));

        // Lista para almacenar las autoridades (roles) del usuario
        List<SimpleGrantedAuthority> authorityList = usuario.getRoles()
                .stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_".concat(rol.getRoleEnum().name())))
                .collect(Collectors.toList());

        // Crear un UserDetails con el username, contraseña y lista de autoridades
        return new User(
                usuario.getUsuario(),
                usuario.getPassword(),
                usuario.isEnabled(),
                usuario.isAccountNonExpired(),
                usuario.isCredentialsNonExpired(),
                usuario.isAccountNonLocked(),
                authorityList);
    }


    public AuthResponse loginUser(AuthLoginRequest loginRequest) {
        String username = loginRequest.username();
        String password = loginRequest.password();

        // Autenticación del usuario
        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Obtener el usuario y su ID
        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));

        Long userId = usuario.getPk_usuario(); // Obtener el ID del usuario

        // Crear el token JWT
        String accessToken = jwtUtils.createToken(authentication, userId);

        // Obtener los roles del usuario autenticado
        List<String> roles = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority().replace("ROLE_", ""))
                .collect(Collectors.toList());

        // Crear la respuesta AuthResponse
        return new AuthResponse(username, accessToken, "Logeo Correcto", roles, true);
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username); // Se asume que lanza excepción si no existe
        // Verificar si la contraseña es correcta
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("La contraseña es incorrecta");
        }
        // Crear el token de autenticación sin pasar la contraseña
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
