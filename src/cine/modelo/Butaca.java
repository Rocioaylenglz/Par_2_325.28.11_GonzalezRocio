
package cine.modelo;

import java.io.Serializable;

public class Butaca implements Serializable {
    private int numero;
    private String fila;
    private boolean ocupada;
    
    public Butaca(String fila, int numero) {
        this.fila = fila;
        this.numero = numero;
        this.ocupada = false;
    }
    
    public int getNumero() { return numero; }
    public String getFila() { return fila; }
    public boolean isOcupada() { return ocupada; }
    
    public void ocupar() { this.ocupada = true; }
    public void liberar() { this.ocupada = false; }
    
    @Override
    public String toString() {
        return fila + numero + " - " + (ocupada ? "OCUPADA" : "LIBRE");
    }
}