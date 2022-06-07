import java.util.Arrays;

public class IndexWithDuplicates implements IndexService{
    private static final int BLOCK = 10;
    private int[] array;


    /* Initialize */
    // elements serán los valores del índice, los anteriores se descartan.
    // lanza excepction si elements is null y deja los valores anteriores.
    public void initialize(int [] elements){
        if(elements == null){
            throw new IllegalArgumentException();
        }
        array = elements;
        Arrays.sort(array);
    }


    /* Search */
    // busca una key en el índice, O(log2 N)
    public boolean search(int key){
        if(array == null){
            return false;
        }
        int idx = getClosestPositionWrapper(0,array.length - 1, key);
        return array[idx] == key;
    }


    /* Insert */
    // inserta el key en pos correcta. Crece automáticamente de a chunks!!
    public void insert(int key){
        if(array == null){
            array = new int[1];
            array[0] = key;
            return;
        }
        // Creo una copia de mi array auxiliar
        int[] aux = Arrays.copyOf(array, array.length);
        // Obtengo la posicion del elemento a insertar
        int idx = getClosestPositionWrapper(0, array.length - 1, key);
        // Agrando en 1 el array
        array = Arrays.copyOf(array, array.length + 1);
        // Usando el vector auxiliar copio los elementos de aux desde la posicion idx en la posicion idx+1 en el
        // array original.
        for(int i = idx; i < aux.length ; i++){
            array[i+1] = aux[i];
        }
        // Luego voy a la posicion de idx e inserto el numero en dicha posicion
        array[idx] = key;
    }

    private void resize(){

    }


    /* Delete */
    // borra el key si lo hay, sino lo ignora.
    // decrece automáticamente de a chunks
    public void delete(int key){
        if(array == null){
            return;
        }
        // Obtengo la posicion donde deberia estar la key a borrar.
        int idx = getClosestPositionWrapper(0,array.length - 1, key);
        // Si efectivamente la key se encontraba en el array debo borrarla.
        if(array[idx] == key){
            // Creo una copia del array original.
            int[] aux = Arrays.copyOf(array, array.length);
            // Copio desde idx+1 los elementos del auxiliar una posicion menos
            // en el array original.
            for(int i = idx; i < aux.length - 1; i++){
                array[i] = aux[i+1];
            }
            array = Arrays.copyOf(array, array.length - 1);
        }
    }


    /* Ocurrences */
    // devuelve la cantidad de apariciones de la clave especificada
    public int occurrences(int key){
        if(array == null){
            return 0;
        }
        int idx = getClosestPositionWrapper(0,array.length - 1, key);
        int count = 0;
        if(array[idx] == key){
            for(int i = idx; i < array.length && array[i] == key; count++, i++);
        }
        return count;
    }


    /* ClosestPosition */
    private int getClosestPositionWrapper(int l, int r, int key){
        // Si el primer elemento del array es mayor o igual a la key entonces
        // debo devolver la posicion cero.
        if(array[0] >= key){
            return 0;
        }
        // Si el ultimo elemento del array es menor que la key entonces debo retornar
        // la posicion siguiente a la ultima porque es donde se debe colocar la key.
        if(array[array.length-1] < key){
            return array.length;
        }
        return getClosestPosition(l, r, key);
    }
    private int getClosestPosition(int l, int r, int key) {
        // Calculo el mid.
        int mid = l + (r - l) / 2;
        // Si array[mid] es igual que la key entonces debo verificar que en las
        // posiciones inferiores no exista un elemento que sea igual a key pues
        // puede pasar que este repetido.
        if(array[mid] == key){
            return getClosestPosition(l, mid, key);
        }
        // Si array[mid] es mayor que key entonces debo buscar es la posicion inferior
        // del array.
        if(array[mid] > key){
            // Para buscar en la parte inferior le mantengo el l y hago que r = mid.
            return getClosestPosition(l, mid, key);
        }
        // Si array[mid] es menor que key entonces primero que nada verfico que el siguiente
        // no sea mayor o igual. Porque si es mayor o igual entonces encontre la posicion a
        // devolver que sera mid+1.
        if(array[mid+1] >= key){
            return mid+1;
        }
        // Si array[mid+1] es menor que key entonces debo buscar en la parte superior.
        // Para poder hacer esto mantengo el r y hago que l = mid.
        return getClosestPosition(mid, r,  key);
    }


    /* Range */
    // devuelve un nuevo arreglo ordenado con los elementos que pertenecen al intervalo dado por
    // leftkey y rightkey. Si el mismo es abierto/cerrado depende de las variables leftIncluded
    // y rightIncluded. True indica que es cerrado. Si no hay matching devuelve arreglo de length 0
    public int[] range(int leftKey, int rightKey, boolean leftIncluded, boolean rightIncluded){
        if(leftKey > array[array.length-1]){
            return new int[0];
        }
        int pos = getClosestPositionWrapper(0, array.length, leftKey);
        int[] ans = new int[BLOCK];
        int j = 0;
        // Copio todas las repeticiones de la leftKey
        while(pos < array.length && array[pos] == leftKey){
            if(leftIncluded){
                if(j == ans.length){
                    ans = Arrays.copyOf(ans, ans.length + BLOCK);
                }
                ans[j++] = array[pos];
            }
            pos++;
        }
        // Copio todos los elementos entre leftKey y rightKey
        while(pos < array.length && array[pos] < rightKey){
            if(j == ans.length){
                ans = Arrays.copyOf(ans, ans.length + BLOCK);
            }
            ans[j++] = array[pos++];
        }
        // Copio todas las repeticiones de rightKey
        while(pos < array.length && array[pos] == rightKey){
            if(rightIncluded){
                if(j == ans.length){
                    ans = Arrays.copyOf(ans, ans.length + BLOCK);
                }
                ans[j++] = array[pos];
            }
            pos++;
        }
        ans = Arrays.copyOf(ans, j);
        return ans;
    }


    /* SortedPrint */
    // imprime el contenido del índice ordenado por su key.
    public void sortedPrint(){
        if(array == null){
            return;
        }
        StringBuilder ans = new StringBuilder();
        ans.append("[");
        for(int i = 0 ; i < array.length - 1; i++){
            ans.append(array[i]);
            ans.append(",");
        }
        ans.append(array[array.length-1]);
        ans.append("]\n");
        System.out.println(ans.toString());
    }


    /* Max */
    // devuelve el máximo elemento del índice. Lanza RuntimeException si no hay elementos
    public int getMax(){
        return array[array.length-1];
    }


    /* Min */
    // devuelve el mínimo elemento del índice. Lanza RuntimeException si no hay elementos
    public int getMin(){
        return array[0];
    }
}
