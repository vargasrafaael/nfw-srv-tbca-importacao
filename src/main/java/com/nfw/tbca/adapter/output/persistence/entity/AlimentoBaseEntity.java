package com.nfw.tbca.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alimento_base")
@Getter @Setter @NoArgsConstructor
public class AlimentoBaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tabela_ref_id", nullable = false)
    private TabelaNutricionalRefEntity tabela;
    @Column(name = "codigo_alimento_origem", nullable = false, length = 50)
    private String codigoAlimentoOrigem;
    @Column(nullable = false, length = 250)
    private String nome;
    @Column(name = "nome_cientifico", length = 150)
    private String nomeCientifico;
    @Column(length = 100)
    private String grupo;
    @Column(name = "tipo_alimento", length = 100)
    private String tipoAlimento;
    @OneToMany(mappedBy = "alimento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlimentoPorcaoEntity> porcoes = new ArrayList<>();
}
