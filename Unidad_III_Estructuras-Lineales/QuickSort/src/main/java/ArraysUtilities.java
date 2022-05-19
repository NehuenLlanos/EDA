public class ArraysUtilities {
	
	
	public static void main(String[] args) {
		int[] unsorted = new int[] {34, 10, 8, 60, 21, 17, 28, 30, 2, 70, 50, 15, 62, 40};
		quicksort( unsorted) ;
		
		for (int i : unsorted) {
			System.out.print(i + " ");
		}
	}
	

	/*
	 * ORDENACION POR QUICKSORT
	 */
	public static void quicksort(int[] unsorted) {
		quicksort (unsorted, unsorted.length-1);
	}
	
	
	public static void quicksort(int[] unsorted, int cantElements) {
		quicksortHelper (unsorted, 0, cantElements);
	}
	
	private static void quicksortHelper (int[] unsorted, int leftPos, int rightPos) {
		if (rightPos <= leftPos )
			return; 
		
		// tomamos como pivot el primero. Podria ser otro elemento
		int pivotValue= unsorted[leftPos];
		
		// excluimos el pivot del cjto.
		swap(unsorted, leftPos, rightPos);

		// particionar el cjto sin el pivot
		int pivotPosCalculated= partition(unsorted, leftPos, rightPos-1, pivotValue);
		
		
		// el pivot en el lugar correcto
		swap(unsorted, pivotPosCalculated, rightPos);
		
		
		// salvo unsorted[middle] todo puede estar mal
		// pero cada particion es autonoma
		quicksortHelper(unsorted, leftPos, pivotPosCalculated - 1);
		quicksortHelper(unsorted, pivotPosCalculated + 1, rightPos );

	}
	


	static public int partition(int[] unsorted, int leftPos, int rightPos, int pivotValue) {
		// COMPLETAR !!!!

		int i = leftPos;
		int j = rightPos;
		while(i <= j){
			if(unsorted[i] > pivotValue){
				if(unsorted[j] > pivotValue){
					j--;
				}else{
					swap(unsorted, i++, j--);
				}
			}else{
				i++;
			}
		}
		return i;
	}	
	

	/* 
	 * FIN ORDENACION QUICKSORT
	 * 	
	 */
	
	static private void swap(int[] unsorted, int pos1, int pos2) {
		int auxi= unsorted[pos1];
		unsorted[pos1]= unsorted[pos2];
		unsorted[pos2]= auxi;
	}
	
	

}
