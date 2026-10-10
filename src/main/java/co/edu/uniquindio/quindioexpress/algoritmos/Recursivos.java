package co.edu.uniquindio.quindioexpress.algoritmos;

import co.edu.uniquindio.quindioexpress.modelo.Paquete;

import java.util.List;

/**
 * Peso total por municipio y conteo por prioridad mínima (recursión lineal).
 *
 * <p>Ningún método recursivo usa for, while ni do-while. Cada operación tiene
 * un método público que arranca la recursión en i = 0 y uno privado que hace
 * el trabajo. Los métodos "ConTraza" hacen exactamente lo mismo pero imprimen
 * cada llamada y cada retorno: sirven para construir la tabla de la pila de
 * llamadas de la Parte C y para la sustentación.
 *
 * <p>Se trabaja sobre List (ArrayList / List.of), donde get(i) es O(1). Sobre
 * la lista enlazada propia, obtener(i) cuesta O(i) y el total sería O(n²).
 *
 * <pre>
 * Tiempo O(n): una llamada por paquete.
 * Espacio O(n): hay n + 1 llamadas apiladas a la vez antes del primer retorno.
 * Con 500.000 paquetes esto puede lanzar StackOverflowError.
 * </pre>
 *
 * <p>Responsable: Dev 2.
 */
public final class Recursivos {

    private Recursivos() {
    }

    // ------------------------------------------------------ peso total por municipio

    /** Suma el peso de los paquetes cuyo destino es el municipio dado. */
    public static double pesoTotalPorMunicipio(List<Paquete> paquetes, String municipio) {
        return pesoTotal(paquetes, municipio, 0, false);
    }

    /** Igual que {@link #pesoTotalPorMunicipio}, imprimiendo la pila de llamadas. */
    public static double pesoTotalPorMunicipioConTraza(List<Paquete> paquetes, String municipio) {
        return pesoTotal(paquetes, municipio, 0, true);
    }

    /**
     * Caso base: i == tamaño, ya no hay paquetes, retorna 0.
     * Caso recursivo: (peso de paquetes[i] si coincide el destino, si no 0) + f(i + 1).
     * Avance hacia el caso base: i aumenta en 1 en cada llamada.
     */
    private static double pesoTotal(List<Paquete> paquetes, String municipio, int i, boolean traza) {
        String sangria = "  ".repeat(i);
        if (i == paquetes.size()) {
            if (traza) {
                System.out.printf("%sf(i=%d) caso base -> retorna 0.0%n", sangria, i);
            }
            return 0;
        }

        Paquete actual = paquetes.get(i);
        boolean coincide = actual.getDestino().equalsIgnoreCase(municipio);
        double aporte = coincide ? actual.getPeso() : 0;
        if (traza) {
            System.out.printf("%sf(i=%d) %s %s -> queda pendiente %.1f + f(%d)%n",
                    sangria, i, actual.getCodigo(), actual.getDestino(), aporte, i + 1);
        }

        double resultado = aporte + pesoTotal(paquetes, municipio, i + 1, traza);

        if (traza) {
            System.out.printf("%sf(i=%d) retorna %.1f%n", sangria, i, resultado);
        }
        return resultado;
    }

    // ------------------------------------------------ conteo por prioridad mínima

    /** Cuenta los paquetes con prioridad mayor o igual a la mínima. */
    public static int contarPrioridadMinima(List<Paquete> paquetes, int prioridadMinima) {
        return contar(paquetes, prioridadMinima, 0, false);
    }

    /** Igual que {@link #contarPrioridadMinima}, imprimiendo la pila de llamadas. */
    public static int contarPrioridadMinimaConTraza(List<Paquete> paquetes, int prioridadMinima) {
        return contar(paquetes, prioridadMinima, 0, true);
    }

    /**
     * Caso base: i == tamaño, retorna 0.
     * Caso recursivo: (1 si prioridad de paquetes[i] &gt;= mínima, si no 0) + f(i + 1).
     */
    private static int contar(List<Paquete> paquetes, int prioridadMinima, int i, boolean traza) {
        String sangria = "  ".repeat(i);
        if (i == paquetes.size()) {
            if (traza) {
                System.out.printf("%sg(i=%d) caso base -> retorna 0%n", sangria, i);
            }
            return 0;
        }

        Paquete actual = paquetes.get(i);
        int aporte = actual.getPrioridad() >= prioridadMinima ? 1 : 0;
        if (traza) {
            System.out.printf("%sg(i=%d) %s prioridad %d -> queda pendiente %d + g(%d)%n",
                    sangria, i, actual.getCodigo(), actual.getPrioridad(), aporte, i + 1);
        }

        int resultado = aporte + contar(paquetes, prioridadMinima, i + 1, traza);

        if (traza) {
            System.out.printf("%sg(i=%d) retorna %d%n", sangria, i, resultado);
        }
        return resultado;
    }
}
