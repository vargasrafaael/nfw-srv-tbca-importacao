package com.nfw.tbca.adapter.output.persistence;

import com.nfw.tbca.adapter.output.persistence.entity.TabelaNutricionalRefEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TabelaNutricionalJpaRepository extends JpaRepository<TabelaNutricionalRefEntity, Long> {
	java.util.Optional<TabelaNutricionalRefEntity> findByCodigo(String codigo);
}
