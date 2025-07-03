package com.fidelidad.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import com.fidelidad.model.Cliente;
import com.fidelidad.model.Nivel;
import com.fidelidad.repository.ClienteRepository;

public class ClienteServiceTest {
    private ClienteService clienteService;
    
    @BeforeEach
    public void setUp() {
        clienteService = new ClienteService(new ClienteRepository());
    }
    
    @Test
    public void testCrearCliente() {
        Cliente cliente = clienteService.crearCliente("John Doe", "john@example.com");
        
        assertEquals(1, cliente.getId());
        assertEquals("John Doe", cliente.getNombre());
        assertEquals("john@example.com", cliente.getCorreo());
        assertEquals(0, cliente.getPuntos());
        assertEquals(Nivel.BRONCE, cliente.getNivel());
    }
    
    @Test
    public void testCrearClienteInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            clienteService.crearCliente("John Doe", "johnexample.com");
        });
    }
    
    @Test
    public void testBuscarPorId() {
        Cliente cliente = clienteService.crearCliente("John Doe", "john@example.com");
        
        Optional<Cliente> res = clienteService.buscarPorId(cliente.getId());
        assertTrue(res.isPresent());
        assertEquals("John Doe", res.get().getNombre());
    }
    
    @Test
    public void testBuscarPorIdNoExistente() {
        Optional<Cliente> res = clienteService.buscarPorId(123);
        assertFalse(res.isPresent());
    }
    
    @Test
    public void testListarTodos() {
        clienteService.crearCliente("John Doe", "john@example.com");
        clienteService.crearCliente("Test testing", "test@example.com");
        
        List<Cliente> clientes = clienteService.listarTodos();
        assertEquals(2, clientes.size());
    }
    
    @Test
    public void testActualizarCliente() {
        Cliente cliente = clienteService.crearCliente("John Doe", "john@example.com");
        
        Cliente act = clienteService.actualizarCliente(cliente.getId(), "Test testing", "test@example.com");
        
        assertEquals("Test testing", act.getNombre());
        assertEquals("test@example.com", act.getCorreo());
    }
    
    @Test
    public void testActualizarClienteNoExistente() {
        assertThrows(IllegalArgumentException.class, () -> {
            clienteService.actualizarCliente(123, "John Doe", "john@example.com");
        });
    }
    
    @Test
    public void testEliminarCliente() {
        Cliente cliente = clienteService.crearCliente("John Doe", "john@example.com");
        
        clienteService.eliminarCliente(cliente.getId());
        
        Optional<Cliente> res = clienteService.buscarPorId(cliente.getId());
        assertFalse(res.isPresent());
    }
    
    @Test
    public void testEliminarClienteNoExistente() {
        assertThrows(IllegalArgumentException.class, () -> {
            clienteService.eliminarCliente(123);
        });
    }
    
    @Test
    public void testAgregarPuntos() {
        Cliente cliente = clienteService.crearCliente("John Doe", "john@example.com");
        
        clienteService.agregarPuntos(cliente.getId(), 100);
        
        Optional<Cliente> clienteAct = clienteService.buscarPorId(cliente.getId());
        assertTrue(clienteAct.isPresent());
        assertEquals(100, clienteAct.get().getPuntos());
        assertEquals(Nivel.BRONCE, clienteAct.get().getNivel());
        
        clienteService.agregarPuntos(cliente.getId(), 500);
        clienteAct = clienteService.buscarPorId(cliente.getId());
        assertEquals(600, clienteAct.get().getPuntos());
        assertEquals(Nivel.PLATA, clienteAct.get().getNivel());
    }
}