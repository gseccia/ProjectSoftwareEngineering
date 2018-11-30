package Unit.src.map;

import configuration.*;
import main.Block;
import map.*;
import org.jgrapht.GraphTests;
import org.junit.*;
import static org.junit.Assert.*;

public class MapGraphTest {
    private MapGraph map;

    @Before
    public  void initialize() throws NoSuchElementInConfigurationException {
        map = new MapGraph(8, new DoorsConfiguration());
        map.generateGraph();
    }

    @Test
    public void testThatGeneratedGraphIsConnected(){
        assertTrue(GraphTests.isConnected(map.getGraph()));
    }

    @Test
    public void testThatGeneratedConnectedGraphIsNotNull(){
        assertNotNull(map.getGraph());
    }

    @Test
    public void testThatGivenABlockTheReturnVertexHasSameId(){
        Vertex v = new Vertex(1,"cucina", true ,2);
        Block block = new Block(v.getId(), v.getEl());
        assertEquals(map.getVertex(block).getId(), 1);
    }

    @Test
    public void testThatGivenABlockTheReturnVertexIsNotNull(){
        Vertex v = new Vertex(1,"cucina", true ,2);
        Block block = new Block(v.getId(), v.getEl());
        assertNotNull(map.getVertex(block));
    }

    @Test
    public void testThatBlockListHasSameItemAsVertexSet(){
        assertEquals(map.generateBlock().size(), map.vertex().size());
    }

    @Test
    public void testThatGeneratedBlocksAreEqualToNumberOfVertex(){
        assertEquals(map.generateBlock().size(),map.vertex().size());
    }

}