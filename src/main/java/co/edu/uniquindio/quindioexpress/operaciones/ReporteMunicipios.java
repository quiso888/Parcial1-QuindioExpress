package co.edu.uniquindio.quindioexpress.operaciones;

import co.edu.uniquindio.quindioexpress.modelo.Paquete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Municipios únicos, ordenados y agrupación de paquetes por municipio.
 *
 * <p>Operaciones sin estado: reciben los paquetes por parámetro y no
 * modifican el centro de distribución.
 */

public final class ReporteMunicipios {

    private ReporteMunicipios() {
    }

    /**
     * Municipios con al menos un paquete, sin repetidos.
     *
     * <p>Set: add() de un elemento que ya está no hace nada, así se evitan
     * duplicados en O(1) promedio por paquete. Se usa LinkedHashSet (y no
     * HashSet) para que salgan en orden de aparición
     */
    public static Set<String> municipios(Collection<Paquete> paquetes) {
        Set<String> resultado = new LinkedHashSet<>();
        for (Paquete p : paquetes) {
            resultado.add(p.getDestino());
        }
        return resultado;
    }

    /**
     * Municipios sin repetidos y en orden alfabético (Armenia, Calarcá, Salento).
     *
     */
    public static SortedSet<String> municipiosOrdenados(Collection<Paquete> paquetes) {
        SortedSet<String> resultado = new TreeSet<>();
        for (Paquete p : paquetes) {
            resultado.add(p.getDestino());
        }
        return resultado;
    }

    /**
     * Relación municipio → paquetes destinados a ese municipio.
     *
     * <p>Map: clave = municipio (única), valor = lista de sus paquetes en
     * orden de llegada. computeIfAbsent crea la lista la primera vez que
     * aparece el municipio. Costo total O(n).
     */
    public static Map<String, List<Paquete>> paquetesPorMunicipio(Collection<Paquete> paquetes) {
        Map<String, List<Paquete>> resultado = new LinkedHashMap<>();
        for (Paquete p : paquetes) {
            resultado.computeIfAbsent(p.getDestino(), k -> new ArrayList<>()).add(p);
        }
        return resultado;
    }
}
