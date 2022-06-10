// lista simplemente encadenada, no acepta repetidos (false e ignora) ni nulls (exception)
public class SortedLinkedList<T extends Comparable<? super T>> implements SortedListService<T>{

    private Node root;

    /* Inserciones implementadas por la catedra */
    // Version Iterativa implementada por la catedra
    public boolean insert1(T data) {
        if (data == null)
            throw new IllegalArgumentException("data cannot be null");

        Node prev = null;
        Node current= root;

        while( current != null && current.data.compareTo(data) <0    ) {
            // avanzo
            prev = current;
            current = current.next;
        }

        // repetido?
        if ( current!= null && current.data.compareTo(data) ==0  ) {
            System.err.println(String.format("Insertion failed %s", data));
            return false;
        }


        // insercion segura
        Node aux= new Node(data, current);

        // como engancho??? cambia el root???
        if (current == root)
            // cambie el primero
            root= aux;
        else  // nodo interno
            prev.next= aux;

        return true;
    }

    // Version Recursiva implementada por la catedra
    public boolean insert2(T data) {
        if (data == null)
            throw new IllegalArgumentException("data cannot be null");

        boolean[] rta= new boolean[1];
        root = insertRec( data, root,  rta);

        return rta[0];
    }
    private Node insertRec(T data, Node current, boolean[] rta ) {
        // repetido?
        if ( current!= null && current.data.compareTo(data) ==0  ) {
            System.err.println(String.format("Insertion failed %s", data));
            rta[0]= false;
            return current;
        }

        if( current != null && current.data.compareTo(data) <0    ) {
            // avanzo
            current.next   = insertRec(data, current.next, rta);
            return current;
        }


        // estoy en parado en el lugar a insertar
        rta[0]= true;
        return new Node(data, current);

    }

    // Version Recursiva delega al nodo implementada por la catedra
    @Override
    public boolean insert(T data) {
        if (data == null)
            throw new IllegalArgumentException("data cannot be null");

        if (root == null) {
            root= new Node(data, null);
            return true;
        }

        boolean[] rta= new boolean[1];
        root =root.insert( data,  rta);

        return rta[0];
    }


    /* Inserciones Implementadas por mi */
    @Override
    public boolean insertIterative(T data) {
        if(data == null){
            return false;
        }
        if(root == null){
            Node ans = new Node(data, null);
            root = ans;
            return true;
        }
        Node current = root;
        Node previous = null;
        while(current != null){
            if(current.data.compareTo(data) == 0){
                return false;
            }else if(current.data.compareTo(data) > 0){ // Inserto porque el current es mayor que el que se quiere insertar
                Node ans = new Node(data, current);
                if(previous == null){
                    root = ans;
                }else{
                    previous.next = ans;
                }
                return true;
            }
            previous = current;
            current = current.next;
        }
        Node ans = new Node(data, current);
        previous.next = ans;
        return true;
    }

    @Override
    public boolean insertRecursiveInSortedListClass(T data) {
        boolean[] ans = new boolean[1];
        root = insertRecursiveInSortedListClassAux(data, root, ans);
        return ans[0];
    }
    private Node insertRecursiveInSortedListClassAux(T data, Node current, boolean[] ans){
        if(current == null || current.data.compareTo(data) > 0){
            Node aux = new Node(data);
            aux.next = current;
            ans[0] = true;
            return aux;
        }else if(current.data.compareTo(data) == 0){
            ans[0] = false;
            return current;
        }
        current.next = insertRecursiveInSortedListClassAux(data, current.next, ans);
        return current;
    }

    @Override
    public boolean insertRecursiveInNodeClass(T data) {
        if(data == null){
            return false;
        }
        if(root == null){
            Node aux = new Node(data);
            root = aux;
            return true;
        }
        boolean ans[] = new boolean[1];
        root = root.insertInNode(data, ans);
        return ans[0];
    }


    @Override
    public boolean find(T data) {
        return getPos(data) != -1;
    }

    /* Implementaciones de remove realizadas por mi */

    // Version Iterativa
    @Override
    public boolean removeIterative(T data) {
        if(data == null){
            return false;
        }
        Node current = root;
        Node previous = null;

        while(current != null && current.data.compareTo(data) < 0){
            previous = current;
            current = current.next;
        }

        if(current != null && current.data.compareTo(data) == 0){
            // Si es el root tengo que actualizarlo
            if(current == root){
                root = root.next;
            }else { // Si no es el root entonces utilizo el previous para borrar el nodo solicitado
                previous.next = current.next;
            }
            return true;
        }
        return false;
    }

    // Version Recursiva implementada en la lista
    private boolean removeRecursiveInSortedListClass(T data){
        if(data == null){
            return false;
        }
        boolean[] ans = new boolean[1];
        root = removeRecursiveInSortedListClassAux(data, root, ans);
        return ans[0];
    }
    private Node removeRecursiveInSortedListClassAux(T data, Node current, boolean[] ans){
        if(current == null){
            ans[0] = false;
            return null;
        }
        if(current.data.compareTo(data) == 0){
            ans[0] = true;
            return current.next;
        }
        if(current.data.compareTo(data) > 0){
            ans[0] = false;
            return current;
        }
        current.next = removeRecursiveInSortedListClassAux(data, current.next, ans);
        return current;
    }

    // Version Recursiva delegada al nodo
    public boolean removeRecursiveInNodeClass(T data){
        if(data == null || root == null){
            return false;
        }
        boolean[] ans = new boolean[1];
        root = root.removeInNode(data, ans);
        return ans[0];
    }
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        int rta= 0;

        Node current = root;

        while (current!=null ) {
            // avanzo
            rta++;
            current= current.next;
        }
        return rta;
    }

    @Override
    public void dump() {
        Node current = root;

        while (current!=null ) {
            // avanzo
            System.out.println(current.data);
            current= current.next;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !  (other instanceof SortedLinkedList) )
            return false;

        @SuppressWarnings("unchecked")
        SortedLinkedList<T> auxi = (SortedLinkedList<T>) other;

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
            //throw new RuntimeException("Lista vacia");
            return null;
        }
        return root.data;
    }

    @Override
    public T getMax() {
        if(root == null){
            return null;
            //throw new RuntimeException("Lista vacia");
        }
        Node current = root;
        while(current.next != null){
            current = current.next;
        }
        return current.data;
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

        private Node insert(T data, boolean[] rta) {

            if ( this.data.compareTo(data) ==0  ) {
                System.err.println(String.format("Insertion failed %s", data));
                rta[0]= false;
                return this;
            }

            if( this.data.compareTo(data) <0    ) {
                // soy el ultimo?
                if (next==null) {
                    rta[0]= true;
                    next   = new Node(data, null);
                    return this;
                }


                // avanzo
                next   = next.insert(data, rta);
                return this;
            }
            // estoy en parado en el lugar a insertar
            rta[0]= true;
            return new Node(data, this);
        }

        private Node insertInNode(T data, boolean[] ans){
            if(this.data.compareTo(data) == 0){
                ans[0] = false;
                return this;
            }
            if(this.data.compareTo(data) < 0){
                if(next == null){
                    next = new Node(data, null);
                    ans[0] = true;
                    return this;
                }
                next = next.insertInNode(data, ans);
                return this;
            }
            ans[0] = true;
            return new Node(data, this);
        }

        private Node removeInNode(T data, boolean[] ans){
            if(this.data.compareTo(data) == 0){
                ans[0] = true;
                return next;
            }
            if(this.data.compareTo(data) > 0 || next == null){
                ans[0] = false;
                return this;
            }
            next = next.removeInNode(data, ans);
            return this;
        }
    }

    public static void main(String[] args) {
        SortedLinkedList<Integer> l = new SortedLinkedList<>();
        l.insert1(30);
        l.insert1(80);
        l.insert1(40);
        l.insert1(40);

        System.out.println("-----------------------------------------------");
        SortedLinkedList<Integer> l2 = new SortedLinkedList<>();
        System.out.println(l2.removeIterative(30));
        System.out.println(l2.insertIterative(30));
        System.out.println(l2.insertIterative(80));
        System.out.println(l2.insertIterative(40));
        System.out.println(l2.insertIterative(40));
        System.out.println(l2.insertIterative(100));
        System.out.println(l2.removeIterative(40));

        System.out.println("-----------------------------------------------");
        SortedLinkedList<Integer> l3 = new SortedLinkedList<>();
        System.out.println(l3.removeRecursiveInSortedListClass(20));
        System.out.println(l3.insertRecursiveInSortedListClass(30));
        System.out.println(l3.insertRecursiveInSortedListClass(80));
        System.out.println(l3.insertRecursiveInSortedListClass(40));
        System.out.println(l3.insertRecursiveInSortedListClass(40));
        System.out.println(l3.insertRecursiveInSortedListClass(50));
        System.out.println(l3.insertRecursiveInSortedListClass(100));
        System.out.println(l3.removeRecursiveInSortedListClass(50));
        System.out.println(l3.removeRecursiveInSortedListClass(100));

        System.out.println("-----------------------------------------------");

        SortedLinkedList<Integer> l4 = new SortedLinkedList<>();
        // Testeo con lista vacia
        System.out.println(l4.removeRecursiveInNodeClass(20)); // False
        // Primer elemento insertado
        System.out.println(l4.insertRecursiveInNodeClass(30)); // True
        // Testeo con 1 solo elemento en la lista
        System.out.println(l4.removeRecursiveInNodeClass(40)); // False
        System.out.println(l4.removeRecursiveInNodeClass(30)); // True
        // Testeo getMin y getMax con lista vacia
        System.out.println(l4.getMin()); // NULL
        System.out.println(l4.getMax()); // NULL
        // Creo la lista grande para testear la insercion
        System.out.println(l4.insertRecursiveInNodeClass(30)); // True
        // Testeo getMin y getMax con lista con 1 elemento
        System.out.println(l4.getMin()); // 30
        System.out.println(l4.getMax()); // 30
        // Continuamos agregando
        System.out.println(l4.insertRecursiveInNodeClass(80)); // True
        System.out.println(l4.insertRecursiveInNodeClass(40)); // True
        System.out.println(l4.insertRecursiveInNodeClass(40)); // False
        System.out.println(l4.insertRecursiveInNodeClass(50)); // True
        // Testeo getMin y getMax con lista con + de 1 elemento
        System.out.println(l4.getMin()); // 30
        System.out.println(l4.getMax()); // 80
        // Testeo de remocion de 1 elemento que no aparece en la lista
        System.out.println(l4.removeRecursiveInNodeClass(90)); // False
        // Testeo de remocion del ultimo elemento
        System.out.println(l4.removeRecursiveInNodeClass(80)); // True
        // Testeo de remocion de un elemento del medio
        System.out.println(l4.removeRecursiveInNodeClass(40)); // True
    }

    public static void main2(String[] args) {
        SortedLinkedList<String> l = new SortedLinkedList<>();
        System.out.println("lista " +  (l.isEmpty()? "":"NO") + " vacia");
        System.out.println();

        System.out.println(l.insert("hola"));
        l.dump();
        System.out.println();

        System.out.println("lista " +  (l.isEmpty()? "":"NO") + " vacia");
        System.out.println();

        System.out.println(l.insert("tal"));
        l.dump();
        System.out.println();

        System.out.println(l.insert("ah"));
        l.dump();
        System.out.println();

        System.out.println(l.insert("veo"));
        l.dump();
        System.out.println();

        System.out.println(l.insert("bio"));
        l.dump();
        System.out.println();

        System.out.println(l.insert("tito"));
        l.dump();
        System.out.println();


        System.out.println(l.insert("hola"));
        l.dump();
        System.out.println();


        System.out.println(l.insert("aca"));
        l.dump();
        System.out.println();

        System.out.println(l.size() );

    }
}