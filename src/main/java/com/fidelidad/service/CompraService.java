package com.fidelidad.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.fidelidad.model.Cliente;
import com.fidelidad.model.Compra;
import com.fidelidad.repository.CompraRepository;

public class CompraService {
    private CompraRepository compraRepository;
    private ClienteService clienteService;
    private PuntosService puntosService;
    
    public CompraService(CompraRepository compraRepository, ClienteService clienteService, PuntosService puntosService) {
        this.compraRepository = compraRepository;
        this.clienteService = clienteService;
        this.puntosService = puntosService;
    }
    
    public Compra registrarCompra(int clienteId, double monto) {
        Cliente cliente = clienteService.buscarPorId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + clienteId));
        
        LocalDate fechaHoy = LocalDate.now();
        Compra newCompra = new Compra(0, clienteId, monto, fechaHoy);
        Compra compraGuardada = compraRepository.save(newCompra);

        // Calcular y agregar puntos de la compra
        int puntosGanados = puntosService.calcularPuntos(cliente, monto, fechaHoy);
        clienteService.agregarPuntos(clienteId, puntosGanados);
        
        return compraGuardada;
    }
    
    public List<Compra> listarCompras() {
        return compraRepository.findAll();
    }
    
    public List<Compra> listarComprasPorCliente(int clienteId) {
        return compraRepository.findByClienteId(clienteId);
    }
    
    public Optional<Compra> buscarCompraPorId(int id) {
        return compraRepository.findById(id);
    }
    
    public void eliminarCompra(int id) {
        if (!compraRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Compra no encontrada con ID: " + id);
        }
        compraRepository.deleteById(id);
    }
}