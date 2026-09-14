package com.nfw.tbca.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nfw.tbca.domain.model.TbcaArquivo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TbcaArquivoJsonTest {
    @Test
    void deveLerMetadadosDoAlimento() throws Exception {
        String json = """
                {
                  "alimentos": [{
                    "codigo": "BRC0001C",
                    "nome": "Abacate, polpa, in natura, Brasil",
                    "tipo_alimento": "A - Alimento in natura",
                    "grupo": "C - Frutas e derivados",
                    "nome_cientifico": "Persea americana Mill"
                  }]
                }
                """;

        TbcaArquivo.Alimento alimento = new ObjectMapper()
                .readValue(json, TbcaArquivo.class)
                .getAlimentos()
                .get(0);

        assertEquals("A - Alimento in natura", alimento.getTipoAlimento());
        assertEquals("C - Frutas e derivados", alimento.getGrupo());
        assertEquals("Persea americana Mill", alimento.getNomeCientifico());
    }
}
