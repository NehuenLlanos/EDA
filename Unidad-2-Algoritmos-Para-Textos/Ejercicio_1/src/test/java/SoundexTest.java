import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SoundexTest {
    @BeforeAll
    static void startTest(){
        System.out.println("Testeo de Soundex");
    }
    @Test
    public void soundexRepresentation() {
        System.out.println("Testeo de Representation");

        Soundex threshold = new Soundex("threshold");
        Assertions.assertEquals("T624", threshold.representation());

        Soundex hold = new Soundex("hold");
        Assertions.assertEquals("H430", hold.representation() );

        Soundex phone = new Soundex("phone");
        Assertions.assertEquals("P500", phone.representation());

        Soundex foun = new Soundex("foun");
        Assertions.assertEquals("F500", foun.representation());

        System.out.println("Teste del Representation Exitoso");
    }

    @Test
    public void soundexSimilarity(){
        System.out.println("Testeo de Similarity");

        Assertions.assertEquals(0.0, Soundex.similarity("threshold", "hold"));
        Assertions.assertEquals(0.75, new Soundex("phone").similarity("foun"));

        System.out.println("Teste del Similarity Exitoso");
    }
}
