public class TestGetClosestPosition {
    public static void main(String[] args) {
        int[] arreglo = new int[10];
        if(arreglo == null){
            System.out.println("SISISI");
        }
        int[] array = {8,9,10,10,10,13,16,19, 23};
        System.out.println(getClosestPosWrapper(array, 0, array.length -1, 10)); //2
        System.out.println(getClosestPosWrapper(array, 0, array.length -1, 7)); // 0
        System.out.println(getClosestPosWrapper(array, 0, array.length -1, 12)); //5
        System.out.println(getClosestPosWrapper(array, 0, array.length -1, 50)); //9
        int[] array2 = {8,8,8,10,10,13,16,23,23};
        System.out.println(getClosestPosWrapper(array2, 0, array2.length -1, 8)); //0
        System.out.println(getClosestPosWrapper(array2, 0, array2.length -1, 23)); //7
        int[] array3 = {8,8,8,9,10,13,16,23,23,23,23}; // 11 de largo
        System.out.println(getClosestPosWrapper(array3, 0, array3.length -1, 10)); //4
        System.out.println(getClosestPosWrapper(array3, 0, array3.length -1, 11)); //5
        System.out.println(getClosestPosWrapper(array3, 0, array3.length -1, 17)); //7
    }
    public static int getClosestPosWrapper(int[] array, int l, int r, int key){
        // Si el primer elemento del array es mayor o igual a la key entonces
        // debo devolver la posicion cero.
        if(array[0] >= key){
            return 0;
        }
        // Si el ultimo elemento del array es menor que la key entonces debo retornar
        // la posicion siguiente a la ultima porque es donde se debe colocar la key.
        if(array[array.length-1] < key){
            return array.length;
        }
        return getClosestPos(array, l, r, key);
    }
    public static int getClosestPos(int[] array, int l, int r, int key) {
        // Calculo el mid.
        int mid = l + (r - l) / 2;
        // Si array[mid] es igual que la key entonces debo verificar que en las
        // posiciones inferiores no exista un elemento que sea igual a key pues
        // puede pasar que este repetido.
        if(array[mid] == key){
            return getClosestPos(array, l, mid, key);
        }
        // Si array[mid] es mayor que key entonces debo buscar es la posicion inferior
        // del array.
        if(array[mid] > key){
            // Para buscar en la parte inferior le mantengo el l y hago que r = mid.
            return getClosestPos(array, l, mid, key);
        }
        // Si array[mid] es menor que key entonces primero que nada verfico que el siguiente
        // no sea mayor o igual. Porque si es mayor o igual entonces encontre la posicion a
        // devolver que sera mid+1.
        if(array[mid+1] >= key){
            return mid+1;
        }
        // Si array[mid+1] es menor que key entonces debo buscar en la parte superior.
        // Para poder hacer esto mantengo el r y hago que l = mid.
        return getClosestPos(array, mid, r,  key);
    }
}
