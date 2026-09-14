package com.nfw.tbca.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alimento_porcao")
@Getter @Setter @NoArgsConstructor
public class AlimentoPorcaoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alimento_base_id", nullable = false)
    private AlimentoBaseEntity alimento;
    @Column(nullable = false, length = 150)
    private String descricao;
    @Column(name = "peso_gramas", precision = 7, scale = 2)
    private BigDecimal pesoGramas;
    @Column(name = "porcao_padrao", nullable = false)
    private boolean porcaoPadrao;
    @OneToMany(mappedBy = "porcao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlimentoPorcaoNutrienteEntity> nutrientes = new ArrayList<>();
}
