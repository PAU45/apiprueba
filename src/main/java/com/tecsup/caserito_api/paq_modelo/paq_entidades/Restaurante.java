package com.tecsup.caserito_api.paq_modelo.paq_entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "restaurante")
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk_restaurante;

    @Column(nullable = false, unique = true)
    @NotNull
    @Size(max = 100)
    private String nombre;

    @Column(nullable = false, unique = false)
    @NotNull
    @Size(max = 500)
    private String descripcion;

    @Column(nullable = false, unique = false)
    @NotNull
    @Size(max = 500)
    private String ubicacion;

    @Column(nullable = true)
    private Double latitud;

    @Column(nullable = true)
    private Double longitud;

    @Column(nullable = true)
    private String tipo;

    @Column(nullable = true)
    @Size(max = 2000)
    private String img;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = true)
    private LocalTime horaApertura;

    @Column(nullable = true)
    private LocalTime horaCierre;

}
