public class CompactIndex implements IndexService{
    public static void main(String[] args) {
        IndexService indexService = new CompactIndex();
        System.out.println(indexService.occurrences("hola"));
        indexService.initialize(new String[]{"hola", "aramco", "ielo", "cacona","hola", "zina"});
        System.out.println(indexService.occurrences("hola"));
        indexService.insert("orangutan");
        System.out.println(indexService.occurrences("orangutan"));
    }
    private IndexCount[] elements = new IndexCount[0];
    private final int chunkSize = 4;
    private int size = 0;
    // Estructura para guardar la cantidad de ocurrencias de cada índice
    class IndexCount {
        String index;
        long count;
        public IndexCount(String index) {
            this.index = index;
            this.count = 1;
        }
    }
    @Override
    public void initialize(String[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException("elements no puede ser null");
        }
        this.elements = new IndexCount[0];
        this.size = 0;

        for(String element : elements) {
            insert(element);
        }
    }
    @Override
    public void insert(String key) {
        int index = binarySearch(key);
        if(index != -1){
            elements[index].count++;
            return;
        }
        index = getClosestPositionWrapper(0, elements.length, key);
        if (elements.length == size) {
            crecer();
        }
        for(int i = size ; i > index ; i--){
            elements[i] = elements[i-1];
        }
        size++;
        elements[index] = new IndexCount(key);
    }
    @Override
    public long occurrences(String key) {
        int index = binarySearch(key);
        if(index == -1) {
            return 0;
        }
        return elements[index].count;
    }
    /* Binary Search */
    private int binarySearch(String key){
        int left = 0;
        int right = size - 1;
        while(left <= right){
            int mid = (left + right) / 2;
            if(elements[mid].index.compareTo(key) > 0){
                right = mid - 1;
            }else if(elements[mid].index.compareTo(key) < 0){
                left = mid + 1;
            }else {
                return mid;
            }
        }
        return -1;
    }

    /* ClosestPosition */
    private int getClosestPositionWrapper(int l, int r, String key){
        // Si el primer elemento del array es mayor o igual a la key entonces
        // debo devolver la posicion cero.
        if(size == 0 || elements[0].index.compareTo(key) >= 0){
            return 0;
        }
        // Si el ultimo elemento del array es menor que la key entonces debo retornar
        // la posicion siguiente a la ultima porque es donde se debe colocar la key.
        if(elements[size-1].index.compareTo(key) < 0){
            return size;
        }
        return getClosestPosition(l, r, key);
    }
    private int getClosestPosition(int l, int r, String key) {
        // Calculo el mid.
        int mid = l + (r - l) / 2;
        // Si array[mid] es igual que la key entonces debo verificar que en las
        // posiciones inferiores no exista un elemento que sea igual a key pues
        // puede pasar que este repetido.
        if(elements[mid].index.compareTo(key) == 0){
            return getClosestPosition(l, mid, key);
        }
        // Si array[mid] es mayor que key entonces debo buscar es la posicion inferior
        // del array.
        if(elements[mid].index.compareTo(key) > 0){
            // Para buscar en la parte inferior le mantengo el l y hago que r = mid.
            return getClosestPosition(l, mid, key);
        }
        // Si array[mid] es menor que key entonces primero que nada verfico que el siguiente
        // no sea mayor o igual. Porque si es mayor o igual entonces encontre la posicion a
        // devolver que sera mid+1.
        if(elements[mid+1].index.compareTo(key) >= 0){
            return mid+1;
        }
        // Si array[mid+1] es menor que key entonces debo buscar en la parte superior.
        // Para poder hacer esto mantengo el r y hago que l = mid.
        return getClosestPosition(mid, r, key);
    }

    @Override
    public String getMax() {
        if (size == 0) {
            return null;
        }
        return elements[size-1].index;
    }
    @Override
    public String getMin() {
        if (size == 0) {
            return null;
        }
        return elements[0].index;
    }
    private void crecer() {
        IndexCount[] newElements = new IndexCount[size + chunkSize];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }
}
