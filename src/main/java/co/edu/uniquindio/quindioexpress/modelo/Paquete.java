package co.edu.uniquindio.quindioexpress.modelo;

/**
 * Paquete a entregar: código, destino, peso, prioridad y tiempo estimado.
 *
 * <p>Orden natural (Comparable): por código ascendente. Los demás criterios
 * (prioridad, peso, tiempo) viven en el paquete {@code comparadores} y no
 * cambian este orden natural.
 *
 * <p>Responsable: Dev 1.
 */
public class Paquete implements Comparable<Paquete> {

    /*
     * El enunciado se contradice: la descripción del atributo dice 0 a 10 y la
     * lista de reglas dice "entre 1 y 5". Se valida 1 a 5 (las reglas y todos
     * los ejemplos caen ahí). Si el profesor dice otra cosa, se cambia aquí.
     */
    public static final int PRIORIDAD_MIN = 1;
    public static final int PRIORIDAD_MAX = 5;
    public static final int PRIORIDAD_POR_DEFECTO = 5;

    private final String codigo;
    private final String destino;
    private final double peso;
    private final int prioridad;
    private final int tiempoEstimado;

    /**
     * Crea un paquete validando todas las reglas del enunciado.
     *
     * @throws IllegalArgumentException si algún dato no cumple las reglas
     */
    public Paquete(String codigo, String destino, double peso, int prioridad, int tiempoEstimado) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código no puede ser nulo ni vacío");
        }
        if (destino == null || destino.isBlank()) {
            throw new IllegalArgumentException("El destino no puede ser nulo ni vacío (" + codigo + ")");
        }
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que cero (" + codigo + ")");
        }
        if (prioridad < PRIORIDAD_MIN || prioridad > PRIORIDAD_MAX) {
            throw new IllegalArgumentException("La prioridad debe estar entre " + PRIORIDAD_MIN
                    + " y " + PRIORIDAD_MAX + " (" + codigo + ")");
        }
        if (tiempoEstimado <= 0) {
            throw new IllegalArgumentException("El tiempo estimado debe ser mayor que cero (" + codigo + ")");
        }
        this.codigo = codigo.trim();
        this.destino = destino.trim();
        this.peso = peso;
        this.prioridad = prioridad;
        this.tiempoEstimado = tiempoEstimado;
    }

    /** Crea un paquete con la prioridad por defecto (5). */
    public Paquete(String codigo, String destino, double peso, int tiempoEstimado) {
        this(codigo, destino, peso, PRIORIDAD_POR_DEFECTO, tiempoEstimado);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    /**
     * Orden natural: código ascendente (PQ105 &lt; PQ318 &lt; PQ731).
     * Es comparación de String: funciona porque todos los códigos tienen el
     * mismo formato (PQ + 3 dígitos). "PQ99" quedaría después de "PQ105".
     */
    @Override
    public int compareTo(Paquete otro) {
        return this.codigo.compareTo(otro.codigo);
    }

    /** Dos paquetes son iguales si tienen el mismo código (coherente con compareTo). */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paquete)) {
            return false;
        }
        return codigo.equals(((Paquete) o).codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s[%s, %.1f kg, p%d, %d min]", codigo, destino, peso, prioridad, tiempoEstimado);
    }
}
