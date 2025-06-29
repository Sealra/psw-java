package com.fidelidad.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fidelidad.model.Compra;

public class CompraRepository {
    private Map<Integer, Compra> compras;
    private int nextId;
    
    public CompraRepository() {
        this.compras = new HashMap<>();
        this.nextId = 1;
    }
    
    public Compra save(Compra compra) {
        if (compra.getId() == 0) {
            compra = new Compra(nextId++, compra.getIdCliente(), compra.getMonto(), compra.getFecha());
        }
        compras.put(compra.getId(), compra);
        return compra;
    }
    
    public Optional<Compra> findById(int id) {
        return Optional.ofNullable(compras.get(id));
    }
    
    public List<Compra> findAll() {
        return new ArrayList<>(compras.values());
    }

    public List<Compra> findByClienteId(int clienteId) {
        return compras.values().stream()
                .filter(compra -> compra.getIdCliente() == clienteId)
                .collect(Collectors.toList());
    }
    
    public void deleteById(int id) {
        compras.remove(id);
    }
}