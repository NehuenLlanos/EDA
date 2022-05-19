import java.sql.Array;
import java.util.HashMap;
import java.util.Locale;

public class Soundex {
    private static final int ASCII = 65;
    private String str;
    private final int[] myArray = {0,1,2,3,0,1,2,0,0,2,2,4,5,5,0,1,2,6,2,3,0,1,0,2,2};

    public Soundex(String str){
        this.str = str;
    }

    public String representation(){
        return representation(this.str);
    }
    public String representation(String str){
        str = str.toUpperCase();
        StringBuilder ans = new StringBuilder();
        ans.append(str.charAt(0));
        int pos = str.charAt(0) - ASCII;
        int last = myArray[pos];
        int current = myArray[pos];
        int count = 1;
        for(int i = 1; i < str.length() && count < 4; i++, last = current){
            pos = str.charAt(i) - ASCII;
            current = myArray[pos];
            if(current != 0 && current != last){
                ans.append(current);
                count++;
            }
        }
        while(count < 4){
            ans.append(0);
            count++;
        }
        return ans.toString();
    }

    public double similarity(String str){
        double ans = 0.0;
        for(int i = 0; i < 4; i++){
            if(representation(this.str).charAt(i) == representation(str).charAt(i)){
                ans += 0.25;
            }
        }
        return ans;
    }
    public static double similarity(String str1, String str2){
        return new Soundex(str1).similarity(str2);
    }
}
