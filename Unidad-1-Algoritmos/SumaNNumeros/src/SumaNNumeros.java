public class SumaNNumeros {

    public int sumaIter(int N){
        int ans = 0;
        for(int i = 1; i <= N; i++){
            ans += i;
        }
        return ans;

    }

    public int sumaRec(int N){
        if(N == 0){
            return 0;
        }
        return N + sumaRec(N-1);
    }
}
