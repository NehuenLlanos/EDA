public class P<E>{
    private Object[] arreglo;

    public void initialize(int dim) {
        arreglo = new Object[dim];
    }

    public void setElements(int pos, E element){
        arreglo[pos] = element;
    }

    @SuppressWarnings("unchecked")
    public E getElement(int pos){
        return (E) arreglo[pos];
    }

}
