package co.edu.uniquindio.quindioexpress.comparadores;

import co.edu.uniquindio.quindioexpress.modelo.Paquete;

import java.util.Comparator;

/**
 * Orden por peso, de mayor a menor.
 *
 * <p>Se usa Double.compare y no una resta (a - b) porque la resta de double
 * no se puede convertir a int sin perder información (0.3 se volvería 0).
 *
 * <p>Responsable: Dev 2.
 */
public class ComparadorPeso implements Comparator<Paquete> {

    @Override
    public int compare(Paquete a, Paquete b) {
        return Double.compare(b.getPeso(), a.getPeso());
    }
}
