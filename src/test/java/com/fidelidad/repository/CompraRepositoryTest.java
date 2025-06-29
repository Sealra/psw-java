package com.fidelidad.repository;

import com.fidelidad.model.Compra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CompraRepositoryTest {
    
    private CompraRepository repository;
    
    @BeforeEach
    public void setUp() {
        repository = new CompraRepository();
    }
    
    @Test
    public void testSaveNewCompra() {
        Compra compra = new Compra(0, 100, 150.50, LocalDate.now());
        
        Compra savedCompra = repository.save(compra);
        
        assertEquals(1, savedCompra.getId());
        assertEquals(100, savedCompra.getIdCliente());
        assertEquals(150.50, savedCompra.getMonto(), 0.001);
        assertNotNull(savedCompra.getFecha());
    }
    
    @Test
    public void testSaveUpdateCompra() {
        Compra compra = new Compra(0, 101, 200.75, LocalDate.now());
        Compra savedCompra = repository.save(compra);
        int id = savedCompra.getId();
        
        Compra updatedCompra = new Compra(id, 101, 300.00, LocalDate.now());
        Compra result = repository.save(updatedCompra);
        
        assertEquals(id, result.getId());
        assertEquals(300.00, result.getMonto());
    }
    
    @Test
    public void testFindById() {
        Compra compra = new Compra(0, 102, 75.25, LocalDate.of(2025, 6, 1));
        Compra savedCompra = repository.save(compra);
        
        Optional<Compra> found = repository.findById(savedCompra.getId());
        
        assertTrue(found.isPresent());
        assertEquals(savedCompra.getId(), found.get().getId());
        assertEquals(102, found.get().getIdCliente());
        assertEquals(75.25, found.get().getMonto());
    }
    
    @Test
    public void testFindByIdNotFound() {
        Optional<Compra> found = repository.findById(999);
        
        assertFalse(found.isPresent());
    }
    
    @Test
    public void testFindByClienteId() {
        repository.save(new Compra(0, 100, 50.0, LocalDate.now()));
        repository.save(new Compra(0, 100, 75.0, LocalDate.now()));
        repository.save(new Compra(0, 101, 100.0, LocalDate.now()));
        
        List<Compra> comprasCliente100 = repository.findByClienteId(100);
        
        assertEquals(2, comprasCliente100.size());
        
        for (Compra compra : comprasCliente100) {
            assertEquals(100, compra.getIdCliente());
        }
    }
    
    @Test
    public void testFindByClienteIdNotFound() {
        repository.save(new Compra(0, 100, 50.0, LocalDate.now()));
        
        List<Compra> compras = repository.findByClienteId(999);
        
        assertEquals(0, compras.size());
    }
    
    @Test
    public void testDeleteById() {
        Compra compra = repository.save(new Compra(0, 103, 120.50, LocalDate.now()));
        int id = compra.getId();
        
        assertTrue(repository.findById(id).isPresent());
        
        repository.deleteById(id);
        
        assertFalse(repository.findById(id).isPresent());
    }
}