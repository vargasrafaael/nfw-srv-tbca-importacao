package com.nfw.tbca.domain;

import com.nfw.tbca.domain.model.NutrienteEnum;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NutrienteEnumTest {
    @Test
    void deveResolverNutrientePelaChaveJsonIgnorandoMaiusculas() {
        assertEquals(NutrienteEnum.VITAMINA_C, NutrienteEnum.deChaveJson("VITAMINA_C"));
        assertEquals("mg", NutrienteEnum.CALCIO.getUnidade());
    }

    @Test
    void deveRejeitarNutrienteDesconhecido() {
        assertThrows(IllegalArgumentException.class, () -> NutrienteEnum.deChaveJson("vitamina_inexistente"));
    }
}
