package Unit.src.configuration;

import map.Edge;
import map.Vertex;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.junit.Assert;
import org.junit.Test;

public class EdgeTest {

    @Test
    public void testThatGetAPortSourceCorrectly(){
        Vertex v1 = new Vertex(1,"cucina", true, 2);
        Vertex v2 = new Vertex (2, "salotto", false, 3);
        DefaultUndirectedGraph<Vertex, Edge> graph = new DefaultUndirectedGraph<>(Edge.class);
        graph.addVertex(v1); graph.addVertex(v2);
        graph.addEdge(v1, v2, new Edge(v1.getDoorNumber(),v2.getDoorNumber()));
        Edge edge = graph.getEdge(v1, v2);
        int result = edge.getPortSource(v1);
        Assert.assertEquals(result,2);
    }

    @Test
    public void testThatGetAPortDestinationCorrectly(){
        Vertex v1 = new Vertex(1,"cucina", true, 2);
        Vertex v2 = new Vertex (2, "salotto", false, 3);
        DefaultUndirectedGraph<Vertex, Edge> graph = new DefaultUndirectedGraph<>(Edge.class);
        graph.addVertex(v1); graph.addVertex(v2);
        graph.addEdge(v1, v2, new Edge(v1.getDoorNumber(),v2.getDoorNumber()));
        Edge edge = graph.getEdge(v1, v2);
        int result = edge.getPortDestination(v1);
        Assert.assertEquals(result,3);
    }

    @Test
    public void testThatGetTheOppositeCorrectly(){
        Vertex v1 = new Vertex(1,"cucina", true, 2);
        Vertex v2 = new Vertex (2, "salotto", false, 3);
        DefaultUndirectedGraph<Vertex, Edge> graph = new DefaultUndirectedGraph<>(Edge.class);
        graph.addVertex(v1); graph.addVertex(v2);
        graph.addEdge(v1, v2, new Edge(v1.getDoorNumber(),v2.getDoorNumber()));
        Edge edge = graph.getEdge(v1, v2);
        Vertex result = edge.opposite(v1);
        Assert.assertEquals(result, v2);
    }
}
