
import java.util.ArrayList;
public class TirarDados {
    public static void main(String[] args) {
        new TirarDados().solve(3);
    }

    public void solve(int cantDadosPendientes) {
        ArrayList<Integer> auxi= new ArrayList<>();
        solveHelper( cantDadosPendientes, auxi);
    }

    private void solveHelper(int cantDadosPendientes, ArrayList<Integer> auxi) {
        if (cantDadosPendientes == 0)
        {
            System.out.println(auxi);
            return;
        }

        for(int rec= 1; rec <= 6; rec++) {
            auxi.add(rec);					// resolver un caso pendiente

            solveHelper(cantDadosPendientes-1, auxi);  // explorar nuevos pendientes

            auxi.remove( auxi.size() - 1);  // quitar el pendiente generado
        }
    }
}
