package com.tecsup.caserito_api.paq_config.paq_filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.tecsup.caserito_api.paq_util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import java.util.stream.Collectors;

public class JwtTokenValidator extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7); // Eliminar "Bearer "

            try {
                DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);
                String username = jwtUtils.extractUsername(decodedJWT);
                String stringRoles = jwtUtils.getSpecificClaim(decodedJWT, "roles").asString();

                Collection<? extends GrantedAuthority> roles = AuthorityUtils.commaSeparatedStringToAuthorityList(stringRoles);
                SecurityContext context = SecurityContextHolder.getContext();

                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, roles);
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido o expirado");
                return; // No continuar con el filtro si el token es inválido
            }
        }


        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
