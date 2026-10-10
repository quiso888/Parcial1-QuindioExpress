package co.edu.uniquindio.quindioexpress.demo;

import co.edu.uniquindio.quindioexpress.algoritmos.Recursivos;
import co.edu.uniquindio.quindioexpress.comparadores.ComparadorPeso;
import co.edu.uniquindio.quindioexpress.comparadores.ComparadorPrioridad;
import co.edu.uniquindio.quindioexpress.comparadores.ComparadorTiempo;
import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import co.edu.uniquindio.quindioexpress.operaciones.Despacho;
import co.edu.uniquindio.quindioexpress.operaciones.ReporteMunicipios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Demostración de colecciones, despacho y algoritmos.
 *  se agregan municipios (Set, TreeSet, Map) y recursividad.
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

        demoMunicipios();
        demoRecursivos();
    }

    private static void demoMunicipios() {
        Titulos.seccion("DEV 2 - MUNICIPIOS (Set y Map)");
        // Ejemplo del enunciado
        List<Paquete> ejemplo = List.of(
                new Paquete("PQ731", "Armenia", 12.0, 5, 45),
                new Paquete("PQ105", "Salento", 4.5, 5, 25),
                new Paquete("PQ942", "Armenia", 3.2, 4, 20),
                new Paquete("PQ318", "Calarcá", 18.7, 2, 35),
                new Paquete("PQ567", "Salento", 7.4, 3, 50),
                new Paquete("PQ829", "Armenia", 5.1, 1, 15)
        );
        System.out.println("Paquetes: " + codigos(ejemplo));
        System.out.println("Municipios sin repetir (LinkedHashSet): " + ReporteMunicipios.municipios(ejemplo)
                + "  (esperado: [Armenia, Salento, Calarcá])");
        System.out.println("Municipios ordenados (TreeSet):         " + ReporteMunicipios.municipiosOrdenados(ejemplo)
                + "  (esperado: [Armenia, Calarcá, Salento])");
        System.out.println("Paquetes por municipio (Map):");
        for (Map.Entry<String, List<Paquete>> e : ReporteMunicipios.paquetesPorMunicipio(ejemplo).entrySet()) {
            System.out.println("  " + e.getKey() + " -> " + codigos(e.getValue()));
        }
    }

    private static void demoRecursivos() {
        Titulos.seccion("DEV 2 - RECURSIVIDAD (con traza de la pila de llamadas)");

        List<Paquete> ejemploPeso = List.of(
                new Paquete("PQ101", "Armenia", 4.5, 3, 22),
                new Paquete("PQ203", "Salento", 3.0, 2, 60),
                new Paquete("PQ415", "Armenia", 7.2, 4, 28),
                new Paquete("PQ730", "Armenia", 2.3, 1, 12)
        );
        Titulos.sub("Peso total de Armenia (esperado 14.0)");
        double peso = Recursivos.pesoTotalPorMunicipioConTraza(ejemploPeso, "Armenia");
        System.out.printf("Resultado: %.1f kg%n", peso);

        // Prioridades 5, 2, 4, 1, 5, 3, 4
        List<Paquete> ejemploPrioridades = List.of(
                new Paquete("PQ501", "Armenia", 1.0, 5, 10),
                new Paquete("PQ502", "Salento", 1.0, 2, 10),
                new Paquete("PQ503", "Calarcá", 1.0, 4, 10),
                new Paquete("PQ504", "Armenia", 1.0, 1, 10),
                new Paquete("PQ505", "Circasia", 1.0, 5, 10),
                new Paquete("PQ506", "Quimbaya", 1.0, 3, 10),
                new Paquete("PQ507", "Montenegro", 1.0, 4, 10)
        );
        Titulos.sub("Paquetes con prioridad >= 4 en 5, 2, 4, 1, 5, 3, 4 (esperado 4)");
        int cantidad = Recursivos.contarPrioridadMinimaConTraza(ejemploPrioridades, 4);
        System.out.println("Resultado: " + cantidad + " paquetes");
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
