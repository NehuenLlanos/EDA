public class ProximityIndex {
    private String[] elements;
    private int size = 0;

    public static void main(String[] args) {
        String[] elements = new String[]{"Ana", "Carlos", "Juan", "Yolanda"};
        ProximityIndex index = new ProximityIndex();
        index.initialize(elements);
        System.out.println(index.search("Carlos", 2));// debe devolver  "Yolanda"
        System.out.println(index.search("Carlos", 0));//debe devolver  "Carlos"
        System.out.println(index.search("Carlos", 3));  //debe devolver  "Ana"
        System.out.println(index.search("Ana", 14));   //   debe devolver  "Juan"
        System.out.println(index.search("Ana", -2)); //→ "Juan"
        System.out.println(index.search("Ana", -17));   // → "Yolanda"
        System.out.println(index.search("Juan", -4)); //→ "Juan"
        System.out.println(index.search("Juan", -4)); //→ "Juan"
        System.out.println(index.search("Juan", -2));// → null
    }

    public void initialize(String[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException("elements no puede ser null");
        }
        
        for(int rec= 0; rec < elements.length-1; rec++) {
        	if (elements[rec].compareTo(elements[rec+1]) >= 0)
                throw new IllegalArgumentException("hay repetidos o no est� ordenado");
        }
        
        this.elements = elements;
        this.size = elements.length;

     }


    public String search(String element, int distance) {
        //TODO completar
        // ...
        int index = binarySearch(0, elements.length-1, element);
        if(index == -1){
            return null;
        }
        int resto = distance % elements.length;
        int finalIndex = index + resto;
        if(distance > 0){
            if(finalIndex < elements.length){
                return elements[finalIndex];
            }
            finalIndex = resto - (elements.length - index);
        }else{
            if(finalIndex >= 0){
                return elements[finalIndex];
            }
            finalIndex = elements.length + resto;
        }
        return elements[finalIndex];
    }

    // Left and right are INDEXES.
    private int binarySearch(int left, int right, String element) {
        while (left <= right) {
            int middle = (left + right) / 2;
            if (elements[middle].compareTo(element) > 0) {
                right = middle - 1;
            } else if (elements[middle].compareTo(element) < 0) {
                left = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }



}
