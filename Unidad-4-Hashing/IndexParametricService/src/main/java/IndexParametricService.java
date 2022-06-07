public interface IndexParametricService<K, V> {


    // no acepta nulls ni en key ni en data=> lanza exception
    // si el key está, realizar un update en el valor
    // si no existia lo inserta. Si hace falta crece de a chunks
    void insertOrUpdate(K key, V data);


    // nunca nunca nunca debe tirar exception
    // devuelve el valor asociado si lo encuentra o null si no está.
    V find(K data);

    // nunca nunca nunca debe tirar exception
    // borra y devuelve true si el elemento estaba
    // si no lo encuentra devuelve false .
    boolean remove(K key);


    // nunca nunca nunca debe tirar exception
    // devuelve la cantidad de elementos presentes
    int size();


    // imprime en cualquier orden
    void dump();

}
