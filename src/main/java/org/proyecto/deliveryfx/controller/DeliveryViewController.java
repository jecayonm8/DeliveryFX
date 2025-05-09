package org.proyecto.deliveryfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.proyecto.deliveryfx.model.DeliveryModel;
import org.proyecto.deliveryfx.model.Pedido;
import org.proyecto.deliveryfx.model.Prioridad;

import java.util.List;
// Ya no necesitamos UUID si ingresamos el ID manualmente, pero lo mantengo si quieres la opción
// import java.util.UUID;

public class DeliveryViewController {

    // Referencias a los elementos de la UI desde delivery-view.fxml
    @FXML
    private Button btnAgregarPedido;

    @FXML
    private Button btnProcesarSiguiente;

    @FXML
    private ChoiceBox<Prioridad> choiceboxPrioridad;

    // Etiquetas (Labels)
    @FXML private Label lblSubtitulo1;
    @FXML private Label lblSubtitulo2;
    @FXML private Label lblSubtitulo3;
    @FXML private Label lblSubtitulo4;
    @FXML private Label lblTituloGestion;
    @FXML private Label lblTituloPedido;
    @FXML private Label lblTituloProcesado;

    // *** NUEVOS: Label y TextField para el ID ***
    @FXML private Label lblSubtituloId; // Si quieres interactuar con esta etiqueta
    @FXML private TextField textfieldId; // Campo para ingresar el ID del pedido


    // TableView y sus columnas
    @FXML
    private TableView<Pedido> tableviewPedidos;

    @FXML private TableColumn<Pedido, String> tablecolumnId;


    @FXML private TableColumn<Pedido, String> tablecolumnNombre;
    @FXML private TableColumn<Pedido, String> tablecolumnDireccion;
    @FXML private TableColumn<Pedido, String> tablecolumnDescrip;
    @FXML private TableColumn<Pedido, Prioridad> tablecolumnPrioridad;


    // Área de texto para mostrar el pedido procesado
    @FXML
    private TextArea textareaPedidoProcesado;

    // Campos de texto para agregar nuevos pedidos
    @FXML
    private TextField textfieldDescripcion;

    @FXML
    private TextField textfieldDireccion;
    @FXML
    private TextField textfieldNombre;


    // Referencia al Modelo de la aplicación
    private DeliveryModel deliveryModel;

    // ObservableList para mantener los datos de la TableView sincronizados
    private ObservableList<Pedido> pedidosEnColaObservableList;


    // Metodo llamado por HelloApplication para pasar la referencia al Modelo
    public void setDeliveryModel(DeliveryModel deliveryModel) {
        this.deliveryModel = deliveryModel;
        cargarPedidosEnTabla();
    }

    // Metodo initialize se llama automáticamente después de cargar el FXML
    @FXML
    public void initialize() {
        // 1. Configurar el ChoiceBox de Prioridad
        choiceboxPrioridad.getItems().setAll(Prioridad.values());
        choiceboxPrioridad.setValue(Prioridad.ESTANDAR);

        // 2. Configurar la TableView y sus Columnas
        // *** Configurar la nueva columna ID ***
        // Asegúrate de que la columna ID en tu FXML tiene fx:id="tablecolumnId"
        if (tablecolumnId != null) { // Verificar que la columna existe
            tablecolumnId.setCellValueFactory(new PropertyValueFactory<>("id")); // Usar el getter getId() de Pedido
        } else {
            System.err.println("Error: La columna 'tablecolumnId' no tiene un fx:id en el FXML o el fx:id es incorrecto.");
        }


        tablecolumnNombre.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        tablecolumnDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tablecolumnDescrip.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tablecolumnPrioridad.setCellValueFactory(new PropertyValueFactory<>("prioridad"));

        pedidosEnColaObservableList = FXCollections.observableArrayList();
        tableviewPedidos.setItems(pedidosEnColaObservableList);
    }

    // Método para cargar o actualizar la TableView con los pedidos del Modelo
    private void cargarPedidosEnTabla() {
        if (deliveryModel != null) {
            List<Pedido> pedidosDelModelo = deliveryModel.getPedidosOrdenadosParaVista();
            pedidosEnColaObservableList.clear();
            pedidosEnColaObservableList.addAll(pedidosDelModelo);
            System.out.println("TableView actualizada. Pedidos en cola: " + deliveryModel.getNumeroPedidosEnCola());
        } else {
            System.err.println("Error: DeliveryModel no está disponible para cargar pedidos en la tabla.");
        }
    }


    // Manejador del clic para el botón "Agregar Pedido"
    @FXML
    void OnAgregarPedidoButtonClick(ActionEvent event) {
        // 1. Obtener los datos de los campos de la UI (incluyendo el ID)
        String idPedido = textfieldId.getText().trim(); // *** Obtiene el ID del TextField ***
        String nombreCliente = textfieldNombre.getText().trim();
        String direccion = textfieldDireccion.getText().trim();
        String descripcion = textfieldDescripcion.getText().trim();
        Prioridad prioridadSeleccionada = choiceboxPrioridad.getValue();

        // 2. Validar los datos (ahora también validando el ID)
        if (idPedido.isEmpty() || nombreCliente.isEmpty() || direccion.isEmpty() || descripcion.isEmpty() || prioridadSeleccionada == null) {
            mostrarMensajeError("Por favor, completa todos los campos (incluido el ID) y selecciona una prioridad.");
            return;
        }

        // Opcional: Podrías añadir validación para asegurar que el ID no esté duplicado antes de crear el Pedido
        // Esto requeriría añadir un método en DeliveryModel para verificar si un ID ya existe.

        // 3. Crear un nuevo objeto Pedido (usando el ID ingresado)
        Pedido nuevoPedido = new Pedido(idPedido, nombreCliente, direccion, descripcion, prioridadSeleccionada, System.currentTimeMillis());

        // 4. Agregar el pedido al Modelo (a la cola de prioridad)
        if (deliveryModel != null) {
            deliveryModel.agregarPedido(nuevoPedido);

            // 5. Actualizar la Vista (la TableView)
            cargarPedidosEnTabla();

            // 6. Limpiar los campos de entrada
            textfieldId.clear(); // Limpia el campo ID también
            textfieldNombre.clear();
            textfieldDireccion.clear();
            textfieldDescripcion.clear();
            choiceboxPrioridad.setValue(Prioridad.ESTANDAR);
            textareaPedidoProcesado.clear();
        } else {
            System.err.println("Error: DeliveryModel no está disponible para agregar el pedido.");
            mostrarMensajeError("Error interno: No se pudo agregar el pedido.");
        }
    }

    // Manejador del clic para el botón "Procesar Siguiente Pedido"
    @FXML
    void OnProcesarSigButtonClick(ActionEvent event) {
        // 1. Solicitar al Modelo que procese el siguiente pedido
        if (deliveryModel != null) {
            Pedido pedidoProcesado = deliveryModel.procesarSiguientePedido();

            // 2. Actualizar la Vista
            if (pedidoProcesado != null) {
                // Mostrar los detalles del pedido procesado en el TextArea (incluyendo el ID)
                textareaPedidoProcesado.setText(
                        "ID: " + pedidoProcesado.getId() + "\n" + // *** Incluye el ID aquí ***
                                "Cliente: " + pedidoProcesado.getCliente() + "\n" +
                                "Prioridad: " + pedidoProcesado.getPrioridad() + "\n" +
                                "Descripción: " + pedidoProcesado.getDescripcion()
                );

                // Actualizar la TableView
                cargarPedidosEnTabla();

            } else {
                textareaPedidoProcesado.setText("La cola de pedidos está vacía. No hay pedidos para procesar.");
                System.out.println("La cola de pedidos está vacía.");
            }
        } else {
            System.err.println("Error: DeliveryModel no está disponible para procesar el pedido.");
            mostrarMensajeError("Error interno: No se pudo procesar el pedido.");
        }
    }

    // Métodos de ayuda para mostrar mensajes
    private void mostrarMensajeError(String mensaje) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarMensajeInfo(String mensaje) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}