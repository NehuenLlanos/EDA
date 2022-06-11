package Ejercicio2;

import java.util.Iterator;
import java.util.NoSuchElementException;


// lista simplemente encadenada, no acepta repetidos (false e ignora) ni nulls (exception)
public class SortedLinkedList<T extends Comparable<? super T>> implements SortedListService<T> {

    private Node root;

	@Override
    public void insert(T data) {

        if (data == null)
            throw new IllegalArgumentException("data cannot be null");

        Node prev= null;
        Node current = root;


        while (current!=null && current.data.compareTo(data) < 0) {
            // avanzo
            prev= current;
            current= current.next;
        }

        Node aux= new Node(data, current);
        // es el lugar para colocarlo
        if (current == root) {
            root= aux;
        }
        else {
            prev.next= aux;
        }

    }

  
	 @Override
    public Iterator<T> iterator() {
        return new SortedLinkedListIterator();
    }

    private class SortedLinkedListIterator implements Iterator<T> {

        private Node current;

        public SortedLinkedListIterator() {
            current= root;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T rta= current.data;
            current= current.next;

            return rta;
        }
    }
    private final class Node {
        private T data;
        private Node next;

        private Node(T data) {
            this.data= data;
        }

        private Node(T data, Node next) {
            this.data= data;
            this.next= next;
        }

    }



	 
 
    
}