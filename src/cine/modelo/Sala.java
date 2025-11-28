
package cine.modelo;

import java.io.Serializable;

public class Sala implements Serializable {
    private int numero;
    private String pelicula;
    private Butaca[][] butacas;
    private int filas = 5;
    private int asientosPorFila = 8;
    
    public Sala(int numero, String pelicula) {
        this.numero = numero;
        this.pelicula = pelicula;
        inicializarButacas();
    }
    
    private void inicializarButacas() {
        butacas = new Butaca[filas][asientosPorFila];
        char letraFila = 'A';
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < asientosPorFila; j++) {
                butacas[i][j] = new Butaca(String.valueOf(letraFila), j + 1);
            }
            letraFila++;
        }
    }
    
    public Butaca getButaca(String fila, int numero) {
        int filaIndex = fila.charAt(0) - 'A';
        int columnaIndex = numero - 1;
        
        if (filaIndex >= 0 && filaIndex < filas && 
            columnaIndex >= 0 && columnaIndex < asientosPorFila) {
            return butacas[filaIndex][columnaIndex];
        }
        return null;
    }
    
    public int getNumero() { return numero; }
    public String getPelicula() { return pelicula; }
    public Butaca[][] getButacas() { return butacas; }
    public int getFilas() { return filas; }
    public int getAsientosPorFila() { return asientosPorFila; }
}