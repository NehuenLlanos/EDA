package core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BST<T extends Comparable<? super T>> implements BSTreeInterface<T>{
    private Node root;
    private Traversal aTraversal;
    public void insert(T myData) {
        if(myData == null){
            return;
        }

        if(root == null) {
            root = new Node<>(myData);
        }else{
            root.insert(myData);
        }
    }

    public void preOrder(){
        return;
    }

    public void postOrder(){
        return;
    }

    public void inOrder(){
        return;
    }

    public NodeTreeInterface<T> getRoot(){
        return root;
    }

    public int getHeight(){
        int[] count = new int[1];
        count[0] = 0;
        return getHeightRec(root, count);
    }
    private int getHeightRec(Node current, int[] count){
        if(current == null){
            return 0;
        }
        int right = getHeightRec(current.right, count);
        int left = getHeightRec(current.left, count);
        if(right > left){
            return 1 + right;
        }
        return 1 + left;
    }

    public void delete(T myData) {
        if (myData == null)
            throw new RuntimeException("element cannot be null");


        if (root != null)  //delegates in Node class
            root= root.delete(myData);
    }

    private class Node<T extends Comparable<? super T>> implements NodeTreeInterface<T>{
        private Node right;
        private Node left;
        private T data;

        public Node(T data){
            this.data = data;
        }

        public T getData(){
            return data;
        }

        public NodeTreeInterface<T> getLeft(){
            return left;
        }

        public NodeTreeInterface<T> getRight(){
            return right;
        }

        public void insert(T myData){
            if(myData.compareTo(data) > 0){
                if(right == null){
                    right = new Node<>(myData);
                }else{
                    right.insert(myData);
                }
            }else{
                if(left == null){
                    left = new Node<>(myData);
                }else{
                    left.insert(myData);
                }
            }
        }
        private Node<T> delete(T myData) {
            if (myData.compareTo(this.data) < 0) {
                if (left != null) {
                    left= left.delete(myData);
                }
                return this;
            }

            if (myData.compareTo(this.data) > 0) {  //greater
                if (right != null) {
                    right= right.delete(myData);
                }
                return this;
            }

            // found!
            //lexicographically replacement
            if (left == null) {
                return right;
            }

            if (right == null) {
                return left;
            }

            T replacement= lexiAdjacent(left);
            this.data= replacement;
            left= left.delete(this.data);
            return this;

        }

        private T lexiAdjacent(Node candidate) {
            Node auxi = candidate;
            // look forward
            while(auxi.right != null) {
                auxi = auxi.right;
            }
            return (T) auxi.data;
        }
    }

    public void setTraversal(Traversal aTraversal){
        this.aTraversal = aTraversal;
    }

    public Iterator<T> iterator(){
        switch(aTraversal){
            case BYLEVELS: return new BSTByLevelIterator();
            case INORDER: return new BSTInOrderIterator();
        }
        throw new RuntimeException("Invalid traversal parameter");
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

    public static void main(String[] args) {
        BST<Integer> myTree = new BST<>();
        myTree.insert(120);
        myTree.insert(100);
        myTree.insert(200);
        myTree.insert(20);
        myTree.insert(100);
        myTree.insert(100);
        myTree.setTraversal(Traversal.BYLEVELS);
        Iterator<Integer> it = myTree.iterator();
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());


    }
}
