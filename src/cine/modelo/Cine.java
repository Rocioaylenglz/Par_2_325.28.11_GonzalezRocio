
package cine.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cine implements Serializable {
    private List<Sala> salas;
    private List<Entrada> entradas;
    private List<Cliente> clientes;
    
    public Cine() {
        this.salas = new ArrayList<>();
        this.entradas = new ArrayList<>();
        this.clientes = new ArrayList<>();
        inicializarDatosEjemplo();
    }
    
    private void inicializarDatosEjemplo() {
        salas.add(new Sala(1, "Avengers: Endgame"));    
        salas.add(new Sala(2, "The Batman"));         
        salas.add(new Sala(3, "Spider-Man: No Way Home")); 

        clientes.add(new Cliente("Juan Pérez", "juan@email.com", "1234"));
    }

    public Cliente buscarCliente(String email) {
        return clientes.stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
    
    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }
    
    public void agregarEntrada(Entrada entrada) {
        entradas.add(entrada);
    }
    
    public List<Entrada> getEntradasPorCliente(Cliente cliente) {
        List<Entrada> resultado = new ArrayList<>();
        for (Entrada entrada : entradas) {
            if (entrada.getCliente().getEmail().equals(cliente.getEmail())) {
                resultado.add(entrada);
            }
        }
        return resultado;
    }
    
    public List<Sala> getSalas() { return salas; }
    public List<Cliente> getClientes() { return clientes; }
}