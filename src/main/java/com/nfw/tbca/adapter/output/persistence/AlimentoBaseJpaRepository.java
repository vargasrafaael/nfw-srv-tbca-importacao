package com.nfw.tbca.adapter.output.persistence;

import com.nfw.tbca.adapter.output.persistence.entity.AlimentoBaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimentoBaseJpaRepository extends JpaRepository<AlimentoBaseEntity, Long> {
	java.util.List<AlimentoBaseEntity> findByTabela_IdAndCodigoAlimentoOrigemIn(Long tabelaId, java.util.Collection<String> codigos);
}