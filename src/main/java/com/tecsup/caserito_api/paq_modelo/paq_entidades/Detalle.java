package com.tecsup.caserito_api.paq_modelo.paq_entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Detalle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Detalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk_detalle;

    @Column(nullable = false, length = 255)
    private String informacion;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_restaurante", nullable = false)
    private Restaurante restaurante;

    @Column(name = "detalles_tipo")
    @Enumerated(EnumType.STRING)
    private DestallesEnum tipo;
}
