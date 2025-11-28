package cine.persistencia;

import cine.modelo.Cine;
import java.io.*;

public class PersistenciaDatos {
    private static final String ARCHIVO_CINE = "cine.ser";
    
    public static void guardarCine(Cine cine) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_CINE))) {
            oos.writeObject(cine);
            System.out.println("Datos guardados correctamente");
        } catch (IOException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
        }
    }
    
    public static Cine cargarCine() {
        File archivo = new File(ARCHIVO_CINE);
        if (!archivo.exists()) {
            System.out.println("No existe archivo previo, creando nuevo cine");
            return new Cine();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ARCHIVO_CINE))) {
            Cine cine = (Cine) ois.readObject();
            System.out.println("Datos cargados correctamente");
            return cine;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
            return new Cine();
        }
    }
}