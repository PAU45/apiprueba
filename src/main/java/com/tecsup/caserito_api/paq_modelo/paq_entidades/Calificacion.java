package com.tecsup.caserito_api.paq_modelo.paq_entidades;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Calificacion")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk_calificacion;

    @Column(nullable = false)
    @NotNull
    @Min(1)
    @Max(5)
    private Long calificacion;

    // Relación con Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario", nullable = false)
    private Usuario usuario;

    // Relación con Restaurante
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_restaurante", nullable = false)
    private Restaurante restaurante;
}
