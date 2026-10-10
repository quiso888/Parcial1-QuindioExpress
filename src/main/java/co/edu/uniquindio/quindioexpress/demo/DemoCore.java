package co.edu.uniquindio.quindioexpress.demo;

import co.edu.uniquindio.quindioexpress.estructuras.IColeccion;
import co.edu.uniquindio.quindioexpress.estructuras.ListaSimplementeEnlazada;
import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import co.edu.uniquindio.quindioexpress.modelo.Repartidor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Demostración de modelo, lista propia y centro de distribución.
 *
 * <p>Tanda 2: se agrega la lista simplemente enlazada propia con sus casos borde.
 * En la tanda 3 llegan el centro de distribución y los datos de prueba.
 *
 * <p>Responsable: Dev 1.
 */
public final class DemoCore {

    private DemoCore() {
    }

    /** Permite probar este módulo solo, sin depender de Main. */
    public static void main(String[] args) {
        ejecutar();
    }

    public static void ejecutar() {
        Titulos.seccion("DEV 1 - MODELO: Paquete y Repartidor");

        Paquete p1 = new Paquete("PQ731", "Armenia", 12.0, 5, 45);
        Paquete p2 = new Paquete("PQ105", "Salento", 4.5, 5, 25);
        Paquete p3 = new Paquete("PQ942", "Armenia", 3.2, 4, 20);
        Paquete p4 = new Paquete("PQ318", "Calarcá", 18.7, 25);   // sin prioridad: queda en 5
        System.out.println("Paquetes creados:");
        System.out.println("  " + p1);
        System.out.println("  " + p2);
        System.out.println("  " + p3);
        System.out.println("  " + p4 + "  <- prioridad por defecto");

        Titulos.sub("Validaciones (cada una debe ser rechazada)");
        intentar("Peso 0", () -> new Paquete("PQ999", "Armenia", 0, 3, 10));
        intentar("Prioridad 7 (rango " + Paquete.PRIORIDAD_MIN + " a " + Paquete.PRIORIDAD_MAX + ")",
                () -> new Paquete("PQ998", "Armenia", 2.0, 7, 10));
        intentar("Prioridad 0", () -> new Paquete("PQ997", "Armenia", 2.0, 0, 10));
        intentar("Destino vacío", () -> new Paquete("PQ996", "  ", 2.0, 3, 10));
        intentar("Tiempo estimado 0", () -> new Paquete("PQ995", "Armenia", 2.0, 3, 0));
        intentar("Código nulo", () -> new Paquete(null, "Armenia", 2.0, 3, 10));

        Titulos.sub("Orden natural (Comparable por código)");
        List<Paquete> lista = new ArrayList<>(List.of(p1, p2, p3, p4));
        System.out.println("Antes:   " + codigos(lista));
        Collections.sort(lista);
        System.out.println("Después: " + codigos(lista) + "  (esperado: PQ105 PQ318 PQ731 PQ942)");
        System.out.println("equals por código: " + p1.equals(new Paquete("PQ731", "Salento", 1.0, 1, 1)) + " (esperado: true)");

        Titulos.sub("Repartidores");
        Repartidor r1 = new Repartidor("R01", "Ana María Gómez", "Armenia", true);
        Repartidor r2 = new Repartidor("R02", "Carlos Ruiz", "Armenia", false);
        System.out.println("  " + r1);
        System.out.println("  " + r2);
        r2.setDisponible(true);
        System.out.println("  R02 después de setDisponible(true): " + r2);
        intentar("Repartidor sin zona", () -> new Repartidor("R03", "Lucía Mejía", "", true));

        Titulos.seccion("DEV 1 - LISTA SIMPLEMENTE ENLAZADA PROPIA: CASOS BORDE");
        demoListaPropia();
    }

    /** La lista es genérica: aquí se usa con String y con Integer, no con Paquete. */
    private static void demoListaPropia() {
        IColeccion<String> lista = new ListaSimplementeEnlazada<>();
        System.out.println("Lista vacía: " + lista + " tamaño=" + lista.tamanio() + " vacía=" + lista.estaVacia());
        System.out.println("Eliminar en lista vacía: " + lista.eliminar("X"));
        intentar("obtener(0) en lista vacía", () -> lista.obtener(0));

        lista.agregar("A");
        System.out.println("Un elemento: " + lista + " obtener(0)=" + lista.obtener(0));
        System.out.println("Eliminar el único: " + lista.eliminar("A") + " -> " + lista + " vacía=" + lista.estaVacia());

        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");
        lista.agregar("D");
        lista.agregar("E");
        System.out.println("Cinco elementos: " + lista + " tamaño=" + lista.tamanio());
        System.out.println("Eliminar el primero (A): " + lista.eliminar("A") + " -> " + lista);
        System.out.println("Eliminar intermedio (C): " + lista.eliminar("C") + " -> " + lista);
        System.out.println("Eliminar el último (E): " + lista.eliminar("E") + " -> " + lista);
        lista.agregar("F");
        System.out.println("Agregar F después de borrar el último (prueba que la cola se movió): " + lista);
        System.out.println("Eliminar inexistente (Z): " + lista.eliminar("Z") + " -> " + lista);
        System.out.println("obtener(1) = " + lista.obtener(1));
        intentar("obtener(10)", () -> lista.obtener(10));

        System.out.print("Recorrido con Iterator explícito: ");
        Iterator<String> it = lista.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println();
        intentar("next() cuando ya no hay más", it::next);

        IColeccion<Integer> numeros = new ListaSimplementeEnlazada<>();
        numeros.agregar(10);
        numeros.agregar(20);
        numeros.agregar(30);
        int suma = 0;
        for (int n : numeros) {           // for-each funciona porque la lista es Iterable
            suma += n;
        }
        System.out.println("Misma lista con Integer " + numeros + " suma=" + suma);
    }

    private static String codigos(List<Paquete> paquetes) {
        StringBuilder sb = new StringBuilder();
        for (Paquete p : paquetes) {
            sb.append(p.getCodigo()).append(" ");
        }
        return sb.toString().trim();
    }

    /** Ejecuta una acción que debería fallar y muestra el mensaje del error. */
    static void intentar(String descripcion, Runnable accion) {
        try {
            accion.run();
            System.out.println("  [!] " + descripcion + ": NO lanzó error");
        } catch (RuntimeException e) {
            System.out.println("  " + descripcion + " -> rechazado: " + e.getMessage());
        }
    }
}
