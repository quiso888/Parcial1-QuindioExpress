package co.edu.uniquindio.quindioexpress.demo;

import co.edu.uniquindio.quindioexpress.algoritmos.BusquedaBinaria;
import co.edu.uniquindio.quindioexpress.algoritmos.DivideYVenceras;
import co.edu.uniquindio.quindioexpress.algoritmos.Recursivos;
import co.edu.uniquindio.quindioexpress.datos.DatosPrueba;
import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import co.edu.uniquindio.quindioexpress.modelo.Repartidor;
import co.edu.uniquindio.quindioexpress.operaciones.Despacho;
import co.edu.uniquindio.quindioexpress.operaciones.ReporteMunicipios;
import co.edu.uniquindio.quindioexpress.servicio.CentroDistribucion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Integración: aplica las operaciones de Dev 2 sobre la jornada real de Dev 1
 * (32 paquetes, 7 repartidores). Es la demostración que se usa en la sustentación
 * para mostrar que todas las piezas funcionan juntas.
 *
 * <p>Responsable: Dev 3.
 */
public final class DemoJornada {

    private DemoJornada() {
    }

    /** Permite probar la integración sola, sin depender de Main. */
    public static void main(String[] args) {
        ejecutar(DatosPrueba.crearCentro());
    }

    public static void ejecutar(CentroDistribucion centro) {
        demoMunicipios(centro);
        demoDespacho(centro);
        demoRecursivos(centro);
        demoBusquedaBinaria(centro);
        demoMayorPeso(centro);
    }

    private static void demoMunicipios(CentroDistribucion centro) {
        Titulos.seccion("JORNADA - MUNICIPIOS Y PAQUETES POR MUNICIPIO");
        System.out.println("Municipios ordenados: " + ReporteMunicipios.municipiosOrdenados(centro.getPaquetes()));
        for (Map.Entry<String, List<Paquete>> e : ReporteMunicipios.paquetesPorMunicipio(centro.getPaquetes()).entrySet()) {
            System.out.println("  " + e.getKey() + " -> " + codigos(e.getValue()));
        }
    }

    /** Despacho prioritario + asignación a repartidor + entrega + historial en la lista propia. */
    private static void demoDespacho(CentroDistribucion centro) {
        Titulos.seccion("JORNADA - 10 DESPACHOS PRIORITARIOS CON ASIGNACIÓN Y ENTREGA");
        Despacho despacho = new Despacho(centro.getPendientes());
        int entregados = 0;
        while (entregados < 10 && despacho.hayPrioritarios()) {
            Paquete p = despacho.siguientePrioritario();
            Repartidor r = centro.asignarAutomaticamente(p.getCodigo());
            if (r == null) {
                System.out.println("  " + p + " -> sin repartidor disponible en " + p.getDestino());
                continue;
            }
            centro.registrarEntrega(p.getCodigo());
            System.out.println("  " + p + " -> " + r.getIdentificacion() + " " + r.getNombre() + " -> entregado");
            entregados++;
        }

        Titulos.sub("Historial de entregas (lista enlazada propia, recorrida con for-each)");
        System.out.println("  " + codigos(centro.getHistorial()));
        System.out.println("  Entregados: " + centro.cantidadEntregados() + ", primero: " + centro.entregadoEn(0).getCodigo()
                + ", pendientes: " + centro.getPendientes().size());
    }

    private static void demoRecursivos(CentroDistribucion centro) {
        Titulos.seccion("JORNADA - RECURSIVIDAD SOBRE LOS 32 PAQUETES");
        for (String municipio : ReporteMunicipios.municipiosOrdenados(centro.getPaquetes())) {
            System.out.printf("  Peso total %-10s = %6.1f kg%n", municipio,
                    Recursivos.pesoTotalPorMunicipio(centro.getPaquetes(), municipio));
        }
        System.out.println("  Paquetes con prioridad >= 4: " + Recursivos.contarPrioridadMinima(centro.getPaquetes(), 4));
    }

    private static void demoBusquedaBinaria(CentroDistribucion centro) {
        Titulos.seccion("JORNADA - BÚSQUEDA BINARIA");
        List<Paquete> copia = new ArrayList<>(centro.getPaquetes());
        Collections.sort(copia);                   // precondición: ordenar una copia por código
        for (String codigo : List.of("PQ059", "PQ731", "PQ999")) {
            int pos = BusquedaBinaria.buscar(copia, codigo);
            System.out.println("  " + codigo + " -> " + (pos < 0 ? "no existe" : "posición " + pos + ": " + copia.get(pos)));
        }
    }

    private static void demoMayorPeso(CentroDistribucion centro) {
        Titulos.seccion("JORNADA - PAQUETE DE MAYOR PESO (divide y vencerás)");
        System.out.println("  " + DivideYVenceras.mayorPeso(centro.getPaquetes()));
    }

    private static String codigos(Iterable<Paquete> paquetes) {
        StringBuilder sb = new StringBuilder();
        for (Paquete p : paquetes) {
            if (sb.length() > 0) {
                sb.append(" -> ");
            }
            sb.append(p.getCodigo());
        }
        return sb.length() == 0 ? "(vacío)" : sb.toString();
    }
}
