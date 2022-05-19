import java.lang.reflect.Array;

public class PV2<E>{
    private E[] arreglo;

    public void initialize(int dim, Class<E> theClass) {
        // El newInstance siempre devuelve un Object que dentro de un arreglo
        // de la clase.
        arreglo = (E[]) Array.newInstance(theClass, dim);
    }

    public void setElement(int pos, E element){
        arreglo[pos] = element;
    }

    public E getElement(int pos){
        return arreglo[pos];
    }

    public static void main(String[] args) {
        //Caso de Uso:
        PV2<Number> auxi = new PV2<>();
        auxi.initialize(5, Number.class);
        auxi.setElement(3, 10);
        auxi.setElement(2, 20.8);
        for (int i= 0; i < 5; i++) {
            System.out.println( auxi.getElement(i) );

        }
    }
}
