package co.edu.uniquindio.quindioexpress.modelo;

/**
 * Repartidor: identificación, nombre, zona y disponibilidad.
 *
 * <p>La zona corresponde directamente a un municipio destino de los paquetes.
 *
 * <p>Responsable: Dev 1.
 */
public class Repartidor {

    private final String identificacion;
    private final String nombre;
    private final String zona;
    private boolean disponible;

    public Repartidor(String identificacion, String nombre, String zona, boolean disponible) {
        if (identificacion == null || identificacion.isBlank()) {
            throw new IllegalArgumentException("La identificación no puede ser nula ni vacía");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío (" + identificacion + ")");
        }
        if (zona == null || zona.isBlank()) {
            throw new IllegalArgumentException("La zona no puede ser nula ni vacía (" + identificacion + ")");
        }
        this.identificacion = identificacion.trim();
        this.nombre = nombre.trim();
        this.zona = zona.trim();
        this.disponible = disponible;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getZona() {
        return zona;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /** Dos repartidores son iguales si tienen la misma identificación. */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Repartidor)) {
            return false;
        }
        return identificacion.equals(((Repartidor) o).identificacion);
    }

    @Override
    public int hashCode() {
        return identificacion.hashCode();
    }

    @Override
    public String toString() {
        return identificacion + " " + nombre + " (" + zona + ", " + (disponible ? "disponible" : "no disponible") + ")";
    }
}
