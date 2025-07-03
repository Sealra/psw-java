package com.fidelidad.service;

import java.time.LocalDate;
import java.util.List;

import com.fidelidad.model.Cliente;
import com.fidelidad.model.Compra;
import com.fidelidad.repository.CompraRepository;

public class PuntosService {
    private CompraRepository compraRepository;
    
    public PuntosService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }
    
    public int calcularPuntos(Cliente cliente, double monto, LocalDate fecha) {
        // 1 punto por cada $100
        int puntosBase = (int) (monto / 100);
        
        // Aplicar multiplicador
        double multiplicador = cliente.getNivel().getMultiplicador();
        int puntosPorNivel = (int) (puntosBase * multiplicador);
        
        int puntosBonus = calcularPuntosBonus(cliente, fecha);
        
        return puntosPorNivel + puntosBonus;
    }
    
    private int calcularPuntosBonus(Cliente cliente, LocalDate fecha) {
        List<Compra> comprasDelDia = compraRepository.findByClienteId(cliente.getId()).stream()
                .filter(c -> c.getFecha().equals(fecha))
                .toList();
        
        // size() = 2 porque la nueva compra seria la 3ra
        if (comprasDelDia.size() == 2) {
            return 10;
        }
        
        return 0;
    }
}