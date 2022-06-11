package Ejercicio1;
import java.util.Collection;

// same interface for graph, digraph, multigraph, weighted graph, etc

// V participa de hashing. Redefinir equals y hashcode para que detecte
// correctamente repetidos segun se desee

// Se precisa la redefinicion de equals para que en remove lo encuentre
// y lo pueda borrar. Actualmente no participa de un hashing. Esta encapsulado
// dentro de un objeto InternalEdge que lo contiene junto con el V destino.
// Esa clase InternalEdge sí tiene hashcode y equals implementado.

public interface GraphService<V,E> {
    enum Multiplicity { SIMPLE, MULTIPLE};
    enum EdgeMode { UNDIRECTED, DIRECTED};
    enum SelfLoop { NO, YES};
    enum Weight{ NO, YES	};
    enum Storage { SPARSE, DENSE };

    // devuelve caracteristicas de la forma en que fue creado
    public String getType();

    // if exists lo ignora
    // else generate a new vertex
    public void addVertex(V aVertex);


    public Collection<V> getVertices();

    public boolean hasEdge(V fromVertex, V toVertex, E edge);

    // parameters cannot be null
    // if any of the vertices is not present, the node is created automatically

    // in the case of a weighted graph, E might implements the method double getWeight()
    // otherwise an exception will be thrown

    // if the graph is not "multi" and there exists other edge with same source and target,
    // throws exception
    public void addEdge(V aVertex, V otherVertex, E theEdge);

    GraphService<V, E> popularSubgraph(Integer popularThreshold);
}