import java.lang.reflect.Array;
import java.util.Arrays;

public class IndexWithDuplicates<T extends Comparable<? super T>> implements IndexParametricService<T>{
    private static final int BLOCK = 10;
    private T[] array;
    private int dim = 0;
    private Class<T> theClass;

    public IndexWithDuplicates(Class<T> theClass){
        this.theClass = theClass;
        this.array = (T[]) Array.newInstance(theClass, BLOCK);
    }
    // elements serán los valores del índice, los anteriores se descartan
    // lanza excepction si elements is null o si alguno de los elementos del
    // arreglo proporcionado son null
    public void initialize(T[] elements){
        if (elements == null) {
            throw new IllegalArgumentException();
        }
        array = (T[]) Array.newInstance(theClass, BLOCK);
        dim = 0;
        for (T i : elements) {
            this.insert(i);
        }
    }
    // busca una key en el índice, O(log2 N)
    public boolean search(T key){
        int index = getClosestPositionWrapper(0, dim - 1, key);
        if(array[index].compareTo(key) == 0){
            return true;
        }
        return false;
    }


    // inserta el key en pos correcta. Crece automáticamente de a chunks.
    // si el valor proporcionado es null, ignora el pedido.
    public void insert(T key){
        if(key == null){
            return;
        }
        int index = getClosestPositionWrapper(0, dim - 1, key);
        for(int i = dim ; i > index ; i--){
            if(dim == array.length){
                array = Arrays.copyOf(array, dim + BLOCK);
            }
            array[i] = array[i-1];
        }
        dim++;
        array[index] = key;
    }


    // borra el key si lo hay, sino lo ignora.
    // decrece automáticamente de a chunks
    public void delete(T key){
        if(key == null){
            return;
        }
        int index = getClosestPositionWrapper(0, dim - 1, key);
        if(array[index].compareTo(key) == 0){
            for(int i = index; i < dim; i++){
                // Tengo que ver como achicar de a chunks
//                if(dim == array.length){
//                    array = Arrays.copyOf(array, dim + BLOCK);
//                }
                array[i] = array[i+1];
            }
            dim--;
        }
    }


    // devuelve la cantidad de apariciones de la clave especificada.
    public int occurrences(T key){
        if(key == null){
            return 0;
        }
        int index = getClosestPositionWrapper(0, dim - 1, key);
        if(array[index].compareTo(key) == 0){
            int counter = 0;
            for(int i = index; i < dim && array[i].compareTo(key) == 0; i++, counter++);
            return counter;
        }
        return 0;
    }


    // devuelve un nuevo arreglo ordenado con los elementos que pertenecen
    // al intervalo dado por leftkey y rightkey. Si el mismo es abierto/cerrado depende
    // de las variables leftIncluded y rightIncluded. True indica que es cerrado. El valor
    // devuelto será un arrego de length 0 si no hay elementos que satisfagan al condicion
    public T[] range(T leftKey, T rightKey, boolean leftIncluded, boolean rightIncluded){
        T[] ans = (T[]) Array.newInstance(theClass, 0);
        if(dim == 0){
            return ans;
        }
        int index = getClosestPositionWrapper(0, dim - 1, leftKey);
        int j = 0;
        // Copio todas las repeticiones de la leftKey
        while(index < dim && array[index].compareTo(leftKey) == 0){
            if(leftIncluded){
                if(j == ans.length){
                    ans = Arrays.copyOf(ans, ans.length + BLOCK);
                }
                ans[j++] = array[index];
            }
            index++;
        }
        // Copio todos los elementos entre leftKey y rightKey
        while(index < dim && array[index].compareTo(rightKey) < 0){
            if(j == ans.length){
                ans = Arrays.copyOf(ans, ans.length + BLOCK);
            }
            ans[j++] = array[index++];
        }
        // Copio todas las repeticiones de rightKey
        while(index < array.length && array[index].compareTo(rightKey) == 0){
            if(rightIncluded){
                if(j == ans.length){
                    ans = Arrays.copyOf(ans, ans.length + BLOCK);
                }
                ans[j++] = array[index];
            }
            index++;
        }
        ans = Arrays.copyOf(ans, j);
        return ans;
    }


    // imprime el contenido del índice ordenado por su key
    public void sortedPrint(){
        return;
    }


    // devuelve el máximo elemento del índice o null si no hay elementos
    public T getMax(){
        return array[dim-1];
    }


    // devuelve el mínimo elemento del índice o null si no hay elementos
    public T getMin(){
        return array[0];
    }


    /* ClosestPosition */
    private int getClosestPositionWrapper(int l, int r, T key){
        // Si el primer elemento del array es mayor o igual a la key entonces
        // debo devolver la posicion cero.
        if(dim == 0 || array[0].compareTo(key) >= 0){
            return 0;
        }
        // Si el ultimo elemento del array es menor que la key entonces debo retornar
        // la posicion siguiente a la ultima porque es donde se debe colocar la key.
        if(array[dim-1].compareTo(key) < 0){
            return dim;
        }
        return getClosestPosition(l, r, key);
    }
    private int getClosestPosition(int l, int r, T key) {
        // Calculo el mid.
        int mid = l + (r - l) / 2;
        // Si array[mid] es igual que la key entonces debo verificar que en las
        // posiciones inferiores no exista un elemento que sea igual a key pues
        // puede pasar que este repetido.
        if(array[mid].compareTo(key) == 0){
            return getClosestPosition(l, mid, key);
        }
        // Si array[mid] es mayor que key entonces debo buscar es la posicion inferior
        // del array.
        if(array[mid].compareTo(key) > 0){
            // Para buscar en la parte inferior le mantengo el l y hago que r = mid.
            return getClosestPosition(l, mid, key);
        }
        // Si array[mid] es menor que key entonces primero que nada verfico que el siguiente
        // no sea mayor o igual. Porque si es mayor o igual entonces encontre la posicion a
        // devolver que sera mid+1.
        if(array[mid+1].compareTo(key) >= 0){
            return mid+1;
        }
        // Si array[mid+1] es menor que key entonces debo buscar en la parte superior.
        // Para poder hacer esto mantengo el r y hago que l = mid.
        return getClosestPosition(mid, r, key);
    }
}
