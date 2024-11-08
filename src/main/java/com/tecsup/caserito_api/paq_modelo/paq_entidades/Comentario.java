package com.tecsup.caserito_api.paq_modelo.paq_entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Comentario")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk_comentario;

    @Column(nullable = false)
    @NotNull
    @Size(min = 1, max = 200)
    private String comentario;

    // Relación con Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario", nullable = false)
    private Usuario usuario;

    // Relación con Restaurante
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_restaurante", nullable = false)
    private Restaurante restaurante;


}
