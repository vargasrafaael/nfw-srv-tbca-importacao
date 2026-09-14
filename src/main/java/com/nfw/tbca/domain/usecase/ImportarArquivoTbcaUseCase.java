package com.nfw.tbca.domain.usecase;

import com.nfw.tbca.config.TbcaImportacaoProperties;
import com.nfw.tbca.domain.model.TbcaArquivo;
import com.nfw.tbca.port.output.TabelaNutricionalRepositoryPort;
import org.springframework.stereotype.Service;
import java.nio.file.Path;
import java.util.ArrayList;

@Service
public class ImportarArquivoTbcaUseCase {
    private final TabelaNutricionalRepositoryPort repository;
    private final TbcaImportacaoProperties properties;

    public ImportarArquivoTbcaUseCase(TabelaNutricionalRepositoryPort repository, TbcaImportacaoProperties properties) {
        this.repository = repository;
        this.properties = properties;
    }

    public int importar(Path arquivo, TbcaArquivo dados) {
        if (dados.getNome() == null || dados.getNome().isBlank()
                || dados.getVersao() == null || dados.getVersao().isBlank()) {
            throw new IllegalArgumentException("O JSON deve informar nome e versao: " + arquivo.getFileName());
        }
        if (dados.getAlimentos() == null || dados.getAlimentos().isEmpty()) return 0;
        int tamanhoLote = properties.getTamanhoLote();
        if (tamanhoLote < 1) throw new IllegalArgumentException("O tamanho do lote deve ser maior que zero");
        Long tabelaId = repository.criarOuObterTabela(dados);
        int importados = 0;
        for (int inicio = 0; inicio < dados.getAlimentos().size(); inicio += tamanhoLote) {
            int fim = Math.min(inicio + tamanhoLote, dados.getAlimentos().size());
            importados += repository.salvarLote(tabelaId, dados, new ArrayList<>(dados.getAlimentos().subList(inicio, fim)));
        }
        return importados;
    }
}
