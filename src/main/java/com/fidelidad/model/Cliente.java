package com.fidelidad.model;

public class Cliente {
    private int id;
    private String nombre;
    private String correo;
    private int puntos;
    private Nivel nivel;
    private int streakDias;
    
    public Cliente(int id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.streakDias = 0;
        if (!correoValido(correo)) {
            throw new IllegalArgumentException("Correo electrónico inválido");
        }
        this.correo = correo;
        
        this.puntos = 0;
        this.nivel = Nivel.BRONCE;
        this.streakDias = 0;
    }
    
    private boolean correoValido(String correo) {
        if (correo == null) {
            return false;
        }
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return correo.matches(regex);
    }
    
    public void actualizarNivel() {
        if (puntos >= 3000) {
            nivel = Nivel.PLATINO;
        } else if (puntos >= 1500) {
            nivel = Nivel.ORO;
        } else if (puntos >= 500) {
            nivel = Nivel.PLATA;
        } else {
            nivel = Nivel.BRONCE;
        }
    }
    
    // Getters and setters
    public int getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        if (!correoValido(correo)) {
            throw new IllegalArgumentException("Correo electrónico inválido");
        }
        this.correo = correo;
    }
    
    public int getPuntos() {
        return puntos;
    }
    
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
    
    public Nivel getNivel() {
        return nivel;
    }
    
    public int getStreakDias() {
        return streakDias;
    }

    public void setStreakDias(int streakDias) {
        this.streakDias = streakDias;
    }
}