package co.edu.uniquindio.quindioexpress.datos;

import co.edu.uniquindio.quindioexpress.modelo.Paquete;
import co.edu.uniquindio.quindioexpress.modelo.Repartidor;
import co.edu.uniquindio.quindioexpress.servicio.CentroDistribucion;

import java.util.List;

/**
 * Datos de prueba definidos directamente en Java.
 *
 * <p>Cumple los mínimos del enunciado: 32 paquetes (mínimo 30), 6 municipios
 * (mínimo 5), 7 repartidores (mínimo 6), prioridades y pesos variados,
 * códigos NO consecutivos y sin relación con el orden de llegada, y varios
 * paquetes por municipio. Uno de los repartidores está no disponible para
 * poder demostrar esa regla.
 *
 * <p>Además trae los conjuntos pequeños de los ejemplos del enunciado, para
 * las pruebas de escritorio de la Parte C.
 *
 * <p>Responsable: Dev 1.
 */
public final class DatosPrueba {

    private DatosPrueba() {
    }

    /** Paquetes de la jornada en el orden en que llegan al centro. */
    public static List<Paquete> paquetesJornada() {
        return List.of(
                // Los primeros seis son los del ejemplo de municipios del enunciado
                new Paquete("PQ731", "Armenia", 12.0, 5, 45),
                new Paquete("PQ105", "Salento", 4.5, 5, 25),
                new Paquete("PQ942", "Armenia", 3.2, 4, 20),
                new Paquete("PQ318", "Calarcá", 18.7, 2, 35),
                new Paquete("PQ567", "Salento", 7.4, 3, 50),
                new Paquete("PQ829", "Armenia", 5.1, 1, 15),
                new Paquete("PQ420", "Circasia", 6.0, 3, 30),
                new Paquete("PQ214", "Montenegro", 2.8, 4, 40),
                new Paquete("PQ421", "Quimbaya", 9.9, 2, 55),
                new Paquete("PQ842", "Calarcá", 1.5, 5, 18),
                new Paquete("PQ101", "Armenia", 4.5, 3, 22),
                new Paquete("PQ203", "Salento", 3.0, 2, 60),
                new Paquete("PQ415", "Armenia", 7.2, 4, 28),
                new Paquete("PQ730", "Armenia", 2.3, 1, 12),
                new Paquete("PQ688", "Circasia", 11.4, 5, 33),
                new Paquete("PQ059", "Montenegro", 0.8, 3, 47),
                new Paquete("PQ976", "Quimbaya", 15.2, 4, 38),
                new Paquete("PQ333", "Calarcá", 6.6, 3, 26),
                new Paquete("PQ512", "Salento", 2.1, 5, 58),
                new Paquete("PQ147", "Circasia", 8.3, 2, 41),
                new Paquete("PQ864", "Montenegro", 13.0, 5, 52),
                new Paquete("PQ290", "Quimbaya", 4.0, 1, 36),
                new Paquete("PQ605", "Armenia", 9.1, 3, 19),
                new Paquete("PQ781", "Calarcá", 3.7, 4, 24),
                new Paquete("PQ456", "Salento", 10.5, 4, 44),
                new Paquete("PQ038", "Circasia", 1.9, 1, 29),
                new Paquete("PQ917", "Montenegro", 5.5, 2, 31),
                new Paquete("PQ372", "Quimbaya", 7.8, 5, 27),
                new Paquete("PQ649", "Armenia", 2.6, 2, 16),
                new Paquete("PQ183", "Calarcá", 14.3, 3, 39),
                new Paquete("PQ525", "Montenegro", 6.9, 4, 21),
                new Paquete("PQ808", "Quimbaya", 3.3, 34)      // sin prioridad: queda en 5 por defecto
        );
    }

    /** Repartidores: al menos uno por municipio; R02 empieza no disponible. */
    public static List<Repartidor> repartidores() {
        return List.of(
                new Repartidor("R01", "Ana María Gómez", "Armenia", true),
                new Repartidor("R02", "Carlos Ruiz", "Armenia", false),
                new Repartidor("R03", "Lucía Mejía", "Calarcá", true),
                new Repartidor("R04", "Jorge Pérez", "Salento", true),
                new Repartidor("R05", "Marta Ríos", "Circasia", true),
                new Repartidor("R06", "Andrés López", "Montenegro", true),
                new Repartidor("R07", "Paula Castro", "Quimbaya", true)
        );
    }

    /** Crea un centro de distribución con toda la jornada ya registrada. */
    public static CentroDistribucion crearCentro() {
        CentroDistribucion centro = new CentroDistribucion();
        for (Paquete p : paquetesJornada()) {
            centro.registrarPaquete(p);
        }
        for (Repartidor r : repartidores()) {
            centro.registrarRepartidor(r);
        }
        return centro;
    }

    // ---------------------------------------- conjuntos pequeños del enunciado (Parte C)

    /** Ejemplo de despacho prioritario. Orden esperado: PQ105, PQ731, PQ942, PQ420. */
    public static List<Paquete> ejemploDespacho() {
        return List.of(
                new Paquete("PQ420", "Circasia", 6.0, 3, 30),
                new Paquete("PQ731", "Armenia", 12.0, 5, 45),
                new Paquete("PQ105", "Salento", 4.5, 5, 25),
                new Paquete("PQ942", "Armenia", 3.2, 4, 20)
        );
    }

    /** Ejemplo de peso total por municipio. Armenia debe dar 14.0 kg. */
    public static List<Paquete> ejemploPesoMunicipio() {
        return List.of(
                new Paquete("PQ101", "Armenia", 4.5, 3, 22),
                new Paquete("PQ203", "Salento", 3.0, 2, 60),
                new Paquete("PQ415", "Armenia", 7.2, 4, 28),
                new Paquete("PQ730", "Armenia", 2.3, 1, 12)
        );
    }

    /** Ejemplo de prioridades 5, 2, 4, 1, 5, 3, 4. Con mínimo 4 debe dar 4. */
    public static List<Paquete> ejemploPrioridades() {
        return List.of(
                new Paquete("PQ501", "Armenia", 1.0, 5, 10),
                new Paquete("PQ502", "Salento", 1.0, 2, 10),
                new Paquete("PQ503", "Calarcá", 1.0, 4, 10),
                new Paquete("PQ504", "Armenia", 1.0, 1, 10),
                new Paquete("PQ505", "Circasia", 1.0, 5, 10),
                new Paquete("PQ506", "Quimbaya", 1.0, 3, 10),
                new Paquete("PQ507", "Montenegro", 1.0, 4, 10)
        );
    }

    /** Ejemplo de pesos 4.5, 12.0, 3.2, 18.7, 7.4, 5.1. El mayor es 18.7 kg. */
    public static List<Paquete> ejemploPesos() {
        return List.of(
                new Paquete("PQ105", "Salento", 4.5, 5, 25),
                new Paquete("PQ731", "Armenia", 12.0, 5, 45),
                new Paquete("PQ942", "Armenia", 3.2, 4, 20),
                new Paquete("PQ318", "Calarcá", 18.7, 2, 35),
                new Paquete("PQ567", "Salento", 7.4, 3, 50),
                new Paquete("PQ829", "Armenia", 5.1, 1, 15)
        );
    }

    /** Ejemplo de búsqueda binaria: ya ordenados por código. */
    public static List<Paquete> ejemploOrdenadosPorCodigo() {
        return List.of(
                new Paquete("PQ105", "Salento", 4.5, 5, 25),
                new Paquete("PQ214", "Montenegro", 2.8, 4, 40),
                new Paquete("PQ318", "Calarcá", 18.7, 2, 35),
                new Paquete("PQ421", "Quimbaya", 9.9, 2, 55),
                new Paquete("PQ567", "Salento", 7.4, 3, 50),
                new Paquete("PQ731", "Armenia", 12.0, 5, 45),
                new Paquete("PQ842", "Calarcá", 1.5, 5, 18),
                new Paquete("PQ942", "Armenia", 3.2, 4, 20)
        );
    }
}

