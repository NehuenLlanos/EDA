public interface ExpressionService {
	
	// lanza exception si no se puede evaluar porque hay algo mal formado en la expresion
	double eval();
	
	void preOrder();
	
	void inOrder();
	
	void postOrder();
	
}
