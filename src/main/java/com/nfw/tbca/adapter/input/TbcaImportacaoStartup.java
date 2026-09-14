package com.nfw.tbca.adapter.input;

import com.nfw.tbca.port.input.ImportarTbcaPort;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TbcaImportacaoStartup implements CommandLineRunner {
    private final ImportarTbcaPort importarTbcaPort;
    public TbcaImportacaoStartup(ImportarTbcaPort importarTbcaPort) {
        this.importarTbcaPort = importarTbcaPort;
    }
    @Override
    public void run(String... args) {
        importarTbcaPort.importarPendentes();
    }
}
