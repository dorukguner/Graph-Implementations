import java.util.*;

public class UndirectedUnweightedGraph<E> implements Graph<E> {

    private HashMap<E, Set<E>> graph;
    private int numEdges;

    /**
     * Constructor for an undirected unweighted graph
     * Creates a new UndirectedUnweightedGraph object
     */

    public UndirectedUnweightedGraph() {
        graph = new HashMap<>();
    }

    /**
     * Gets the current state of the graph
     * @return Current state of graph
     */

    @Override
    public Map<E, Set<E>> getGraph() {
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
            graph.put(vertex, new HashSet<>());
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
                if (hasEdge(vertex, curVertex)) {
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
     * @param v1 Vertex to add an edge to
     * @param v2 Vertex to add an edge to
     * @return true if the graph contains both vertices and we can add an edge between them, false if otherwise
     */

    @Override
    public boolean addEdge(E v1, E v2) {
        if (!hasVertex(v1) || !hasVertex(v2)) return false;
        numEdges++;
        return graph.get(v1).add(v2) && graph.get(v2).add(v1);
    }

    /**
     * Removes an edge between the two input vertices
     * @param v1 Vertex to remove an edge from
     * @param v2 Vertex to remove an edge from
     * @return True if there exists an edge that is removable between the two vertices, false if otherwise
     */

    @Override
    public boolean removeEdge(E v1, E v2) {
        if (hasEdge(v1, v2) && graph.get(v1).remove(v2) && graph.get(v2).remove(v1)) {
            numEdges--;
            return true;
        }
        return false;
    }

    /**
     * Checks if there exists an edge between the two input vertices
     * @param v1 Vertex that is to be checked
     * @param v2 Vertex that is to be checked
     * @return true if there exists an edge between the two vertices, false if otherwise
     */

    @Override
    public boolean hasEdge(E v1, E v2) {
        return hasVertex(v1) && hasVertex(v2) && (graph.get(v1).contains(v2) || graph.get(v2).contains(v1));
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
            return graph.get(vertex);
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
                UndirectedUnweightedGraph<E> otherGraph = (UndirectedUnweightedGraph<E>)o;
                if (!otherGraph.getGraph().containsKey(curVertex) || otherGraph.getGraph().get(curVertex).size() != graph.get(curVertex).size()) {
                    return false;
                } else {
                    try {
                        for (E curNeighbour : neighbours(curVertex)) {
                            if (!otherGraph.getGraph().get(curVertex).contains(curNeighbour)) {
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
