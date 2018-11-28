package Unit.src.map;

import map.Edge;
import map.Vertex;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EdgeTest {

    private Vertex v1;
    private Vertex v2;
    private DefaultUndirectedGraph<Vertex, Edge> graph;

    @Before
    public void setUp(){
        this.v1 = new Vertex(1,"cucina", true, 2);
        this.v2 = new Vertex (2, "salotto", false, 3);
        this.graph = new DefaultUndirectedGraph<>(Edge.class);
        graph.addVertex(v1); graph.addVertex(v2);

        graph.addEdge(v1, v2, new Edge(v1.getDoorNumber(),v2.getDoorNumber()));

    }

    @Test
    public void testThatGetAPortSourceCorrectly(){
        Edge edge = graph.getEdge(v1, v2);
        int result = edge.getPortSource(v1);
        Assert.assertEquals(result,2);
    }

    @Test
    public void testThatGetAPortDestinationCorrectly(){
        Edge edge = graph.getEdge(v1, v2);
        int result = edge.getPortDestination(v1);
        Assert.assertEquals(result,3);
    }

    @Test
    public void testThatGetTheOppositeCorrectly(){
        Edge edge = graph.getEdge(v1, v2);
        Vertex result = edge.opposite(v1);
        Assert.assertEquals(result, v2);
    }
}
