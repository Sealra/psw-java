package com.fidelidad.service;

import java.util.List;
import java.util.Optional;

import com.fidelidad.model.Cliente;
import com.fidelidad.repository.ClienteRepository;

public class ClienteService {
    private ClienteRepository repository;
    
    public ClienteService() {
        this.repository = new ClienteRepository();
    }
    
    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }
    
    public Cliente crearCliente(String nombre, String correo) {
        Cliente nuevoCliente = new Cliente(0, nombre, correo);
        return repository.save(nuevoCliente);
    }
    
    public Optional<Cliente> buscarPorId(int id) {
        return repository.findById(id);
    }
    
    public List<Cliente> listarTodos() {
        return repository.findAll();
    }
    
    public Cliente actualizarCliente(int id, String nombre, String correo) {
        return repository.findById(id).map(cliente -> {
            cliente.setNombre(nombre);
            cliente.setCorreo(correo);
            return repository.save(cliente);
        }).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));
    }
    
    public void eliminarCliente(int id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Cliente no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }
    
    public void agregarPuntos(int clienteId, int puntos) {
        Cliente cliente = repository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + clienteId));
                
        cliente.setPuntos(cliente.getPuntos() + puntos);
        cliente.actualizarNivel();
        repository.save(cliente);
    }
}