package com.tecsup.caserito_api.paq_modelo.paq_entidades;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fk_rol;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RolEnum roleEnum;
}
