package cine.vista;

import cine.controlador.ControladorCine;
import cine.modelo.Butaca;
import cine.modelo.Sala;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ConfirmacionView extends BorderPane {
    private ControladorCine controlador;
    private NavegacionManager navegacion;
    private Sala sala;
    private Butaca butaca;
    
    private Label resumenLabel;
    private Button confirmarButton;
    private Button cancelarButton;
    
    public ConfirmacionView(ControladorCine controlador, NavegacionManager navegacion) {
        this.controlador = controlador;
        this.navegacion = navegacion;
        inicializarComponentes();
    }
    
    public void setDatosCompra(Sala sala, Butaca butaca) {
        this.sala = sala;
        this.butaca = butaca;
        actualizarResumen();
    }
    
    private void inicializarComponentes() {
        Text titulo = new Text("Confirmación de Compra");
        titulo.setFont(Font.font("Arial", 24));
        
        VBox header = new VBox(titulo);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        
        resumenLabel = new Label();
        resumenLabel.setStyle("-fx-font-size: 14; -fx-padding: 20;");
        resumenLabel.setAlignment(Pos.CENTER);
        
        confirmarButton = new Button("Confirmar Compra");
        cancelarButton = new Button("Cancelar");
        
        confirmarButton.setStyle("-fx-font-size: 14; -fx-padding: 10 20;");
        cancelarButton.setStyle("-fx-font-size: 14; -fx-padding: 10 20;");
        
        confirmarButton.setOnAction(e -> realizarCompra());
        cancelarButton.setOnAction(e -> navegacion.mostrarButacas(sala));
        
        VBox botonesBox = new VBox(15, confirmarButton, cancelarButton);
        botonesBox.setAlignment(Pos.CENTER);
        botonesBox.setPadding(new Insets(20));
        
        setTop(header);
        setCenter(resumenLabel);
        setBottom(botonesBox);
    }
    
    private void actualizarResumen() {
        if (sala == null || butaca == null) return;
        
        String resumen = String.format(
            "📋 RESUMEN DE COMPRA\n\n" +
            "🎬 Película: %s\n" +
            "🏪 Sala: %d\n" +
            "💺 Butaca: %s%d\n" +
            "👤 Cliente: %s\n\n" +
            "💰 Precio: $12000",
            sala.getPelicula(),
            sala.getNumero(),
            butaca.getFila(),
            butaca.getNumero(),
            controlador.getClienteActual().getNombre()
        );
        
        resumenLabel.setText(resumen);
    }
    
    private void realizarCompra() {
        if (controlador.comprarEntrada(sala, butaca)) {
            mostrarAlerta("¡Compra exitosa!", 
                "Tu entrada ha sido reservada exitosamente.\n" +
                "Butaca: " + butaca.getFila() + butaca.getNumero() + "\n" +
                "Película: " + sala.getPelicula(), 
                Alert.AlertType.INFORMATION);
            
            navegacion.mostrarSalas();
        } else {
            mostrarAlerta("Error en la compra", 
                "No se pudo completar la compra. La butaca puede estar ocupada.", 
                Alert.AlertType.ERROR);
        }
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}