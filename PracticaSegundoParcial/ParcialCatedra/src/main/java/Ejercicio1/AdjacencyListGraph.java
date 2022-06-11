package Ejercicio1;

import java.util.*;

abstract public class AdjacencyListGraph<V, E> implements GraphService<V, E> {

    private boolean isSimple;
    protected boolean isDirected;
    private boolean acceptSelfLoop;
    private boolean isWeighted;
    protected String type;

    // HashMap no respeta el orden de insercion. En el testing considerar eso
    private Map<V,Collection<InternalEdge>> adjacencyList= new HashMap<>();

    // respeta el orden de llegada y facilita el testing
    //	private Map<V,Collection<InternalEdge>> adjacencyList= new LinkedHashMap<>();

    protected   Map<V,  Collection<AdjacencyListGraph<V, E>.InternalEdge>> getAdjacencyList() {
        return adjacencyList;
    }

    protected AdjacencyListGraph(boolean isSimple, boolean isDirected, boolean acceptSelfLoop, boolean isWeighted) {
        this.isSimple = isSimple;
        this.isDirected = isDirected;
        this.acceptSelfLoop= acceptSelfLoop;
        this.isWeighted = isWeighted;

        this.type = String.format("%s %sWeighted %sGraph with %sSelfLoop",
                isSimple ? "Simple" : "Multi", isWeighted ? "" : "Non-",
                isDirected ? "Di" : "", acceptSelfLoop? "":"No ");
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void addVertex(V aVertex) {

        if (aVertex == null )
            throw new IllegalArgumentException("addVertex parameters cannot be null");

        // no edges yet
        getAdjacencyList().putIfAbsent(aVertex, new ArrayList<InternalEdge>());
    }

    @Override
    public Collection<V> getVertices() {
        return getAdjacencyList().keySet() ;
    }

    @Override
    public void addEdge(V aVertex, V otherVertex, E theEdge) {

        // validation!!!!
        if (aVertex == null || otherVertex == null || theEdge == null)
            throw new IllegalArgumentException("addEdge parameters cannot be null");

        // es con peso? debe tener implementado el metodo double getWeight()
        if (isWeighted) {
            // reflection
            Class<? extends Object> c = theEdge.getClass();
            try {
                c.getDeclaredMethod("getWeight");
            } catch (NoSuchMethodException | SecurityException e) {
                throw new RuntimeException(
                        type + " is weighted but the method double getWeighed() is not declared in theEdge");
            }
        }

        if (! acceptSelfLoop && aVertex.equals(otherVertex)) {
            throw new RuntimeException(String.format("%s does not accept self loops between %s and %s" ,
                    type, aVertex, otherVertex) );
        }

        // if any of the vertex is not presented, the node is created automatically
        addVertex(aVertex);
        addVertex(otherVertex);
    }


    @Override
    public boolean hasEdge(V fromVertex, V toVertex, E edge) {
        if (fromVertex == null || toVertex == null || edge == null) {
            throw new RuntimeException("hasEdges called with at least one null parameter");
        }

        Collection<InternalEdge> edges = adjacencyList.get(fromVertex);
        if (edges == null) {
            return false;
        }

        for (InternalEdge e : edges) {
            if (e.target.equals(toVertex) && e.edge.equals(edge)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public GraphService<V, E> popularSubgraph(Integer popularThreshold) {
        if(!isDirected || !isWeighted){
            throw new RuntimeException("Only for directed and weighted graphs.");
        }
        Map<V, Integer> songs = new HashMap<>();
        // Recorro todos los vertices en busca de canciones
        for(Map.Entry<V, Collection<InternalEdge>> vertexInfo : adjacencyList.entrySet()){
            if(vertexInfo.getValue().size() == 0){
                songs.put(vertexInfo.getKey(), 0);
            }
        }
        // Aca tengo todas las canciones en mi mapa.
        // Tengo que recorrer todos los vertices que son personas
        for(V vertex : adjacencyList.keySet()){
            // Verifico que sea una persona
            if(adjacencyList.get(vertex).size() > 0){
                // Recorro todas las aristas de la persona
                for(InternalEdge edge : adjacencyList.get(vertex)){
                    // Si se conecta con una cancion tengo que actualizar el valor de las reproducciones
                    if(songs.containsKey(edge.target)){
                        WeightedEdge currentEdge = (WeightedEdge) edge.edge;
                        int reproductions = songs.get(edge.target) +  currentEdge.getWeight();
                        songs.put(edge.target, reproductions);
                    }
                }
            }
        }
        // Aca tengo el mapa con las canciones y sus reproducciones. Tengo que crear el subgrafo con todas las canciones
        // que superen el popularTreshold
        GraphService<V,E> popularSubGraph = GraphFactory.create(Multiplicity.MULTIPLE, EdgeMode.DIRECTED, SelfLoop.NO, Weight.YES, Storage.SPARSE);
        // Tengo que agregar todos los vertices que sean canciones que superaron el thresHold y todos los usuarios que se conectan a las mismas
        // Primero agrego las canciones
        for(V songToAdd: songs.keySet()){
            if(songs.get(songToAdd) >= popularThreshold) {
                popularSubGraph.addVertex(songToAdd);
            }
        }
        // Agrego las personas y los vertices que se unen a las canciones que superan el thresHold
        for(V vertex : adjacencyList.keySet()){
            // Chequeo si el vertice contiene a alguna de las canciones populares
            boolean containsPopularSong = false;
            for(V popularSong : popularSubGraph.getVertices()){
                for(InternalEdge edge : adjacencyList.get(vertex)){
                    if(edge.target.equals(popularSong)){
                        containsPopularSong = true;
                    }
                }
            }
            // Filtro las personas teniendo en cuenta el tamano de la lista de aristas
            if(adjacencyList.get(vertex).size() > 0 && containsPopularSong){
                // Agrego el vertice de la persona
                popularSubGraph.addVertex(vertex);
                // Recorro las aristas del vertice
                for(InternalEdge edge : adjacencyList.get(vertex)){
                    // Si el vertice de la arista es una cancion que esta en el popularSubGraph entonces agrego la arista
                    if(popularSubGraph.getVertices().contains(edge.target)){
                        popularSubGraph.addEdge(vertex, edge.target, edge.edge);
                    }
                }
            }
        }
        return popularSubGraph;
    }

    class InternalEdge {
        E edge;
        V target;

        InternalEdge(E propEdge, V target) {
            this.target = target;
            this.edge = propEdge;
        }

        @Override
        public int hashCode() {
            return target.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            @SuppressWarnings("unchecked")
            InternalEdge aux = (InternalEdge) obj;

            return ((edge == null && aux.edge == null) || (edge != null && edge.equals(aux.edge)))
                    && target.equals(aux.target);
        }

        @Override
        public String toString() {
            return String.format("-[%s]-(%s)", edge, target);
        }
    }

    public static void main(String[] args) {
        GraphService<String, WeightedEdge> graph = GraphFactory.create(Multiplicity.MULTIPLE, EdgeMode.DIRECTED, SelfLoop.NO, Weight.YES, Storage.SPARSE);
        graph.addEdge("Juan", "Pink", new WeightedEdge(2));
        graph.addEdge("Juan", "Eiti Leda", new WeightedEdge(2));
        graph.addEdge("Mer", "Pink", new WeightedEdge(2));
        graph.addEdge("Mer", "Eiti Leda", new WeightedEdge(1));
        graph.addEdge("Mer", "Call me maybe", new WeightedEdge(9));
        graph.addEdge("Ale", "Call me maybe", new WeightedEdge(1));
        graph.addEdge("Ale", "Eiti Leda", new WeightedEdge(4));
        GraphService<String, WeightedEdge> popularSubGraph = graph.popularSubgraph(8);
    }
}