package co.edu.uniquindio.quindioexpress.comparadores;

import co.edu.uniquindio.quindioexpress.modelo.Paquete;

import java.util.Comparator;

/**
 * Orden por prioridad, de mayor a menor.
 *
 * <p>Se invierten los argumentos (b, a) para que el mayor quede primero.
 *
 * <p>Responsable: Dev 2.
 */
public class ComparadorPrioridad implements Comparator<Paquete> {

    @Override
    public int compare(Paquete a, Paquete b) {
        return Integer.compare(b.getPrioridad(), a.getPrioridad());
    }
}
