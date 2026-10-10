package co.edu.uniquindio.quindioexpress.demo;

import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import co.edu.uniquindio.quindioexpress.modelo.Repartidor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Demostración de modelo, lista propia y centro de distribución.
 *
 * <p>Primer commit: solo el contrato (Paquete, Repartidor, IColeccion).
 * En los siguientes commits de Dev 1 se agregan la lista propia, el centro de
 * distribución y los datos de prueba.
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
