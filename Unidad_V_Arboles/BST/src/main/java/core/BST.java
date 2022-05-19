package core;

import java.util.*;

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

    public void insertInBST(T myData){
        if(myData == null){
            return;
        }
        if(root == null){
            root = new Node<>(myData);
            return;
        }
        Node<T> current = root;
        Node<T> previous = current;
        int flag = 0;
        while(current != null){
            previous = current;
            if(myData.compareTo(current.getData()) > 0){
                current = current.right;
                flag = 1;
            }else{
                current = current.left;
                flag = 2;
            }
        }
        if(flag == 1){
            previous.right = new Node<>(myData);
        }else if (flag == 2){
            previous.left = new Node<>(myData);
        }
    }

    public void preOrder(){
        preOrderRec(root);
    }
    private void preOrderRec(Node<T> current){
        if(current == null){
            return;
        }
        System.out.println(current.getData());
        preOrderRec(current.left);
        preOrderRec(current.right);
    }

    public void postOrder(){
        postOrderRec(root);
    }
    private void postOrderRec(Node<T> current){
        if(current == null) {
            return;
        }
        postOrderRec(current.left);
        postOrderRec(current.right);
        System.out.println(current);
    }

    public void inOrder(){
        inOrderRec(root);
    }
    private void inOrderRec(Node<T> current){
        if(current == null) {
            return;
        }
        inOrderRec(current.left);
        System.out.println(current);
        inOrderRec(current.right);
    }

    public boolean contains(T myData){
        Node<T> current = root;
        while(current != null){
            if(myData.compareTo(current.data) == 0){
                return true;
            }
            if(myData.compareTo(current.data) > 0){
                current = current.right;
            }else{
                current = current.left;
            }
        }
        return false;
    }

    public T getMax(){
        if(root == null){
            return null;
        }
        Node<T> current = root;
        while(current.right != null){
            current = current.right;
        }
        return current.data;
    }

    public T getMin(){
        if(root == null){
            return null;
        }
        Node<T> current = root;
        while(current.left != null){
            current = current.left;
        }
        return current.data;
    }

    public void printByLevels(){
        if(root == null){
            System.out.println(" ");
            return;
        }
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            Node<T> current = queue.poll();
            System.out.print(current + " ");
            if(current.left != null){
                queue.add(current.left);
            }
            if(current.right != null){
                queue.add(current.right);
            }
        }
    }

    public int getOcurrences(T element){
        if(root == null || element == null){
            return 0;
        }
        int ans = 0;
        Node<T> current = root;
        while(current != null){
            if(current.data.compareTo(element) == 0){
                ans++;
            }
            if(element.compareTo(current.data) > 0){
                current = current.right;
            }else{
                current = current.left;
            }
        }
        return ans;
    }

    public T getCommonNode(T element1, T element2){
        if(element1.compareTo(element2) == 0 || root == null){
            return null;
        }
        Deque<T> queue1 = new LinkedList<>();
        Deque<T> queue2 = new LinkedList<>();
        boolean flag = queueCreation(root, element1, queue1);
        if(!flag){
            return null;
        }
        flag = queueCreation(root, element2, queue2);
        if(!flag){
            return null;
        }
        T aux1 = queue1.pollFirst();
        T aux2 = queue2.pollFirst();
        T ans = null;
        while(aux1 != null && aux2 != null && aux1 == aux2){
            ans = aux1;
            aux1 = queue1.pollFirst();
            aux2 = queue2.pollFirst();
        }
        return ans;
    }

    private boolean queueCreation(Node<T> root, T element, Deque<T> queue){
        Node<T> current = root;
        boolean flag = false;
        while(current != null && !flag){
            queue.addLast(current.data);
            if(element.compareTo(current.data) == 0){
                flag = true;
            }else if(element.compareTo(current.data) > 0){
                current =  current.right;
            }else{
                current = current.left;
            }
        }
        return flag;
    }

    public T getCommonNodeWithRepeated(T element1, T element2){
        if(root == null || element1 == null || element2 == null){
            return null;
        }
        if(element1 != element2){
            return getCommonNode(element1, element2);
        }
        Node<T> current = queueCreationWithRep(root, element1);
//        Node<T> current = root;
//        boolean flag = false;
//        while(current != null && !flag){
//            if(element1.compareTo(current.data) == 0){
//                flag = true;
//            }else if(element1.compareTo(current.data) > 0){
//                current =  current.right;
//            }else{
//                current = current.left;
//            }
//        }
        if(current == null){
            return null;
        }
        // flag = false;
        current = current.left;
        current = queueCreationWithRep(current, element2);
//        while(current != null && !flag){
//            if(element1.compareTo(current.data) == 0){
//                flag = true;
//            }else if(element1.compareTo(current.data) > 0){
//                current =  current.right;
//            }else{
//                current = current.left;
//            }
//        }
        if(current == null){
            return null;
        }
        return element1;
    }

    private Node<T> queueCreationWithRep(Node<T> current, T element){
        boolean flag = false;
        while(current != null && !flag){
            if(element.compareTo(current.data) == 0){
                flag = true;
            }else if(element.compareTo(current.data) > 0){
                current =  current.right;
            }else{
                current = current.left;
            }
        }
        if(!flag){
            return null;
        }
        return current;
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

        public String toString(){
            return data.toString();
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
            stack = new Stack<>();
            current = root;

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
        myTree.insertInBST(120);
        myTree.insertInBST(100);
        myTree.insertInBST(200);
        myTree.insertInBST(20);
        myTree.insertInBST(100);
        myTree.insertInBST(100);
        myTree.setTraversal(Traversal.BYLEVELS);
        Iterator<Integer> it = myTree.iterator();
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println("-------------PREORDER------------");
        myTree.preOrder();
        System.out.println("-------------POSTORDER------------");
        myTree.postOrder();
        System.out.println("-------------INORDER------------");
        myTree.inOrder();
        System.out.println("-------------CONTAINS------------");
        System.out.println(myTree.contains(20));
        System.out.println(myTree.contains(120));
        System.out.println(myTree.contains(30));
        System.out.println("-------------GETMAX------------");
        System.out.println(myTree.getMax());
        System.out.println("-------------GETMIN------------");
        System.out.println(myTree.getMin());
        System.out.println("-------------PRINTBYLEVELS------------");
        myTree.printByLevels();
        System.out.println();
        myTree.setTraversal(Traversal.BYLEVELS);
        System.out.println("-------------PRINTBYLEVELSWITHITERATOR------------");
        for (Integer data : myTree) {
            System.out.print(data + " ");
        }
        System.out.println();
        System.out.println("-------------PRINTBYLEVELSWITHITERATORV2------------");
        for (Integer data : myTree) {
            System.out.print(data + " ");
        }
        myTree.setTraversal(Traversal.INORDER);
        System.out.println();
        System.out.println("-------------PRINTINORDERWITHITERATOR------------");
        for (Integer data : myTree) {
            System.out.print(data + " ");
        }
        System.out.println();
        System.out.println("-------------PRINTINORDERWITHITERATORV2------------");
        for (Integer data : myTree) {
            System.out.print(data + " ");
        }
        System.out.println();
        System.out.println("-------------GETOCURRENCES------------");
        System.out.println(myTree.getOcurrences(100));
        System.out.println(myTree.getOcurrences(200));
          // POR SEPARADO
//        System.out.println("-------------GETCOMMONNODEWITHOUTREPS------------");
//
//        myTree.insert(5);
//        myTree.insert(70);
//        myTree.insert(30);
//        myTree.insert(35);
//        myTree.insert(20);
//        myTree.insert(40);
//        myTree.insert(80);
//        myTree.insert(90);
//        myTree.insert(85);
//
//        System.out.println(myTree.getCommonNode(80, 85));
//        System.out.println(myTree.getCommonNode(85, 92));
//        System.out.println(myTree.getCommonNode(40, 85));
//        System.out.println(myTree.getCommonNode(85, 85));

//        System.out.println("-------------GETCOMMONNODEWITHREPS------------");
//        myTree.insert(5);
//        myTree.insert(70);
//        myTree.insert(30);
//        myTree.insert(70);
//        myTree.insert(20);
//        myTree.insert(40);
//        myTree.insert(80);
//        myTree.insert(90);
//        myTree.insert(85);
//
//        System.out.println( myTree.getCommonNodeWithRepeated(40, 40));
//        System.out.println( myTree.getCommonNodeWithRepeated(0, 85));
//        System.out.println( myTree.getCommonNodeWithRepeated(70, 70));
//        System.out.println( myTree.getCommonNodeWithRepeated(40, 80));
//        System.out.println( myTree.getCommonNodeWithRepeated(85, 80));
    }
}
