package com.tecsup.caserito_api.paq_modelo.paq_entidades;

import jakarta.persistence.*;
import lombok.*;

// Clase Favorito
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Favorito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk_favorito;

    // Relación con Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario", nullable = false)
    private Usuario usuario;

    // Relación con Restaurante
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_restaurante", nullable = false)
    private Restaurante restaurante;
}
