package co.edu.uniquindio.quindioexpress;

import co.edu.uniquindio.quindioexpress.demo.DemoAlgoritmos;
import co.edu.uniquindio.quindioexpress.demo.DemoCore;

/**
 * Punto de entrada: solo invoca las demos de cada módulo.
 *
 * <p>Cuando Dev 1 agregue el CentroDistribucion y los DatosPrueba, Main creará
 * la jornada y se la pasará a las dos demos.
 *
 * <p>Responsable: Dev 3.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("QuindíoExpress - Parcial 1 Estructuras de Datos");
        DemoCore.ejecutar();
        DemoAlgoritmos.ejecutar();
        System.out.println();
        System.out.println("Fin de la demostración.");
    }
}
