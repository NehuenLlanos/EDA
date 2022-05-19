public interface IndexService {
    // Elements serán los valores del índice, los anteriores se descartan.
    // Lanza exception si elements es null y deja los valores anteriores.
    void initialize(String [] elements);

    // Busca una key en el índice. Devuelve true si está en el índice y false en caso contrario
    //boolean search(String key);

    // Inserta el key en posición correcta.
    // Crece automáticamente de a chunks!!
    void insert(String key);

    // Borra el key si lo hay, sino lo ignora. Si hubiera más de uno, borra cualquiera de ellos.
    // Decrece automáticamente de a chunks
    //void delete(String key);

    // Devuelve un array con todas las claves que se encuentren en el intervalo [leftKey, rightKey). Es decir, las claves mayores o iguales a leftKey y las estrictamente menores a rightKey.
    //String[] range(String leftKey, String rightKey);


    // Devuelve la cantidad de apariciones de la clave especificada
    long occurrences(String key);

    // Devuelve la mayor clave
    String getMax();

    // Devuelve la menor clave
    String getMin();
}
