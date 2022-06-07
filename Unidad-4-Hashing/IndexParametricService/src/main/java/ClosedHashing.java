import java.util.function.Function;

public class ClosedHashing<K, V> implements IndexParametricService<K, V> {
    final private int initialLookupSize= 10;
    private final double threshold = 0.75;
    private int dim = 0; // Cantidad de elementos que tengo guardados en mi vector
    // estática. No crece. Espacio suficiente...
    @SuppressWarnings({"unchecked"})
    private Slot<K,V>[] Lookup= (Slot<K,V>[]) new Slot[initialLookupSize];

    private Function<? super K, Integer> prehash;
    public ClosedHashing( Function<? super K, Integer> mappingFn) {
        if (mappingFn == null)
            throw new RuntimeException("fn not provided");

        prehash= mappingFn;
    }
    // ajuste al tamaño de la tabla
    private int hash(K key) {
        if (key == null)
            throw new IllegalArgumentException("key cannot be null");

        return prehash.apply(key) % Lookup.length;
    }
    public void insertOrUpdate(K key, V data) {
        // Se verifica que la key y el data sean distintos de null
        // Si son iguales a null entonces son elementos que no se pueden insertar y por ende
        // se lanza una excepcion.
        if (key == null || data == null) {
            String msg= String.format("inserting or updating (%s,%s). ", key, data);
            if (key==null)
                msg+= "Key cannot be null. ";

            if (data==null)
                msg+= "Data cannot be null.";

            throw new IllegalArgumentException(msg);
        }
        // Verifico que en el lugar donde quiero almacenar mi par key-value pueda ser ocupado
        // Si esta ocupado con el mismo key, actualizo el value.
        // Si esta ocupado con otro key entonces lanzo una exception.
        // Obtengo el hashCode de la key ingresada
        int hashKey = hash(key);
        if(Lookup[hashKey] != null){
            if(Lookup[hashKey].key == key){
                Lookup[hashKey].value = data;
            }else{
                throw new RuntimeException("Could not insert pair key-value");
            }
            return;
        }
        // Si llegue aca quiere decir que el lugar de inserccion estaba vacio
        // Primero insertamos el par key-value
        Lookup[hashKey] = new Slot<>(key, data);
        dim++;
        // Luego verifico la tasa de ocupacion. Si supera el 0.75 entonces tengo que:
        //      * Duplicar el espacio de la tabla.
        //      * Rehashear todos los keys de los values existentes.
        if(Double.compare((double) dim, threshold* Lookup.length) > 0){
            Slot<K,V>[] auxLookup = Lookup;
            Lookup = (Slot<K,V>[]) new Slot[initialLookupSize + Lookup.length];
            dim = 0;
            for(Slot<K,V> e : auxLookup){
                if(e != null){
                    insertOrUpdate(e.key, e.value);
                }
            }
        }
    }
    public Slot<K,V> findPair(int hashCode){
        Slot<K, V> entry = Lookup[hashCode];
        if (entry == null)
            return null;
        return entry;
    }
    // find or get
    public V find(K key) {
        if (key == null)
            return null;
        Slot<K, V> entry = Lookup[hash(key)];
        if (entry == null)
            return null;
        return entry.value;
    }

    public boolean remove(K key) {
        if (key == null)
            return false;

        // lo encontre?
        if (Lookup[ hash( key) ] == null)
            return false;

        Lookup[ hash( key) ] = null;
        return true;
    }

    public void dump()  {
        for(int rec= 0; rec < Lookup.length; rec++) {
            if (Lookup[rec] == null)
                System.out.println(String.format("slot %d is empty", rec));
            else
                System.out.println(String.format("slot %d contains %s",rec, Lookup[rec]));
        }
    }

    public int size() {
        // todavia no esta implementado
        return 0;
    }

    static private final class Slot<K, V>	{
        private final K key;
        private V value;
        private Slot(K theKey, V theValue){
            key= theKey;
            value= theValue;
        }
        public String toString() {
            return String.format("(key=%s, value=%s)", key, value );
        }
    }


    public static void main(String[] args) {
        ClosedHashing<Integer, String> myHash= new ClosedHashing<>(f->f);
        myHash.insertOrUpdate(10, "Hector2");
        myHash.insertOrUpdate(21, "Michael");
        // Ejemplo exception
        // myHash.insertOrUpdate(11, "Michael");
        // Ejemplo exception
        // myHash.insertOrUpdate(21, "Scott");
        myHash.insertOrUpdate(44, "Juan");
        myHash.insertOrUpdate(55, "Ana");
        myHash.insertOrUpdate(76, "Hector");
        myHash.insertOrUpdate(77, "Hector");
        myHash.insertOrUpdate(18, "Paula");
        myHash.insertOrUpdate(19, "Lucas");
        myHash.dump();

    }
}
