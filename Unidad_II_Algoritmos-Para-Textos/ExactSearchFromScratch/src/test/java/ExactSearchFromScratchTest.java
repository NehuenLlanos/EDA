public class ExactSearchFromScratchTest {
    public static void main(String[] args) {
        char[] target= "abracadabra".toCharArray();
        char[] query= "ra".toCharArray();
        System.out.println( ExactSearchFromScratch.indexOf( query, target) );  //2

        target= "abracadabra".toCharArray();
        query= "abra".toCharArray();
        System.out.println(ExactSearchFromScratch.indexOf( query, target) );  //0

        target= "abracadabra".toCharArray();
        query= "aba".toCharArray();
        System.out.println(ExactSearchFromScratch.indexOf( query, target) );  //-1

        target= "ab".toCharArray();
        query= "aba".toCharArray();
        System.out.println(ExactSearchFromScratch.indexOf( query, target) );  //-1

        target= "xa".toCharArray();
        query= "aba".toCharArray();
        System.out.println(ExactSearchFromScratch.indexOf( query, target) );  //-1

        target= "abracadabras".toCharArray();
        query= "abras".toCharArray();
        System.out.println(ExactSearchFromScratch.indexOf( query, target) );  //7
        System.out.println(new String("Hola").indexOf("ol"));
    }
}
