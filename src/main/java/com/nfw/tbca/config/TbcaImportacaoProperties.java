package com.nfw.tbca.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tbca.importacao")
public class TbcaImportacaoProperties {
    private String pastaPendentes;
    private String pastaProcessados;
    private int tamanhoLote = 100;
    public String getPastaPendentes() { return pastaPendentes; }
    public void setPastaPendentes(String pastaPendentes) { this.pastaPendentes = pastaPendentes; }
    public String getPastaProcessados() { return pastaProcessados; }
    public void setPastaProcessados(String pastaProcessados) { this.pastaProcessados = pastaProcessados; }
    public int getTamanhoLote() { return tamanhoLote; }
    public void setTamanhoLote(int tamanhoLote) { this.tamanhoLote = tamanhoLote; }
}
