package cine.vista;

import cine.controlador.ControladorCine;
import cine.modelo.Butaca;
import cine.modelo.Sala;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.HPos;

public class ButacasView extends BorderPane {
    private ControladorCine controlador;
    private NavegacionManager navegacion;
    private Sala salaSeleccionada;
    private Butaca butacaSeleccionada;
    private GridPane gridButacas;
    private Label infoLabel;
    private Button confirmarButton;
    private Button volverButton;
    
    public ButacasView(ControladorCine controlador, NavegacionManager navegacion) {
        this.controlador = controlador;
        this.navegacion = navegacion;
        inicializarComponentes();
    }
    
    public void setSalaSeleccionada(Sala sala) {
        this.salaSeleccionada = sala;
        this.butacaSeleccionada = null;
        actualizarVista();
    }
    
    private void inicializarComponentes() {
        Text titulo = new Text("Selección de Butacas");
        titulo.setFont(Font.font("Arial", 20));
        
        infoLabel = new Label();
        infoLabel.setStyle("-fx-font-size: 14;");
        
        VBox header = new VBox(10, titulo, infoLabel);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        
        gridButacas = new GridPane();
        gridButacas.setHgap(5);
        gridButacas.setVgap(5);
        gridButacas.setAlignment(Pos.CENTER);
        gridButacas.setPadding(new Insets(20));
        
        HBox leyenda = crearLeyenda();
        
        volverButton = new Button("Volver a Salas");
        confirmarButton = new Button("Confirmar Butaca");
        confirmarButton.setDisable(true);
        
        volverButton.setOnAction(e -> navegacion.mostrarSalas());
        confirmarButton.setOnAction(e -> confirmarButaca());
        
        HBox botonesBox = new HBox(20, volverButton, confirmarButton);
        botonesBox.setAlignment(Pos.CENTER);
        botonesBox.setPadding(new Insets(20));
        
        setTop(header);
        setCenter(gridButacas);
        setBottom(new VBox(20, leyenda, botonesBox));
    }
    
    private void actualizarVista() {
        if (salaSeleccionada == null) return;
        
        infoLabel.setText("Película: " + salaSeleccionada.getPelicula() + 
                         " | Sala: " + salaSeleccionada.getNumero());
        
        gridButacas.getChildren().clear();
        crearGridButacas();
    }
    
    private void crearGridButacas() {
        
         gridButacas.setAlignment(Pos.CENTER);
         gridButacas.setHgap(10);
        Label pantalla = new Label(" P A N T A L L A ");
        GridPane.setHalignment(pantalla, HPos.CENTER);
        gridButacas.add(pantalla, 0, 0, salaSeleccionada.getAsientosPorFila() + 1, 1);
        
        Butaca[][] butacas = salaSeleccionada.getButacas();
        
        for (int fila = 0; fila < salaSeleccionada.getFilas(); fila++) {
            Label letraFila = new Label(String.valueOf((char) ('A' + fila)));
            letraFila.setStyle("-fx-font-weight: bold;");
            gridButacas.add(letraFila, 0, fila + 1);
            
            for (int columna = 0; columna < salaSeleccionada.getAsientosPorFila(); columna++) {
                Butaca butaca = butacas[fila][columna];
                Rectangle rectButaca = crearButacaVisual(butaca);
                final int currentFila = fila;
                final int currentColumna = columna;
                
                if (!butaca.isOcupada()) {
                    rectButaca.setOnMouseClicked(e -> seleccionarButaca(currentFila, currentColumna));
                }
                
                gridButacas.add(rectButaca, columna + 1, fila + 1);
                
                if (fila == 0) {
                    Label numeroButaca = new Label(String.valueOf(columna + 1));
                    gridButacas.add(numeroButaca, columna + 1, salaSeleccionada.getFilas() + 1);
                }
            }
        }
    }
    
    private Rectangle crearButacaVisual(Butaca butaca) {
        Rectangle rect = new Rectangle(30, 30);
        
        if (butaca.isOcupada()) {
            rect.setFill(Color.RED);
        } else if (butacaSeleccionada == butaca) {
            rect.setFill(Color.GREEN);
        } else {
            rect.setFill(Color.LIGHTGREEN);
        }
        
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(1);
        
        return rect;
    }
    
    private void seleccionarButaca(int fila, int columna) {
        butacaSeleccionada = salaSeleccionada.getButacas()[fila][columna];
        confirmarButton.setDisable(false);
        actualizarVista();
    }
    
    private void confirmarButaca() {
        if (butacaSeleccionada != null) {
            navegacion.mostrarConfirmacion(salaSeleccionada, butacaSeleccionada);
        }
    }
    
    private HBox crearLeyenda() {
        Rectangle libre = new Rectangle(20, 20, Color.LIGHTGREEN);
        Rectangle seleccionada = new Rectangle(20, 20, Color.GREEN);
        Rectangle ocupada = new Rectangle(20, 20, Color.RED);
        
        Label lblLibre = new Label("Libre");
        Label lblSeleccionada = new Label("Seleccionada");
        Label lblOcupada = new Label("Ocupada");
        
        HBox leyenda = new HBox(10, 
            new HBox(5, libre, lblLibre),
            new HBox(5, seleccionada, lblSeleccionada),
            new HBox(5, ocupada, lblOcupada)
        );
        leyenda.setAlignment(Pos.CENTER);
        leyenda.setPadding(new Insets(10));
        
        return leyenda;
    }
}