package com.nfw.tbca.port.output;

import com.nfw.tbca.domain.model.TbcaArquivo;
import java.nio.file.Path;
import java.util.List;

public interface TbcaArquivoPort {
    List<Path> listarPendentes();
    TbcaArquivo ler(Path arquivo);
    void moverParaProcessados(Path arquivo);
}
