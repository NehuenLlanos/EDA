public class TestIndexWithDuplicates{
    public static void main(String[] args) {
        IndexParametricService<Integer>  myIndex = new IndexWithDuplicates<>(Integer.class);
        Integer[] rta = myIndex.range(10, 50, true, true);

        myIndex.initialize(new Integer[] {100, 50, 30, 50, 80} );
        rta = myIndex.range(10, 50, true, true);


        IndexParametricService<String>  anIndex=  new  IndexWithDuplicates<>(String.class);
        String[] rta2 = anIndex.range("hola", "tal", true, true);

        anIndex.initialize( new String[] {"hola", "ha", "sii" });
        rta2 = anIndex.range("a", "b", true, true);
        rta2 = anIndex.range("a", "quizas", true, true);
    }

}

