package co.edu.uniquindio.quindioexpress.operaciones;

import co.edu.uniquindio.quindioexpress.comparadores.ComparadorDespacho;
import co.edu.uniquindio.quindioexpress.modelo.Paquete;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Las dos modalidades de atención:
 * <ul>
 *   <li>por orden de llegada: Queue con ArrayDeque (FIFO), offer/poll en O(1);</li>
 *   <li>prioritaria: PriorityQueue con {@link ComparadorDespacho}, offer/poll
 *       en O(log n). Es un montículo (heap): siempre sabe cuál es el siguiente
 *       sin mantener toda la colección ordenada.</li>
 * </ul>
 *
 * <p>Recibe los paquetes por parámetro; no modifica el centro de distribución.
 * Ojo: iterar una PriorityQueue con for-each NO da el orden de prioridad;
 * el orden solo se respeta al sacar con poll().
 *
 * <p>Responsable: Dev 2.
 */
public class Despacho {

    private final Queue<Paquete> porLlegada;
    private final PriorityQueue<Paquete> prioritaria;

    /**
     * @param paquetesEnOrdenDeLlegada paquetes en el orden en que se registraron
     */
    public Despacho(Collection<Paquete> paquetesEnOrdenDeLlegada) {
        this.porLlegada = new ArrayDeque<>(paquetesEnOrdenDeLlegada);
        this.prioritaria = new PriorityQueue<>(new ComparadorDespacho());
        this.prioritaria.addAll(paquetesEnOrdenDeLlegada);
    }

    /** Siguiente según orden de llegada (el primero registrado sale primero), o null si no hay. */
    public Paquete siguientePorLlegada() {
        return porLlegada.poll();
    }

    /** Siguiente según prioridad, tiempo y código, o null si no hay. Se puede llamar repetidamente. */
    public Paquete siguientePrioritario() {
        return prioritaria.poll();
    }

    /** Consulta el siguiente prioritario sin sacarlo. */
    public Paquete verSiguientePrioritario() {
        return prioritaria.peek();
    }

    public boolean hayPorLlegada() {
        return !porLlegada.isEmpty();
    }

    public boolean hayPrioritarios() {
        return !prioritaria.isEmpty();
    }
}
