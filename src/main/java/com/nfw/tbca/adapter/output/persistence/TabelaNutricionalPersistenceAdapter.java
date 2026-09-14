package com.nfw.tbca.adapter.output.persistence;

import com.nfw.tbca.adapter.output.persistence.entity.*;
import com.nfw.tbca.domain.model.NutrienteEnum;
import com.nfw.tbca.domain.model.TbcaArquivo;
import com.nfw.tbca.port.output.TabelaNutricionalRepositoryPort;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TabelaNutricionalPersistenceAdapter implements TabelaNutricionalRepositoryPort {
    private final TabelaNutricionalJpaRepository repository;
    private final AlimentoBaseJpaRepository alimentoRepository;
    private final EntityManager entityManager;
    public TabelaNutricionalPersistenceAdapter(TabelaNutricionalJpaRepository repository,
                                               AlimentoBaseJpaRepository alimentoRepository,
                                               EntityManager entityManager) {
        this.repository = repository;
        this.alimentoRepository = alimentoRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Long criarOuObterTabela(TbcaArquivo arquivo) {
        String codigo = arquivo.getNome() + "-" + arquivo.getVersao();
        return repository.findByCodigo(codigo).map(TabelaNutricionalRefEntity::getId).orElseGet(() -> {
            TabelaNutricionalRefEntity tabela = new TabelaNutricionalRefEntity();
            tabela.setCodigo(codigo);
            tabela.setNome(arquivo.getNome());
            tabela.setVersao(arquivo.getVersao());
            tabela.setDescricao(arquivo.getDescricao());
            tabela.setAtivo(true);
            return repository.saveAndFlush(tabela).getId();
        });
    }

    @Override
    @Transactional
    public int salvarLote(Long tabelaId, TbcaArquivo arquivo, List<TbcaArquivo.Alimento> alimentos) {
        if (alimentos == null || alimentos.isEmpty()) return 0;

        Set<String> codigosDoLote = alimentos.stream()
                .map(TbcaArquivo.Alimento::getCodigo)
                .collect(java.util.stream.Collectors.toSet());
        Set<String> codigosExistentes = new HashSet<>(alimentoRepository
                .findByTabela_IdAndCodigoAlimentoOrigemIn(tabelaId, codigosDoLote)
                .stream()
                .map(AlimentoBaseEntity::getCodigoAlimentoOrigem)
                .toList());
        TabelaNutricionalRefEntity tabela = entityManager.getReference(TabelaNutricionalRefEntity.class, tabelaId);
        java.util.List<AlimentoBaseEntity> loteNovo = new java.util.ArrayList<>();

        for (TbcaArquivo.Alimento alimento : alimentos) {
            if (codigosExistentes.contains(alimento.getCodigo())) continue;
            AlimentoBaseEntity alimentoEntity = new AlimentoBaseEntity();
            alimentoEntity.setTabela(tabela);
            alimentoEntity.setCodigoAlimentoOrigem(alimento.getCodigo());
            alimentoEntity.setNome(alimento.getNome());
            alimentoEntity.setTipoAlimento(alimento.getTipoAlimento());
            alimentoEntity.setGrupo(alimento.getGrupo());
            alimentoEntity.setNomeCientifico(alimento.getNomeCientifico());

            if (alimento.getPorcoes() != null) for (TbcaArquivo.Porcao porcao : alimento.getPorcoes()) {
                AlimentoPorcaoEntity porcaoEntity = new AlimentoPorcaoEntity();
                porcaoEntity.setAlimento(alimentoEntity);
                porcaoEntity.setDescricao(porcao.getDescricao());
                porcaoEntity.setPesoGramas(porcao.getPesoGramas());
                porcaoEntity.setPorcaoPadrao(porcao.isPorcaoPadrao());
                alimentoEntity.getPorcoes().add(porcaoEntity);

                if (porcao.getNutrientes() == null) continue;
                for (var nutriente : porcao.getNutrientes().entrySet()) {
                    NutrienteEnum definicao = NutrienteEnum.deChaveJson(nutriente.getKey());
                    AlimentoPorcaoNutrienteEntity nutrienteEntity = new AlimentoPorcaoNutrienteEntity();
                    nutrienteEntity.setPorcao(porcaoEntity);
                    nutrienteEntity.setIdentificadorNutriente(definicao.getChaveJson());
                    nutrienteEntity.setValor(nutriente.getValue());
                    String unidade = arquivo.getUnidades() == null ? null : arquivo.getUnidades().get(definicao.getChaveJson());
                    nutrienteEntity.setUnidadeMedida(unidade == null ? definicao.getUnidade() : unidade);
                    porcaoEntity.getNutrientes().add(nutrienteEntity);
                }
            }
            loteNovo.add(alimentoEntity);
        }
        if (loteNovo.isEmpty()) return 0;
        alimentoRepository.saveAllAndFlush(loteNovo);
        entityManager.clear();
        return loteNovo.size();
    }
}
