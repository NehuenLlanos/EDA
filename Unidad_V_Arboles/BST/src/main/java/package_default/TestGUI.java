package package_default;

// bajar el paquete nativo    
// https://gluonhq.com/products/javafx/ 

// en el VM poner el lib del paquete nativo
// --module-path d:\eclipse/workspace-eda-05/javafx-sdk-18.0.1/lib --add-modules javafx.fxml,javafx.controls


import controller.GraphicsTree;
import core.BST;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class TestGUI extends Application {

	public static void main(String[] args) {
		// GUI
		launch(args);
	}

    @Override
	public void start(Stage stage) {
		stage.setTitle("Drawing the BST");
		StackPane root = new StackPane();
		Scene scene = new Scene(root, 500, 500);

		//BST<Integer> myTree = createModel();
		BST<Person> myTree = createModelPerson();
		//GraphicsTree<Integer> c = new GraphicsTree<>(myTree);
		GraphicsTree<Person> c = new GraphicsTree<>(myTree);

		c.widthProperty().bind(scene.widthProperty());
		c.heightProperty().bind(scene.heightProperty());
	
		root.getChildren().add(c);
		stage.setScene(scene);
		stage.show();
		

	}

    
	private BST<Integer> createModeVersion2() {
		BST<Integer> myTree = new BST<>();
		myTree = new BST<>();
		myTree.insert(50);
		myTree.insert(60);
		myTree.insert(80);
		myTree.insert(20);
		myTree.insert(70);
		myTree.insert(40);
		myTree.insert(44);
		myTree.insert(10);
		myTree.insert(40);
		myTree.inOrder();

		return myTree;
	}
	
	
	private BST<Integer> createModel() {
		BST<Integer> myTree = new BST<>();
		myTree.insert(120);
		myTree.insert(100);
		myTree.insert(200);
		myTree.insert(20);
		myTree.insert(100);
		myTree.insert(100);
		return myTree;
	}
	private BST<Person> createModelPerson() {
		BST<Person> myTree = new BST<>();
		myTree.insert(new Person(50, "Ana"));
		myTree.insert(new Person(60, "Juan"));
		myTree.insert(new Person(80, "Sergio"));
		myTree.insert(new Person(20, "Lila"));
		myTree.insert(new Person(77, "Ana"));
		return myTree;
	}
}