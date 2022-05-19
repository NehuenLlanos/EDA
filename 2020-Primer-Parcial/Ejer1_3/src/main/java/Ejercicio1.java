public class Ejercicio1
{
    static public void main(String args[])
    {
        System.out.println("Primer dataset");
        System.out.println( "sorpresaV1= " + sorpresaV1( new int[] { 30, 20, 15, 80, 10, 20}, 6) );
        System.out.println( "sorpresaV2= " + sorpresaV2( new int[] { 30, 20, 15, 80, 10, 20}, 6) );
        System.out.println( "sorpresaV3= " + sorpresaV3( new int[] { 30, 20, 15, 80, 10, 20}, 6) );
        System.out.println("Segundo dataset");
        System.out.println( "sorpresaV1= " + sorpresaV1( new int[] { 30, 20, 15, 80, 10, 20}, 4) );
        System.out.println( "sorpresaV2= " + sorpresaV2( new int[] { 30, 20, 15, 80, 10, 20}, 4) );
        System.out.println( "sorpresaV3= " + sorpresaV3( new int[] { 30, 20, 15, 80, 10, 20}, 4) );
        System.out.println("Tercer dataset");
        int[] auxi= new int[100];
        auxi[0]= 30;
        auxi[1]= 20;
        auxi[2]= 10;
        auxi[3]= 120;
        auxi[4]= 140;
        auxi[5]= 150;
        auxi[6]= 150;
        System.out.println( "sorpresaV1= "+ sorpresaV1( auxi,7) ) ;
        System.out.println( "sorpresaV2= "+ sorpresaV2( auxi,7) ) ;
        System.out.println( "sorpresaV3= "+ sorpresaV3( auxi,7) ) ;
        System.out.println("Cuarto dataset");
        auxi= new int[100];
        auxi[0]= 20;
        auxi[1]= 30;
        auxi[2]= 60;
        auxi[3]= 70;
        auxi[4]= 50;
        auxi[5]= 40;
        System.out.println( "sorpresaV1= "+ sorpresaV1( auxi,6) ) ;
        System.out.println( "sorpresaV2= "+ sorpresaV2( auxi,6) ) ;
        System.out.println( "sorpresaV3= "+ sorpresaV3( auxi,6) ) ;
        System.out.println("Quinto dataset");
        System.out.println( "sorpresaV1= " + sorpresaV1( new int[] { 30, 20, 15, 80, 10, 20}, 8) );
        System.out.println( "sorpresaV2= " + sorpresaV2( new int[] { 30, 20, 15, 80, 10, 20}, 8) );
        System.out.println( "sorpresaV3= " + sorpresaV3( new int[] { 30, 20, 15, 80, 10, 20}, 8) );
    }
    static public boolean sorpresaV1(int[] arreglo, int n)
    {
        if (arreglo== null || n < 0 || arreglo.length < n)
            throw new RuntimeException("bad parameter");
        for ( int rec= 0; rec < n - 1; rec++)
            for ( int iter = rec + 1 ; iter <= n - 1 ; iter++)
                if ( arreglo[rec] == arreglo[iter] )
                    return false;
        return true;
    }
    static public boolean sorpresaV2(int[] arreglo, int n)
    {
        if (arreglo== null || n < 0 || arreglo.length < n)
            throw new RuntimeException("bad parameter");
        for ( int rec= 0; rec <= n - 1; rec++)
            for ( int iter = 0 ; iter <= n - 1 ; iter++)
                if ( rec != iter && arreglo[rec] == arreglo[iter] )
                    return false;
        return true;
    }
    static public boolean sorpresaV3(int[] arreglo, int n)
    {
        if (arreglo== null || n < 0 || arreglo.length < n)
            throw new RuntimeException("bad parameter");

        quicksort(arreglo, n);

        for (int i = 0; i < n; i++) {
            int occurrenceToTheLeft = binarySearch(arreglo, 0, i-1, arreglo[i]);
            int occurrenceToTheRight = binarySearch(arreglo, i+1, n-1, arreglo[i]);
            if (occurrenceToTheLeft >= 0 || occurrenceToTheRight >= 0) {
                return false;
            }
        }
        return true;
    }

    // Left and right are INDEXES.
    private static int binarySearch(int[] array, int left, int right, int k) {
        while (left <= right) {
            int middle = (left + right) / 2;
            if (array[middle] > k) {
                right = middle - 1;
            } else if (array[middle] < k) {
                left = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    /*
     * ORDENACION POR QUICKSORT
     */
    private static void quicksort(int[] unsorted) {
        quicksort(unsorted, unsorted.length - 1);
    }


    private static void quicksort(int[] unsorted, int cantElements) {
        quicksortHelper(unsorted, 0, cantElements - 1);
    }

    private static void quicksortHelper(int[] unsorted, int leftPos, int rightPos) {
        if (rightPos <= leftPos)
            return;

        // tomamos como pivot el primero. Podria ser otro elemento
        int pivotValue= unsorted[leftPos];

        // excluimos el pivot del cjto.
        swap(unsorted, leftPos, rightPos);

        // particionar el cjto sin el pivot
        int pivotPosCalculated = partition(unsorted, leftPos, rightPos-1, pivotValue);


        // el pivot en el lugar correcto
        swap(unsorted, pivotPosCalculated, rightPos);

        // salvo unsorted[middle] el resto puede estar mal
        // pero cada particion es autonoma
        quicksortHelper(unsorted, leftPos, pivotPosCalculated - 1);
        quicksortHelper(unsorted, pivotPosCalculated + 1, rightPos );

    }

    static private int partition(int[] unsorted, int leftPos, int rightPos, int pivotValue) {
        while (leftPos <= rightPos) {
            while (leftPos <= rightPos && unsorted[leftPos] <= pivotValue) {
                leftPos++;
            }
            while (leftPos <= rightPos && unsorted[rightPos] >= pivotValue) {
                rightPos--;
            }
            if (leftPos <= rightPos) {
                swap(unsorted, leftPos++, rightPos--);
            }
        }
        return leftPos;
    }
    /*
     * FIN ORDENACION QUICKSORT
     *
     */

    static private void swap(int[] unsorted, int pos1, int pos2) {
        int auxi = unsorted[pos1];
        unsorted[pos1] = unsorted[pos2];
        unsorted[pos2] = auxi;
    }

}