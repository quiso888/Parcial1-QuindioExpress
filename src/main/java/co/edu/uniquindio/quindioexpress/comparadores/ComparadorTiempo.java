package co.edu.uniquindio.quindioexpress.comparadores;
import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import java.util.Comparator;

/**
 * Orden por tiempo estimado, de menor a mayor.
 *
 * <p>Responsable: Dev 2.
 */
public class ComparadorTiempo implements Comparator<Paquete> {

    @Override
    public int compare(Paquete p1, Paquete p2) {
        // Usa getTiempoEstimado() en lugar de getTiempo()
        return Integer.compare(p1.getTiempoEstimado(), p2.getTiempoEstimado());
    }
}
