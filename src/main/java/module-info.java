module org.proyecto.deliveryfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics; // Asegúrate de que este esté presente también, ya que LauncherImpl está aquí

    // Permite que FXML acceda a los miembros (@FXML) de los controladores por reflexión
    opens org.proyecto.deliveryfx.controller to javafx.fxml;

    // Exporta el paquete del controlador para que FXML pueda acceder a las clases públicas
    exports org.proyecto.deliveryfx.controller;

    // EXPORTA EL PAQUETE DE TU CLASE APPLICATION para que el launcher de JavaFX pueda acceder a ella
    exports org.proyecto.deliveryfx; // <--- ¡Añade o verifica esta línea!

    // Opcional: exportar el paquete del modelo si es necesario fuera de este módulo
    exports org.proyecto.deliveryfx.model;
}