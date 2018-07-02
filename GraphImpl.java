import java.util.*;

public class GraphImpl<E> implements Graph<E> {

    private Map<E, Map<E, Integer>> graph;
    private int numEdges;
    private boolean weighted;

    /**
     * Constructor for a generic graph implementation
     * Creates a new GraphImpl object
     * @param weighted Defines whether or not the new Graph object is to be weighted
     */

    public GraphImpl(boolean weighted) {
        graph = new HashMap<>();
        this.weighted = weighted;
    }

    /**
     * Gets the current state of the graph
     * @return Current state of graph
     */

    public Map<E, Map<E, Integer>> getGraph() {
        return graph;
    }

    /**
     * Gets the total number of vertices in the graph
     * @return Count of vertices
     */

    @Override
    public int getNumVertices() {
        return graph.size();
    }

    /**
     * Gets the total number of edges in the graph
     * @return Count of edges
     */

    @Override
    public int getNumEdges() {
        return numEdges;
    }

    /**
     * Gets the degree of the input vertex
     * @param vertex Vertex to retrieve the degree of
     * @return Degree of the input vertex
     */

    @Override
    public int degree(E vertex) {
        try {
            if (hasVertex(vertex)) {
                return neighbours(vertex).size();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Adds the input vertex to the graph
     * @param vertex Vertex to add to the graph
     */

    @Override
    public void addVertex(E vertex) {
        if (!hasVertex(vertex)) {
            graph.put(vertex, new HashMap<>());
        }
    }

    /**
     * Adds a collection of vertices to the graph
     * @param vertices Collection to add to the graph
     */

    @Override
    public void addVertices(Collection<E> vertices) {
        for (E vertex : vertices) {
            addVertex(vertex);
        }
    }

    /**
     * Removes the input vertex if it is contained in the graph and removes all edges connected to it
     * @param vertex Vertex to remove
     */

    @Override
    public void removeVertex(E vertex) {
        if (hasVertex(vertex)) {
            for (E curVertex : graph.keySet()) {
                if (hasEdge(vertex, curVertex) || hasEdge(curVertex, vertex)) {
                    removeEdge(vertex, curVertex);
                }
            }
            graph.remove(vertex);
        }
    }


    /**
     * Checks if the input vertex is contained in the graph
     * @param vertex Vertex that is to be checked
     * @return true if the graph contains the vertex, false if otherwise
     */

    @Override
    public boolean hasVertex(E vertex) {
        return graph.containsKey(vertex);
    }

    /**
     * Adds an edge to the graph between the two input vertices
     * @param from Vertex to add an edge from
     * @param to Vertex to add an edge to
     * @param weight Desired weight between vertices, if graph is not weighted 1 is used
     * @return true if the graph contains both vertices and we can add an edge between them, false if otherwise
     */

    @Override
    public boolean addEdge(E from, E to, int weight) {
        if (!hasVertex(to) || !hasVertex(from)) return false;
        if (weighted) {
            graph.get(from).put(to, weight);
        } else {
            graph.get(from).put(to, 1); 
            graph.get(to).put(from, 1);
        }
        numEdges++;
        return true;

    }

    /**
     * Removes an edge between the two input vertices
     * @param from Vertex to remove an edge from
     * @param to Vertex to remove an edge from
     * @return True if there exists an edge that is removable between the two vertices, false if otherwise
     */

    @Override
    public boolean removeEdge(E from, E to) {
        if (hasEdge(from, to)) {
            graph.get(from).remove(to);
            if (!weighted) {
                graph.get(to).remove(from);
            }
            numEdges--;
            return true;
        }
        return false;
    }

    /**
     * Checks if there exists an edge between the two input vertices
     * @param from Vertex that is to be checked
     * @param to Vertex that is to be checked
     * @return true if there exists an edge between the two vertices, false if otherwise
     */

    @Override
    public boolean hasEdge(E from, E to) {
        if (weighted) {
            return hasVertex(from) && hasVertex(to) && graph.get(from).containsKey(to);
        }
        return hasVertex(from) && hasVertex(to) && (graph.get(from).containsKey(to) || graph.get(to).containsKey(from));
    }

    /**
     * Gets the set of vertices that are connected to the input vertex
     * @param vertex Vertex to retrieve neighbours of
     * @return Set of all vertices connected to the input vertex
     * @throws NoSuchFieldException if the input vertex does not exist in the graph
     */

    @Override
    public Set<E> neighbours(E vertex) throws NoSuchFieldException {
        if (graph.containsKey(vertex)) {
            return graph.get(vertex).keySet();
        }
        throw new NoSuchFieldException();
    }

    /**
     * Checks if this graph object is equal to the input Object
     * @param o Object that is being checked
     * @return true if this is equal to the input object, false if otherwise
     */

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;

        if (o.getClass().isInstance(this)) {
            for (E curVertex : graph.keySet()) {
                GraphImpl<E> otherGraph = (GraphImpl<E>)o;
                if (!otherGraph.getGraph().containsKey(curVertex) || otherGraph.getGraph().get(curVertex).size() != graph.get(curVertex).size()) {
                    return false;
                } else {
                    try {
                        for (E curNeighbour : neighbours(curVertex)) {
                            if (!otherGraph.getGraph().get(curVertex).containsKey(curNeighbour) || !otherGraph.getGraph().get(curVertex).get(curNeighbour).equals(graph.get(curVertex).get(curNeighbour))) {
                                return false;
                            }
                        }
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }
}
