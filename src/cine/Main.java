package cine;

import cine.controlador.ControladorCine;
import cine.vista.NavegacionManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private ControladorCine controlador;
    private NavegacionManager navegacion;
    
    @Override
    public void start(Stage primaryStage) {
        controlador = new ControladorCine();
        navegacion = new NavegacionManager(primaryStage, controlador);
        
        navegacion.mostrarLogin();
        primaryStage.show();
    }
    
    @Override
    public void stop() {
        if (controlador != null) {
            controlador.guardarDatos();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}