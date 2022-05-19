public class URLfy {
    public static void main(String[] args) {
        URLfy urlfy = new URLfy();
//        char [] arreglo = new char[] { 'e', 's', ' ', 'u', 'n', ' ', 'e', 'j', 'e', 'm', 'p', 'l', 'o', '\0', '\0', '\0', '\0'};
//        urlfy.reemplazarEspacios(arreglo);
//        System.out.println(arreglo);
//        arreglo= new char [] {'a', ' ', 'b', ' ', 'c', ' ', 'd', ' ', 'e', ' ', 'f', ' ', 'g', ' ', 'h', 'o', 'l', 'a', '\0', '\0',
//                '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0'};
//        urlfy.reemplazarEspacios(arreglo);
//        System.out.println(arreglo);
//        arreglo= new char [] {' ', ' ', 'e', 's', 'p', 'a', 'c', 'i', 'o', 's', ' ', ' ', '\0', '\0', '\0', '\0', '\0', '\0',
//                '\0', '\0'};
//        urlfy.reemplazarEspacios(arreglo);
//        System.out.println(arreglo);
        char [] arreglo = new char[] { 'e', 's', ' ', ' ', 'e', 'j', 'e', 'm', 'p', 'l', 'o', '\0', '\0', '\0', '\0'};
         urlfy.reemplazarEspacios(arreglo);
    }
    // Complejidad espacial O(1)
    // Complejidad temporal O(N)
    // Siendo N la cantidad de elementos
    public void reemplazarEspacios(char [] arregloParam) {
//        char[] elems = new char[]{'%','2','0'};
//        boolean flag = false;
//        char[] aux = new char[];
//        int count = 0;
//        // Recorro todo el arreglo
//        for(int i = 0; i < arregloParam.length; i++){
//            if(flag == true && count < 3){
//                aux[count] = arregloParam[i];
//                arregloParam[i] = elems[++count];
//            }
//            // Cuando me encuentro con un espacio tengo que encender un flag
//            if(arregloParam[i] == ' '){
//                flag = true;
//                arregloParam[i] = elems[count];
//            }
//
//        }
    }
}
