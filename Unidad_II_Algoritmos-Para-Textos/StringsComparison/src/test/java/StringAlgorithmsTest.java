import info.debatty.java.stringsimilarity.QGram;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringAlgorithmsTest {
    @AfterEach
    void NextTest(){
        System.out.println("");
    }
    @Test
    void TestSoundex() throws EncoderException {
        System.out.println("Testeo de Soundex");
        System.out.println("Testeo de Soundex Encoding");
        Assertions.assertEquals("T624", StringAlgorithms.soundexRepresentation("threshold"));
        Assertions.assertEquals("H430", StringAlgorithms.soundexRepresentation("hold"));
        Assertions.assertEquals("P500", StringAlgorithms.soundexRepresentation("phone"));
        Assertions.assertEquals("F500", StringAlgorithms.soundexRepresentation("foun"));
        System.out.println("Teste del Soundex Encoding Exitoso");
        System.out.println("Testeo de Soundex Similarity");
        Assertions.assertEquals(0.0, StringAlgorithms.soundexSimilarity("threshold", "hold"));
        Assertions.assertEquals(0.75, StringAlgorithms.soundexSimilarity("phone", "foun"));
        System.out.println("Teste del Soundex Similarity Exitoso");
        System.out.println("Teste del Soundex Exitoso");
    }
    @Test
    void TestLevenshtein(){
        System.out.println("Testeo de Levenshtein");
        System.out.println("Testeo de Levenshtein Distance");
        Assertions.assertEquals(2, StringAlgorithms.levenshteinDistance("big data", "bigdaa"));
        System.out.println("Testeo de Levenshtein Distance   exitoso");
        System.out.println("Testeo de Levenshtein Similarity");
        Assertions.assertEquals(0.75, StringAlgorithms.levenshteinSimilarity("big data", "bigdaa"));
        System.out.println("Testeo de Levenshtein Similarity");
    }
    @Test
    void TestQGrams(){
        System.out.println("Testeo de QGrams");
        System.out.println("Testeo de QGrams Similarity");
        Assertions.assertEquals(0.4285714285714286, StringAlgorithms.qGramsSimilarity("alale", "salesal", 2));
        System.out.println("Testeo de QGrams Similarity exitoso");
        System.out.println("Testeo de QGrams PrintTokens");
        StringAlgorithms.printTokens("hola", 3);
        System.out.println("Testeo de QGrams PrintTokens exitoso");
    }
//    @Test
//    void TestMetaphone(){
//        System.out.println("Testeo de Metaphone");
//        System.out.println("Testeo de Metaphone Distance");
//        Assertions.assertEquals(3, StringAlgorithms.metaphoneDistance("brooklin", "clean"));
//        System.out.println("Testeo de Metaphone Distance exitoso");
//        System.out.println("Testeo de Metaphone Similarity");
//        Assertions.assertEquals(0.6, StringAlgorithms.metaphoneSimilarity("brooklin", "clean"));
//        System.out.println("Testeo de Metaphone Similarity exitoso");
//    }
}
