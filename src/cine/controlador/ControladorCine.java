package cine.controlador;

import cine.modelo.*;
import cine.persistencia.PersistenciaDatos;
import java.util.List;

public class ControladorCine {
    private Cine cine;
    private Cliente clienteActual;
    
    public ControladorCine() {
        this.cine = PersistenciaDatos.cargarCine();
    }
    
    public boolean login(String email, String contraseña) {
        Cliente cliente = cine.buscarCliente(email);
        if (cliente != null && cliente.validarCredenciales(email, contraseña)) {
            this.clienteActual = cliente;
            return true;
        }
        return false;
    }
    
    public boolean registrarCliente(String nombre, String email, String contraseña) {
        if (cine.buscarCliente(email) != null) {
            return false; //usuario existe
        }
        
        Cliente nuevoCliente = new Cliente(nombre, email, contraseña);
        cine.registrarCliente(nuevoCliente);
        guardarDatos();
        return true;
    }
    
    public boolean comprarEntrada(Sala sala, Butaca butaca) {
        if (clienteActual == null || butaca.isOcupada()) {
            return false;
        }
        
        Entrada nuevaEntrada = new Entrada(clienteActual, sala, butaca);
        cine.agregarEntrada(nuevaEntrada);
        guardarDatos();
        return true;
    }
    
    public List<Sala> getSalasDisponibles() {
        return cine.getSalas();
    }
    
    public List<Entrada> getEntradasClienteActual() {
        if (clienteActual == null) return new java.util.ArrayList<>();
        return cine.getEntradasPorCliente(clienteActual);
    }
    
    public void guardarDatos() {
        PersistenciaDatos.guardarCine(cine);
    }
    
    public Cliente getClienteActual() { return clienteActual; }
    public void cerrarSesion() { this.clienteActual = null; }
}