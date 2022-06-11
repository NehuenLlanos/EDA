package Ejercicio2;

import java.util.*;


public class MultiSortedLinkedList<T extends Comparable<? super T>> implements MultiListService<T> {
    private SuperNode root;
    private Map<String, SuperNode> listRoots = new HashMap<>();

    @Override
    public void insert(String listName, SortedListService<T> list ) {
    	// No se que debo hacer si la key esta dentro del mapa
        if(listRoots.containsKey(listName)){


        }
        Stack<T> listData = new Stack<>();
        Iterator<T> it = list.iterator();
        while(it.hasNext()){
            listData.push(it.next());
        }
        // Si no tengo elementos en esta lista tengo que insertarlos para poder darle al root un primer valor
        if(listRoots.size() == 0){
            SuperNode last = new SuperNode(listData.pop(), listName, null, null);
            SuperNode posterior = last;
            while(!listData.empty()){
                SuperNode aux = new SuperNode(listData.pop(), listName, posterior, posterior);
                posterior = aux;
            }
            root = posterior;
            listRoots.put(listName, root);
        }
        // Si tengo una lista dentro de mi multilista entonces tengo que hacer otra cosa.
        else{
            SuperNode posteriorInList = null;
            while(!listData.empty()){
                T aux = listData.pop();
                SuperNode globalPosterior = findGlobalPosterior(aux, root);
                SuperNode globalPrevious = findGlobalPrevious(aux, root);
                SuperNode newSuperNode = new SuperNode(aux, listName, globalPosterior, posteriorInList);
                globalPrevious.nextInML = newSuperNode;
                posteriorInList = newSuperNode;
            }
            listRoots.put(listName, posteriorInList);
        }

    }
    // Avanzo en la lista pero me encuentro con null en el ultimo
    private SuperNode findGlobalPosterior(T data, SuperNode current){
        if(current == null){
            return null;
        }
        if(current.data.compareTo(data) >= 0) {
            return current;
        }
        return findGlobalPosterior(data, current.nextInML);
    }

    private SuperNode findGlobalPrevious(T data, SuperNode current){
        SuperNode previous = null;
        while(current != null && current.data.compareTo(data) < 0){
            previous = current;
            current = current.nextInML;
        }
        return previous;
    }
    private final class SuperNode {
        private T data;
        private String listName;
        private SuperNode nextInML;
        private SuperNode nextInList;

        private SuperNode(T data) {
            this.data= data;
        }

        private SuperNode(T data, String lName, SuperNode theNextInML, SuperNode theNextInlist) {
            this.data= data;
            this.listName = lName;
            this.nextInML = theNextInML;
            this.nextInList= theNextInlist;
        }

        public String toString() {
            return "List: [" +  listName + "] Data: " + data.toString();
        }
       
    }

    public void dump() {
        SuperNode current = root;
        while( current != null ){
            System.out.println( current );
            current = current.nextInML;
        }
    }
    
    public void dump(String listName) {
        SuperNode current = listRoots.get( listName );
        while( current != null ){
        	System.out.println( current );
           current = current.nextInList;
        }
    }
    

    public static void main(String[] args) {
        SortedLinkedList<Integer> l1 = new SortedLinkedList<>();
        l1.insert(4);
        l1.insert(8);
        l1.insert(4);

        SortedLinkedList<Integer> l2 = new SortedLinkedList<>();
        l2.insert(5);
        l2.insert(11);

        MultiListService<Integer> ml = new MultiSortedLinkedList<>();

        ml.insert( "l1", l1);
        ml.insert( "l2", l2);
        
        System.out.println("global");
        ml.dump();
        
        System.out.println("\nsolo l1");
        ml.dump("l1");
        
        System.out.println("\nsolo l2");
        ml.dump("l2");
        

 
    }

}