// lista simplemente encadenada, no acepta repetidos (false e ignora) ni nulls (exception)
public class SortedLinkedList<T extends Comparable<? super T>> implements SortedListService<T>{

    private Node root;

    // iterativa
//	@Override
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

    // recursiva desde afuera
//	@Override
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
        root = insertRecursiveInSortedListClassAux(data, root, ans[0]);
        return ans[0];
    }
    private Node insertRecursiveInSortedListClassAux(T data, Node current, Boolean flag){
        if(current == null || current.data.compareTo(data) > 0){
            Node ans = new Node(data);
            ans.next = current;
            flag = true;
            return ans;
        }else if(current.data.compareTo(data) == 0){
            return current;
        }
        current.next = insertRecursiveInSortedListClassAux(data, current.next, flag);
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



    // delega en Node
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

    @Override
    public boolean find(T data) {
        return getPos(data) != -1;
    }


    // delete resuelto todo en la clase SortedLinkedList, iterativo
    @Override
    public boolean remove(T data) {

        // COMPLETAR!!!
        return false;
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
        // COMPLETAR
        return null;
    }


    @Override
    public T getMax() {
        // COMPLETAR
        return null;
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
    }

    public static void main(String[] args) {
        SortedLinkedList<Integer> l = new SortedLinkedList<>();
        l.insert1(30);
        l.insert1(80);
        l.insert1(40);
        l.insert1(40);

        SortedLinkedList<Integer> l2 = new SortedLinkedList<>();
        l2.insertIterative(30);
        l2.insertIterative(80);
        l2.insertIterative(40);
        l2.insertIterative(40);

        SortedLinkedList<Integer> l3 = new SortedLinkedList<>();
        System.out.println(l3.insertRecursiveInSortedListClass(30));
        System.out.println(l3.insertRecursiveInSortedListClass(80));
        System.out.println(l3.insertRecursiveInSortedListClass(40));
        System.out.println(l3.insertRecursiveInSortedListClass(40));

        System.out.println("-----------------------------------------------");

        SortedLinkedList<Integer> l4 = new SortedLinkedList<>();
        System.out.println(l4.insertRecursiveInNodeClass(30));
        System.out.println(l4.insertRecursiveInNodeClass(80));
        System.out.println(l4.insertRecursiveInNodeClass(40));
        System.out.println(l4.insertRecursiveInNodeClass(40));

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