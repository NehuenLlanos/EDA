import java.util.ArrayList;

public class KMP {
    public static int[] nextComputation(char[] query){
        int[] next = new int[query.length];
        int border = 0;
        int rec = 1;
        while(rec < query.length){
            if(query[rec] != query[border]){
                if(border != 0){
                    border = next[border-1];
                }else{
                    next[rec++] = 0;
                }
            }else{
                border++;
                next[rec] = border;
                rec++;
            }
        }
        return next;
    }
    public static int indexOf(char[] query, char[] target, int from){
        int[] next = nextComputation(query);
        int targetCursor = from;
        int queryCursor = 0;
        while(queryCursor < query.length && targetCursor < target.length){
            if(query[queryCursor] == target[targetCursor]){
                queryCursor++;
                targetCursor++;
            }else{
                if(queryCursor == 0){
                    targetCursor++;
                }else{
                    queryCursor = next[queryCursor - 1];
                }
            }
        }
        if(queryCursor == query.length){
            return targetCursor - queryCursor;
        }
        return -1;
    }

    public static ArrayList<Integer> findAll(char[] query, char[] target){
        int index = indexOf(query, target, 0);
        ArrayList<Integer> ans = new ArrayList<>();
        while(index != -1){
            ans.add(index);
            index = indexOf(query, target, index+1);
        }
        return ans;
    }
}
