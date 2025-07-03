package com.fidelidad.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import com.fidelidad.model.Cliente;
import com.fidelidad.model.Compra;
import com.fidelidad.repository.ClienteRepository;
import com.fidelidad.repository.CompraRepository;

public class CompraServiceTest {
    private CompraRepository compraRepository;
    private ClienteRepository clienteRepository;
    private ClienteService clienteService;
    private PuntosService puntosService;
    private CompraService compraService;
    
    private Cliente clientePrueba;
    
    @BeforeEach
    public void setUp() {
        compraRepository = new CompraRepository();
        clienteRepository = new ClienteRepository();
        clienteService = new ClienteService(clienteRepository);
        puntosService = new PuntosService(compraRepository);
        
        compraService = new CompraService(compraRepository, clienteService, puntosService);
        
        clientePrueba = clienteService.crearCliente("John Doe", "john@example.com");
    }
    
    @Test
    public void testRegistrarCompra() {
        Compra compra = compraService.registrarCompra(clientePrueba.getId(), 1000.0);
        
        assertNotNull(compra);
        assertEquals(1, compra.getId());
        assertEquals(clientePrueba.getId(), compra.getIdCliente());
        assertEquals(1000.0, compra.getMonto());
        assertNotNull(compra.getFecha());
        
        Cliente clienteActualizado = clienteService.buscarPorId(clientePrueba.getId()).get();
        assertTrue(clienteActualizado.getPuntos() > 0);
    }
    
    @Test
    public void testRegistrarCompraClienteNoExistente() {
        assertThrows(IllegalArgumentException.class, () -> {
            compraService.registrarCompra(999, 1000.0);
        });
    }
    
    @Test
    public void testListarCompras() {
        compraService.registrarCompra(clientePrueba.getId(), 1000.0);
        compraService.registrarCompra(clientePrueba.getId(), 2000.0);
        
        var compras = compraService.listarCompras();
        
        assertEquals(2, compras.size());
    }
    
    @Test
    public void testListarComprasPorCliente() {
        Cliente otroCliente = clienteService.crearCliente("Jane Doe", "jane@example.com");
        
        compraService.registrarCompra(clientePrueba.getId(), 1000.0);
        compraService.registrarCompra(clientePrueba.getId(), 2000.0);
        compraService.registrarCompra(otroCliente.getId(), 1500.0);
        
        var compras = compraService.listarComprasPorCliente(clientePrueba.getId());
        
        assertEquals(2, compras.size());
    }
    
    @Test
    public void testBuscarCompraPorId() {
        Compra compraCreada = compraService.registrarCompra(clientePrueba.getId(), 1000.0);
        
        Optional<Compra> res = compraService.buscarCompraPorId(compraCreada.getId());
        
        assertTrue(res.isPresent());
        assertEquals(compraCreada.getId(), res.get().getId());
    }
    
    @Test
    public void testEliminarCompra() {
        Compra compraCreada = compraService.registrarCompra(clientePrueba.getId(), 1000.0);
        
        compraService.eliminarCompra(compraCreada.getId());
        
        Optional<Compra> res = compraService.buscarCompraPorId(compraCreada.getId());
        assertFalse(res.isPresent());
    }
    
    @Test
    public void testEliminarCompraNoExistente() {
        assertThrows(IllegalArgumentException.class, () -> {
            compraService.eliminarCompra(999);
        });
    }
}