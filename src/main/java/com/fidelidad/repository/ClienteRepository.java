package com.fidelidad.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fidelidad.model.Cliente;

public class ClienteRepository {
    private Map<Integer, Cliente> clientes;
    private int nextId;
    
    public ClienteRepository() {
        this.clientes = new HashMap<>();
        this.nextId = 1;
    }
    
    public Cliente save(Cliente cliente) {
        if (cliente.getId() == 0) {
            cliente = new Cliente(nextId++, cliente.getNombre(), cliente.getCorreo());
        }
        clientes.put(cliente.getId(), cliente);
        return cliente;
    }
    
    public Optional<Cliente> findById(int id) {
        return Optional.ofNullable(clientes.get(id));
    }
    
    public List<Cliente> findAll() {
        return new ArrayList<>(clientes.values());
    }
    
    public void deleteById(int id) {
        clientes.remove(id);
    }
    
    public boolean existsById(int id) {
        return clientes.containsKey(id);
    }
}