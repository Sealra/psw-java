package com.fidelidad.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import com.fidelidad.model.Cliente;
import com.fidelidad.model.Compra;
import com.fidelidad.repository.CompraRepository;

public class PuntosServiceTest {
    private PuntosService puntosService;
    private CompraRepository compraRepository;
    
    @BeforeEach
    public void setUp() {
        compraRepository = new CompraRepository();
        puntosService = new PuntosService(compraRepository);
    }
    
    @Test
    public void testCalcularPuntosBase() {
        Cliente cliente = new Cliente(1, "John Doe", "john@example.com");
        
        int puntos = puntosService.calcularPuntos(cliente, 450, LocalDate.now());
        assertEquals(4, puntos);
        
        // 1050$ = 10 puntos x 1
        puntos = puntosService.calcularPuntos(cliente, 1050, LocalDate.now());
        assertEquals(10, puntos);
    }
    
    @Test
    public void testCalcularPuntosPlata() {
        Cliente cliente = new Cliente(1, "John Doe", "john@example.com");
        cliente.setPuntos(500);
        cliente.actualizarNivel(); // Actualizar a nivel PLATA
        
        // 1000$ = 10 puntos x 1.2 = 12 puntos
        int puntos = puntosService.calcularPuntos(cliente, 1000, LocalDate.now());
        assertEquals(12, puntos);
    }
    
    @Test
    public void testCalcularPuntosOro() {
        Cliente cliente = new Cliente(1, "John Doe", "john@example.com");
        cliente.setPuntos(1500);
        cliente.actualizarNivel(); // Actualizar a nivel ORO

        // 1000$ = 10 puntos x 1.5 = 15 puntos
        int puntos = puntosService.calcularPuntos(cliente, 1000, LocalDate.now());
        assertEquals(15, puntos);
    }
    
    @Test
    public void testCalcularPuntosPlatino() {
        Cliente cliente = new Cliente(1, "John Doe", "john@example.com");
        cliente.setPuntos(3000);
        cliente.actualizarNivel(); // Actualizar a nivel PLATINO

        // 1000$ = 10 puntos x 2 = 20 puntos
        int puntos = puntosService.calcularPuntos(cliente, 1000, LocalDate.now());
        assertEquals(20, puntos);
    }
    
    @Test
    public void testCalcularPuntosBonus() {
        Cliente cliente = new Cliente(1, "John Doe", "john@example.com");
        LocalDate hoy = LocalDate.now();
        
        compraRepository.save(new Compra(1, cliente.getId(), 100, hoy));
        compraRepository.save(new Compra(2, cliente.getId(), 200, hoy));
        
        // should give bonus for 3rd buy
        int puntos = puntosService.calcularPuntos(cliente, 300, hoy);
        assertEquals(13, puntos);
    }
    
    @Test
    public void testCalcularPuntosSinBonus() {
        Cliente cliente = new Cliente(1, "Juan Pérez", "juan@example.com");
        LocalDate hoy = LocalDate.now();
        
        compraRepository.save(new Compra(1, cliente.getId(), 100, hoy));
        
        int puntos = puntosService.calcularPuntos(cliente, 300, hoy);
        assertEquals(3, puntos);
    }
}