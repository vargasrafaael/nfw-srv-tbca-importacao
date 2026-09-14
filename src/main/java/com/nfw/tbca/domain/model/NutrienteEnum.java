package com.nfw.tbca.domain.model;

public enum NutrienteEnum {
    ENERGIA_KCAL("energia_kcal", "Energia", "kcal"),
    CARBOIDRATO_TOTAL("carboidrato_total", "Carboidrato Total", "g"),
    PROTEINA("proteina", "Proteína", "g"),
    LIPIDIOS("lipidios", "Lipídios", "g"),
    FIBRA_ALIMENTAR("fibra_alimentar", "Fibra Alimentar", "g"),
    SATURADOS("saturados", "Gorduras Saturadas", "g"),
    MONOINSATURADOS("monoinsaturados", "Gorduras Monoinsaturadas", "g"),
    POLIINSATURADOS("poliinsaturados", "Gorduras Poli-insaturadas", "g"),
    TRANS("trans", "Gorduras Trans", "g"),
    COLESTEROL("colesterol", "Colesterol", "mg"),
    CALCIO("calcio", "Cálcio", "mg"),
    FERRO("ferro", "Ferro", "mg"),
    SODIO("sodio", "Sódio", "mg"),
    POTASSIO("potassio", "Potássio", "mg"),
    MAGNESIO("magnesio", "Magnésio", "mg"),
    ZINCO("zinco", "Zinco", "mg"),
    VITAMINA_C("vitamina_c", "Vitamina C", "mg"),
    VITAMINA_D("vitamina_d", "Vitamina D", "mcg"),
    VITAMINA_B12("vitamina_b12", "Vitamina B12", "mcg"),
    VITAMINA_A("vitamina_a", "Vitamina A", "mcg"),
    FOLATO("folato", "Folato", "mcg");

    private final String chaveJson;
    private final String descricao;
    private final String unidade;

    NutrienteEnum(String chaveJson, String descricao, String unidade) {
        this.chaveJson = chaveJson;
        this.descricao = descricao;
        this.unidade = unidade;
    }

    public static NutrienteEnum deChaveJson(String chave) {
        for (NutrienteEnum nutriente : values()) {
            if (nutriente.chaveJson.equalsIgnoreCase(chave)) return nutriente;
        }
        throw new IllegalArgumentException("Nutriente desconhecido: " + chave);
    }

    public String getChaveJson() { return chaveJson; }
    public String getDescricao() { return descricao; }
    public String getUnidade() { return unidade; }
}
