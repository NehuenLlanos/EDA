import java.util.HashMap;
import java.util.Map;

public class QGrams {
    private int n;

    public QGrams(int n){
        this.n = n;
    }

    private Map<String, Integer> getTokens(String str){
        Map<String, Integer> tokens = new HashMap<>();
        for(int i = 0; i < str.length() - (n-1); i++){
            tokens.putIfAbsent(str.substring(i, i + n), 0);
            tokens.put(str.substring(i, i + n), tokens.get(str.substring(i, i + n)) + 1);
        }
        return tokens;
    }
    private String createString(String str){
        for(int i = 0; i < n-1 ; i++){
            str = "#" + str + "#";
        }
        return str;
    }
    public void printTokens(String str){
        str = createString(str);
        Map<String, Integer> tokens = getTokens(str);
        System.out.println(tokens.toString());
    }
    private int getNumberOfTokens(String str){
        str = createString(str);
        Map<String, Integer> token = getTokens(str);
        return token.size();
    }
//    private int getNotShared(String str1, String str2){
//        Map<String, Integer> tokenStr1 = getTokens(str1);
//        Map<String, Integer> tokenStr2 = getTokens(str2);
//
//    }

    public double similarity(String str1, String str2){
        str1 = createString(str1);
        str2 = createString(str2);
        int numberOfTokens = getNumberOfTokens(str1) + getNumberOfTokens(str2);
        return (numberOfTokens) / (double) numberOfTokens; // + getNotShared(str1, str2)
    }
}
