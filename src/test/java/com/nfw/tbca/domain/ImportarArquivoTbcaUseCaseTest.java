package com.nfw.tbca.domain;

import com.nfw.tbca.domain.model.TbcaArquivo;
import com.nfw.tbca.domain.usecase.ImportarArquivoTbcaUseCase;
import com.nfw.tbca.config.TbcaImportacaoProperties;
import com.nfw.tbca.port.output.TabelaNutricionalRepositoryPort;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ImportarArquivoTbcaUseCaseTest {
    @Test
    void deveUsarMetadadosEDelegarOAgregadoEmLote() {
        TabelaNutricionalRepositoryPort repository = mock(TabelaNutricionalRepositoryPort.class);
        TbcaImportacaoProperties properties = new TbcaImportacaoProperties();
        properties.setTamanhoLote(1);
        ImportarArquivoTbcaUseCase useCase = new ImportarArquivoTbcaUseCase(repository, properties);
        TbcaArquivo arquivo = new TbcaArquivo();
        arquivo.setNome("TBCA");
        arquivo.setVersao("7.3");
        arquivo.setDescricao("Tabela Brasileira de Composição de Alimentos");
        arquivo.setAlimentos(java.util.List.of(new TbcaArquivo.Alimento()));
        when(repository.criarOuObterTabela(arquivo)).thenReturn(10L);
        when(repository.salvarLote(eq(10L), eq(arquivo), anyList())).thenReturn(1);

        int alimentos = useCase.importar(Path.of("dados-tabela-tbca-07-09-2026-11-48-19.json"), arquivo);

        assertEquals(1, alimentos);
        verify(repository).criarOuObterTabela(arquivo);
        verify(repository).salvarLote(eq(10L), eq(arquivo), anyList());
    }
}
