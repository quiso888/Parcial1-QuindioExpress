package co.edu.uniquindio.quindioexpress;

import co.edu.uniquindio.quindioexpress.datos.DatosPrueba;
import co.edu.uniquindio.quindioexpress.demo.DemoAlgoritmos;
import co.edu.uniquindio.quindioexpress.demo.DemoCore;
import co.edu.uniquindio.quindioexpress.demo.DemoJornada;
import co.edu.uniquindio.quindioexpress.servicio.CentroDistribucion;

/**
 * Punto de entrada: crea la jornada con los datos de prueba y ejecuta las demos
 * de cada módulo. Todas las demos de la jornada usan el MISMO centro, así el
 * historial acumula las entregas en el orden real.
 *
 * <p>Responsable: Dev 3.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("QuindíoExpress - Parcial 1 Estructuras de Datos");
        CentroDistribucion centro = DatosPrueba.crearCentro();

        DemoCore.ejecutar();               // Dev 1: modelo y lista propia
        DemoCore.ejecutarCentro(centro);   // Dev 1: registro, consulta, asignación
        DemoAlgoritmos.ejecutar();         // Dev 2: algoritmos con los ejemplos del enunciado
        DemoJornada.ejecutar(centro);      // Dev 3: todo junto sobre la jornada

        System.out.println();
        System.out.println("Fin de la demostración.");
    }
}
