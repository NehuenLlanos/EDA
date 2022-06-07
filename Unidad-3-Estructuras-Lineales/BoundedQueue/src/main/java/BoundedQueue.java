import java.util.NoSuchElementException;

public class BoundedQueue<T> {
    private T[] elements;
    private int first;
    private int last;
    private int qty = 0;

    public BoundedQueue(int limit){
        qty = limit;
    }

    public boolean isEmpty(){
        return qty == 0;
    }

    public boolean isFull(){
        return qty == elements.length;
    }

    /*
    Tengo 2 casos
        * El primero es que qty == elements.length
        * El segundo que no pase lo del primero
 */
    public void enqueue(T element){
        if(isFull()){
            throw new NoSuchElementException();
        }
        if(last == elements.length - 1){
            elements[0] = element;
            last = 0;
        }else{
            elements[++last] = element;
        }
    }

    public T dequeue(){
        // Si esta vacio no puedo desencolar nada
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        T ans = elements[first];
        if(first == elements.length - 1){
            first = 0;
        }else{
            first++;
        }
        return ans;
    }

    private void dump(){

    }
}
