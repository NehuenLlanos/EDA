import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class MetaphoneWithLibrary {
    public static void main(String[] args) {
        Metaphone meta = new Metaphone();
        LevenshteinDistance leven = new LevenshteinDistance();
        System.out.println("BROOKLIN = " + meta.encode("brooklin"));
        System.out.println("BRUQLEEN = " + meta.encode("bruqleen"));
        System.out.println("BROOCLEAN = " + meta.encode("brooclean"));
        System.out.println("BRUCLEAN = " + meta.encode("bluclean"));
        System.out.println("CLEAN = " + meta.encode("clean"));
        System.out.println("BROOKLIN Y BRUQLEEN = " + leven.apply("BROOKLIN", "BRUQLEEN"));
        System.out.println("BROOKLIN Y BROOCLEAN = " + leven.apply("BROOKLIN", "BROOCLEAN"));
        System.out.println("BROOKLIN Y BROOCLEAN = " + leven.apply("BROOKLIN", "BROOCLEAN"));
        System.out.println("BROOKLIN Y CLEAN = " + leven.apply("BROOKLIN", "CLEAN"));
        System.out.println("BROOKLIN Y BROOKLIN = " + leven.apply("BROOKLIN", "BROOKLIN"));
    }
}
