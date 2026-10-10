package co.edu.uniquindio.quindioexpress.comparadores;

import co.edu.uniquindio.quindioexpress.modelo.Paquete;

import java.util.Comparator;

/**
 * Reglas de despacho prioritario:
 * <ol>
 *   <li>mayor prioridad primero;</li>
 *   <li>si empatan, menor tiempo estimado primero;</li>
 *   <li>si siguen empatados, código alfabéticamente primero (orden natural).</li>
 * </ol>
 *
 * <p>Reutiliza los otros comparadores en lugar de repetir la lógica.
 *
 * <p>Ojo (caso de IA cuestionable, Parte D): escribirlo como
 * {@code comparingInt(prioridad).thenComparingInt(tiempo).reversed()} está MAL,
 * porque reversed() invierte también el tiempo y rompe la regla 2.
 *
 * <p>Responsable: Dev 2.
 */
public class ComparadorDespacho implements Comparator<Paquete> {

    private final Comparator<Paquete> porPrioridad = new ComparadorPrioridad();
    private final Comparator<Paquete> porTiempo = new ComparadorTiempo();

    @Override
    public int compare(Paquete a, Paquete b) {
        int resultado = porPrioridad.compare(a, b);
        if (resultado != 0) {
            return resultado;
        }
        resultado = porTiempo.compare(a, b);
        if (resultado != 0) {
            return resultado;
        }
        return a.compareTo(b);
    }
}
