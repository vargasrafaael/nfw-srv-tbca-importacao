package com.nfw.tbca.domain.usecase;

import com.nfw.tbca.domain.model.TbcaArquivo;
import com.nfw.tbca.port.input.ImportacaoResultado;
import com.nfw.tbca.port.input.ImportarTbcaPort;
import com.nfw.tbca.port.output.TbcaArquivoPort;
import org.springframework.stereotype.Service;
import java.nio.file.Path;

@Service
public class ImportarTbcaUseCase implements ImportarTbcaPort {
    private final TbcaArquivoPort arquivoPort;
    private final ImportarArquivoTbcaUseCase importarArquivo;

    public ImportarTbcaUseCase(TbcaArquivoPort arquivoPort, ImportarArquivoTbcaUseCase importarArquivo) {
        this.arquivoPort = arquivoPort;
        this.importarArquivo = importarArquivo;
    }

    @Override
    public ImportacaoResultado importarPendentes() {
        int arquivos = 0;
        int alimentos = 0;
        for (Path arquivo : arquivoPort.listarPendentes()) {
            TbcaArquivo dados = arquivoPort.ler(arquivo);
            importarArquivo.importar(arquivo, dados);
            arquivoPort.moverParaProcessados(arquivo);
            arquivos++;
            alimentos += dados.getAlimentos() == null ? 0 : dados.getAlimentos().size();
        }
        return new ImportacaoResultado(arquivos, alimentos);
    }
}
