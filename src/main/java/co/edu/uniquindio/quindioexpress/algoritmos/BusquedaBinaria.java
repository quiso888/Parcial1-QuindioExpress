package co.edu.uniquindio.quindioexpress.algoritmos;

import co.edu.uniquindio.quindioexpress.modelo.Paquete;

import java.util.List;

/**
 * Búsqueda binaria por código sobre una lista ordenada.
 *
 * <p><b>PRECONDICIÓN:</b> la lista debe estar ordenada por código ascendente
 * (el orden natural de Paquete). Si no lo está, el resultado no es confiable:
 * puede decir que un paquete no existe aunque sí esté. No se verifica aquí
 * porque revisar el orden cuesta O(n) y anularía la ventaja de O(log n);
 * para eso existe {@link #estaOrdenadaPorCodigo}.
 *
 * <pre>
 * Tiempo O(log n): cada paso descarta la mitad. Con 8 paquetes, máximo 4 pasos;
 *                  con 500.000, unos 19.
 * Espacio O(1) la iterativa; O(log n) la recursiva (por la pila).
 * </pre>
 *
 * <p>Responsable: Dev 2.
 */
public final class BusquedaBinaria {

    private BusquedaBinaria() {
    }


    public static int buscar(List<Paquete> ordenadosPorCodigo, String codigo) {
        return buscarIterativa(ordenadosPorCodigo, codigo, false);
    }


    public static int buscarConTraza(List<Paquete> ordenadosPorCodigo, String codigo) {
        return buscarIterativa(ordenadosPorCodigo, codigo, true);
    }

    private static int buscarIterativa(List<Paquete> lista, String codigo, boolean traza) {
        int ini = 0;
        int fin = lista.size() - 1;
        int paso = 1;
        while (ini <= fin) {
            int medio = ini + (fin - ini) / 2;     // evita desbordamiento de (ini + fin)
            String codigoMedio = lista.get(medio).getCodigo();
            int comparacion = codigoMedio.compareTo(codigo);
            if (traza) {
                System.out.printf("  paso %d: ini=%d fin=%d medio=%d (%s) -> %s%n", paso, ini, fin, medio,
                        codigoMedio, comparacion == 0 ? "encontrado" : comparacion < 0 ? "buscar a la derecha" : "buscar a la izquierda");
            }
            if (comparacion == 0) {
                return medio;
            } else if (comparacion < 0) {
                ini = medio + 1;                   // el buscado es mayor: mitad derecha
            } else {
                fin = medio - 1;                   // el buscado es menor: mitad izquierda
            }
            paso++;
        }
        if (traza) {
            System.out.printf("  ini=%d > fin=%d -> no está%n", ini, fin);
        }
        return -1;
    }

    /** Versión recursiva (mismo resultado). Caso base: ini &gt; fin o encontrado en medio. */
    public static int buscarRecursiva(List<Paquete> ordenadosPorCodigo, String codigo) {
        return buscarRecursiva(ordenadosPorCodigo, codigo, 0, ordenadosPorCodigo.size() - 1);
    }

    private static int buscarRecursiva(List<Paquete> lista, String codigo, int ini, int fin) {
        if (ini > fin) {
            return -1;
        }
        int medio = ini + (fin - ini) / 2;
        int comparacion = lista.get(medio).getCodigo().compareTo(codigo);
        if (comparacion == 0) {
            return medio;
        }
        if (comparacion < 0) {
            return buscarRecursiva(lista, codigo, medio + 1, fin);
        }
        return buscarRecursiva(lista, codigo, ini, medio - 1);
    }

    /** Verifica la precondición. O(n). */
    public static boolean estaOrdenadaPorCodigo(List<Paquete> lista) {
        for (int i = 1; i < lista.size(); i++) {
            if (lista.get(i - 1).compareTo(lista.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }
}
