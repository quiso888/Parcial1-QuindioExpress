package co.edu.uniquindio.quindioexpress.servicio;

import co.edu.uniquindio.quindioexpress.estructuras.ListaSimplementeEnlazada;
import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import co.edu.uniquindio.quindioexpress.modelo.Repartidor;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Registro de paquetes y repartidores, asignación y entrega.
 *
 * <p>Cada atributo usa la estructura que mejor sirve a su operación dominante
 * (ver Parte A.1 en docs/):
 * <ul>
 *   <li>{@code paquetes}: LinkedHashMap código → Paquete. Búsqueda por código
 *       O(1) promedio y además conserva el orden exacto de registro
 *       (necesidades 2 y 7, y la auditoría del orden de llegada).</li>
 *   <li>{@code pendientes}: Queue con ArrayDeque. FIFO: offer/poll en O(1)
 *       (necesidad 1).</li>
 *   <li>{@code repartidores}: LinkedHashMap identificación → Repartidor.
 *       Unicidad y consulta O(1); el orden de registro hace predecible
 *       cuál repartidor se elige primero.</li>
 *   <li>{@code asignaciones}: HashMap código de paquete → repartidor que lo lleva.</li>
 *   <li>{@code historial}: lista simplemente enlazada PROPIA (necesidad 6,
 *       obligatorio por el enunciado).</li>
 * </ul>
 *
 * <p>Responsable: Dev 1.
 */
public class CentroDistribucion {

    private final Map<String, Paquete> paquetes = new LinkedHashMap<>();
    private final Queue<Paquete> pendientes = new ArrayDeque<>();
    private final Map<String, Repartidor> repartidores = new LinkedHashMap<>();
    private final Map<String, Repartidor> asignaciones = new HashMap<>();
    private final ListaSimplementeEnlazada<Paquete> historial = new ListaSimplementeEnlazada<>();

    // ------------------------------------------------------------------ paquetes

    /**
     * Registra un paquete al final del orden de llegada.
     * Antes de aceptarlo verifica que el código no exista (O(1) con containsKey).
     *
     * @throws IllegalArgumentException si el código ya está registrado
     */
    public void registrarPaquete(Paquete paquete) {
        if (paquete == null) {
            throw new IllegalArgumentException("El paquete no puede ser nulo");
        }
        if (paquetes.containsKey(paquete.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un paquete con el código " + paquete.getCodigo());
        }
        paquetes.put(paquete.getCodigo(), paquete);
        pendientes.offer(paquete);
    }

    /** true si existe un paquete con ese código. O(1) promedio. */
    public boolean existePaquete(String codigo) {
        return paquetes.containsKey(codigo);
    }

    /** Retorna el paquete con ese código, o null si no existe. O(1) promedio. */
    public Paquete buscarPaquete(String codigo) {
        return paquetes.get(codigo);
    }

    /** Todos los paquetes registrados, en orden de registro (copia, no se puede modificar). */
    public List<Paquete> getPaquetes() {
        return List.copyOf(paquetes.values());
    }

    /** Paquetes aún no entregados, en orden de llegada (copia, no se puede modificar). */
    public List<Paquete> getPendientes() {
        return List.copyOf(pendientes);
    }

    // -------------------------------------------------------------- repartidores

    /**
     * @throws IllegalArgumentException si la identificación ya está registrada
     */
    public void registrarRepartidor(Repartidor repartidor) {
        if (repartidor == null) {
            throw new IllegalArgumentException("El repartidor no puede ser nulo");
        }
        if (repartidores.containsKey(repartidor.getIdentificacion())) {
            throw new IllegalArgumentException("Ya existe un repartidor con la identificación "
                    + repartidor.getIdentificacion());
        }
        repartidores.put(repartidor.getIdentificacion(), repartidor);
    }

    /** Retorna el repartidor o null si no existe. O(1) promedio. */
    public Repartidor buscarRepartidor(String identificacion) {
        return repartidores.get(identificacion);
    }

    public List<Repartidor> getRepartidores() {
        return List.copyOf(repartidores.values());
    }

    // ----------------------------------------------------------------- asignación

    /**
     * Asigna un paquete pendiente a un repartidor concreto, validando las reglas:
     * el repartidor debe estar disponible y su zona debe ser el destino del paquete.
     *
     * @throws IllegalArgumentException si el paquete o el repartidor no existen
     * @throws IllegalStateException    si se incumple alguna regla de asignación
     */
    public void asignar(String codigoPaquete, String idRepartidor) {
        Paquete paquete = buscarPaquete(codigoPaquete);
        Repartidor repartidor = buscarRepartidor(idRepartidor);
        if (paquete == null) {
            throw new IllegalArgumentException("No existe el paquete " + codigoPaquete);
        }
        if (repartidor == null) {
            throw new IllegalArgumentException("No existe el repartidor " + idRepartidor);
        }
        if (!pendientes.contains(paquete)) {
            throw new IllegalStateException("El paquete " + codigoPaquete + " ya fue entregado");
        }
        if (asignaciones.containsKey(codigoPaquete)) {
            throw new IllegalStateException("El paquete " + codigoPaquete + " ya tiene repartidor asignado");
        }
        if (!repartidor.isDisponible()) {
            throw new IllegalStateException("El repartidor " + idRepartidor + " no está disponible");
        }
        if (!repartidor.getZona().equalsIgnoreCase(paquete.getDestino())) {
            throw new IllegalStateException("La zona de " + idRepartidor + " (" + repartidor.getZona()
                    + ") no corresponde al destino de " + codigoPaquete + " (" + paquete.getDestino() + ")");
        }
        repartidor.setDisponible(false);
        asignaciones.put(codigoPaquete, repartidor);
    }

    /**
     * Busca el primer repartidor disponible cuya zona sea el destino del paquete
     * y se lo asigna. O(r), con r = número de repartidores.
     *
     * @return el repartidor asignado, o null si no hay ninguno que cumpla las reglas
     */
    public Repartidor asignarAutomaticamente(String codigoPaquete) {
        Paquete paquete = buscarPaquete(codigoPaquete);
        if (paquete == null) {
            throw new IllegalArgumentException("No existe el paquete " + codigoPaquete);
        }
        for (Repartidor r : repartidores.values()) {
            if (r.isDisponible() && r.getZona().equalsIgnoreCase(paquete.getDestino())) {
                asignar(codigoPaquete, r.getIdentificacion());
                return r;
            }
        }
        return null;
    }

    /** Repartidor que lleva el paquete, o null si no está asignado. */
    public Repartidor repartidorAsignado(String codigoPaquete) {
        return asignaciones.get(codigoPaquete);
    }

    // -------------------------------------------------------------------- entrega

    /**
     * Registra la entrega de un paquete asignado: sale de pendientes, entra al
     * final del historial (orden real de entrega) y el repartidor queda libre.
     *
     * @throws IllegalStateException si el paquete no tiene repartidor asignado
     */
    public void registrarEntrega(String codigoPaquete) {
        Repartidor repartidor = asignaciones.remove(codigoPaquete);
        if (repartidor == null) {
            throw new IllegalStateException("El paquete " + codigoPaquete + " no tiene repartidor asignado");
        }
        Paquete paquete = paquetes.get(codigoPaquete);
        pendientes.remove(paquete);      // O(n): busca en la cola
        historial.agregar(paquete);      // O(1): agrega en la cola de la lista propia
        repartidor.setDisponible(true);
    }

    /**
     * El historial se entrega como Iterable: quien lo usa puede recorrerlo con
     * for-each pero no puede modificarlo ni ver sus nodos.
     */
    public Iterable<Paquete> getHistorial() {
        return historial;
    }

    public int cantidadEntregados() {
        return historial.tamanio();
    }

    /** Paquete entregado en la posición indicada (0 = primero entregado). */
    public Paquete entregadoEn(int posicion) {
        return historial.obtener(posicion);
    }
}
