package org.proyecto.deliveryfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import org.proyecto.deliveryfx.HelloApplication;
import org.proyecto.deliveryfx.model.DeliveryModel; // Importa el Modelo

import java.io.IOException;

public class LoginViewController {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnIrARegistro;

    @FXML
    private ImageView imgDelivery;

    @FXML
    private Label lblTexto1;

    @FXML
    private Label lblTexto2;

    @FXML
    private Label lblTitulo1;

    @FXML
    private Label lblTitulo2;

    @FXML
    private PasswordField txtPassword; // Generalmente, la contraseña se usa con PasswordField

    @FXML
    private TextField txtUsuario; // Correo electrónico es el usuario

    // Referencia a la clase principal de la aplicación
    private HelloApplication mainApplication;

    // *** Referencia al Modelo de la aplicación ***
    private DeliveryModel deliveryModel;

    // Método llamado por HelloApplication para pasar la referencia a la aplicación principal
    public void setMainApplication(HelloApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    // *** Nuevo método llamado por HelloApplication para pasar la referencia al Modelo ***
    public void setDeliveryModel(DeliveryModel deliveryModel) {
        this.deliveryModel = deliveryModel;
        // Opcional: Imprimir usuarios existentes al cargar la pantalla de login para depuración
        if (this.deliveryModel != null) {
            this.deliveryModel.imprimirUsuariosRegistrados();
        }
    }


    // Este método se ejecuta cuando se hace clic en el botón "Ingresar"
    @FXML
    void OnIniciarSesion(ActionEvent event) {
        String usuario = txtUsuario.getText().trim(); // Usar trim()
        String password = txtPassword.getText().trim(); // Usar trim()

        // 1. *** Usar el Modelo para verificar las credenciales ***
        if (deliveryModel != null) {
            boolean credencialesValidas = deliveryModel.verificarCredenciales(usuario, password);

            if (credencialesValidas) {
                System.out.println("Inicio de sesión exitoso para: " + usuario);
                // Limpiar campos después de inicio de sesión exitoso (opcional)
                txtUsuario.clear();
                txtPassword.clear();

                // Si las credenciales son válidas, cambia a la vista principal
                if (mainApplication != null) {
                    try {
                        mainApplication.mostrarDeliveryView();
                    } catch (IOException e) {
                        e.printStackTrace();
                        mostrarMensajeError("Error al cargar la pantalla principal.");
                    }
                } else {
                    System.err.println("Referencia a HelloApplication no establecida.");
                }
            } else {
                System.out.println("Intento de inicio de sesión fallido para: " + usuario);
                // Credenciales inválidas
                mostrarMensajeError("Usuario o contraseña incorrectos. Inténtalo de nuevo.");
            }
        } else {
            System.err.println("Referencia a DeliveryModel no establecida en LoginViewController.");
            mostrarMensajeError("Error interno: No se pudo acceder a los datos de usuario.");
        }
    }


    @FXML
    void OnIrARegistroClick(ActionEvent event) {
        System.out.println("Clic en 'Registrarse'.");
        if (mainApplication != null) {
            try {
                mainApplication.mostrarRegistrationView();
            } catch (IOException e) {
                e.printStackTrace();
                mostrarMensajeError("Error al cargar la pantalla de registro.");
            }
        } else {
            System.err.println("Referencia a HelloApplication no establecida.");
        }
    }


    // Método de ayuda para mostrar un mensaje de error en una alerta
    private void mostrarMensajeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método de ayuda para mostrar un mensaje de información o éxito
    private void mostrarMensajeInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}