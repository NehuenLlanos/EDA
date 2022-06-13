public interface ExpTreeService {

    // lanza exception si no se puede evaluar porque hay algo mal formado en la expresion
    boolean eval();

    void preOrder();

    void inOrder();

    void postOrder();

}
