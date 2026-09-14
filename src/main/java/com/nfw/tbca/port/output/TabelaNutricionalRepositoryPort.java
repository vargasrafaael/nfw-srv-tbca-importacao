package com.nfw.tbca.port.output;

import com.nfw.tbca.domain.model.TbcaArquivo;
import java.util.List;

public interface TabelaNutricionalRepositoryPort {
    Long criarOuObterTabela(TbcaArquivo arquivo);
    int salvarLote(Long tabelaId, TbcaArquivo arquivo, List<TbcaArquivo.Alimento> alimentos);
}
