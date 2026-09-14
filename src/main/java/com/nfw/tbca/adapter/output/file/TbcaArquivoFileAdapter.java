package com.nfw.tbca.adapter.output.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nfw.tbca.config.TbcaImportacaoProperties;
import com.nfw.tbca.domain.model.TbcaArquivo;
import com.nfw.tbca.port.output.TbcaArquivoPort;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Component
public class TbcaArquivoFileAdapter implements TbcaArquivoPort {
    private final ObjectMapper objectMapper;
    private final TbcaImportacaoProperties properties;

    public TbcaArquivoFileAdapter(ObjectMapper objectMapper, TbcaImportacaoProperties properties) {
        this.objectMapper = objectMapper;
        this.properties = properties;
    }

    @Override
    public List<Path> listarPendentes() {
        Path pasta = Path.of(properties.getPastaPendentes());
        try {
            Files.createDirectories(pasta);
            try (var arquivos = Files.list(pasta)) {
                return arquivos.filter(Files::isRegularFile)
                        .filter(path -> path.getFileName().toString().toLowerCase().endsWith(".json"))
                        .sorted()
                        .toList();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível listar arquivos pendentes em " + pasta, e);
        }
    }

    @Override
    public TbcaArquivo ler(Path arquivo) {
        try {
            return objectMapper.readValue(arquivo.toFile(), TbcaArquivo.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("JSON TBCA inválido: " + arquivo.getFileName(), e);
        }
    }

    @Override
    public void moverParaProcessados(Path arquivo) {
        Path destino = Path.of(properties.getPastaProcessados());
        try {
            Files.createDirectories(destino);
            Files.move(arquivo, destino.resolve(arquivo.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível mover o arquivo processado: " + arquivo, e);
        }
    }
}
