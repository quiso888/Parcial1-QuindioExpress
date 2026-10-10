package co.edu.uniquindio.quindioexpress.estructuras;

/**
 * Nodo de la lista simplemente enlazada: guarda el valor y la referencia al
 * siguiente nodo.
 *
 * <p>La clase y sus miembros tienen visibilidad de paquete (sin public): solo
 * la lista y su iterador, que están en este mismo paquete, pueden usarla. El
 * código externo nunca ve un nodo.
 *
 * <p>Responsable: Dev 1.
 */
class Nodo<T> {

    private T dato;
    private Nodo<T> siguiente;

    Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    T getDato() {
        return dato;
    }

    Nodo<T> getSiguiente() {
        return siguiente;
    }

    void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
}
