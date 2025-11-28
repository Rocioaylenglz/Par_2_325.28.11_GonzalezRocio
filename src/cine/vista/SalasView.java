package cine.vista;

import cine.controlador.ControladorCine;
import cine.modelo.Sala;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SalasView extends BorderPane {
    private ControladorCine controlador;
    private NavegacionManager navegacion;
    private VBox salasContainer;
    
    public SalasView(ControladorCine controlador, NavegacionManager navegacion) {
        this.controlador = controlador;
        this.navegacion = navegacion;
        inicializarComponentes();
        actualizarVista();
    }
    
    private void inicializarComponentes() {
        Text titulo = new Text("Salas Disponibles");
        titulo.setFont(Font.font("Arial", 24));
        
        Label bienvenida = new Label();
        if (controlador.getClienteActual() != null) {
            bienvenida.setText("Bienvenido, " + controlador.getClienteActual().getNombre());
        }
        
        VBox header = new VBox(10, titulo, bienvenida);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        
        salasContainer = new VBox(15);
        salasContainer.setPadding(new Insets(20));
        salasContainer.setAlignment(Pos.TOP_CENTER);
        
        ScrollPane scrollPane = new ScrollPane(salasContainer);
        scrollPane.setFitToWidth(true);
        
        Button logoutButton = new Button("Cerrar Sesión");
        Button verEntradasButton = new Button("Mis Entradas");
        
        logoutButton.setOnAction(e -> {
            controlador.cerrarSesion();
            navegacion.mostrarLogin();
        });
        
        verEntradasButton.setOnAction(e -> mostrarMisEntradas());
        
        VBox bottomBox = new VBox(10, verEntradasButton, logoutButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));
        
        setTop(header);
        setCenter(scrollPane);
        setBottom(bottomBox);
    }
    
    public void actualizarVista() {
        salasContainer.getChildren().clear();
        
        for (Sala sala : controlador.getSalasDisponibles()) {
            salasContainer.getChildren().add(crearCardSala(sala));
        }
    }
    
    private GridPane crearCardSala(Sala sala) {
        GridPane card = new GridPane();
        card.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-padding: 15;");
        card.setHgap(10);
        card.setVgap(5);
        
        Label tituloPelicula = new Label(sala.getPelicula());
        tituloPelicula.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        
        Label infoSala = new Label("Sala: " + sala.getNumero() + 
                                 " | Capacidad: " + sala.getFilas() + "x" + sala.getAsientosPorFila());
        
        int butacasOcupadas = 0;
        int totalButacas = sala.getFilas() * sala.getAsientosPorFila();
        
        for (int i = 0; i < sala.getFilas(); i++) {
            for (int j = 0; j < sala.getAsientosPorFila(); j++) {
                if (sala.getButacas()[i][j].isOcupada()) {
                    butacasOcupadas++;
                }
            }
        }
        
        int butacasDisponibles = totalButacas - butacasOcupadas;
        Label disponibilidad = new Label("Butacas disponibles: " + butacasDisponibles + "/" + totalButacas);
        
        if (butacasDisponibles == 0) {
            disponibilidad.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        } else {
            disponibilidad.setStyle("-fx-text-fill: green;");
        }
        
        Button seleccionarButton = new Button("Seleccionar Butaca");
        seleccionarButton.setDisable(butacasDisponibles == 0);
        
        seleccionarButton.setOnAction(e -> navegacion.mostrarButacas(sala));
        
        card.add(tituloPelicula, 0, 0, 2, 1);
        card.add(infoSala, 0, 1);
        card.add(disponibilidad, 0, 2);
        card.add(seleccionarButton, 1, 1, 1, 2);
        
        return card;
    }
    
    private void mostrarMisEntradas() {
        var entradas = controlador.getEntradasClienteActual();
        
        if (entradas.isEmpty()) {
            mostrarAlerta("No tienes entradas compradas");
        } else {
            StringBuilder mensaje = new StringBuilder("Tus entradas:\n\n");
            for (var entrada : entradas) {
                mensaje.append("• ").append(entrada.toString()).append("\n");
            }
            mostrarAlerta(mensaje.toString());
        }
    }
    
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mis Entradas");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}