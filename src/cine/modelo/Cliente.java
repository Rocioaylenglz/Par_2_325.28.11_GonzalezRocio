/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cine.modelo;

import java.io.Serializable;

public class Cliente implements Serializable {
    private String nombre;
    private String email;
    private String contraseña;
    
    public Cliente(String nombre, String email, String contraseña) {
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
    }
    
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getContraseña() { return contraseña; }
    
    public boolean validarCredenciales(String email, String contraseña) {
        return this.email.equals(email) && this.contraseña.equals(contraseña);
    }
}