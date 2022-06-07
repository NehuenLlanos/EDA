import java.util.function.Function;

public class ClosedHashingWithCollisions<K,V> implements IndexParametricService<K,V>{
    final private int initialLookupSize= 10;
    private final double threshold = 0.75;
    private int dim = 0; // Cantidad de elementos que tengo guardados en mi vector
    // estática. No crece. Espacio suficiente...
    @SuppressWarnings({"unchecked"})
    private Slot<K,V>[] Lookup= (Slot<K,V>[]) new Slot[initialLookupSize];

    private Function<? super K, Integer> prehash;
    public ClosedHashingWithCollisions(Function<? super K, Integer> mappingFn) {
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
    // Si la ranura calculada está marcada como baja física, el elemento se inserta allí. Caso contrario
    // (está ocupado y no es el elemento a insertar, o bien está marcado como baja lógica) hay que comenzar
    // a navegar con las sucesivas celdas hasta encontrar la primera baja física (o el error porque el
    // elemento ya existía). Atención que no se puede detenerse en la primera baja lógica y pretender
    // insertarlo allí porque justamente puede estar más adelante. Una vez que se encuentra la primera
    // baja física se lo puede insertar allí o en alguna de las bajas lógicas halladas en ese trayecto.
    public void insertOrUpdate(K key, V data) {
        if (key == null || data == null) {
            String msg= String.format("inserting or updating (%s,%s). ", key, data);
            if (key==null)
                msg+= "Key cannot be null. ";

            if (data==null)
                msg+= "Data cannot be null.";

            throw new IllegalArgumentException(msg);
        }
        // Rehasheo Lineal (linear probing). Si hay colisión en la ranura i, entonces intentar con la ranura i+1,
        // y así siguiendo hasta encontrar que el elemento (se hace update) o encontrar un lugar vacío (baja física)
        // y se inserta allí. Con esta técnica si hay lugar lo encuentra seguro.
        // Ej: si cae en ranura 4 y está ocupado va a intentar ranura 4+1, luego ranura 4+1+1,
        // luego ranura 4+1+1+1, etc. Se suele tratar al arreglo como una lista circular.

        // Verifico que en el lugar donde quiero almacenar mi par key-value pueda ser ocupado
        // Si esta ocupado con el mismo key, actualizo el value.
        // Si esta ocupado con otro key entonces tengo que seguir buscando un lugar segun rehasheo lineal
        // Obtengo el hashCode de la key ingresada
        int hashKey = hash(key);
        int firstLogicLow = -1;
        // Si esta ocupado
        if(Lookup[hashKey] != null){
            // Si es la misma key entonces actualizo y me voy porque actualice
            if(Lookup[hashKey].key == key){
                Lookup[hashKey].value = data;
                return;
            }
            // Veo si esta mas adelante
            //  * Si esta mas adelante la actualizo
            //  * Si no esta la guardo en la primer baja
            int firstHashKey = hashKey++;
            while(firstHashKey != hashKey){
                if(hashKey == Lookup.length){
                    hashKey = 0;
                }
                // En el index solicitado tengo una baja fisica
                if(Lookup[hashKey] == null){
                    // Tengo que insertar en la primer baja logica encontrada o aca
                    // Si el firstLogicLow es igual a -1 no encontre ninguna baja logica en mi camino
                    // entonces tengo que insertar en este hashKey, que es donde encontre mi baja fisica
                    if(firstLogicLow == -1){
                        firstLogicLow = hashKey;
                    }
                    // En ambos casos tengo que salir de mi ciclo para insertar en firstLogicLow
                    hashKey = firstHashKey;
                    // En el index solicitado no tengo una baja fisica
                }else{
                    // Verifico si el elemento no es baja logica y si es igual
                    if(Lookup[hashKey].exists && Lookup[hashKey].key == key){
                        // Actualizo el value
                        Lookup[hashKey].value = data;
                        // Me voy porque actualice mi valor
                        return;
                        // No soy una baja logica pero no soy igual o soy una baja logica
                        // Sigo buscando
                    }else{
                        // Si me encuentro con la primer baja logica, la guardo
                        if(!Lookup[hashKey].exists && firstLogicLow == -1){
                            firstLogicLow = firstHashKey;
                        }
                    }
                    // Incremento hashKey para seguir buscando.
                    hashKey++;
                }
            }
            // Si llegue aca es porque en firstLowKey tengo el index donde debo insertar el elemento
        }
        // El lugar solicitado esta libre o debo insertar en el index guardado en firstLowKey
        // Si firstLowKey es igual a -1 entonces guardo en hashKey. Sino tengo que guardar donde dice firstLowKey
        if(firstLogicLow != -1){
            hashKey = firstLogicLow;
        }
        // Inserto en el lugar correspondiente
        Lookup[hashKey] = new Slot<>(key, data);
        dim++;
        // Luego verifico la tasa de ocupacion. Si supera el 0.75 entonces tengo que:
        //      * Duplicar el espacio de la tabla.
        //      * Rehashear todos los keys de los values existentes.
        if(Double.compare((double) dim, threshold*Lookup.length) > 0){
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
    //  Búsqueda: Por lo dicho en el punto anterior se comienza buscando la clave en la ranura calculada. Si el lugar
    //  está con baja física seguro que no está en otro lado. Caso contrario si está marcado como ocupado y coincide
    //  con el valor esperado, se ha encontrado!!!. Pero si el lugar está ocupado y no es el elemento buscado o bien
    //  está como baja lógica, no se sabe si va aparecer más adelante (en la aplicación de las sucesivas funciones
    //  de hashing). O sea que en ese caso hay que seguir buscando hasta encontrarlo (hallar una ranura ocupada que
    //  coincida con el elemento) o bien hallar una baja física.
    public V find(K key) {
        // Si key == null retorno null porque no estoy buscando nada
        if (key == null)
            return null;
        // Empiezo a buscar en la ranura calculada.
        int hashKey = hash(key);
        Slot<K, V> entry = Lookup[hashKey];
        // Si entry es null quiere decir que es una baja fisica entonces no existe.
        if (entry == null)
            return null;
        // Si entry no es null entonces esta ocupado
        // Si coinicide el valor con el de la key entonces la encontre.
        if(entry.key == key){
            return entry.value;
        }
        // Si no coincide el valor con el de la key entonces tengo que seguir buscando circularmente
        // Si en la busqueda circular me encuentro con baja fisica entonces no existe
        // Sino lo puedo encontrar
        boolean flag = false;
        int firstHashKey = hashKey;
        hashKey++;
        // Recorro el LookUp circularmente hasta encontrarme con una baja fisica o
        // me encuentro con el elemento
        while(!flag){
            // Si hashKey es igual que Lookup.length entonces llegue al final de mi array
            // tengo que circularlo circularmente, entonces reinicio el hashKey.
            if(hashKey == Lookup.length){
                hashKey = 0;
            }
            // Obtengo el entry de Lookup
            entry = Lookup[hashKey];
            // Si me encuentro con un null es baja fisica entonces no lo encontre.
            // Si volvi al primer lugar consultado entonces tampoco lo encontre.
            if(firstHashKey == hashKey || entry == null){
                return null;
                // Si lo encuentro entonces enciendo el flag para que finalice el loop.
            }else if(entry.key == key){
                flag = true;
            }
            hashKey++;
        }
        // Retorno el valor encontrado
        return entry.value;
    }


    // Borrado
    //    No se puede reemplazar al lugar borrado por una ranura vacía porque la búsqueda de alguna clave puede
    //    necesitar "pasar sobre ella" si hubo colisión. Se debe manejar dos tipos de borrado: físico
    //    ( realmente se elimina el elemento ) y lógico ( se lo marca como que no está, y si más tarde hay que
    //    insertar en esa ranura se la puede aprovechar).
    //    * El borrado físico se lo usa cuando la ranura que le sigue (la que se obtiene al aplicar hashi+1 )
    //    está también borrado físicamente
    //    * El borrado lógico se lo usa en caso contrario, o sea cuando la ranura que le sigue está ocupada o
    //    bien borrada lógicamente
    public boolean remove(K key) {
        if (key == null)
            return false;
        // Encuentro el hashKey de la key solicitada
        int hashKey = hash(key);
        Slot<K,V> entry = Lookup[hashKey];
        // Si en el lugar solicitado no tengo elemento entonces no remuevo nada
        if (entry == null){
            return false;
        }
        // Primero verifico el lugar de la hashKey correspondiente
        // Si las key dada y la de entry son iguales entonces debo remover el par logica o fisicamente
        int nextHashKey = hashKey + 1;
        if(entry.key == key){
            // Como tienen la misma key ahora tengo que ver si el siguiente indice posee o no un elemento
            // Si el nextHashKey es igual que la longitud del array tengo que ponerlo en cero para dar la vuelta
            if(nextHashKey == Lookup.length){
                nextHashKey = 0;
            }
            // Si el siguiente indice no posee un elemento entonces tengo que borrar fisicamente
            if(Lookup[nextHashKey] == null){
                Lookup[hashKey] = null;
                // Si el siguiente indice posee un elemento entonces tengo que borrar logicamente
            }else{
                entry.exists = false;
            }
            dim--;
            return true;
        }
        // La key dada y la de entry no son iguales entonces verifico en los siguientes lugares
        int firstHashKey = hashKey++;
        // Recorro el vector hasta que me encuentro con una baja fisica o hasta que volvi
        // a la primer posicion solicitada
        while(firstHashKey != hashKey){
            if(hashKey == Lookup.length){
                hashKey = 0;
            }
            // Si encuentro el elemento lo borro fisica o logicamente
            if(Lookup[hashKey].key == key){
                nextHashKey = hashKey + 1;
                if(nextHashKey == Lookup.length){
                    nextHashKey = 0;
                }
                // Si el siguiente indice no posee un elemento entonces tengo que borrar fisicamente
                if(Lookup[nextHashKey] == null){
                    Lookup[hashKey] = null;
                    // Si el siguiente indice posee un elemento entonces tengo que borrar logicamente
                }else{
                    entry.exists = false;
                }
                dim--;
                return true;
            }
            // Si no lo encuentro en esta posicion, sigo buscando
            // Si me encuentro con una baja fisica y no lo encontre entonces retorno false
            if(Lookup[hashKey] == null){
                return false;
            }
            hashKey++;
        }
        return false;
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
        private boolean exists;

        private Slot(K theKey, V theValue){
            key= theKey;
            value= theValue;
            exists = true;
        }

        public String toString() {
            return String.format("(key=%s, value=%s)", key, value );
        }
    }


    public static void main(String[] args) {
        ClosedHashingWithCollisions<Integer, String> myHash= new ClosedHashingWithCollisions<>(f->f);
        myHash.insertOrUpdate(21, "Sol");
        myHash.insertOrUpdate(44, "Juan");
        myHash.insertOrUpdate(55, "Ana");
        myHash.insertOrUpdate(18, "Paula");
        myHash.insertOrUpdate(19, "Lucas");
        myHash.insertOrUpdate(29, "Carlos");
        myHash.insertOrUpdate(39, "Marta");
        myHash.dump();
        System.out.println(myHash.find(39));
        System.out.println(myHash.find(1));
        // Borro logicamente a Juan
        myHash.remove(44);
        // Verifico que el borrado logico me permita poner a Paolo
        myHash.insertOrUpdate(44, "Paolo");
        // Borro fisicamente a Marta
        myHash.remove(39);
        myHash.insertOrUpdate(59, "Marta pero 2");
        myHash.dump();
    }

}
