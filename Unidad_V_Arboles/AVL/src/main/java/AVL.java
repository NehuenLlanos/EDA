import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

// acepta repetidos
public class AVL<T extends Comparable<? super T>> implements BSTreeInterface<T> {

    private Node root;

    private Traversal aTraversal = Traversal.BYLEVELS;

    public void printHierarchy() {
        printHierarchy("",  root);
    }

    // preorder encubierto
    public void printHierarchy(String initial,  Node current) {

        if (current == null) {
            System.out.println(initial +  "└── " + "null");
            return;
        }

        System.out.println(initial + "└── " + current);

        if ( current.left != null || current.right != null) {
            printHierarchy(initial + "    " ,  current.left) ;
            printHierarchy(initial + "    " ,   current.right) ;

        }

    }

    private Node rightRotate(Node pivote) {

        Node newRoot = pivote.left;
        pivote.left= newRoot.right;
        newRoot.right = pivote;


        // Update heights
        pivote.height = Math.max(pivote.left==null?-1:pivote.left.height, pivote.right==null?-1:pivote.right.height) + 1;
        newRoot.height = Math.max(newRoot.left==null?-1:newRoot.left.height, newRoot.right==null?-1:newRoot.right.height) + 1;

        return newRoot;
    }

    private Node leftRotate(Node pivote) {

        Node newRoot = pivote.right;
        pivote.right= newRoot.left;
        newRoot.left = pivote;

        //  Update heights
        pivote.height = Math.max(pivote.left==null?-1:pivote.left.height, pivote.right==null?-1:pivote.right.height) + 1;
        newRoot.height = Math.max(newRoot.left==null?-1:newRoot.left.height, newRoot.right==null?-1:newRoot.right.height) + 1;

        return newRoot;
    }

    private int getBalance(Node currentNode) {
        if (currentNode == null)
            return 0;

        return (currentNode.left==null?-1:currentNode.left.height)-
                (currentNode.right==null?-1:currentNode.right.height);

    }

    @Override
    public void insert(T myData) {
        if (myData == null)
            throw new RuntimeException("element cannot be null");

        root= insert(root, myData);

    }

    private Node insert(Node currentNode, T myData) {
        if (currentNode == null)
            return new Node(myData);

        if (myData.compareTo(currentNode.data) <= 0)
            currentNode.left= insert(currentNode.left, myData);
        else
            currentNode.right= insert(currentNode.right, myData);

        // agregado para AVL
        int i = currentNode.left==null?-1:currentNode.left.height;
        int d = currentNode.right==null?-1:currentNode.right.height;
        currentNode.height = 1 + Math.max(i, d);

        int balance = getBalance(currentNode);

        // Op: Left left
        if (balance > 1 && myData.compareTo(currentNode.left.data) <= 0)
            return rightRotate(currentNode);

        // Op: Right Right
        if (balance < -1 && myData.compareTo(currentNode.right.data) > 0)
            return leftRotate(currentNode);

        // Op: Left Right
        if (balance > 1 && myData.compareTo(currentNode.left.data) > 0) {
            currentNode.left = leftRotate(currentNode.left);
            return rightRotate(currentNode);
        }

        // Op: Right Left
        if (balance < -1 && myData.compareTo(currentNode.right.data) <= 0) {
            currentNode.right = rightRotate(currentNode.right);
            return leftRotate(currentNode);
        }

        return currentNode;
    }

    public boolean search(T myData){
        boolean found = false;
        Node current = root;
        while(current != null) {
            if(myData.compareTo(current.data) == 0) {
                return true;
            }else if(myData.compareTo(current.data) > 0){
                current = current.right;
            }else{
                current = current.left;
            }
        }
        return found;
    }

    @Override
    public void preOrder() {
        preOrder(root);
        System.out.println();
    }

    private void preOrder(Node currentNode) {
        if (currentNode != null) {
            System.out.print(currentNode.data + " ");
            preOrder(currentNode.left);
            preOrder(currentNode.right);
        }
    }

    @Override
    public void postOrder() {
        postOrder(root);
        System.out.println();
    }

    private void postOrder(Node currentNode) {
        if (currentNode != null) {
            postOrder(currentNode.left);
            postOrder(currentNode.right);
            System.out.print(currentNode.data + " ");
        }
    }

    @Override
    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder(Node currentNode) {
        if (currentNode != null) {
            inOrder(currentNode.left);
            System.out.print(currentNode.data  + " ");
            inOrder(currentNode.right);
        }
    }

    // version iterativa
    public void inOrderIter() {
        Stack<NodeTreeInterface<T>> stack=  new Stack<>();

        NodeTreeInterface<T> current = root;
        while ( ! stack.isEmpty() || current != null) {
            if (current != null) {
                stack.push(current);
                current= current.getLeft();
            }
            else {
                NodeTreeInterface<T> elementToProcess = stack.pop();
                System.out.print(elementToProcess.getData() + "\t");
                current= elementToProcess.getRight();
            }
        }
    }

    @Override
    public NodeTreeInterface<T> getRoot() {
        return root;
    }

    @Override
    public int getHeight() {
        if (root == null)
            return -1;

        return root.height;
    }

    @Override
    public boolean contains(T myData) {
        throw new RuntimeException("implementar !!!");
    }

    @Override
    public T  getMax() {
        throw new RuntimeException("implementar !!!");
    }

    @Override
    public T  getMin() {
        throw new RuntimeException("implementar !!!");
    }

    // es iterativa
    @Override
    public void printByLevels() {
        if (root == null) {
            return;
        }

        // create an empty queue and enqueue the root node
        Queue<NodeTreeInterface<T>> queue = new LinkedList<>();
        queue.add(root);

        NodeTreeInterface<T >currentNode;

        // hay elementos?
        while (!queue.isEmpty())
        {
            currentNode = queue.remove();
            System.out.print(currentNode + " ");

            if (currentNode.getLeft() != null) {
                queue.add(currentNode.getLeft());
            }

            if (currentNode.getRight() != null) {
                queue.add(currentNode.getRight());
            }
        }

    }

    @Override
    public void delete(T myData) {
        throw new RuntimeException("Not implemented in this AVL version");
    }

    @Override
    public Iterator<T> iterator() {
        switch (aTraversal) {
            case BYLEVELS: return new BSTByLevelIterator();
            case INORDER: return new BSTInOrderIterator();
        }
        throw new RuntimeException("invalid traversal parameter");

    }

    class BSTInOrderIterator implements Iterator<T> {
        Stack<NodeTreeInterface<T>> stack;
        NodeTreeInterface<T> current;

        public BSTInOrderIterator() {
            stack= new Stack<>();
            current= root;

        }

        @Override
        public boolean hasNext() {
            return ! stack.isEmpty() || current != null;
        }

        @Override
        public T next() {
            while(current != null) {
                stack.push(current);
                current= current.getLeft();
            }

            NodeTreeInterface<T> elementToProcess= stack.pop();
            current= elementToProcess.getRight();
            return elementToProcess.getData();
        }
    }

    class BSTByLevelIterator implements Iterator<T>{

        private Queue<NodeTreeInterface<T>> queue;

        private BSTByLevelIterator() {
            // create an empty queue and enqueue the root node
            queue = new LinkedList<>();

            if (root!= null)
                queue.add(root);
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public T next() {
            NodeTreeInterface<T> currentNode = queue.remove();

            if (currentNode.getLeft() != null) {
                queue.add(currentNode.getLeft());
            }

            if (currentNode.getRight() != null) {
                queue.add(currentNode.getRight());
            }

            return currentNode.getData();
        }
    }

    public void setTraversal(Traversal traversal) {
        this.aTraversal = traversal;

    }

    class Node implements NodeTreeInterface<T> {

        private T data;
        private Node left;
        private Node right;

        // para AVL
        private int height;

        @Override
        public String toString() {
            return data + " h=" + height;
        }

        public Node(T myData) {
            this.data= myData;

            this.height= 0;
        }


        public T getData() {
            return data;
        }

        public NodeTreeInterface<T> getLeft() {
            return left;
        }

        public NodeTreeInterface<T> getRight() {
            return right;
        }


    }

    public static void main(String[] args) {

        AVL<Integer>  myTree= new AVL<>();
        myTree.insert(1);
        myTree.printHierarchy();
        System.out.println();

        myTree.insert(2);
        myTree.printHierarchy();
        System.out.println();

        myTree.insert(4);
        myTree.printHierarchy();
        System.out.println();

        myTree.insert(7);
        myTree.printHierarchy();
        System.out.println();

        myTree.insert(15);
        myTree.printHierarchy();
        System.out.println();


        myTree.insert(3);
        myTree.printHierarchy();
        System.out.println();

        myTree.insert(10);
        myTree.printHierarchy();
        System.out.println();

        myTree.insert(17);
        myTree.printHierarchy();
        System.out.println();

        myTree.insert(19);
        myTree.printHierarchy();
        System.out.println();

        myTree.insert(16);
        myTree.printHierarchy();
        System.out.println();

        System.out.println(myTree.search(19));
        System.out.println(myTree.search(22));
/*
		System.out.println("\n\nInOrder\n");
		myTree.inOrder();

		System.out.println("\n\nInOrderIter\n");
		myTree.inOrderIter();

		System.out.println("\n\nPreOrder\n");
		myTree.preOrder();

		System.out.println("\n\nPostOrder\n");
		myTree.postOrder();


		System.out.println("\n\nByLevels\n");
		myTree.printByLevels();


		System.out.println("\n\nDefault Traversal…(by levels)\n");
		myTree.forEach( t-> System.out.print(t + " ") );

		myTree.setTraversal(Traversal.INORDER);
		System.out.println("\n\nUna vez más INORDER\n");
		myTree.forEach( t-> System.out.print(t + " ") );

		myTree.setTraversal(Traversal.BYLEVELS);
		System.out.println("\n\nUna vez más BYLEVELS\n");
		myTree.forEach( t-> System.out.print(t + " ") );
*/
    }


}
