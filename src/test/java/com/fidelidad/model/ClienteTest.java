package com.fidelidad.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    @Test
    public void testCrearCliente() {
        Cliente cliente = new Cliente(1, "John Doe", "john@example.com");
        
        assertEquals(1, cliente.getId());
        assertEquals("John Doe", cliente.getNombre());
        assertEquals("john@example.com", cliente.getCorreo());
        assertEquals(0, cliente.getPuntos());
        assertEquals(Nivel.BRONCE, cliente.getNivel());
        assertEquals(0, cliente.getStreakDias());
    }
    
    @Test
    public void testCorreoInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Cliente(1, "John Doe", "johnexample.com");
        });
        
        String expectedMessage = "Correo electrónico inválido";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void testActualizarNivel() {
        Cliente cliente = new Cliente(1, "John Doe", "john@example.com");
        
        assertEquals(Nivel.BRONCE, cliente.getNivel());
        
        cliente.setPuntos(500);
        cliente.actualizarNivel();
        assertEquals(Nivel.PLATA, cliente.getNivel());
        
        cliente.setPuntos(1500);
        cliente.actualizarNivel();
        assertEquals(Nivel.ORO, cliente.getNivel());
        
        cliente.setPuntos(3000);
        cliente.actualizarNivel();
        assertEquals(Nivel.PLATINO, cliente.getNivel());
    }
}