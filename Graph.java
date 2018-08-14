import java.util.Collection;
import java.util.Set;

public interface Graph<E> {

    void addVertex(E vertex);
    void addVertices(Collection<E> vertices);
    void removeVertex(E vertex);
    Set<E> getVertices();
    boolean addEdge(E v1, E v2, int weight);
    boolean removeEdge(E v1, E v2);
    boolean hasVertex(E vertex);
    boolean hasEdge(E v1, E v2);
    int getNumVertices();
    int getNumEdges();
    int degree(E vertex);
    Set<E> neighbours(E vertex) throws NoSuchFieldException;
    int getWeight(E from, E to) throws NoSuchFieldException;
    boolean equals(Object o);



}
