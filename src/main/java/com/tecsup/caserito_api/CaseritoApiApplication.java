package com.tecsup.caserito_api;

import com.tecsup.caserito_api.paq_modelo.paq_daos.UsuarioRepository;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Rol;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.RolEnum;
import com.tecsup.caserito_api.paq_modelo.paq_entidades.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class CaseritoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaseritoApiApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			/* Creación de roles */
			Rol rolUser = Rol.builder()
					.roleEnum(RolEnum.USER)
					.build();

			Rol rolAdmin = Rol.builder()
					.roleEnum(RolEnum.TIENDA)
					.build();

			/* Creación de usuarios */
			Usuario usuario1 = Usuario.builder()
					.usuario("diego")
					.email("diego@tecsup.edu.pe")
					.password(passwordEncoder.encode("12345weferf678")) // Contraseña ajustada
					.isEnabled(true)
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.telefono("930501210")
					.direccion("jr.Francisco")
					.roles(Set.of(rolUser))
					.fecha_creacion(LocalDateTime.now())
					.build();

			Usuario usuario2 = Usuario.builder()
					.usuario("maria")
					.email("maria@tecsup.edu.pe")
					.password(passwordEncoder.encode("passwdsdfvord123")) // Contraseña ajustada
					.isEnabled(true)
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.telefono("930501210")
					.direccion("jr.Francisco2")
					.roles(Set.of(rolAdmin))
					.fecha_creacion(LocalDateTime.now())
					.build();

			Usuario usuario3 = Usuario.builder()
					.usuario("juan")
					.email("juan@tecsup.edu.pe")
					.password(passwordEncoder.encode("password456")) // Contraseña ajustada
					.telefono("930501210")
					.direccion("jr.Francisco")
					.isEnabled(true)
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.roles(Set.of(rolUser))
					.fecha_creacion(LocalDateTime.now())
					.build();

			// Guardar usuarios (con roles en cascada)
			usuarioRepository.saveAll(List.of(usuario1, usuario2, usuario3));
		};
	}
}
