package co.edu.uniquindio.quindioexpress.algoritmos;

import co.edu.uniquindio.quindioexpress.modelo.Paquete;

import java.util.List;

/**
 * Paquete de mayor peso por divide y vencerás.
 * Etapas, con el mismo nombre en el código:
 * <ol>
 *   Caso base: el rango tiene un solo paquete (ini == fin); ese es el mayor.</li>
 *   División: medio = (ini + fin) / 2.</li>
 *   Subproblema izquierdo: mayor de [ini, medio].</li>
 *   Subproblema derecho: mayor de [medio + 1, fin].</li>
 *   Combinación: el de mayor peso entre los dos resultados.</li>
 *
 *   Resultado final: lo que retorna la primera llamada, sobre [0, n - 1].</li>
 * </ol>
 *
 * No es lo mismo que una recursión lineal (f(i) = max(p[i], f(i + 1))):
 * aquí el problema se parte en DOS mitades independientes y luego se combinan.
 *
 *
 * Tiempo O(n): T(n) = 2T(n/2) + O(1), se visita cada paquete una vez.
 * Espacio O(log n): la pila solo llega a la altura del árbol de llamadas.
 * </pre>
 *
 * <p>Responsable: Dev 2.
 */
public final class DivideYVenceras {

    private DivideYVenceras() {
    }

    /** @throws IllegalArgumentException si la lista está vacía */
    public static Paquete mayorPeso(List<Paquete> paquetes) {
        validar(paquetes);
        return mayorPeso(paquetes, 0, paquetes.size() - 1, 0, false);
    }

    /** Igual que {@link #mayorPeso}, imprimiendo el árbol de llamadas. */
    public static Paquete mayorPesoConTraza(List<Paquete> paquetes) {
        validar(paquetes);
        return mayorPeso(paquetes, 0, paquetes.size() - 1, 0, true);
    }

    private static Paquete mayorPeso(List<Paquete> paquetes, int ini, int fin, int nivel, boolean traza) {
        String sangria = "  ".repeat(nivel);

        // 1. Caso base: un solo elemento
        if (ini == fin) {
            Paquete unico = paquetes.get(ini);
            if (traza) {
                System.out.printf("%s[%d..%d] caso base -> %.1f (%s)%n", sangria, ini, fin, unico.getPeso(), unico.getCodigo());
            }
            return unico;
        }

        // 2. División
        int medio = (ini + fin) / 2;
        if (traza) {
            System.out.printf("%s[%d..%d] dividir en [%d..%d] y [%d..%d]%n", sangria, ini, fin, ini, medio, medio + 1, fin);
        }

        // 3. Subproblema izquierdo
        Paquete izquierdo = mayorPeso(paquetes, ini, medio, nivel + 1, traza);

        // 4. Subproblema derecho
        Paquete derecho = mayorPeso(paquetes, medio + 1, fin, nivel + 1, traza);

        // 5. Combinación
        Paquete mayor = izquierdo.getPeso() >= derecho.getPeso() ? izquierdo : derecho;
        if (traza) {
            System.out.printf("%s[%d..%d] combinar max(%.1f, %.1f) -> %.1f%n", sangria, ini, fin,
                    izquierdo.getPeso(), derecho.getPeso(), mayor.getPeso());
        }
        return mayor;
    }

    private static void validar(List<Paquete> paquetes) {
        if (paquetes == null || paquetes.isEmpty()) {
            throw new IllegalArgumentException("No se puede buscar el mayor peso en una lista vacía");
        }
    }
}
