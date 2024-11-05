package com.tecsup.caserito_api.paq_config;

import com.tecsup.caserito_api.paq_modelo.paq_daos.UsuarioRepository;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Rol;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.RolEnum;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Usuario;
import com.tecsup.caserito_api.paq_util.JwtUtils;
import com.tecsup.caserito_api.paq_web.paq_dto.AuthCreateUserRequest;
import com.tecsup.caserito_api.paq_web.paq_dto.AuthLoginRequest;
import com.tecsup.caserito_api.paq_web.paq_dto.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest) {
        String username = authCreateUserRequest.username();
        String password = authCreateUserRequest.password();
        String email = authCreateUserRequest.email();
        String telefono = authCreateUserRequest.telefono();
        String direccion = authCreateUserRequest.direccion();

        // Obtiene el rol Usuario
        Rol rolUser = Rol.builder()
                .roleEnum(RolEnum.USER) // Asigna directamente el rol USER
                .build();
        Set<Rol> roles = new HashSet<>();
        roles.add(rolUser); // Agrega el rol USER al conjunto de roles

        Usuario usuario = Usuario.builder()
                .usuario(username)
                .password(passwordEncoder.encode(password))
                .isEnabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .email(email)
                .telefono(telefono)
                .direccion(direccion)
                .roles(roles) // Establece los roles
                .fecha_creacion(LocalDateTime.now())
                .build();

        // Guarda el usuario en la base de datos
        Usuario userCreated = usuarioRepository.save(usuario);

        Long userId = userCreated.getPk_usuario();

        // Crea la lista de autoridades a partir de los roles del usuario
        List<SimpleGrantedAuthority> authorityArrayList = userCreated.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleEnum().name())) // Asegúrate de que roleEnum sea del tipo correcto
                .collect(Collectors.toList());

        // Crea la autenticación
        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getUsuario(), userCreated.getPassword(), authorityArrayList);

        // Genera el token de acceso
        String accessToken = jwtUtils.createToken(authentication, userId);

        // Crea la lista de nombres de roles a partir de los roles del usuario
        List<String> rolesList = userCreated.getRoles().stream()
                .map(role -> role.getRoleEnum().name()) // Obtiene solo el nombre del rol
                .collect(Collectors.toList());

        // Corrige la instancia de AuthResponse
        AuthResponse authResponse = new AuthResponse(
                userCreated.getUsuario(), // username
                "user created",           // msg
                accessToken,              // jwt
                rolesList,                // roles como List<String>
                true                      // status
        );

        return authResponse;
    }






}
