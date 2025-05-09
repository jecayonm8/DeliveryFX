package org.proyecto.deliveryfx.model;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

public class DeliveryModel {

    // La cola de prioridad que almacenará los pedidos
    private Queue<Pedido> colaPedidos;

    // Estructura para almacenar usuarios registrados (Correo -> Contraseña)
    private Map<String, String> usuariosRegistrados;

    public DeliveryModel() {
        // Inicializamos la cola de prioridad con un comparador personalizado
        this.colaPedidos = new PriorityQueue<>(Comparator.comparing(Pedido::getPrioridad,
                        Comparator.comparing(Prioridad::getNivel))
                .thenComparing(Pedido::getTiempoCreacion));

        // Inicializamos el mapa de usuarios
        this.usuariosRegistrados = new HashMap<>();

        // Opcional: Agregar algunos usuarios por defecto para prueba inicial
        // usuariosRegistrados.put("admin", "password123");
        // usuariosRegistrados.put("ejemplo@correo.com", "12345");
    }

    // *** Métodos para Gestión de Pedidos (Cola de Prioridad) ***

    /**
     * Agrega un nuevo pedido a la cola de prioridad.
     * @param pedido El pedido a agregar.
     */
    public void agregarPedido(Pedido pedido) {
        colaPedidos.offer(pedido); // offer() es el metodo para agregar a la cola
        System.out.println("Pedido agregado a la cola: " + pedido.getId() + " (" + pedido.getPrioridad() + ")");
        imprimirEstadoCola(); // Opcional: para ver el estado actual en consola
    }

    /**
     * Procesa y remueve el pedido de mayor prioridad de la cola.
     * @return El pedido procesado, o null si la cola está vacía.
     */
    public Pedido procesarSiguientePedido() {
        Pedido siguiente = colaPedidos.poll(); // poll() remueve y retorna el elemento de mayor prioridad
        if (siguiente != null) {
            System.out.println("Procesando pedido: " + siguiente.getId() + " (" + siguiente.getPrioridad() + ")");
        } else {
            System.out.println("La cola de pedidos está vacía.");
        }
        imprimirEstadoCola(); // Opcional: para ver el estado actual en consola
        return siguiente;
    }

    /**
     * Retorna (sin remover) el pedido de mayor prioridad.
     * @return El pedido de mayor prioridad, o null si la cola está vacía.
     */
    public Pedido verSiguientePedido() {
        return colaPedidos.peek(); // peek() retorna el elemento de mayor prioridad sin removerlo
    }

    /**
     * Retorna el número actual de pedidos en la cola.
     * @return El tamaño de la cola.
     */
    public int getNumeroPedidosEnCola() {
        return colaPedidos.size();
    }

    /**
     * Verifica si la cola de pedidos está vacía.
     * @return true si la cola está vacía, false en caso contrario.
     */
    public boolean estaVacia() {
        return colaPedidos.isEmpty();
    }

    /**
     * Metodo para obtener pedidos ordenados para la vista.
     * Crea una copia temporal de la cola y extrae los elementos en orden de prioridad.
     * @return Una lista de pedidos en orden de prioridad.
     */
    public List<Pedido> getPedidosOrdenadosParaVista() {
        List<Pedido> listaVisual = new ArrayList<>();
        // Creamos una copia temporal de la cola para no modificar la original
        Queue<Pedido> tempQueue = new PriorityQueue<>(this.colaPedidos);
        while (!tempQueue.isEmpty()) {
            listaVisual.add(tempQueue.poll()); // Sacamos los elementos en orden de prioridad
        }
        return listaVisual;
    }

    // Metodo opcional para imprimir el estado actual de la cola (útil para depuración)
    private void imprimirEstadoCola() {
        System.out.println("--- Estado actual de la cola ---");
        if (colaPedidos.isEmpty()) {
            System.out.println("Cola vacía");
        } else {
            // Crear una copia temporal para imprimir sin modificar la cola original
            Queue<Pedido> tempQueue = new PriorityQueue<>(colaPedidos);
            while (!tempQueue.isEmpty()) {
                Pedido p = tempQueue.poll();
                System.out.println("  - ID: " + p.getId() + ", Prioridad: " + p.getPrioridad() + ", Tiempo: " + p.getTiempoCreacion());
            }
        }
        System.out.println("------------------------------");
    }


    // *** Métodos para Gestión de Usuarios ***

    /**
     * Registra un nuevo usuario en el modelo.
     * @param email El correo electrónico del usuario (usado como usuario).
     * @param password La contraseña del usuario.
     * @return true si el usuario se registró con éxito (el correo no existía), false en caso contrario.
     */
    public boolean registrarUsuario(String email, String password) {
        // Convertir el correo a minúsculas para evitar distinción entre mayúsculas y minúsculas en el usuario
        String emailLowerCase = email.toLowerCase();
        if (usuariosRegistrados.containsKey(emailLowerCase)) {
            // Si el correo ya está registrado, no podemos registrarlo de nuevo
            return false;
        }
        // Registra el usuario con el correo en minúsculas
        usuariosRegistrados.put(emailLowerCase, password);
        System.out.println("Usuario registrado en el modelo: " + emailLowerCase);
        imprimirUsuariosRegistrados(); // Opcional: ver el estado de usuarios en consola
        return true;
    }

    /**
     * Verifica si las credenciales proporcionadas corresponden a un usuario registrado.
     * @param email El correo electrónico (usuario) a verificar.
     * @param password La contraseña a verificar.
     * @return true si las credenciales son válidas para un usuario existente, false en caso contrario.
     */
    public boolean verificarCredenciales(String email, String password) {
        // Convertir el correo ingresado a minúsculas para la verificación
        String emailLowerCase = email.toLowerCase();
        // Verifica si el correo existe como clave y si la contraseña asociada coincide
        return usuariosRegistrados.containsKey(emailLowerCase) && usuariosRegistrados.get(emailLowerCase).equals(password);
    }

    /**
     * Metodo para obtener el número actual de usuarios registrados.
     * @return El número de usuarios registrados.
     */
    public int getNumeroUsuariosRegistrados() {
        return usuariosRegistrados.size();
    }


    // Metodo opcional para imprimir los usuarios registrados (útil para depuración)
    public void imprimirUsuariosRegistrados() {
        System.out.println("--- Usuarios Registrados ---");
        if (usuariosRegistrados.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            // Iteramos sobre las claves (correos) y mostramos la información.
            // Cuidado con imprimir contraseñas en consolas en aplicaciones reales.
            usuariosRegistrados.forEach((correo, contra) ->
                    System.out.println("  - Correo: " + correo + ", Contraseña: " + contra)
            );
        }
        System.out.println("--------------------------");
    }
}