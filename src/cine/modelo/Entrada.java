package cine.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Entrada implements Serializable {
    private Cliente cliente;
    private Sala sala;
    private Butaca butaca;
    private LocalDateTime fechaCompra;
    
    public Entrada(Cliente cliente, Sala sala, Butaca butaca) {
        this.cliente = cliente;
        this.sala = sala;
        this.butaca = butaca;
        this.fechaCompra = LocalDateTime.now();
        butaca.ocupar();
    }
    
    public Cliente getCliente() { return cliente; }
    public Sala getSala() { return sala; }
    public Butaca getButaca() { return butaca; }
    public LocalDateTime getFechaCompra() { return fechaCompra; }
    
    @Override
    public String toString() {
        return "Entrada para: " + sala.getPelicula() + 
               " - Sala: " + sala.getNumero() + 
               " - Butaca: " + butaca.getFila() + butaca.getNumero();
    }
}