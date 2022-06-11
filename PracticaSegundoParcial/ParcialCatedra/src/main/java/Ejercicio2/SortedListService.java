package Ejercicio2;

// lista simplemente encadenada
public interface SortedListService<T extends Comparable<? super T>>  extends Iterable<T>  {
    // no acepta nulls=> lanza exception
    // inserta ordenadamente y acepta repetidos
    void insert(T data);
}