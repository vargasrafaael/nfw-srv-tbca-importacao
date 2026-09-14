package com.nfw.tbca.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "alimento_porcao_nutriente")
@Getter @Setter @NoArgsConstructor
public class AlimentoPorcaoNutrienteEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alimento_porcao_id", nullable = false)
    private AlimentoPorcaoEntity porcao;
    @Column(name = "identificador_nutriente", nullable = false, length = 50)
    private String identificadorNutriente;
    @Column(nullable = false, precision = 8, scale = 3)
    private BigDecimal valor;
    @Column(name = "unidade_medida", nullable = false, length = 10)
    private String unidadeMedida;
}
