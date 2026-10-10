package co.edu.uniquindio.quindioexpress.estructuras;

import java.util.Iterator;

/**
 * Lista simplemente enlazada genérica propia.
 *
 * <p>No usa ninguna colección de Java por dentro: solo nodos enlazados.
 * Guarda referencia a la cabeza (primer nodo) y a la cola (último nodo):
 * con la cola, agregar al final cuesta O(1) en vez de O(n).
 *
 * <p>No hay getters ni setters de nodos: el código externo recorre la lista
 * con for-each (Iterable) o con obtener(i), nunca con las referencias internas.
 *
 * <pre>
 * Complejidades:
 *   agregar     O(1)   (se enlaza después de la cola)
 *   eliminar    O(n)   (hay que buscar el elemento)
 *   obtener(i)  O(i)   (hay que caminar i nodos)
 *   tamanio     O(1)   (se lleva un contador)
 *   estaVacia   O(1)
 * </pre>
 *
 * @param <T> tipo de los elementos
 *
 * <p>Responsable: Dev 1.
 */
public class ListaSimplementeEnlazada<T> implements IColeccion<T> {

    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int tamanio;

    public ListaSimplementeEnlazada() {
        cabeza = null;
        cola = null;
        tamanio = 0;
    }

    /** Agrega al final. Así el historial conserva el orden real de entrega. */
    @Override
    public void agregar(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        if (estaVacia()) {
            cabeza = nuevo;              // caso lista vacía: el nuevo es cabeza y cola
        } else {
            cola.setSiguiente(nuevo);    // se enlaza después del último
        }
        cola = nuevo;
        tamanio++;
    }

    /**
     * Elimina la primera aparición del elemento.
     *
     * <p>Casos: lista vacía, eliminar el primero, uno intermedio, el último
     * (hay que mover la cola) y elemento inexistente (retorna false).
     */
    @Override
    public boolean eliminar(T elemento) {
        if (estaVacia()) {
            return false;
        }

        // Caso 1: el elemento está en la cabeza
        if (sonIguales(cabeza.getDato(), elemento)) {
            cabeza = cabeza.getSiguiente();
            if (cabeza == null) {        // tenía un solo elemento: queda vacía
                cola = null;
            }
            tamanio--;
            return true;
        }

        // Caso 2: buscar el nodo ANTERIOR al que se quiere borrar
        Nodo<T> anterior = cabeza;
        while (anterior.getSiguiente() != null && !sonIguales(anterior.getSiguiente().getDato(), elemento)) {
            anterior = anterior.getSiguiente();
        }

        if (anterior.getSiguiente() == null) {
            return false;                // no existe
        }

        Nodo<T> aBorrar = anterior.getSiguiente();
        anterior.setSiguiente(aBorrar.getSiguiente());   // se "salta" el nodo
        if (aBorrar == cola) {           // era el último: la cola retrocede
            cola = anterior;
        }
        tamanio--;
        return true;
    }

    @Override
    public T obtener(int posicion) {
        if (posicion < 0 || posicion >= tamanio) {
            throw new IndexOutOfBoundsException("Posición " + posicion + " fuera de rango (tamaño " + tamanio + ")");
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < posicion; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }

    @Override
    public int tamanio() {
        return tamanio;
    }

    @Override
    public boolean estaVacia() {
        return tamanio == 0;
    }

    /** Entrega un iterador nuevo que empieza en la cabeza. */
    @Override
    public Iterator<T> iterator() {
        return new ListaIterator<>(cabeza);
    }

    /** equals que tolera null, sin usar java.util.Objects. */
    private boolean sonIguales(T a, T b) {
        return a == null ? b == null : a.equals(b);
    }

    /** Formato: [A -> B -> C]. Usa el propio iterador. */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(" -> ");
            }
        }
        return sb.append("]").toString();
    }
}
