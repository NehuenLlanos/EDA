import org.testng.annotations.Test;

public class IndexServiceTest {
    public static void main(String[] args) {
        IndexService myIndex= new IndexWithDuplicates();
        System.out.println (myIndex.occurrences( 10 ) ); // se obtiene 0
        myIndex.delete( 10 ); // ignora
        System.out.println (myIndex.search( 10 ) ); // se obtiene false
        myIndex.insert( 80 ); // almacena [80]
        myIndex.insert( 20 ); // almacena [20, 80]
        myIndex.insert( 80 ); // almacena [20, 80, 80]
        try{
            myIndex.initialize( null );
        }catch(Exception e){
        }
        // sigue con lo anterior
        System.out.println (myIndex.occurrences( 80 ) ); // se obtiene 2
        try{
            myIndex.initialize( new int[] {100, 50, 30, 50, 80, 100, 100, 30} );
        }catch(Exception e){
        }
        // el índice posee [30, 30, 50, 50, 80, 100, 100, 100]
        System.out.println( myIndex.search( 20 )); // se obtiene false
        System.out.println( myIndex.search( 80 )); // se obtiene true
        System.out.println (myIndex.occurrences( 50 ) ); // se obtiene 2
        myIndex.delete( 50 );
        System.out.println (myIndex.occurrences( 50 ) ); // se obtiene 1
        IndexService myIndex2= new IndexWithDuplicates();
        myIndex2.sortedPrint();
        try{
            System.out.println( myIndex2.getMax() );
        }catch(Exception e){
        }
        try{
            System.out.println( myIndex2.getMin() );
        }catch(Exception e){
        }
        try{
            myIndex2.initialize( new int[] {100, 50, 30, 50, 80, 100, 100, 30} );
        }catch(Exception e){
        }
        myIndex2.sortedPrint();
        System.out.println();
        System.out.println( myIndex2.getMax() );
        System.out.println( myIndex2.getMin() );
        int[] rta= myIndex2.range(50, 100, false, false); // [80]
        rta= myIndex2.range(50, 100, true, false); // [50, 50, 80]
        rta= myIndex2.range(50, 100, false, true); // [80, 100, 100, 100]
        rta= myIndex2.range(30, 50, true, false); // [30, 30]
        rta= myIndex2.range(45, 100, false, false); // [50, 50, 80]
        rta= myIndex2.range(45, 100, true, false); // [50, 50, 80]
        rta= myIndex2.range(45, 100, false, true); // [50, 50, 80, 100, 100, 100]
        rta= myIndex2.range(10, 50, true, false); // [30, 30]
        rta= myIndex2.range(10, 50, false, false); // [30, 30]
        rta= myIndex2.range(10, 20, false, false); // []
        rta= myIndex2.range(47, 45, false, false); // []
        rta= myIndex2.range(120, 120, true, true); // []
        rta= myIndex2.range(80, 80, false, false); // []
        rta= myIndex2.range(80, 81, true, false); // [80]
        rta= myIndex2.range(80, 81, false, false); // []
    }
}
