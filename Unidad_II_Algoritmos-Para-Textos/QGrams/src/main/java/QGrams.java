import java.util.HashMap;
import java.util.Map;

public class QGrams {
    private int n;

    public QGrams(int n){
        this.n = n;
    }

    // Creamos el string con los # necesarios
    private String createString(String str){
        for(int i = 0; i < n-1 ; i++){
            str = "#" + str + "#";
        }
        return str;
    }
    // Obtenemos el mapa con los pares clave-valor de todos los tokens.
    private Map<String, Integer> getTokens(String str){
        Map<String, Integer> tokens = new HashMap<>();
        for(int i = 0; i < str.length() - (n-1); i++){
            tokens.put(str.substring(i, i + n), tokens.getOrDefault(str.substring(i, i + n), 0) + 1);
        }
        return tokens;
    }
    // PrintTokens method
    public void printTokens(String str){
        str = createString(str);
        System.out.println(getTokens(str));
    }

    private int getNumberOfTokens(String str){
        Map<String, Integer> token = getTokens(str);
        int ans = 0;
        for(Integer aux : token.values()){
            ans += aux;
        }
        return ans;
    }
    //   #a al la al le e#
    // #s sa al le es sa al l#
    // Comparten = al al le = 3
    // No comparten = #a la e# #s sa es sa l# = 8

    private int getNotShared(String str1, String str2){
        Map<String, Integer> tokenStr1 = getTokens(str1);
        Map<String, Integer> tokenStr2 = getTokens(str2);
        int ans = 0;
        for(String aux : tokenStr1.keySet()){
            // Si token 2 no la contiene tengo que ver cuantos hay dentro de eso porque puede haber mas de uno
            // Si la contiene no te importa.
            if(!tokenStr2.containsKey(aux)){
                ans += tokenStr1.get(aux);
            }
        }
        for(String aux : tokenStr2.keySet()){
            // Si token 1 no la contiene tengo que ver cuantos hay dentro de eso porque puede haber mas de uno
            // Si la contiene no te importa.
            if(!tokenStr1.containsKey(aux)){
                ans += tokenStr2.get(aux);
            }
        }
        return ans;
    }

    public double similarity(String str1, String str2){
        str1 = createString(str1);
        str2 = createString(str2);
        System.out.println(getNumberOfTokens(str1) + " " + getNumberOfTokens(str2));
        int numberOfTokens = getNumberOfTokens(str1) + getNumberOfTokens(str2);
        return (numberOfTokens - getNotShared(str1, str2)) / (double) numberOfTokens;
    }
}
