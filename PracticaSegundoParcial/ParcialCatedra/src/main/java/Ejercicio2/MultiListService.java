package Ejercicio2;

public interface MultiListService<T extends Comparable<? super T>>  {

    void insert( String listName, SortedListService<T> list );

    void dump();
    
    void dump(String listName);
}