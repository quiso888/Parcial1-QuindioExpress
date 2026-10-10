package co.edu.uniquindio.quindioexpress.demo;

import co.edu.uniquindio.quindioexpress.comparadores.ComparadorPeso;
import co.edu.uniquindio.quindioexpress.comparadores.ComparadorPrioridad;
import co.edu.uniquindio.quindioexpress.comparadores.ComparadorTiempo;
import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import co.edu.uniquindio.quindioexpress.operaciones.Despacho;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Demostración de colecciones, despacho y algoritmos.
 *
 * <p>Primer commit: comparadores y despacho (Queue y PriorityQueue).
 * En los siguientes commits de Dev 2 se agregan municipios, recursividad,
 * búsqueda binaria y divide y vencerás.
 *
 * <p>Responsable: Dev 2.
 */
public final class DemoAlgoritmos {

    private DemoAlgoritmos() {
    }

    /** Permite probar este módulo solo, sin depender de Main. */
    public static void main(String[] args) {
        ejecutar();
    }

    public static void ejecutar() {
        // Ejemplo del enunciado, en orden de llegada
        List<Paquete> llegada = List.of(
                new Paquete("PQ420", "Circasia", 6.0, 3, 30),
                new Paquete("PQ731", "Armenia", 12.0, 5, 45),
                new Paquete("PQ105", "Salento", 4.5, 5, 25),
                new Paquete("PQ942", "Armenia", 3.2, 4, 20)
        );

        Titulos.seccion("DEV 2 - COMPARADORES (Comparator)");
        System.out.println("Orden de llegada:          " + codigos(llegada));
        ordenarYMostrar("Prioridad (mayor a menor)", llegada, new ComparadorPrioridad());
        ordenarYMostrar("Peso (mayor a menor)", llegada, new ComparadorPeso());
        ordenarYMostrar("Tiempo (menor a mayor)", llegada, new ComparadorTiempo());
        System.out.println("La lista original no cambió: " + codigos(llegada));

        Titulos.seccion("DEV 2 - DESPACHO (Queue y PriorityQueue)");
        Despacho despacho = new Despacho(llegada);

        StringBuilder fifo = new StringBuilder();
        while (despacho.hayPorLlegada()) {
            fifo.append(despacho.siguientePorLlegada().getCodigo()).append(" ");
        }
        System.out.println("Por llegada (FIFO): " + fifo + " (esperado: PQ420 PQ731 PQ105 PQ942)");

        StringBuilder prioritario = new StringBuilder();
        while (despacho.hayPrioritarios()) {
            prioritario.append(despacho.siguientePrioritario().getCodigo()).append(" ");
        }
        System.out.println("Prioritario:        " + prioritario + " (esperado: PQ105 PQ731 PQ942 PQ420)");
    }

    private static void ordenarYMostrar(String nombre, List<Paquete> lista, Comparator<Paquete> criterio) {
        List<Paquete> copia = new ArrayList<>(lista);     // se ordena una copia
        copia.sort(criterio);
        System.out.printf("%-26s %s%n", nombre + ":", codigos(copia));
    }

    private static String codigos(List<Paquete> paquetes) {
        StringBuilder sb = new StringBuilder();
        for (Paquete p : paquetes) {
            sb.append(p.getCodigo()).append(" ");
        }
        return sb.toString().trim();
    }
}
