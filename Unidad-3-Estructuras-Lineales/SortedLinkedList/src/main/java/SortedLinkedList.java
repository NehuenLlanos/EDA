import java.util.Iterator;

// lista simplemente encadenada, no acepta repetidos (false e ignora) ni nulls (exception)
public class SortedLinkedList<T extends Comparable<? super T>> implements SortedListService<T>{
    private Node root;
    // iterativa
    //	@Override
    public boolean insert1(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        Node prev = null;
        Node current = root;
        while( current != null && current.data.compareTo(data) <0    ) {
            // avanzo
            prev= current;
            current= current.next;
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
        root = insertRec(data, root, rta);

        return rta[0];
    }
    private Node insertRec(T data, Node current, boolean[] rta ) {
        // repetido?
        if ( current!= null && current.data.compareTo(data) == 0) {
            System.err.println(String.format("Insertion failed %s", data));
            rta[0]= false;
            return current;
        }
        if( current != null && current.data.compareTo(data) < 0) {
            // avanzo
            current.next = insertRec(data, current.next, rta);
            return current;
        }
        // estoy en parado en el lugar a insertar
        rta[0]= true;
        return new Node(data, current);

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
        root = root.insert( data,  rta);

        return rta[0];
    }

    @Override
    public boolean find(T data) {
        return getPos(data) != -1;
    }

    // Versión iterativa resuelto totalmente desde SortedLinkedList
    @Override
    public boolean remove(T data) {
        if(data == null){
            throw new IllegalArgumentException("data cannot be null");
        }
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
            }else{
                // En este caso tengo mas de un nodo y por ende al previo.next le asigno el current.next
                prev.next = current.next;
            }
            // En ambos casos retorno true porque borre el elemento
            return true;
        }
        // Si llegue aca es porque el current es null, es decir, llegue al final de mi lista o el data
        // no es igual al current.data lo que genera que no encuentre mi nodo a borrar. Entonces devuelvo
        // false porque no se borra ningun nodo.
        return false;
    }

    // Versión recursiva resuelto totalmente desde SortedLinkedList
    public boolean remove2(T data) {
        if(data == null){
            throw new IllegalArgumentException("data cannot be null");
        }
        if(root == null){
            return false;
        }
        boolean[] rta= new boolean[1];
        root = removeRec(root, data, rta);
        return rta[0];
    }

    private Node removeRec(Node root, T data, boolean[] rta){
        // Si root es igual a null y data es igual a current.data retorno el root.next
        if(root != null && data.compareTo(root.data) == 0){
            rta[0] = true;
            return root.next;
        }
        if(root != null && data.compareTo(root.data) > 0){
            root.next = removeRec(root.next, data, rta);
            return root;
        }
        // root == null
        // root != null && data.compareTo(root.data) < 0
        rta[0] = false;
        return root;
    }

    // Version que “delega” en Node el método remove, para lo cual invoca dicho
    // método a través del node root.
    public boolean remove3(T data) {
        if(data == null){
            throw new IllegalArgumentException("data cannot be null");
        }
        if (root == null) {
            return false;
        }
        boolean[] rta= new boolean[1];
        root = root.remove(data, rta);
        return rta[0];
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
            return null;
        }
        return root.data;
    }


    @Override
    public T getMax() {
        if(root != null){
            Node ans = root;
            while(ans.next != null){
                ans = ans.next;
            }
            return ans.data;
        }
        return null;
    }

    @Override
    public Iterator<T> iterator(){
        return new Iterator<T>() {
            Node current = root;
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
                Node ans = current;
                current = current.next;
                return ans.data;
            }
        };
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
            if ( this.data.compareTo(data) == 0 ) {
                System.err.println(String.format("Insertion failed %s", data));
                rta[0]= false;
                return this;
            }
            if( this.data.compareTo(data) <0) {
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
        private Node remove(T data, boolean[] rta){
            if(this.data.compareTo(data) == 0){
                rta[0] = true;
                return this.next;
            }
            if(this.data.compareTo(data) < 0){
                if(next == null){
                    rta[0] = false;
                    return null;
                }
                next = next.remove(data, rta);
                return this;
            }
            // this.data.compaareTo(data) > 0
            rta[0] = false;
            return this;
        }
    }

    public static void main(String[] args) {
//        SortedLinkedList<Integer> l = new SortedLinkedList<>();
//        l.insert1(10);
//        l.insert1(20);
//        l.insert1(30);
//        l.insert2(40);
//        l.insert2(50);
//        l.insert2(60);
//        l.insert(70);
//        l.insert(80);
//        System.out.println("LikedList");
//        l.dump();
//        System.out.println("Max" + l.getMax());
//        System.out.println("Min" + l.getMin());
//        System.out.println("-----------------");
//        l.remove(30);
//        l.remove2(50);
//        l.remove3(70);
//        System.out.println("LikedList");
//        l.dump();
//        System.out.println("Tamano " + l.size());


        SortedLinkedList<String> l = new SortedLinkedList<>();
        System.out.println(l.insert("hola"));
        System.out.println(l.insert("tal"));
        System.out.println(l.insert("ah"));
        System.out.println(l.insert("veo"));
        System.out.println(l.insert("bio"));
        System.out.println(l.insert("tito"));
        System.out.println(l.insert("aca"));
        // la lista deberia contener: aca ah bio hola tal tito veo
        System.out.println(l.size() );
        for (String s : l) {
            System.out.println(s);
        }
        System.out.println("\n");
        l.remove("tito");
        for (String s : l) {
            System.out.println(s);
        }
//        Es decir, deberá poder participar de “foreach” readonly (solo para leer la información a
//                través del cursor).
    }

//    public static void main(String[] args) {
//        SortedLinkedList<String> l = new SortedLinkedList<>();
//        System.out.println("lista " +  (l.isEmpty()? "":"NO") + " vacia");
//        System.out.println();
//        System.out.println(l.insert("hola"));
//        l.dump();
//        System.out.println();
//        System.out.println("lista " +  (l.isEmpty()? "":"NO") + " vacia");
//        System.out.println();
//        System.out.println(l.insert("tal"));
//        l.dump();
//        System.out.println();
//        System.out.println(l.insert("ah"));
//        l.dump();
//        System.out.println();
//        System.out.println(l.insert("veo"));
//        l.dump();
//        System.out.println();
//        System.out.println(l.insert("bio"));
//        l.dump();
//        System.out.println();
//        System.out.println(l.insert("tito"));
//        l.dump();
//        System.out.println();
//        System.out.println(l.insert("hola"));
//        l.dump();
//        System.out.println();
//        System.out.println(l.insert("aca"));
//        l.dump();
//        System.out.println();
//        System.out.println(l.size() );
//
//    }

}
