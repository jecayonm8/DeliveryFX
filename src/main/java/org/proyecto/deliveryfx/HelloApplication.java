package org.proyecto.deliveryfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.proyecto.deliveryfx.controller.DeliveryViewController;
import org.proyecto.deliveryfx.controller.LoginViewController; // Importa el controlador de Login
import org.proyecto.deliveryfx.controller.RegistrationViewController; // Importa el controlador de Registro
import org.proyecto.deliveryfx.model.DeliveryModel; // Importa el Modelo


import java.io.IOException;

public class HelloApplication extends Application {

    private Stage stage; // Guardaremos una referencia al Stage principal
    private DeliveryModel deliveryModel; // *** Instancia única del Modelo ***

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage; // Almacena el Stage principal
        this.deliveryModel = new DeliveryModel(); // *** Crea una instancia del Modelo al inicio ***

        // Carga la pantalla de inicio de sesión al inicio
        mostrarLoginView();
    }

    // Metodo para mostrar la vista de inicio de sesión
    public void mostrarLoginView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/proyecto/deliveryfx/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        LoginViewController loginController = fxmlLoader.getController();
        loginController.setMainApplication(this);
        // *** Pasa la instancia del Modelo al controlador de Login ***
        loginController.setDeliveryModel(this.deliveryModel);


        stage.setTitle("Inicio de Sesión - Delivery App");
        stage.setScene(scene);
        stage.show();
    }

    // Metodo para mostrar la vista de registro
    public void mostrarRegistrationView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/proyecto/deliveryfx/registration-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        RegistrationViewController registrationController = fxmlLoader.getController();
        registrationController.setMainApplication(this);
        // *** Pasa la instancia del Modelo al controlador de Registro ***
        registrationController.setDeliveryModel(this.deliveryModel);


        stage.setTitle("Crear Cuenta - Delivery App");
        stage.setScene(scene);
        stage.show();
    }


    // Metodo para mostrar la vista principal de la plataforma de delivery
    public void mostrarDeliveryView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/proyecto/deliveryfx/delivery-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        DeliveryViewController deliveryController = fxmlLoader.getController();
        // *** Pasa la instancia del Modelo al controlador de Delivery ***
        deliveryController.setDeliveryModel(this.deliveryModel);
        // Opcional: podrías necesitar pasar la referencia a HelloApplication aquí también si el DeliveryController necesita navegar de vuelta

        stage.setTitle("Plataforma de Gestión de Pedidos");
        stage.setScene(scene);
        stage.show();
    }

    // Opcional: Metodo para obtener la instancia del Modelo (si fuera necesario fuera de HelloApplication, pero mejor pasarla)
    public DeliveryModel getDeliveryModel() {
        return deliveryModel;
    }

    public static void main(String[] args) {
        launch();
    }
}