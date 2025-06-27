package com.fidelidad.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NivelTest {

    @Test
    public void testNivelValues() {
        assertEquals(4, Nivel.values().length);
        assertNotNull(Nivel.BRONCE);
        assertNotNull(Nivel.PLATA);
        assertNotNull(Nivel.ORO);
        assertNotNull(Nivel.PLATINO);
    }

    @Test
    public void testNivelMultiplicadores() {
        assertEquals(1.0, Nivel.BRONCE.getMultiplicador());
        assertEquals(1.2, Nivel.PLATA.getMultiplicador());
        assertEquals(1.5, Nivel.ORO.getMultiplicador());
        assertEquals(2.0, Nivel.PLATINO.getMultiplicador());
    }
}