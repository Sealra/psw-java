package com.fidelidad.repository;

import com.fidelidad.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteRepositoryTest {
    
    private ClienteRepository repository;
    
    @BeforeEach
    public void setUp() {
        repository = new ClienteRepository();
    }
    
    @Test
    public void testSaveNewCliente() {
        Cliente cliente = new Cliente(0, "John Doe", "john@example.com");
        
        Cliente savedCliente = repository.save(cliente);
        
        assertEquals(1, savedCliente.getId());
        assertEquals("John Doe", savedCliente.getNombre());
        assertEquals("john@example.com", savedCliente.getCorreo());
    }
    
    @Test
    public void testSaveExistingCliente() {
        Cliente cliente = new Cliente(0, "John Doe", "john@example.com");
        Cliente savedCliente = repository.save(cliente);
        int id = savedCliente.getId();

        savedCliente.setNombre("John A. Doe");
        Cliente updatedCliente = repository.save(savedCliente);
        
        assertEquals(id, updatedCliente.getId());
        assertEquals("John A. Doe", updatedCliente.getNombre());
    }
    
    @Test
    public void testFindById() {
        Cliente cliente = new Cliente(0, "John Doe", "john@example.com");
        Cliente savedCliente = repository.save(cliente);
        
        Optional<Cliente> found = repository.findById(savedCliente.getId());
        
        assertTrue(found.isPresent());
        assertEquals(savedCliente.getId(), found.get().getId());
        assertEquals("John Doe", found.get().getNombre());
    }
    
    @Test
    public void testFindByIdNotFound() {
        Optional<Cliente> found = repository.findById(999);
        
        assertFalse(found.isPresent());
    }
    
    @Test
    public void testFindAll() {
        List<Cliente> clientes = repository.findAll();
        assertEquals(0, clientes.size());

        repository.save(new Cliente(0, "John Doe", "john@example.com"));
        repository.save(new Cliente(0, "User Doe", "user@example.com"));

        clientes = repository.findAll();
        
        assertEquals(2, clientes.size());
    }
    
    @Test
    public void testDeleteById() {
        Cliente cliente = repository.save(new Cliente(0, "John Doe", "john@example.com"));
        int id = cliente.getId();
        
        assertTrue(repository.existsById(id));
        
        repository.deleteById(id);
        
        assertFalse(repository.existsById(id));
    }
    
    @Test
    public void testExistsById() {
        assertFalse(repository.existsById(1));

        Cliente cliente = repository.save(new Cliente(0, "John Doe", "john@example.com"));

        assertTrue(repository.existsById(cliente.getId()));
    }
    
    @Test
    public void testMultipleClienteIds() {
        Cliente c1 = repository.save(new Cliente(0, "Cliente 1", "cliente1@example.com"));
        Cliente c2 = repository.save(new Cliente(0, "Cliente 2", "cliente2@example.com"));
        Cliente c3 = repository.save(new Cliente(0, "Cliente 3", "cliente3@example.com"));
        
        assertEquals(1, c1.getId());
        assertEquals(2, c2.getId());
        assertEquals(3, c3.getId());
    }
}