package com.tecsup.caserito_api.paq_modelo.paq_entidades;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
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
    private int pk_menu;

    @Column(nullable = false)
    @NotNull
    @Size(max = 100)
    private String nombre;


    @Column(nullable = false)
    @NotNull
    @Size(max = 500)
    private String descripcion;

    @Column(nullable = true)
    @NotNull
    @Size(max = 500)
    private String imagen;

    @Column(nullable = false)
    @NotNull
    @Max(50)
    private Long precio;
}
