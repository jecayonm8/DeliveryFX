package org.proyecto.deliveryfx.model;

public class Pedido {
    //atributos
    private String id; // Un identificador único para el pedido
    private String cliente;
    private String direccion;
    private String descripcion; //Detalles del pedido (ej: "Pizza grande, Coca-Cola").
    private Prioridad prioridad; //Almacena la prioridad del pedido usando nuestro enum Prioridad.
    private long tiempoCreacion; // Para manejar desempates si es necesario (el más antiguo primero)

    //constructor
    public Pedido(String id, String cliente, String direccion, String descripcion, Prioridad prioridad, long tiempoCreacion) {
        this.id = id;
        this.cliente = cliente;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.tiempoCreacion = System.currentTimeMillis();
    }

    //getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public long getTiempoCreacion() {
        return tiempoCreacion;
    }

    public void setTiempoCreacion(long tiempoCreacion) {
        this.tiempoCreacion = tiempoCreacion;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id='" + id + '\'' +
                ", cliente='" + cliente + '\'' +
                ", direccion='" + direccion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", prioridad=" + prioridad +
                ", tiempoCreacion=" + tiempoCreacion +
                '}';
    }

}
