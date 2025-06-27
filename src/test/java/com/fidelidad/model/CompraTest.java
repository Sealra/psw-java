package com.fidelidad.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class CompraTest {

    @Test
    public void testCrearCompra() {
        int id = 1;
        int idCliente = 100;
        double monto = 150.50;
        LocalDate fecha = LocalDate.of(2025, 6, 27);
        
        Compra compra = new Compra(id, idCliente, monto, fecha);
        
        assertEquals(id, compra.getId());
        assertEquals(idCliente, compra.getIdCliente());
        assertEquals(monto, compra.getMonto());
        assertEquals(fecha, compra.getFecha());
    }
    
    @Test
    public void testCompraConMontoNegativo() {
        int id = 2;
        int idCliente = 101;
        double montoNegativo = -50.25;
        LocalDate fecha = LocalDate.now();
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Compra(id, idCliente, montoNegativo, fecha);
        });
        
        String expectedMessage = "El monto de la compra no puede ser negativo";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCompraConMontoCero() {
        int id = 3;
        int idCliente = 102;
        double monto = 0.0;
        LocalDate fecha = LocalDate.now();
        
        Compra compra = new Compra(id, idCliente, monto, fecha);
        
        assertEquals(0.0, compra.getMonto());
    }
    
    @Test
    public void testFechaAntigua() {
        int id = 4;
        int idCliente = 103;
        double monto = 75.0;
        LocalDate fechaAntigua = LocalDate.of(2020, 1, 1);
        
        Compra compra = new Compra(id, idCliente, monto, fechaAntigua);
        
        assertEquals(fechaAntigua, compra.getFecha());
    }
    
    @Test
    public void testFechaFutura() {
        int id = 5;
        int idCliente = 104;
        double monto = 200.0;
        LocalDate fechaFutura = LocalDate.now().plusYears(1);
        
        Compra compra = new Compra(id, idCliente, monto, fechaFutura);
        
        assertEquals(fechaFutura, compra.getFecha());
    }
}