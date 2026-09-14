package com.nfw.tbca.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TbcaArquivo {
    private String nome;
    private String versao;
    private String descricao;
    private Map<String, String> unidades = new LinkedHashMap<>();
    private List<Alimento> alimentos;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getVersao() { return versao; }
    public void setVersao(String versao) { this.versao = versao; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Map<String, String> getUnidades() { return unidades; }
    public void setUnidades(Map<String, String> unidades) { this.unidades = unidades; }
    public List<Alimento> getAlimentos() { return alimentos; }
    public void setAlimentos(List<Alimento> alimentos) { this.alimentos = alimentos; }

    public static class Alimento {
        private String codigo;
        private String nome;
        private List<Porcao> porcoes;
        public String getCodigo() { return codigo; }
        public void setCodigo(String codigo) { this.codigo = codigo; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public List<Porcao> getPorcoes() { return porcoes; }
        public void setPorcoes(List<Porcao> porcoes) { this.porcoes = porcoes; }
    }

    public static class Porcao {
        private String descricao;
        private BigDecimal quantidade;
        @JsonProperty("unidade_medida") private String unidadeMedida;
        @JsonProperty("peso_gramas") private BigDecimal pesoGramas;
        @JsonProperty("porcao_padrao") private boolean porcaoPadrao;
        private Map<String, BigDecimal> nutrientes = new LinkedHashMap<>();
        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }
        public BigDecimal getQuantidade() { return quantidade; }
        public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }
        public String getUnidadeMedida() { return unidadeMedida; }
        public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }
        public BigDecimal getPesoGramas() { return pesoGramas; }
        public void setPesoGramas(BigDecimal pesoGramas) { this.pesoGramas = pesoGramas; }
        public boolean isPorcaoPadrao() { return porcaoPadrao; }
        public void setPorcaoPadrao(boolean porcaoPadrao) { this.porcaoPadrao = porcaoPadrao; }
        public Map<String, BigDecimal> getNutrientes() { return nutrientes; }
        public void setNutrientes(Map<String, BigDecimal> nutrientes) { this.nutrientes = nutrientes; }
    }
}
