package com.fidelidad.model;

import java.time.LocalDate;

public class Compra {
    private int id;
    private int idCliente;
    private double monto;
    private LocalDate fecha;
    
    public Compra(int id, int idCliente, double monto, LocalDate fecha) {
        this.id = id;
        this.idCliente = idCliente;
        
        if (monto < 0) {
            throw new IllegalArgumentException("El monto de la compra no puede ser negativo");
        }
        this.monto = monto;
        
        this.fecha = fecha;
    }
    
    public int getId() {
        return id;
    }
    
    public int getIdCliente() {
        return idCliente;
    }
    
    public double getMonto() {
        return monto;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }
}