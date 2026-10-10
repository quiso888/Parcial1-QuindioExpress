package co.edu.uniquindio.quindioexpress.estructuras;

/**
 * Contrato de las colecciones propias de QuindíoExpress.
 *
 * <p>La interfaz dice QUÉ operaciones existen; la clase que la implementa
 * (por ejemplo {@link ListaSimplementeEnlazada}) decide CÓMO se hacen.
 * Extiende {@link Iterable} para que cualquier colección propia se pueda
 * recorrer con for-each sin conocer sus nodos.
 *
 * @param <T> tipo de los elementos (la colección no es solo para Paquete)
 *
 * <p>Responsable: Dev 1.
 */
public interface IColeccion<T> extends Iterable<T> {

    /** Agrega un elemento al final de la colección. */
    void agregar(T elemento);

    /**
     * Elimina la primera aparición del elemento (comparando con equals).
     *
     * @return true si lo encontró y lo eliminó; false si no existía
     */
    boolean eliminar(T elemento);

    /**
     * Retorna el elemento de la posición indicada (la primera es 0).
     *
     * @throws IndexOutOfBoundsException si la posición no existe
     */
    T obtener(int posicion);

    /** Cantidad de elementos almacenados. */
    int tamanio();

    /** true si la colección no tiene elementos. */
    boolean estaVacia();
}
