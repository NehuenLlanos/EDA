import core.GraphFactory;
import core_service.GraphService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import use.EmptyEdgeProp;
import use.WeightedEdge;

public class GraphTest {

    // Number of Edges Test
    @Test
    public void numberEdgesTest1(){
        GraphService<Character, EmptyEdgeProp> undirectedSimpleGraph = GraphFactory.create(GraphService.Multiplicity.SIMPLE,
                GraphService.EdgeMode.UNDIRECTED, GraphService.SelfLoop.YES,
                GraphService.Weight.NO, GraphService.Storage.SPARSE);

        undirectedSimpleGraph.addEdge('E', 'B', new EmptyEdgeProp());
        undirectedSimpleGraph.addEdge('A', 'B', new EmptyEdgeProp());
        undirectedSimpleGraph.addEdge('F', 'B', new EmptyEdgeProp());
        undirectedSimpleGraph.addVertex('D');
        undirectedSimpleGraph.addVertex('G');
        undirectedSimpleGraph.addEdge('E', 'F', new EmptyEdgeProp());
        undirectedSimpleGraph.addEdge('F', 'A', new EmptyEdgeProp());
        undirectedSimpleGraph.addEdge('F', 'G', new EmptyEdgeProp());
        undirectedSimpleGraph.addEdge('U', 'G', new EmptyEdgeProp());
        undirectedSimpleGraph.addEdge('T', 'U', new EmptyEdgeProp());
        undirectedSimpleGraph.addEdge('C', 'G', new EmptyEdgeProp());

        Assertions.assertEquals(9, undirectedSimpleGraph.numberOfEdges());
    }

    @Test
    public void numberEdgesTest2(){
        GraphService<Character, EmptyEdgeProp> undirectedSimpleGraph = GraphFactory.create(GraphService.Multiplicity.SIMPLE,
                GraphService.EdgeMode.UNDIRECTED, GraphService.SelfLoop.YES,
                GraphService.Weight.NO, GraphService.Storage.SPARSE);

        undirectedSimpleGraph.addEdge('E', 'B', new EmptyEdgeProp());
        undirectedSimpleGraph.addEdge('A', 'B', new EmptyEdgeProp());
        undirectedSimpleGraph.addEdge('F', 'B', new EmptyEdgeProp());
        undirectedSimpleGraph.addVertex('D');
        undirectedSimpleGraph.addVertex('G');
        undirectedSimpleGraph.addEdge('E', 'F', new EmptyEdgeProp());
        undirectedSimpleGraph.addEdge('F', 'A', new EmptyEdgeProp());
        undirectedSimpleGraph.addEdge('F', 'G', new EmptyEdgeProp());
        undirectedSimpleGraph.addEdge('U', 'G', new EmptyEdgeProp());
        undirectedSimpleGraph.addEdge('T', 'U', new EmptyEdgeProp());
        undirectedSimpleGraph.addEdge('C', 'G', new EmptyEdgeProp());
        undirectedSimpleGraph.addEdge('F', 'F', new EmptyEdgeProp());

        Assertions.assertEquals(10, undirectedSimpleGraph.numberOfEdges());
    }

    @Test
    public void numberEdgesTest3(){
        GraphService<Character, EmptyEdgeProp> undirectedMultipleGraph = GraphFactory.create(GraphService.Multiplicity.MULTIPLE,
                GraphService.EdgeMode.UNDIRECTED, GraphService.SelfLoop.YES,
                GraphService.Weight.NO, GraphService.Storage.SPARSE);

        undirectedMultipleGraph.addEdge('E', 'B', new EmptyEdgeProp());
        undirectedMultipleGraph.addEdge('A', 'B', new EmptyEdgeProp());
        undirectedMultipleGraph.addEdge('F', 'B', new EmptyEdgeProp());
        undirectedMultipleGraph.addVertex('D');
        undirectedMultipleGraph.addVertex('G');
        undirectedMultipleGraph.addEdge('E', 'F', new EmptyEdgeProp());
        undirectedMultipleGraph.addEdge('F', 'A', new EmptyEdgeProp());
        undirectedMultipleGraph.addEdge('F', 'G', new EmptyEdgeProp());
        undirectedMultipleGraph.addEdge('U', 'G', new EmptyEdgeProp());
        undirectedMultipleGraph.addEdge('T', 'U', new EmptyEdgeProp());
        undirectedMultipleGraph.addEdge('C', 'G', new EmptyEdgeProp());
        undirectedMultipleGraph.addEdge('G', 'U', new EmptyEdgeProp());
        undirectedMultipleGraph.addEdge('F', 'F', new EmptyEdgeProp());
        undirectedMultipleGraph.addEdge('F', 'F', new EmptyEdgeProp());

        Assertions.assertEquals(12, undirectedMultipleGraph.numberOfEdges());
    }

    // Degree Edges
    @Test
    public void degreeTest1(){
        GraphService<Character, EmptyEdgeProp> g = GraphFactory.create(GraphService.Multiplicity.MULTIPLE,
                GraphService.EdgeMode.DIRECTED, GraphService.SelfLoop.YES,
                GraphService.Weight.NO, GraphService.Storage.SPARSE);

        g.addVertex('D');
        g.addVertex('G');
        g.addEdge('G', 'F', new EmptyEdgeProp());
        g.addEdge('U', 'G', new EmptyEdgeProp());
        g.addEdge('U', 'G', new EmptyEdgeProp());
        g.addEdge('F', 'F', new EmptyEdgeProp());
        g.addEdge('F', 'F', new EmptyEdgeProp());
        Assertions.assertThrows(RuntimeException.class,() -> g.degree('G'), "Degree is not valid for Directed Graphs");
    }

    @Test
    public void degreeTest2(){
        GraphService<Character, EmptyEdgeProp> g = GraphFactory.create(GraphService.Multiplicity.MULTIPLE,
                GraphService.EdgeMode.UNDIRECTED, GraphService.SelfLoop.YES,
                GraphService.Weight.NO, GraphService.Storage.SPARSE);

        g.addEdge('E', 'B', new EmptyEdgeProp());
        g.addEdge('A', 'B', new EmptyEdgeProp());
        g.addEdge('F', 'B', new EmptyEdgeProp());
        g.addVertex('D');
        g.addVertex('G');
        g.addEdge('E', 'F', new EmptyEdgeProp());
        g.addEdge('F', 'A', new EmptyEdgeProp());
        g.addEdge('F', 'G', new EmptyEdgeProp());
        g.addEdge('U', 'G', new EmptyEdgeProp());
        g.addEdge('T', 'U', new EmptyEdgeProp());
        g.addEdge('C', 'G', new EmptyEdgeProp());
        g.addEdge('G', 'U', new EmptyEdgeProp());
        g.addEdge('F', 'F', new EmptyEdgeProp());
        g.addEdge('F', 'F', new EmptyEdgeProp());

        Assertions.assertEquals(4, g.degree('G'));
        Assertions.assertEquals(8, g.degree('F'));
    }

    @Test
    public void inDegreeAndOutDegreeTest1(){
        GraphService<Character, EmptyEdgeProp> g = GraphFactory.create(GraphService.Multiplicity.MULTIPLE,
                GraphService.EdgeMode.DIRECTED, GraphService.SelfLoop.YES,
                GraphService.Weight.NO, GraphService.Storage.SPARSE);

        g.addVertex('D');
        g.addVertex('G');
        g.addEdge('G', 'F', new EmptyEdgeProp());
        g.addEdge('U', 'G', new EmptyEdgeProp());
        g.addEdge('U', 'G', new EmptyEdgeProp());
        g.addEdge('F', 'F', new EmptyEdgeProp());
        g.addEdge('F', 'F', new EmptyEdgeProp());

        Assertions.assertEquals(2, g.inDegree('G'));
        Assertions.assertEquals(1, g.outDegree('G'));

        Assertions.assertEquals(3, g.inDegree('F'));
        Assertions.assertEquals(2, g.outDegree('F'));
    }

    @Test
    public void inDegreeAndOutDegreeTest2(){
        GraphService<Character, EmptyEdgeProp> g = GraphFactory.create(GraphService.Multiplicity.MULTIPLE,
                GraphService.EdgeMode.UNDIRECTED, GraphService.SelfLoop.YES,
                GraphService.Weight.NO, GraphService.Storage.SPARSE);

        g.addEdge('E', 'B', new EmptyEdgeProp());
        g.addEdge('A', 'B', new EmptyEdgeProp());
        g.addEdge('F', 'B', new EmptyEdgeProp());
        g.addVertex('D');
        g.addVertex('G');
        g.addEdge('E', 'F', new EmptyEdgeProp());
        g.addEdge('F', 'A', new EmptyEdgeProp());
        g.addEdge('F', 'G', new EmptyEdgeProp());
        g.addEdge('U', 'G', new EmptyEdgeProp());
        g.addEdge('T', 'U', new EmptyEdgeProp());
        g.addEdge('C', 'G', new EmptyEdgeProp());
        g.addEdge('G', 'U', new EmptyEdgeProp());
        g.addEdge('F', 'F', new EmptyEdgeProp());
        g.addEdge('F', 'F', new EmptyEdgeProp());

        Assertions.assertThrows(RuntimeException.class,() -> g.inDegree('G'), "InDegree is not valid for inDirected Graphs");
        Assertions.assertThrows(RuntimeException.class,() -> g.outDegree('G'), "OutDegree is not valid for inDirected Graphs");
    }

    // Remove Vertex
    @Test
    public void removeVertexTest1(){
        GraphService<Character, WeightedEdge> g = GraphFactory.create(GraphService.Multiplicity.MULTIPLE,
                GraphService.EdgeMode.DIRECTED, GraphService.SelfLoop.YES,
                GraphService.Weight.YES, GraphService.Storage.SPARSE);
        g.addVertex('D');
        g.addVertex('G');
        g.addEdge('G', 'F', new WeightedEdge(2));
        g.addEdge('U', 'G', new WeightedEdge(-10));
        g.addEdge('U', 'G', new WeightedEdge(0));
        g.addEdge('F', 'F', new WeightedEdge(3));
        g.addEdge('F', 'F', new WeightedEdge(2));

        g.removeVertex('G' );
    }
}
