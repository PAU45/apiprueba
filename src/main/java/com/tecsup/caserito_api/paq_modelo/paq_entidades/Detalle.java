package com.tecsup.caserito_api.paq_modelo.paq_entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Detalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk_detalles;

    @Column(nullable = false, length = 255)
    private String informacion;

    @Column(name = "detalles_tipo")
    @Enumerated(EnumType.STRING)
    private DestallesEnum tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_restaurante", nullable = false)
    private Restaurante restaurante; // Relación con Restaurante
}
