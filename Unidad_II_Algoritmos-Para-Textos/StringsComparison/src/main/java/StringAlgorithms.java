import info.debatty.java.stringsimilarity.QGram;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.HashMap;
import java.util.Map;

public class StringAlgorithms {
    //Soundex: similitud entre 2 strings y metodo para obtener el encoding
    public static String soundexRepresentation(String str){
        return new Soundex().encode(str);
    }
    public static double soundexSimilarity(String str1, String str2) throws EncoderException {
        return new Soundex().difference(str1, str2) * 0.25;
    }

    //Levenshtein:  similitud entre 2 strings y la cantidad de sustituciones/insercciones y borrados
    //Cantidad de sustituciones/insercciones/borrados
    public static int levenshteinDistance(String str1, String str2){
        return new LevenshteinDistance().apply(str1, str2);
    }
    //Similitud entre 2 strings
    public static double levenshteinSimilarity(String str1, String str2){
        return 1.0 - levenshteinDistance(str1, str2) / (double) Math.max(str1.length(), str2.length());
    }

    //QGrams: similitud entre 2 strings y analogo de printTokens
    public static double qGramsSimilarity(String str1, String str2, int n){
        QGram qgram = new QGram(n);
        str1 = createString(str1, n);
        str2 = createString(str2, n);
        return qgram.similarity(str1, str2);
    }
    public static void printTokens(String str, int n){
        str = createString(str, n);
        System.out.println(getTokens(str, n));
    }
    private static String createString(String str, int n){
        for(int i = 0; i < n-1 ; i++){
            str = "#" + str + "#";
        }
        return str;
    }
    private static Map<String, Integer> getTokens(String str, int n){
        Map<String, Integer> tokens = new HashMap<>();
        for(int i = 0; i < str.length() - (n-1); i++){
            tokens.put(str.substring(i, i + n), tokens.getOrDefault(str.substring(i, i + n), 0) + 1);
        }
        return tokens;
    }

    //Metaphone: necesita solamente un encoding
    public static int metaphoneDistance(String str1, String str2){
        return new LevenshteinDistance().apply(str1, str2);
    }
    public static double metaphoneSimilarity(String str1, String str2){
        return levenshteinSimilarity(new Metaphone().encode(str1), new Metaphone().encode(str2));
    }
}
