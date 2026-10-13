package co.edu.uniquindio.quindioexpress.consola;

import co.edu.uniquindio.quindioexpress.algoritmos.BusquedaBinaria;
import co.edu.uniquindio.quindioexpress.algoritmos.DivideYVenceras;
import co.edu.uniquindio.quindioexpress.algoritmos.Recursivos;
import co.edu.uniquindio.quindioexpress.comparadores.ComparadorDespacho;
import co.edu.uniquindio.quindioexpress.comparadores.ComparadorPeso;
import co.edu.uniquindio.quindioexpress.comparadores.ComparadorPrioridad;
import co.edu.uniquindio.quindioexpress.comparadores.ComparadorTiempo;
import co.edu.uniquindio.quindioexpress.datos.DatosPrueba;
import co.edu.uniquindio.quindioexpress.demo.DemoAlgoritmos;
import co.edu.uniquindio.quindioexpress.demo.DemoCore;
import co.edu.uniquindio.quindioexpress.demo.DemoJornada;
import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import co.edu.uniquindio.quindioexpress.modelo.Repartidor;
import co.edu.uniquindio.quindioexpress.operaciones.Despacho;
import co.edu.uniquindio.quindioexpress.operaciones.ReporteMunicipios;
import co.edu.uniquindio.quindioexpress.servicio.CentroDistribucion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Menú de consola para usar QuindíoExpress en vivo: registrar, consultar,
 * asignar, entregar y ejecutar los reportes y algoritmos sobre los datos
 * que el usuario va ingresando.
 *
 * <p>No tiene lógica de negocio propia: cada opción llama a las clases de
 * servicio, operaciones y algoritmos, y muestra el mensaje de la excepción
 * cuando una regla no se cumple.
 */
public class MenuConsola {

    private final Scanner entrada;
    private CentroDistribucion centro;

    public MenuConsola(Scanner entrada) {
        this.entrada = entrada;
    }

    /** Ciclo principal: muestra el menú hasta que el usuario elige 0. */
    public void iniciar() {
        System.out.println("QuindíoExpress - Parcial 1 Estructuras de Datos");
        cargarDatosIniciales();

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Opción: ");
            System.out.println();
            try {
                switch (opcion) {
                    case 1: registrarPaquete(); break;
                    case 2: consultarPaquete(); break;
                    case 3: listarPaquetes(); break;
                    case 4: registrarRepartidor(); break;
                    case 5: listarRepartidores(); break;
                    case 6: asignarManual(); break;
                    case 7: asignarAutomatico(); break;
                    case 8: registrarEntrega(); break;
                    case 9: verHistorial(); break;
                    case 10: verDespacho(); break;
                    case 11: ordenarPendientes(); break;
                    case 12: reporteMunicipios(); break;
                    case 13: pesoTotalPorMunicipio(); break;
                    case 14: contarPrioridadMinima(); break;
                    case 15: busquedaBinaria(); break;
                    case 16: mayorPeso(); break;
                    case 17: ejecutarDemos(); break;
                    case 18: cargarDatosIniciales(); break;
                    case 0: salir = true; break;
                    default: System.out.println("Opción no válida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                // Las reglas del sistema se validan en el modelo y el servicio
                System.out.println("No se pudo: " + e.getMessage());
            }
        }
        System.out.println("Hasta luego.");
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.printf("MENÚ  (%d paquetes, %d pendientes, %d entregados, %d repartidores)%n",
                centro.getPaquetes().size(), centro.getPendientes().size(),
                centro.cantidadEntregados(), centro.getRepartidores().size());
        System.out.println("=".repeat(60));
        System.out.println(" Registro y consulta");
        System.out.println("   1. Registrar paquete");
        System.out.println("   2. Consultar paquete por código");
        System.out.println("   3. Listar paquetes (todos / pendientes)");
        System.out.println("   4. Registrar repartidor");
        System.out.println("   5. Listar repartidores");
        System.out.println(" Asignación y entrega");
        System.out.println("   6. Asignar paquete a un repartidor");
        System.out.println("   7. Asignar paquete automáticamente");
        System.out.println("   8. Registrar entrega");
        System.out.println("   9. Ver historial de entregas (lista propia)");
        System.out.println(" Despacho y reportes");
        System.out.println("  10. Orden de despacho (por llegada y prioritario)");
        System.out.println("  11. Ordenar pendientes por un criterio");
        System.out.println("  12. Reporte de municipios");
        System.out.println(" Algoritmos");
        System.out.println("  13. Peso total por municipio (recursivo)");
        System.out.println("  14. Contar por prioridad mínima (recursivo)");
        System.out.println("  15. Buscar por código con búsqueda binaria");
        System.out.println("  16. Paquete de mayor peso (divide y vencerás)");
        System.out.println(" Otros");
        System.out.println("  17. Ejecutar las demos completas");
        System.out.println("  18. Reiniciar el centro");
        System.out.println("   0. Salir");
    }

    // ------------------------------------------------------------ datos iniciales

    private void cargarDatosIniciales() {
        if (leerSiNo("¿Cargar los datos de prueba (32 paquetes y 7 repartidores)? (s/n): ")) {
            centro = DatosPrueba.crearCentro();
            System.out.println("Centro cargado con los datos de prueba.");
        } else {
            centro = new CentroDistribucion();
            System.out.println("Centro vacío.");
        }
    }

    // --------------------------------------------------------- registro y consulta

    private void registrarPaquete() {
        String codigo = leerCodigo("Código: ");
        String destino = leerTexto("Municipio destino: ");
        double peso = leerDouble("Peso (kg): ");
        String textoPrioridad = leerTexto("Prioridad " + Paquete.PRIORIDAD_MIN + "-" + Paquete.PRIORIDAD_MAX
                + " (Enter = " + Paquete.PRIORIDAD_POR_DEFECTO + "): ");
        int tiempo = leerEntero("Tiempo estimado (min): ");

        Paquete paquete;
        if (textoPrioridad.isEmpty()) {
            paquete = new Paquete(codigo, destino, peso, tiempo);
        } else {
            paquete = new Paquete(codigo, destino, peso, convertirEntero(textoPrioridad), tiempo);
        }
        centro.registrarPaquete(paquete);
        System.out.println("Registrado: " + paquete);
    }

    private void consultarPaquete() {
        String codigo = leerCodigo("Código: ");
        Paquete paquete = centro.buscarPaquete(codigo);
        if (paquete == null) {
            System.out.println("No existe un paquete con el código " + codigo);
            return;
        }
        System.out.println(paquete);
        Repartidor repartidor = centro.repartidorAsignado(codigo);
        if (repartidor != null) {
            System.out.println("Asignado a: " + repartidor.getIdentificacion() + " " + repartidor.getNombre());
        } else if (centro.getPendientes().contains(paquete)) {
            System.out.println("Pendiente, sin repartidor asignado.");
        } else {
            System.out.println("Entregado.");
        }
    }

    private void listarPaquetes() {
        boolean soloPendientes = leerSiNo("¿Solo los pendientes? (s/n): ");
        List<Paquete> lista = soloPendientes ? centro.getPendientes() : centro.getPaquetes();
        imprimirLista(lista);
    }

    private void registrarRepartidor() {
        String id = leerCodigo("Identificación: ");
        String nombre = leerTexto("Nombre: ");
        String zona = leerTexto("Zona (municipio): ");
        Repartidor repartidor = new Repartidor(id, nombre, zona, true);
        centro.registrarRepartidor(repartidor);
        System.out.println("Registrado: " + repartidor);
    }

    private void listarRepartidores() {
        List<Repartidor> repartidores = centro.getRepartidores();
        if (repartidores.isEmpty()) {
            System.out.println("No hay repartidores registrados.");
        }
        for (Repartidor r : repartidores) {
            System.out.println("  " + r);
        }
    }

    // --------------------------------------------------------- asignación y entrega

    private void asignarManual() {
        String codigo = leerCodigo("Código del paquete: ");
        String id = leerCodigo("Identificación del repartidor: ");
        centro.asignar(codigo, id);
        System.out.println("Paquete " + codigo + " asignado a " + centro.repartidorAsignado(codigo));
    }

    private void asignarAutomatico() {
        String codigo = leerCodigo("Código del paquete: ");
        Repartidor repartidor = centro.asignarAutomaticamente(codigo);
        if (repartidor == null) {
            System.out.println("No hay un repartidor disponible para el destino de " + codigo);
        } else {
            System.out.println("Paquete " + codigo + " asignado a " + repartidor.getIdentificacion()
                    + " " + repartidor.getNombre());
        }
    }

    private void registrarEntrega() {
        List<Paquete> enRuta = paquetesEnRuta();
        if (enRuta.isEmpty()) {
            System.out.println("Ningún paquete tiene repartidor asignado todavía (opciones 6 y 7).");
        } else {
            System.out.println("Paquetes asignados (en ruta):");
            for (Paquete p : enRuta) {
                Repartidor r = centro.repartidorAsignado(p.getCodigo());
                System.out.printf("  %s -> %s %s%n", p, r.getIdentificacion(), r.getNombre());
            }
        }

        String codigo = leerCodigo("Código del paquete entregado: ");
        Paquete paquete = centro.buscarPaquete(codigo);
        if (paquete == null) {
            System.out.println("No existe un paquete con el código " + codigo);
            return;
        }
        if (!centro.getPendientes().contains(paquete)) {
            System.out.println("El paquete " + codigo + " ya fue entregado.");
            return;
        }
        if (centro.repartidorAsignado(codigo) == null) {
            System.out.println("El paquete " + codigo + " no tiene repartidor asignado.");
            if (!leerSiNo("¿Asignarlo automáticamente y registrar la entrega? (s/n): ")) {
                return;
            }
            Repartidor repartidor = centro.asignarAutomaticamente(codigo);
            if (repartidor == null) {
                System.out.println("No hay un repartidor disponible para " + paquete.getDestino());
                return;
            }
            System.out.println("Asignado a " + repartidor.getIdentificacion() + " " + repartidor.getNombre());
        }
        Repartidor repartidor = centro.repartidorAsignado(codigo);
        centro.registrarEntrega(codigo);
        System.out.println("Entrega registrada: " + paquete + " por " + repartidor.getNombre()
                + ". Total entregados: " + centro.cantidadEntregados());
    }

    private void verHistorial() {
        if (centro.cantidadEntregados() == 0) {
            System.out.println("Todavía no hay entregas. Asigna un paquete (opción 6 o 7) y entrégalo (opción 8).");
            return;
        }
        System.out.println("Historial de entregas en orden real (" + centro.cantidadEntregados() + " paquetes):");
        int i = 1;
        for (Paquete p : centro.getHistorial()) {   // recorrido con el iterador de la lista propia
            System.out.printf("  %2d. %s%n", i++, p);
        }
    }

    /** Pendientes que ya tienen repartidor asignado. */
    private List<Paquete> paquetesEnRuta() {
        List<Paquete> enRuta = new ArrayList<>();
        for (Paquete p : centro.getPendientes()) {
            if (centro.repartidorAsignado(p.getCodigo()) != null) {
                enRuta.add(p);
            }
        }
        return enRuta;
    }

    // ------------------------------------------------------------ despacho y reportes

    private void verDespacho() {
        // Se crea un Despacho nuevo con los pendientes: consultar no altera el centro
        Despacho despacho = new Despacho(centro.getPendientes());
        int cantidad = leerEntero("¿Cuántos paquetes mostrar? ");

        System.out.println("Por llegada (ArrayDeque, FIFO):");
        for (int i = 1; i <= cantidad && despacho.hayPorLlegada(); i++) {
            System.out.printf("  %2d. %s%n", i, despacho.siguientePorLlegada());
        }
        System.out.println("Prioritario (PriorityQueue: prioridad, tiempo, código):");
        for (int i = 1; i <= cantidad && despacho.hayPrioritarios(); i++) {
            System.out.printf("  %2d. %s%n", i, despacho.siguientePrioritario());
        }
    }

    private void ordenarPendientes() {
        System.out.println("  1. Prioridad (mayor a menor)");
        System.out.println("  2. Peso (mayor a menor)");
        System.out.println("  3. Tiempo estimado (menor a mayor)");
        System.out.println("  4. Despacho (prioridad, tiempo y código)");
        System.out.println("  5. Código (orden natural)");
        int criterio = leerEntero("Criterio: ");

        Comparator<Paquete> comparador;
        switch (criterio) {
            case 1: comparador = new ComparadorPrioridad(); break;
            case 2: comparador = new ComparadorPeso(); break;
            case 3: comparador = new ComparadorTiempo(); break;
            case 4: comparador = new ComparadorDespacho(); break;
            case 5: comparador = Comparator.naturalOrder(); break;
            default:
                System.out.println("Criterio no válido.");
                return;
        }
        List<Paquete> copia = new ArrayList<>(centro.getPendientes());
        copia.sort(comparador);
        imprimirLista(copia);
    }

    private void reporteMunicipios() {
        List<Paquete> paquetes = centro.getPaquetes();
        System.out.println("Sin repetir, en orden de aparición (LinkedHashSet):");
        System.out.println("  " + ReporteMunicipios.municipios(paquetes));
        System.out.println("Ordenados alfabéticamente (TreeSet):");
        System.out.println("  " + ReporteMunicipios.municipiosOrdenados(paquetes));
        System.out.println("Paquetes por municipio (Map):");
        for (Map.Entry<String, List<Paquete>> e : ReporteMunicipios.paquetesPorMunicipio(paquetes).entrySet()) {
            List<String> codigos = new ArrayList<>();
            for (Paquete p : e.getValue()) {
                codigos.add(p.getCodigo());
            }
            System.out.printf("  %-12s %d -> %s%n", e.getKey(), codigos.size(), codigos);
        }
    }

    // ---------------------------------------------------------------- algoritmos

    private void pesoTotalPorMunicipio() {
        String municipio = leerTexto("Municipio: ");
        List<Paquete> pendientes = centro.getPendientes();
        double total;
        if (leerSiNo("¿Mostrar la pila de llamadas? (s/n): ")) {
            total = Recursivos.pesoTotalPorMunicipioConTraza(pendientes, municipio);
        } else {
            total = Recursivos.pesoTotalPorMunicipio(pendientes, municipio);
        }
        System.out.printf("Peso total pendiente hacia %s: %.1f kg%n", municipio, total);
    }

    private void contarPrioridadMinima() {
        int minima = leerEntero("Prioridad mínima: ");
        List<Paquete> pendientes = centro.getPendientes();
        int cantidad;
        if (leerSiNo("¿Mostrar la pila de llamadas? (s/n): ")) {
            cantidad = Recursivos.contarPrioridadMinimaConTraza(pendientes, minima);
        } else {
            cantidad = Recursivos.contarPrioridadMinima(pendientes, minima);
        }
        System.out.println("Pendientes con prioridad >= " + minima + ": " + cantidad);
    }

    private void busquedaBinaria() {
        String codigo = leerCodigo("Código a buscar: ");
        // Precondición: la lista debe estar ordenada por código. Se ordena una copia.
        List<Paquete> ordenados = new ArrayList<>(centro.getPaquetes());
        Collections.sort(ordenados);
        System.out.println("¿Ordenada por código? " + BusquedaBinaria.estaOrdenadaPorCodigo(ordenados));
        int posicion = BusquedaBinaria.buscarConTraza(ordenados, codigo);
        if (posicion >= 0) {
            System.out.println("Encontrado en la posición " + posicion + ": " + ordenados.get(posicion));
        } else {
            System.out.println("No existe un paquete con el código " + codigo);
        }
    }

    private void mayorPeso() {
        List<Paquete> pendientes = centro.getPendientes();
        if (pendientes.isEmpty()) {
            System.out.println("No hay paquetes pendientes.");
            return;
        }
        Paquete mayor;
        if (leerSiNo("¿Mostrar el árbol de llamadas? (s/n): ")) {
            mayor = DivideYVenceras.mayorPesoConTraza(pendientes);
        } else {
            mayor = DivideYVenceras.mayorPeso(pendientes);
        }
        System.out.println("Paquete pendiente de mayor peso: " + mayor);
    }

    // --------------------------------------------------------------------- otros

    private void ejecutarDemos() {
        // Las demos usan su propio centro para no alterar el que se está usando
        CentroDistribucion centroDemo = DatosPrueba.crearCentro();
        DemoCore.ejecutar();
        DemoCore.ejecutarCentro(centroDemo);
        DemoAlgoritmos.ejecutar();
        DemoJornada.ejecutar(centroDemo);
    }

    // ------------------------------------------------------------ lectura segura

    private void imprimirLista(List<Paquete> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay paquetes.");
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.printf("  %2d. %s%n", i + 1, lista.get(i));
        }
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        if (!entrada.hasNextLine()) {
            return "";
        }
        return entrada.nextLine().trim();
    }

    /** Lee un código o identificación en mayúsculas: pq731 y PQ731 son el mismo paquete. */
    private String leerCodigo(String mensaje) {
        return leerTexto(mensaje).toUpperCase();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                return convertirEntero(texto);
            } catch (IllegalArgumentException e) {
                if (!entrada.hasNextLine()) {
                    return 0;   // fin de la entrada: se sale del menú
                }
                System.out.println("Escribe un número entero.");
            }
        }
    }

    private double leerDouble(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje).replace(',', '.');   // acepta 4,5 y 4.5
            try {
                return Double.parseDouble(texto);
            } catch (NumberFormatException e) {
                if (!entrada.hasNextLine()) {
                    return 0;
                }
                System.out.println("Escribe un número (por ejemplo 4.5).");
            }
        }
    }

    private boolean leerSiNo(String mensaje) {
        String texto = leerTexto(mensaje).toLowerCase();
        return texto.startsWith("s");
    }

    private int convertirEntero(String texto) {
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Se esperaba un número entero y llegó \"" + texto + "\"");
        }
    }
}
