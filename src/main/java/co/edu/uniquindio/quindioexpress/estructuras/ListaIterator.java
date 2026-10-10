package co.edu.uniquindio.quindioexpress.estructuras;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterador de la lista propia. Avanza nodo por nodo y entrega solo los datos.
 *
 * <p>Diferencia clave: {@link Iterable} es "algo que se puede recorrer"
 * (la lista, con su método iterator()); {@link Iterator} es "el que recorre"
 * (esta clase, con hasNext() y next()). Cada llamada a iterator() crea un
 * recorrido nuevo e independiente.
 *
 * <p>El constructor es de paquete: solo la lista puede crear su iterador.
 *
 * <p>Responsable: Dev 1.
 */
public class ListaIterator<T> implements Iterator<T> {

    private Nodo<T> actual;

    ListaIterator(Nodo<T> inicio) {
        this.actual = inicio;
    }

    @Override
    public boolean hasNext() {
        return actual != null;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No hay más elementos en la lista");
        }
        T dato = actual.getDato();
        actual = actual.getSiguiente();
        return dato;
    }
}
