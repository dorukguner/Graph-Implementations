import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface Graph<E> {

    Map<E, Set<E>> getGraph();
    void addVertex(E vertex);
    void addVertices(Collection<E> vertices);
    void removeVertex(E vertex);
    boolean addEdge(E v1, E v2);
    boolean removeEdge(E v1, E v2);
    boolean hasVertex(E vertex);
    boolean hasEdge(E v1, E v2);
    int getNumVertices();
    int getNumEdges();
    int degree(E vertex);
    Set<E> neighbours(E vertex) throws NoSuchFieldException;
    boolean equals(Object o);



}
