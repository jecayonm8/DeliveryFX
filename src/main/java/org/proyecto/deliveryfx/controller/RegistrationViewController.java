package org.proyecto.deliveryfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField; // Podrías cambiar txtContrasena a PasswordField

import org.proyecto.deliveryfx.HelloApplication;
import org.proyecto.deliveryfx.model.DeliveryModel; // Importa el Modelo

import java.io.IOException;

public class RegistrationViewController {

    @FXML
    private Button btnRegistrarse;

    @FXML
    private Label lblContra;

    @FXML
    private Label lblCorreo;

    @FXML
    private Label lblNombrecom;

    @FXML
    private Label lblTituloCrearc;

    @FXML
    // Considera cambiar a PasswordField para la contraseña
    private TextField txtContrasena; // O PasswordField txtContrasena;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNombre;

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
    }


    @FXML
    void OnRegistrarCuenta(ActionEvent event) {
        // 1. Obtener los datos de los campos
        String nombreCompleto = txtNombre.getText().trim(); // Usar trim() para quitar espacios en blanco
        String correoElectronico = txtCorreo.getText().trim();
        String contrasena = txtContrasena.getText().trim();

        // 2. Lógica de validación
        if (nombreCompleto.isEmpty() || correoElectronico.isEmpty() || contrasena.isEmpty()) {
            mostrarMensajeError("Todos los campos son obligatorios.");
            return;
        }

        // Validación simple de formato de correo (opcional, pero recomendado)
        if (!correoElectronico.contains("@")) {
            mostrarMensajeError("Por favor, introduce un correo electrónico válido.");
            return;
        }


        // 3. *** Usar el Modelo para registrar el usuario ***
        if (deliveryModel != null) {
            boolean registroExitoso = deliveryModel.registrarUsuario(correoElectronico, contrasena);

            if (registroExitoso) {
                mostrarMensajeInfo("¡Registro exitoso! Ahora puedes iniciar sesión con tu correo y contraseña.");

                // Limpiar los campos después del registro exitoso
                txtNombre.clear();
                txtCorreo.clear();
                txtContrasena.clear();

                // 4. Regresar a la pantalla de inicio de sesión
                if (mainApplication != null) {
                    try {
                        mainApplication.mostrarLoginView();
                    } catch (IOException e) {
                        e.printStackTrace();
                        mostrarMensajeError("Error al cargar la pantalla de inicio de sesión.");
                    }
                } else {
                    System.err.println("Referencia a HelloApplication no establecida.");
                }

            } else {
                // El usuario ya existe
                mostrarMensajeError("El correo electrónico ya está registrado. Por favor, usa otro correo o inicia sesión.");
            }
        } else {
            System.err.println("Referencia a DeliveryModel no establecida en RegistrationViewController.");
        }
    }

    // Métodos de ayuda para mostrar mensajes
    private void mostrarMensajeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Registro");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarMensajeInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}