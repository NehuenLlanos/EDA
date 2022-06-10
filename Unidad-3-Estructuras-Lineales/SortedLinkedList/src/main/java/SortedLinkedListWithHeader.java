import java.util.Iterator;
import java.util.NoSuchElementException;

// getMax(), getMin() y size() tienen Complejidad de O(1)
public class SortedLinkedListWithHeader<T extends Comparable<? super T>> implements SortedListService<T> {
    private Node root;
    private int size = 0;
    private Node last;

    @Override // Iterativa
    public boolean insert(T data) {
        checkDataNotNull(data);
        Node prev = null;
        Node current = root;
        // Avanzo en la lista
        while(current != null && current.data.compareTo(data) < 0) {
            prev= current;
            current= current.next;
        }
        // Si esta repetido
        if (current!= null && current.data.compareTo(data) == 0) {
            System.err.println(String.format("Insertion failed %s", data));
            return false;
        }
        // Insercion segura
        Node aux = new Node(data, current);
        // Si el current es igual al root entonces no teniamos elementos en nuestra lista.
        if (current == root){
            root = aux;
            last = aux;
        }else{
            prev.next = aux;
            // Si el siguiente a insertar es null entonces estoy insertando en el ultimo lugar de la lista
            // Actualizo mi last.
            if(aux.next == null){
                last = aux;
            }
        }
        size++;
        return true;
    }

    @Override
    public boolean find(T data) {
        return getPos(data) != -1;
    }


    // Versión iterativa resuelto totalmente desde SortedLinkedList
    @Override
    public boolean remove(T data) {
        checkDataNotNull(data);
        Node prev = null;
        Node current = root;
        // Me muevo dentro de la lista ordenada hasta que el data sea menor o igual al current.data
        while(current != null && data.compareTo(current.data) > 0){
            prev = current;
            current = current.next;
        }
        // Si el current es distinto de null y el data es igual al current.data tengo que borrar el current
        if(current != null && data.compareTo(current.data) == 0){
            // Si el current es el root, es decir, que tengo solo un nodo en mi lista,
            // el prev es igual a null y por ende tengo que borrar el unico nodo que tengo
            // Para esto igualo root a null
            if(prev == null){
                root = null;
                last = null;
            }else{
                // En este caso tengo mas de un nodo y por ende al previo.next le asigno el current.next
                prev.next = current.next;
                // Actualizacion del last
                if(prev.next == null){
                    last = prev;
                }
            }
            // En ambos casos retorno true porque borre el elemento
            size--;
            return true;
        }
        // Si llegue aca es porque el current es null, es decir, llegue al final de mi lista o el data
        // no es igual al current.data lo que genera que no encuentre mi nodo a borrar. Entonces devuelvo
        // false porque no se borra ningun nodo.
        return false;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public void dump() {
        Node current = root;
        while (current!=null ) {
            System.out.println(current.data);
            current = current.next;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !  (other instanceof SortedLinkedList) )
            return false;
        @SuppressWarnings("unchecked")
        SortedLinkedListWithHeader<T> auxi = (SortedLinkedListWithHeader<T>) other;
        Node current = root;
        Node currentOther= auxi.root;
        while (current!=null && currentOther != null ) {
            if (current.data.compareTo(currentOther.data) != 0)
                return false;

            // por ahora si, avanzo ambas
            current= current.next;
            currentOther= currentOther.next;
        }
        return current == null && currentOther == null;

    }

    // -1 si no lo encontro
    protected int getPos(T data) {
        Node current = root;
        int pos= 0;

        while (current!=null ) {
            if (current.data.compareTo(data) == 0)
                return pos;

            // avanzo
            current= current.next;
            pos++;
        }
        return -1;
    }

    @Override
    public T getMin() {
        if(root == null){
            return null;
        }
        return root.data;
    }


    @Override
    public T getMax() {
        if(last == null){
            return null;
        }
        return last.data;
    }

    @Override
    public Iterator<T> iterator(){
        return new Iterator<T>() {
            Node current = root;
            Node prev = null;
            @Override
            public boolean hasNext() {
                if(current == null){
                    return false;
                }
                return true;
            }

            @Override
            public T next() {
                if(!hasNext()){
                    throw new RuntimeException("Finished");
                }
                prev = current;
                current = current.next;
                return prev.data;
            }

            public void remove(){
                // Casos importantes:
                //  * Si no tengo elementos para borrar lanzo una excepcion
                //  * Si tengo solo un elemento y lo remueve tengo que actualizar a size, root y last
                //  * Si elimino el ultimo tengo que actualizar size y last
                //  * Si elimino cualquiera que no sea el ultimo y primero tengo que actualizar size
                if(root == null){
                    throw new NoSuchElementException("No existen elementos en la lista");
                }
                if(current == root){
                    root = null;
                    last = null;
                }else if(current == last){
                    prev.next = null;
                    last = prev;
                }else {
                    prev.next = current.next;
                }
                size--;
            }
        };
    }

    private void checkDataNotNull(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
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

    public static void main(String[] args) {
//        SortedLinkedList<Integer> l = new SortedLinkedList<>();
//        System.out.println("LikedList 0: Sin nada");
//        l.dump();
//        System.out.println("Size: " + l.size());
//        System.out.println("Min: " + l.getMin());
//        System.out.println("Max: " + l.getMax());
//        System.out.println("-----------------");
//        l.insert1(10);
//        l.insert1(20);
//        l.insert1(30);
//        l.insert2(40);
//        l.insert2(50);
//        l.insert2(60);
//        l.insert(70);
//        l.insert(80);
//        System.out.println("LikedList 1");
//        l.dump();
//        System.out.println("Size: " + l.size());
//        System.out.println("Min: " + l.getMin());
//        System.out.println("Max: " + l.getMax());
//        System.out.println("-----------------");
//        l.remove(30);
//        l.remove2(50);
//        l.remove3(70);
//        l.remove3(80);
//        System.out.println("LikedList 2");
//        l.dump();
//        System.out.println("Size: " + l.size());
//        System.out.println("Min: " + l.getMin());
//        System.out.println("Max: " + l.getMax());

//        SortedLinkedListWithHeader<String> l = new SortedLinkedListWithHeader<>();
//        System.out.println(l.insert("hola"));
//        System.out.println(l.insert("tal"));
//        System.out.println(l.insert("ah"));
//        System.out.println(l.insert("veo"));
//        System.out.println(l.insert("bio"));
//        System.out.println(l.insert("tito"));
//        System.out.println(l.insert("aca"));
//        // la lista deberia contener: aca ah bio hola tal tito veo
//        System.out.println(l.size() );
//        for (String s : l) {
//            System.out.println(s);
//        }
//        System.out.println("\n");
//        l.remove("tito");
//        for (String s : l) {
//            System.out.println(s);
//        }
//        Es decir, deberá poder participar de “foreach” readonly (solo para leer la información a
//                través del cursor).
        SortedListService<String> lista = new SortedLinkedListWithHeader<>();
        lista.insert("aca");
        lista.insert("ah");
        lista.insert("bio");
        lista.insert("hola");
        lista.insert("tal");
        lista.insert("tito");
        lista.insert("veo");
        lista.dump();
        System.out.println("Size: " + lista.size());
        System.out.println("Min: " + lista.getMin());
        System.out.println("Max: " + lista.getMax());
        System.out.println("-------------------------");
        Iterator<String> iter = lista.iterator();
        int rec = 0;
//        while(iter.hasNext()) {
              //iter.next();
//            if (rec % 2 == 1)
//                iter.remove();
//            rec++;
//
//        }
        while(iter.hasNext()){
            iter.next();
            if(rec == 0){
                iter.remove();
            }
//            if(rec == 6){
//                iter.remove();
//            }

            rec++;
        }
        lista.dump();
        System.out.println("Size: " + lista.size());
        System.out.println("Min: " + lista.getMin());
        System.out.println("Max: " + lista.getMax());
    }
}

