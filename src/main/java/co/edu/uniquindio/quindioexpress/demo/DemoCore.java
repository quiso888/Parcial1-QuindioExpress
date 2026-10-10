package co.edu.uniquindio.quindioexpress.demo;

import co.edu.uniquindio.quindioexpress.datos.DatosPrueba;
import co.edu.uniquindio.quindioexpress.estructuras.IColeccion;
import co.edu.uniquindio.quindioexpress.estructuras.ListaSimplementeEnlazada;
import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import co.edu.uniquindio.quindioexpress.modelo.Repartidor;
import co.edu.uniquindio.quindioexpress.servicio.CentroDistribucion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Demostración de modelo, lista propia y centro de distribución.
 *
 * <p>{@link #ejecutar()} muestra el modelo y la lista propia;
 * {@link #ejecutarCentro(CentroDistribucion)} muestra la jornada completa:
 * registro, validaciones, consulta por código y reglas de asignación.
 *
 * <p>Responsable: Dev 1.
 */
public final class DemoCore {

    private DemoCore() {
    }

    /** Permite probar este módulo solo, sin depender de Main. */
    public static void main(String[] args) {
        ejecutar();
        ejecutarCentro(DatosPrueba.crearCentro());
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

    /** Jornada completa sobre el centro de distribución (datos de prueba). */
    public static void ejecutarCentro(CentroDistribucion centro) {
        Titulos.seccion("DEV 1 - REGISTRO DE LA JORNADA");
        System.out.println("Paquetes registrados: " + centro.getPaquetes().size());
        System.out.println("Repartidores registrados: " + centro.getRepartidores().size());
        System.out.println("Orden de registro (se conserva tal cual llegaron):");
        for (Paquete p : centro.getPaquetes()) {
            System.out.println("  " + p);
        }
        System.out.println("Repartidores:");
        for (Repartidor r : centro.getRepartidores()) {
            System.out.println("  " + r);
        }

        Titulos.seccion("DEV 1 - VALIDACIONES DEL CENTRO");
        intentar("Código repetido PQ731", () -> centro.registrarPaquete(new Paquete("PQ731", "Salento", 1.0, 3, 10)));
        intentar("Repartidor con id repetido R01", () -> centro.registrarRepartidor(new Repartidor("R01", "Otro", "Armenia", true)));
        System.out.println("Paquetes después de los intentos: " + centro.getPaquetes().size() + " (no cambió)");

        Titulos.seccion("DEV 1 - CONSULTA POR CÓDIGO (HashMap, O(1) promedio)");
        consultar(centro, "PQ942");
        consultar(centro, "PQ105");
        consultar(centro, "PQ000");
        System.out.println("¿Existe repartidor R04? " + (centro.buscarRepartidor("R04") != null)
                + " -> " + centro.buscarRepartidor("R04"));

        Titulos.seccion("DEV 1 - REGLAS DE ASIGNACIÓN Y ENTREGA");
        intentar("Asignar PQ731 (Armenia) a R02 (no disponible)", () -> centro.asignar("PQ731", "R02"));
        intentar("Asignar PQ731 (Armenia) a R04 (zona Salento)", () -> centro.asignar("PQ731", "R04"));
        centro.asignar("PQ731", "R01");
        System.out.println("PQ731 asignado a " + centro.repartidorAsignado("PQ731"));
        Repartidor otro = centro.asignarAutomaticamente("PQ942");
        System.out.println("PQ942 (Armenia) con R01 ocupado y R02 no disponible -> "
                + (otro == null ? "no hay repartidor disponible, queda esperando" : otro));
        centro.registrarEntrega("PQ731");
        System.out.println("Se entregó PQ731; R01 vuelve a estar disponible: " + centro.buscarRepartidor("R01").isDisponible());
        System.out.println("Ahora PQ942 -> " + centro.asignarAutomaticamente("PQ942"));
        centro.registrarEntrega("PQ942");
        System.out.println("Historial (lista propia): " + codigos(centro.getHistorial())
                + "  pendientes: " + centro.getPendientes().size());
    }

    private static void consultar(CentroDistribucion centro, String codigo) {
        Paquete p = centro.buscarPaquete(codigo);
        if (p == null) {
            System.out.println(codigo + ": no existe");
        } else {
            System.out.printf("%s: destino=%s, peso=%.1f kg, prioridad=%d, tiempo=%d min%n",
                    codigo, p.getDestino(), p.getPeso(), p.getPrioridad(), p.getTiempoEstimado());
        }
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

    private static String codigos(Iterable<Paquete> paquetes) {
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

