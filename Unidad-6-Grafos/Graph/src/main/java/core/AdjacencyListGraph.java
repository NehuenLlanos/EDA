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
	
	protected   Map<V, Collection<InternalEdge>> getAdjacencyList() {
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
	
		if (aVertex == null ){
			throw new IllegalArgumentException(Messages.getString("Parameter cannot be null."));
		}
		// Colocamos el vértice solamente sino no esta creado
		getAdjacencyList().putIfAbsent(aVertex, new ArrayList<InternalEdge>());
	}

	@Override
	public int numberOfVertices() {
		return getVertices().size();
	}

	@Override
	public Collection<V> getVertices() {
		return getAdjacencyList().keySet() ;
	}

	// Cantidad de ejes en mi grafo
	@Override
	public int numberOfEdges() {
		return isDirected ? edgesCounter() : edgesCounter() / 2;
	}
	private int edgesCounter(){
		int ans = 0;
		for(Map.Entry<V,Collection<InternalEdge>> aux : getAdjacencyList().entrySet()){
			ans += aux.getValue().size();
		}
		return ans;
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
		if(!adjacencyList.containsKey(aVertex)){
			return false;
		}
		/* Tengo dos casos. Si no es dirigo tengo que borrar todas las apariciones del nodo
		*  en los demas nodos y luego el vertice en si.
		*  Si tengo un dirigido, es decir, tengo que borrar todas las apariciones solamente
		*  en los demas nodos.
		* */

		if(isDirected){
			// Si es dirigido tengo que recorrer todos los vertices del mapa y remover todas las aristas
			// conectadas con aVertex
			// Recorro todos los vértices del grafo.
			for(Map.Entry<V, Collection<InternalEdge>> entry : adjacencyList.entrySet()){
				// Recorro la coleccion de aristas de cada uno de los vértices mediante un iterador
				Iterator<InternalEdge> it = entry.getValue().iterator();
				while(it.hasNext()){
					// Si el current tiene como target al aVertex entonces lo borro.
					if(it.next().target.equals(aVertex)){
						it.remove();
					}
				}
			}
		}else{
			// Recorro la coleccion de aristas del vértice a borrar
			for(InternalEdge edge : getAdjacencyList().get(aVertex)){
				// Obtenemos la coleccion de aristas del vértice de la arista en la que estoy
				Collection<InternalEdge> collection = getAdjacencyList().get(edge.target);
				// Iterator que va a recorrer el vértice que se conecta con el vértice a borrar
				Iterator<InternalEdge> it = collection.iterator();
				while(it.hasNext()){
					// Borro la arista de la colección de aristas que tiene el vértice que se conecta con el aVertex
					if(it.next().target.equals(aVertex)){
						it.remove();
					}
				}
			}
		}
		// Finalmente elimino el vértice del mapa
		getAdjacencyList().remove(aVertex);
		return true;
	}

	// Permite remover todas las aristas entre un vertice y otro.
	@Override
	public boolean removeEdge(V aVertex, V otherVertex) {
		if(!isSimple){
			throw new RuntimeException("This method is not valid for multigragh or digraph.");
		}
		if(aVertex == null || otherVertex == null){
			throw new IllegalArgumentException("No se permite vertices null");
		}
		boolean ans = false;
		// Recorro todas las aristas del aVertex
		Iterator<InternalEdge> edgesVertex = getAdjacencyList().get(aVertex).iterator();
		while(edgesVertex.hasNext()){
			// Si encuentro una que se conecta con otherVertex la elimino y pongo en true el flag ans
			if(edgesVertex.next().target == otherVertex){
				edgesVertex.remove();
				ans = true;
			}
		}
		// Recorro todas las aristas del otherVertex
		Iterator<InternalEdge> edgesOtherVertex = getAdjacencyList().get(otherVertex).iterator();
		while(edgesOtherVertex.hasNext()){
			// Si encuentro una que se conecta con otherVertex la elimino y pongo en true el flag ans
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
	
	// Solamente para grafos no dirigidos
	@Override
	public int degree(V aVertex) {
		// Si es dirigido tengo que lanzar exception
		if(isDirected){
			throw new RuntimeException("Degree is not valid for Directed Graphs");
		}
		int ans = getAdjacencyList().get(aVertex).size();
		return ans;
	}

	// Solamente para grafos dirigidos
	@Override
	public int inDegree(V aVertex) {
		if(!isDirected){
			throw new RuntimeException("InDegree is not valid for inDirected Graphs");
		}
		int ans = 0;
		// Recorro todos los vértices del mapa
		for(Map.Entry<V,Collection<InternalEdge>> vertex : getAdjacencyList().entrySet()){
			// Recorro todas las aristas de cada vértice del mapa
			for(InternalEdge edge : vertex.getValue()){
				// Si la arista apunta al aVertex entonces incremento ans
				if(edge.target.equals(aVertex)){
					ans++;
				}
			}
		}
		// Retorno ans que es donde guardo el grado del vértice aVertex
		return ans;
	}
	@Override
	public int outDegree(V aVertex) {
		if(!isDirected){
			throw new RuntimeException("OutDegree is not valid for inDirected Graphs");
		}
		// Para saber el grado de salida de un dirigido lo que tengo que hacer es contar la cantidad de aristas
		// adyacentes al mismo, entonces obtengo el size de la collection donde se guardan las aristas.
		int ans = getAdjacencyList().get(aVertex).size();
		return ans;
	}


	public void printBFS(V startNode){
//		if(startNode == null /*|| !existsVertex(startNode*/)){
//			throw new IllegalArgumentException(Messages.getString("Vertex Parameter Error."));
//		}
		if(startNode == null){
			throw new IllegalArgumentException(Messages.getString("Vertex Parameter Error."));
		}
		Set<V> visited = new HashSet<>();
		Queue<V> theQueue = new LinkedList<>();
		theQueue.add(startNode);
		while(!theQueue.isEmpty()){
			V current = theQueue.poll();
			if(visited.contains(current)){
				continue;
			}
			visited.add(current);
			System.out.println(current);
			Collection<InternalEdge> adjListOther = getAdjacencyList().get(current);
			for(InternalEdge internalEdge : adjListOther){
				if(!visited.contains(internalEdge.target)){
					theQueue.add(internalEdge.target);
				}
			}
		}
	}

	public void printAllPaths(V startNode, V endNode){
		if(startNode == null || endNode == null){
			throw new IllegalArgumentException(Messages.getString("Parameter error"));
		}

		if(acceptSelfLoop){
			throw new IllegalArgumentException(Messages.getString("Get all paths error"));
		}

		Set<V> visited = new HashSet<>();
		ArrayList<V> path = new ArrayList<>();

		printAllPaths(startNode, endNode, visited,  path);
	}

	public void printAllPaths(V startNode, V endNode, Set<V> visited, ArrayList<V> path){
		path.add(startNode);
		visited.add(startNode);
		if(startNode.equals(endNode)){
			System.out.println(path);

			// Deshago porque no voy a pasar por flujo normal
			visited.remove(endNode);
			path.remove(endNode);

			return;
		}

		Collection<InternalEdge> adjListOther = getAdjacencyList().get(startNode);
		for(InternalEdge internalEdge : adjListOther){
			if(!visited.contains(internalEdge.target)){
				printAllPaths(internalEdge.target, endNode, visited, path);
			}
		}
		// Deshago el vertice
		visited.remove(startNode);
		path.remove(startNode);
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
