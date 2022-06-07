package core;

import java.util.*;

import core_service.GraphBuilder;
import core_service.GraphService;
import use.EmptyEdgeProp;

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
	
	protected   Map<V,  Collection<InternalEdge>> getAdjacencyList() {
		return adjacencyList;
	}
	
	
	protected AdjacencyListGraph(boolean isSimple, boolean isDirected, boolean acceptSelfLoop, boolean isWeighted) {
		this.isSimple = isSimple;
		this.isDirected = isDirected;
		this.acceptSelfLoop= acceptSelfLoop;
		this.isWeighted = isWeighted;

		this.type = String.format("%s %sWeighted %sGraph with %sSelfLoop", 
				isSimple ? "Simple" : "Core.Multi", isWeighted ? "" : "Non-",
				isDirected ? "Di" : "", acceptSelfLoop? "":"No ");
	}
	
	
	@Override
	public String getType() {
		return type;
	}
	
	@Override
	public void addVertex(V aVertex) {
	
		if (aVertex == null )
		throw new IllegalArgumentException(Messages.getString("addVertexParamCannotBeNull"));
	
		// no edges yet
		getAdjacencyList().putIfAbsent(aVertex, 
				new ArrayList<InternalEdge>());
	}

	
	@Override
	public int numberOfVertices() {
		return getVertices().size();
	}

	@Override
	public Collection<V> getVertices() {
		return getAdjacencyList().keySet() ;
	}
	
	@Override
	public int numberOfEdges() {
		int ans = 0;
		for(Map.Entry<V,Collection<InternalEdge>> aux : getAdjacencyList().entrySet()){
			ans += aux.getValue().size();
		}
		return ans / 2;
	}

	@Override
	public void addEdge(V aVertex, V otherVertex, E theEdge) {

		// validation!!!!
		if (aVertex == null || otherVertex == null || theEdge == null)
			throw new IllegalArgumentException(Messages.getString("addEdgeParamCannotBeNull"));

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
	public boolean removeVertex(V aVertex) {
		/* Tengo dos casos. Si es simple tengo que borrar todas las apariciones del nodo
		*  en los demas nodos y luego el vertice en si
		*  Si tengo un dirigido, es decir, no es simple tengo que borrar todas las apariciones
		*  en los demas nodos.
		* */
		Collection<InternalEdge> connections = getAdjacencyList().get(aVertex);
		if(connections == null){
			return false;
		}
		if(isSimple){
			for(InternalEdge internalEdge : connections){
				Collection<InternalEdge> collection = getAdjacencyList().get(internalEdge.target);
				Iterator<InternalEdge> it = collection.iterator();
				while(it.hasNext()){
					if(it.next().target.equals(aVertex)){
						it.remove();
					}
				}
			}
			getAdjacencyList().remove(aVertex);
		}else{
			// Si es dirigido tengo que recorrer todos los vertices del mapa.
			// En cada uno de ellos debo remover las conexiones con el aVertex
			for(Map.Entry<V, Collection<InternalEdge>> entry : adjacencyList.entrySet()){
				Iterator<InternalEdge> it = entry.getValue().iterator();
				while(it.hasNext()){
					if(it.next().target.equals(aVertex)){
						it.remove();
					}
				}
			}
			getAdjacencyList().remove(aVertex);
		}
		return true;
	}

	// Permite remover todas las aristas entre un vertice y otro.
	@Override
	public boolean removeEdge(V aVertex, V otherVertex) {
		if(aVertex == null || otherVertex == null){
			throw new RuntimeException("No se permite vertices null");
		}
		boolean ans = false;
		// Si es simple tenemos que eliminar de cada vertices las aristas que unen a cada uno.
		// Preguntar si es lo mismo para grafos simples y grafos con multiaristas
		Iterator<InternalEdge> edgesVertex = getAdjacencyList().get(aVertex).iterator();
		while(edgesVertex.hasNext()){
			if(edgesVertex.next().target == otherVertex){
				edgesVertex.remove();
				ans = true;
			}
		}
		Iterator<InternalEdge> edgesOtherVertex = getAdjacencyList().get(otherVertex).iterator();
		while(edgesOtherVertex.hasNext()){
			if(edgesOtherVertex.next().target == edgesOtherVertex){
				edgesOtherVertex.remove();
				ans = true;
			}
		}
		return ans;
	}

	// Permite remover la arista theEdge entre un vertice y otro.
	@Override
	public boolean removeEdge(V aVertex, V otherVertex, E theEdge) {
		if(aVertex == null || otherVertex == null){
			throw new RuntimeException("No se permite vertices null");
		}
		boolean ans = false;
		// Si es simple tenemos que eliminar de cada vertices las aristas que unen a cada uno.
		// Preguntar si es lo mismo para grafos simples y grafos con multiaristas
		Iterator<InternalEdge> edgesVertex = getAdjacencyList().get(aVertex).iterator();
		while(edgesVertex.hasNext()){
			InternalEdge aux = edgesVertex.next();
			if(aux.target == otherVertex && aux.edge == theEdge){
				edgesVertex.remove();
				ans = true;
			}
		}
		Iterator<InternalEdge> edgesOtherVertex = getAdjacencyList().get(otherVertex).iterator();
		while(edgesOtherVertex.hasNext()){
			InternalEdge aux = edgesOtherVertex.next();
			if(aux.target == otherVertex && aux.edge == theEdge){
				edgesOtherVertex.remove();
				ans = true;
			}
		}
		return ans;
	}

	@Override
	public void dump() {
		// COMPLETAR
		throw new RuntimeException("not implemented yet");
	}
	
	
	@Override
	public int degree(V aVertex) {
		if(!isSimple){
			throw new RuntimeException("Degree is not valid in Grafos Dirigidos");
		}
		int ans = getAdjacencyList().get(aVertex).size();
		return ans;
	}

	@Override
	public int inDegree(V aVertex) {
		if(isSimple){
			throw new RuntimeException("Degree is not valid in Grafos Dirigidos");
		}
		int ans = 0;
		for(Map.Entry<V,Collection<InternalEdge>> vertex : getAdjacencyList().entrySet()){
			for(InternalEdge edge : vertex.getValue()){
				if(edge.target.equals(aVertex)){
					ans++;
				}
			}
		}
		return ans;
	}

	@Override
	public int outDegree(V aVertex) {
		if(isSimple){
			throw new RuntimeException("Degree is not valid in Grafos Dirigidos");
		}
		int ans = getAdjacencyList().get(aVertex).size();
		return ans;
	}

	public void printBFS(V vertex){
		Queue<V> vertexQueue = new LinkedList<>();


	}
	class InternalEdge {
		E edge;
		V target;

		InternalEdge(E propEdge, V target) {
			this.target = target;
			this.edge = propEdge;
		}

		@Override
		public boolean equals(Object obj) {
			@SuppressWarnings("unchecked")
			InternalEdge aux = (InternalEdge) obj;

			return ((edge == null && aux.edge == null) || (edge != null && edge.equals(aux.edge)))
					&& target.equals(aux.target);
		}

		@Override
		public int hashCode() {
			return target.hashCode();
		}

		@Override
		public String toString() {
			return String.format("-[%s]-(%s)", edge, target);
		}
	}

	public static void main(String[] args) {
//		GraphService <Character,EmptyEdgeProp> g =
//				new GraphBuilder<Character, EmptyEdgeProp>().
//						withMultiplicity(Multiplicity.SIMPLE).
//						withDirected(EdgeMode.UNDIRECTED).
//						withAcceptSelfLoop(SelfLoop.NO).
//						withAcceptWeight(Weight.NO).
//						withStorage(Storage.SPARSE).
//						build();
//		g.addEdge('E', 'B', new EmptyEdgeProp());
//		g.addEdge('A', 'B', new EmptyEdgeProp());
//		g.addEdge('F', 'B', new EmptyEdgeProp());
//		g.addVertex('D');
//		g.addVertex('G');
//		g.addEdge('E', 'F', new EmptyEdgeProp());
//		g.addEdge('F', 'A', new EmptyEdgeProp());
//		g.addEdge('F', 'G', new EmptyEdgeProp());
//		g.addEdge('U', 'G', new EmptyEdgeProp());
//		g.addEdge('T', 'U', new EmptyEdgeProp());
//		g.addEdge('C', 'G', new EmptyEdgeProp());
//
//		int ans =  g.numberOfEdges();
//		System.out.println(ans);

		GraphService <Character,EmptyEdgeProp> g =
				new GraphBuilder<Character, EmptyEdgeProp>().
						withMultiplicity(Multiplicity.MULTIPLE).
						withDirected(EdgeMode.DIRECTED).
						withAcceptSelfLoop(SelfLoop.YES).
						withAcceptWeight(Weight.NO).
						withStorage(Storage.SPARSE).
						build();

		g.addVertex('D');
		g.addVertex('G');
		g.addEdge('G', 'F', new EmptyEdgeProp());
		g.addEdge('U', 'G', new EmptyEdgeProp());
		g.addEdge('U', 'G', new EmptyEdgeProp());
		g.addEdge('F', 'F', new EmptyEdgeProp());
		g.addEdge('F', 'F', new EmptyEdgeProp());
		g.removeVertex('G');

		System.out.println( g.inDegree('G') );     // 2
		System.out.println( g.outDegree('G') );  // 1


		System.out.println( g.inDegree('F') );     // 3
		System.out.println( g.outDegree('F') );  //  2

	}
	
	
}
