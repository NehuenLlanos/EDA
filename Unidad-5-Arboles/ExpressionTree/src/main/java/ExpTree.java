import java.util.Scanner;

import static java.lang.Double.parseDouble;


public class ExpTree implements ExpressionService {

	private Node root;

	public ExpTree() {
	    System.out.print("Introduzca la expresion en notacion infija con todos los parentesis y blancos: ");

		// Lectura del teclado hasta encontrarse con un "\n".
	    Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
	    String line = inputScanner.nextLine();
	    inputScanner.close();

	    buildTree(line);
	}
	
	private void buildTree(String line) {	
		  // En lineScanner se guarda la linea como si fuera un vector.
		  // Lo que sirvio para separar cada token es el \\s que es el espacio.
		  Scanner lineScanner = new Scanner(line).useDelimiter("\\s+");
		  root = new Node(lineScanner);
		  lineScanner.close();
	}

	// Notacion Prefija
	public void preOrder(){
		if(root == null){
			throw new RuntimeException("Arbol vacio");
		}
		Node current = root;
		preOrderRec(current);
		System.out.println();
	}
	private void preOrderRec(Node current){
		if(current == null){
			return;
		}
		System.out.print(current.data + " ");
		preOrderRec(current.left);
		preOrderRec(current.right);
	}

	// Notacion Postfija
	public void postOrder(){
		if(root == null){
			throw new RuntimeException("Arbol vacio");
		}
		Node current = root;
		postOrderRec(current);
		System.out.println();
	}
	private void postOrderRec(Node current){
		if(current == null){
			return;
		}
		postOrderRec(current.left);
		postOrderRec(current.right);
		System.out.print(current.data + " ");
	}

	// Notacion inFija
	public void inOrder(){
		if(root == null){
			throw new RuntimeException("Arbol vacio");
		}
		Node current = root;
		inOrderRec(current);
		System.out.println();
	}
	private void inOrderRec(Node current){
		if(current == null){
			return;
		}
		if(Utils.isConstant(current.data)){
			System.out.print(current.data);
			return;
		}
		// Voy a llegar aca solamente si soy un operador
		System.out.print("( ");
		inOrderRec(current.left);
		System.out.print(" " + current.data + " ");
		inOrderRec(current.right);
		System.out.print(" )");
	}

	public double eval(){
		if(root == null){
			throw new RuntimeException("Arbol vacio");
		}
		Node current = root;
		return evalRec(current);
	}
	private double evalRec(Node current){
		if(current == null){
			throw new RuntimeException("Invalid Node");
		}
		if(Utils.isConstant(current.data)){
			return parseDouble(current.data);
		}
		// Sino soy un operador
		double left = evalRec(current.left);
		double right = evalRec(current.right);
		for(Operator op : Operator.values()){
			if(op.getOp().compareTo(current.data) == 0){
				return op.apply(left, right);
			}
		}
		return left;
	}

	// Permite crear un nodo.
	static final class Node {
		private String data;
		private Node left, right;
		
		private Scanner lineScanner;

		public Node(Scanner theLineScanner) {
			lineScanner = theLineScanner;
			Node auxi = buildExpression();
			data = auxi.data;
			left = auxi.left;
			right = auxi.right;
			// Validamos que no haya nada mas
			if (lineScanner.hasNext() ) 
				throw new RuntimeException("Bad expression");
		}
		// Constructor cuando generen la hoja
		private Node() 	{

		}
		
		// Permite crear tu nodo.
		private Node buildExpression() {
			Node n = new Node();
			// Siempre tengo solamente dos casos posibles
			// Soy una expresion del tipo (E ? E) o soy una constante
			// Si el siguiente es un parentesis entonces estoy en presencia de una expresion
			if(lineScanner.hasNext("\\(")){
				// Me muevo a la E
				lineScanner.next();
				// Hago llamado recursivo a esta E
				n.left = buildExpression();
				// Si no tiene siguiente a la E entonces me falta un token y lanzo excepcion
				if(!lineScanner.hasNext()){
					throw new RuntimeException("Debe haber otro token");
				}
				// Me muevo al siguiente para obtener el operador
				n.data = lineScanner.next();
				// Verifico que sea un operador
				if(!Utils.isOperator(n.data)){
					throw new RuntimeException("No es un operador");
				}
				// Me guardo la expresion de la derecha
				n.right = buildExpression();
				// Verifico que el siguiente sea el parentesis de cierre. Si no lo es debo
				// lanzar una excepcion
				if(lineScanner.hasNext("\\)")){
					lineScanner.next();
				}else{
					throw new RuntimeException("Missing )");
				}
				return n;
			}
			// Si no tiene siguiente entonces falta un parentesis de cierre y por ende
			// lanzamos una excepcion
			if(!lineScanner.hasNext()){
				throw new RuntimeException("Missing Expression");
			}
			// Obtenemos la constante
			n.data = lineScanner.next();
			// Verificamos que sea una constante
			if(!Utils.isConstant(n.data)){
				throw new RuntimeException("No es un numero");
			}
			// Retornamos el nodo
			return n;
		}
	}  // end Node class

	// hasta que armen los testeos
	public static void main(String[] args) {
		ExpressionService myExp = new ExpTree();
		// ( ( 2 + 3.5 ) * -10 )
		System.out.print("Notacion preFija: ");
		myExp.preOrder();
		System.out.println();
		System.out.print("Notacion postFija: ");
		myExp.postOrder();
		System.out.println();
		System.out.print("Notacion inFija: ");
		myExp.inOrder();
		System.out.println();
		System.out.println( myExp.eval() );
	}

}  // end ExpTree class
