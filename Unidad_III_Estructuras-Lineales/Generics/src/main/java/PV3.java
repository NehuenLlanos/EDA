import java.lang.reflect.Array;

public class PV3<E>{
    private E[] arreglo;

    /*
       Si tuviese E extends Comparable se pierde el unbound y se caga todo
    */

    public void initialize(int dim, Class<E> theClass) {
        arreglo = (E[]) new Object[dim];
    }

    public void setElement(int pos, E element){
        arreglo[pos] = element;
    }

    public E getElement(int pos){
        return arreglo[pos];
    }
}
