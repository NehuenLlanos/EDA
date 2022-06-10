// lista simplemente encadenada
public interface SortedListService<T extends Comparable<? super T>> {


    // no acepta nulls=> lanza exception
    // ignora repetidos y devuelve false en ese caso
    // si no existia devuelve true y lo inserta ordenadamente
    //boolean insert(T data);

    // Inserciones
    // No acepto nulls -> se lanza exception
    // Ignora repetidos y devuelve false en ese caso.
    // Si no existia devuelve true y lo inserta de manera ordenada.

    // Insercion Iterativa.
    boolean insertIterative(T data);
    // Insercion Recursiva realizada en la clase de la Lista.
    //boolean insertRecursiveInSortedListClass(T data);
    // Insercion Recursiva que se delega al nodo.
    //boolean insertRecursiveInNodeClass(T data);


    // nunca nunca nunca debe tirar exception
    // devuelve true si esta o false si no lo encuentra.
    boolean find(T data);

    // nunca nunca nunca debe tirar exception
    // borra y devuelve true si el elemento estaba
    // si no lo encuentra devuelve false .
    boolean removeIterative(T data);


    // nunca nunca nunca debe tirar exception
    // devuelve true si la lista no tiene elementos, false caso contrario
    boolean isEmpty();


    // nunca nunca nunca debe tirar exception
    // devuelve la cantidad de elementos presentes
    int size();

    // si la lista esta vacia devuelve null
    T getMin();

    // si la lista esta vacia devuelve null
    T getMax();


    void dump();

}
