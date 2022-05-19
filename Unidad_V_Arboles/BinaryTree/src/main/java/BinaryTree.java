import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.function.Function;


public class BinaryTree implements BinaryTreeService {
	private Node root;

	private Scanner inputScanner;

	public BinaryTree(String fileName) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, FileNotFoundException {
		InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
		if (is == null)
			throw new FileNotFoundException(fileName);

		inputScanner = new Scanner(is);
		inputScanner.useDelimiter("\\s+");
		buildTree();
		inputScanner.close();
	}

	private void buildTree() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Queue<NodeHelper> pendingOps= new LinkedList<>();
		String token;

		root= new Node();
		pendingOps.add(new NodeHelper(root, NodeHelper.Action.CONSUMIR));

		while(inputScanner.hasNext()) {
			token= inputScanner.next();
			NodeHelper aPendingOp = pendingOps.remove();
			Node currentNode = aPendingOp.getNode();
			if ( token.equals("?") ) {
				// no hace falta poner en null al L o R porque ya esta con null
				// reservar el espacio en Queue aunque NULL no tiene hijos para aparear
				pendingOps.add( new NodeHelper(null, NodeHelper.Action.CONSUMIR) );  // como si hubiera izq
				pendingOps.add( new NodeHelper(null, NodeHelper.Action.CONSUMIR) );  // como si hubiera der
			}
			else {
				switch (aPendingOp.getAction())
				{
					case LEFT:
						currentNode = currentNode.setLeftTree(new Node() );
						break;
					case RIGHT:
						currentNode = currentNode.setRightTree(new Node());
						break;
				}
				// armo la info del izq, der o el root
				currentNode.data= token;
				// hijos se postergan
				pendingOps.add(new NodeHelper(currentNode, NodeHelper.Action.LEFT));
				pendingOps.add(new NodeHelper(currentNode, NodeHelper.Action.RIGHT));
			}
		}
		if (root.data == null)  // no entre al ciclo jamas
			root= null;
	}

	@Override
	public void preorder() {
		// COMPLETE
	}

	@Override
	public void postorder() {
		// COMPLETE
	}

	public void printHierarchy(){
		Node current = root;
		int[] tab = new int[1];
		tab[0] = 0;
		printHierarchyRec(current, 0);
	}
	public void printHierarchyRec(Node current, int tab){
		// Primero verfico que el nodo en donde me encuentro es o no null
		if(current == null){
			for(int i = 0; i < tab ; i++){
				System.out.print("\t");
			}
			System.out.println("╚═══null");
			return;
		}
		for(int i = 0; i < tab ; i++){
			System.out.print("\t");
		}
		System.out.println("╚═══" + current.data);
		if(!current.data.matches("-*[0-9]+")) {
			printHierarchyRec(current.left, tab + 1);
			printHierarchyRec(current.right, tab + 1);
		}
	}

	public void toFile(String fileName){

	}

	// hasta el get() no se evalua
	class Node {
		private String data;
		private Node left;
		private Node right;

		public Node setLeftTree(Node aNode) {
			left= aNode;
			return left;
		}

		public Node setRightTree(Node aNode) {
			right= aNode;
			return right;
		}

		public Node() {
			// TODO Auto-generated constructor stub
		}

		private boolean isLeaf() {
			return left == null && right == null;
		}
	}  // end Node class

	static class NodeHelper {
		static enum Action {LEFT,RIGHT,CONSUMIR};
		private Node aNode;
		private Action anAction;

		public NodeHelper(Node aNode, Action anAction ) {
			this.aNode= aNode;
			this.anAction= anAction;
		}

		public Node getNode() {
			return aNode;
		}

		public Action getAction() {
			return anAction;
		}
	}

	public static void main(String[] args) throws FileNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		BinaryTreeService rta = new BinaryTree("data0_1");
		rta.printHierarchy();
		rta.preorder();
		rta.postorder();
		System.out.println("----------------");
		BinaryTree rta2 = new BinaryTree("data0_3");
		rta2.printHierarchy();
	}
}