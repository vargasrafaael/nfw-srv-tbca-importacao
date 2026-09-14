package com.nfw.tbca.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tabela_nutricional_ref")
@Getter @Setter @NoArgsConstructor
public class TabelaNutricionalRefEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 20)
    private String codigo;
    @Column(nullable = false, length = 100)
    private String nome;
    @Column(length = 20)
    private String versao;
    @Column(length = 150)
    private String descricao;
    @Column(nullable = false)
    private boolean ativo = true;
    @OneToMany(mappedBy = "tabela", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlimentoBaseEntity> alimentos = new ArrayList<>();
}
