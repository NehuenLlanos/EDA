package core;


public interface BSTreeInterface<T extends Comparable<? super T>> extends Iterable<T>{
	enum Traversal {BYLEVELS, INORDER};
	void insert(T myData);

	void preOrder();

	void postOrder();

	void inOrder();

	NodeTreeInterface<T> getRoot();
	
	int getHeight();

	boolean contains(T myData);

	T getMin();

	T getMax();

	void printByLevels();

	int getOcurrences(T element);

	T getCommonNode(T element1, T element2);

	//T getCommonNodeWithRepeated(T element1, T element2);

}