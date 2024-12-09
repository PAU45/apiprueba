package com.tecsup.caserito_api.paq_modelo.paq_entidades;
import jakarta.persistence.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk_menu;

    @Column(nullable = false)
    @NotNull
    @Size(max = 100)
    private String nombre;


    @Column(nullable = false)
    @NotNull
    @Size(max = 1500)
    private String descripcion;

    @Column(nullable = true)
    @NotNull
    private String img;

    @Column(nullable = false)
    @NotNull
    private Long precio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_restaurante", nullable = false)
    private Restaurante restaurante;


}
