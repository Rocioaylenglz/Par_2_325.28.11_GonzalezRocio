package cine.vista;

import cine.controlador.ControladorCine;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LoginView extends VBox {
    private ControladorCine controlador;
    private NavegacionManager navegacion;
    private TextField emailField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registrarButton;
    private Label mensajeLabel;
    
    public LoginView(ControladorCine controlador, NavegacionManager navegacion) {
        this.controlador = controlador;
        this.navegacion = navegacion;
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setSpacing(20);
        setPadding(new Insets(20));
        setAlignment(Pos.CENTER);
        
        Text titulo = new Text("CINE - Sistema de Entradas");
        titulo.setFont(Font.font("Arial", 24));
        
        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(10);
        formulario.setAlignment(Pos.CENTER);
        
        formulario.add(new Label("Email:"), 0, 0);
        emailField = new TextField();
        emailField.setPromptText("tu@email.com");
        formulario.add(emailField, 1, 0);
        
        formulario.add(new Label("Contraseña:"), 0, 1);
        passwordField = new PasswordField();
        formulario.add(passwordField, 1, 1);
        
        loginButton = new Button("Iniciar Sesión");
        registrarButton = new Button("Registrarse");
        
        HBox botonesBox = new HBox(10, loginButton, registrarButton);
        botonesBox.setAlignment(Pos.CENTER);
        
        mensajeLabel = new Label();
        mensajeLabel.setStyle("-fx-text-fill: red;");
        
        configurarEventos();
        
        getChildren().addAll(titulo, formulario, botonesBox, mensajeLabel);
    }
    
    private void configurarEventos() {
        loginButton.setOnAction(e -> realizarLogin());
        registrarButton.setOnAction(e -> realizarRegistro());
        passwordField.setOnAction(e -> realizarLogin());
    }
    
    private void realizarLogin() {
        String email = emailField.getText().trim();
        String contraseña = passwordField.getText();

        if (email.isEmpty() || contraseña.isEmpty()) {
            mensajeLabel.setText("Por favor complete todos los campos");
            return;
        }

        if (controlador.login(email, contraseña)) {
            mensajeLabel.setText("¡Login exitoso!");
            limpiarCampos();
            navegacion.mostrarSalas();
        } else {
            mensajeLabel.setText("Credenciales incorrectas");
        }
    }
    
    private void realizarRegistro() {
    String email = emailField.getText().trim();
    String contraseña = passwordField.getText();
    
    //valido mail
    if (email.isEmpty() || contraseña.isEmpty()) {
        mensajeLabel.setText("Por favor complete todos los campos");
        return;
    }
    
    // formato
    if (!email.contains("@") || !email.contains(".") || email.indexOf("@") > email.lastIndexOf(".")) {
        mensajeLabel.setText("Por favor ingrese un email válido (ej: usuario@dominio.com)");
        return;
    }
    
    // longitud mínima de contraseña
    if (contraseña.length() < 4) {
        mensajeLabel.setText("La contraseña debe tener al menos 4 caracteres");
        return;
    }
    
    String nombre = email.split("@")[0]; //separo antes del @
    
    if (controlador.registrarCliente(nombre, email, contraseña)) {
        mensajeLabel.setText("¡Registro exitoso! Ahora puede iniciar sesión");
        limpiarCampos();
    } else {
        mensajeLabel.setText("El email ya está registrado");
    }
}

    
    private void limpiarCampos() {
        emailField.clear();
        passwordField.clear();
    }
}