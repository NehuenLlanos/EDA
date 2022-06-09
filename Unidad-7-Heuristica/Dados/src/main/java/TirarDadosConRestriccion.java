import java.util.ArrayList;


public class TirarDadosConRestriccion {
    public static void main(String[] args) {
        new TirarDadosConRestriccion().solve(3);
    }

    public void solve(int cantDadosPendientes) {
        ArrayList<Integer> auxi= new ArrayList<>();
        solveHelper( cantDadosPendientes, auxi, 6);
    }

    private void solveHelper(int cantDadosPendientes, ArrayList<Integer> auxi, int umbral) {

        if (cantDadosPendientes == 0) {
            int sum= 0;
            for (Integer value : auxi) {
                sum+= value;
            }
            if (sum <= umbral)
                System.out.println(auxi);
            return;
        }


        for(int rec= 1; rec <= 6; rec++) {
            auxi.add(rec);					// resolver un caso pendiente

            solveHelper(cantDadosPendientes-1, auxi, umbral);  // explorar nuevos pendientes

            auxi.remove( auxi.size() - 1);  // quitar el pendiente generado
        }
    }
}