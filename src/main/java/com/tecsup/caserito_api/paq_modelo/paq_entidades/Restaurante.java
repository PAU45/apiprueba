package com.tecsup.caserito_api.paq_modelo.paq_entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_menu", nullable = true)
    private Menu fk_menu;
}
