package core;

import java.util.Collection;



public class SimpleOrDefault<V,E> extends AdjacencyListGraph<V,E> {

	protected SimpleOrDefault(boolean isDirected, boolean acceptSelfLoops, boolean isWeighted) {
		super(true, isDirected, acceptSelfLoops, isWeighted);
	
	}
	
	@Override
	public void addEdge(V aVertex, V otherVertex, E theEdge) {

		// Llamamos a addEdge de la clase AdjacencListGraph
		// Allí se valida y se crean los vértices si es necesario para poder agrregar el Edge.
		super.addEdge(aVertex, otherVertex, theEdge);

		// Guardamos todas las aristas que posee el vértice aVertex
		Collection<InternalEdge> adjListSrc = getAdjacencyList().get(aVertex);

		// Lanza una excepcion si se intenta agregar mas de una arista entre dos mismos nodos.
		// if exists edge with same target...
		for (InternalEdge internalEdge : adjListSrc) {
			if (internalEdge.target.equals(otherVertex)) {
				throw new IllegalArgumentException(
						String.format(Messages.getString("addEdgeSimpleOrDefaultError"), aVertex, otherVertex)); //$NON-NLS-1$
			}
		}
		

		// Creación de los ejes
		adjListSrc.add(new InternalEdge(theEdge, otherVertex));

		// Si no es dirigido entonces dejemos agregar los ejes en el vértice otherVertex.
		if (!isDirected ) {
			Collection<AdjacencyListGraph<V, E>.InternalEdge> adjListDst = getAdjacencyList().get(otherVertex);
			adjListDst.add(new InternalEdge(theEdge, aVertex));
		}
	
	}
	
	






}
