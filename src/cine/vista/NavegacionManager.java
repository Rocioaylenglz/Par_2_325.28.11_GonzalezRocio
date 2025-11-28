package cine.vista;

import cine.controlador.ControladorCine;
import cine.modelo.Sala;
import cine.modelo.Butaca;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavegacionManager {
    private Stage primaryStage;
    private ControladorCine controlador;
    private Scene loginScene;
    private Scene salasScene;
    private Scene butacasScene;
    private Scene confirmacionScene;
    
    public NavegacionManager(Stage primaryStage, ControladorCine controlador) {
        this.primaryStage = primaryStage;
        this.controlador = controlador;
        inicializarVistas();
    }
    
    private void inicializarVistas() {
        LoginView loginView = new LoginView(controlador, this);
        SalasView salasView = new SalasView(controlador, this);
        ButacasView butacasView = new ButacasView(controlador, this);
        ConfirmacionView confirmacionView = new ConfirmacionView(controlador, this);
        
        loginScene = new Scene(loginView, 400, 300);
        salasScene = new Scene(salasView, 600, 500);
        butacasScene = new Scene(butacasView, 700, 600);
        confirmacionScene = new Scene(confirmacionView, 500, 400);
    }
    
    public void mostrarLogin() {
        primaryStage.setTitle("Cine - Login");
        primaryStage.setScene(loginScene);
    }
    
    public void mostrarSalas() {
        primaryStage.setTitle("Cine - Salas Disponibles");
        primaryStage.setScene(salasScene);
        if (salasScene.getRoot() instanceof SalasView) {
            ((SalasView) salasScene.getRoot()).actualizarVista();
        }
    }
    
    public void mostrarButacas(Sala sala) {
        primaryStage.setTitle("Cine - Selección de Butacas - " + sala.getPelicula());
        primaryStage.setScene(butacasScene);
        if (butacasScene.getRoot() instanceof ButacasView) {
            ((ButacasView) butacasScene.getRoot()).setSalaSeleccionada(sala);
        }
    }
    
    public void mostrarConfirmacion(Sala sala, Butaca butaca) {
        primaryStage.setTitle("Cine - Confirmación de Compra");
        primaryStage.setScene(confirmacionScene);
        if (confirmacionScene.getRoot() instanceof ConfirmacionView) {
            ((ConfirmacionView) confirmacionScene.getRoot()).setDatosCompra(sala, butaca);
        }
    }
    
    public void cerrarAplicacion() {
        controlador.guardarDatos();
        primaryStage.close();
    }
}