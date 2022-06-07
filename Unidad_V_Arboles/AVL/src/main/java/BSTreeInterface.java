public interface BSTreeInterface<T extends Comparable<? super T>> extends Iterable<T> {

    enum Traversal { BYLEVELS, INORDER }

    void insert(T myData);

    boolean search(T myData);

    void preOrder();

    void postOrder();

    void inOrder();

    NodeTreeInterface<T> getRoot();

    int getHeight();

    boolean contains(T myData);

    T  getMax();

    T  getMin();

    void printByLevels();

    void delete(T myData);

    void setTraversal(Traversal traversal);
}


