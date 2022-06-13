package Ejercicio2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ejercicio2 {

    static public void Sorpresa(ArrayList<Integer> input){
        Map<Integer, Integer> elements = new HashMap<>();
        // Recorro los elementos del array desordenado
        // Realizo dos comparaciones por elemento entonces la complejidad es 2N
        for(Integer element : input){
            if(elements.containsKey(element)){
                int count = elements.get(element) + 1;
                elements.put(element, count);
            }else{
                elements.put(element, 1);
            }
        }
        // Realizo dos comparaciones por elemento entonces la complejidad es 2N
        for(Map.Entry<Integer, Integer> entry : elements.entrySet()){
            if(elements.containsKey(entry.getValue())){
                System.out.println(entry.getKey());
            }
        }
        // La complejidad total es 2N + 2N = 4N entonces la complejidad es O(N)
    }

    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(10);
        arrayList.add(5);
        arrayList.add(5);
        arrayList.add(5);
        arrayList.add(20);
        arrayList.add(30);
        arrayList.add(3);
        arrayList.add(10);
        arrayList.add(5);
        arrayList.add(10);
        arrayList.add(20);
        arrayList.add(2);
        Ejercicio2.Sorpresa(arrayList);
    }
}

