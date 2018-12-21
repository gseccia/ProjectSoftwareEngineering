package Unit.src.map;

import blocks.ConcreteBlockFactory;
import configuration.*;
import blocks.Block;
import map.*;
import org.jgrapht.GraphTests;
import org.junit.*;
import static org.junit.Assert.*;

public class MapGraphTest {
    private MapGraph map;

    @Before
    public  void initialize() throws NoSuchElementInConfigurationException {
        map = new MapGraph(8, MapConfiguration.getInstance(), new ConcreteBlockFactory());
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
    public void testThatGivenABlockTheReturnVertexIsNotNull(){
        Vertex v = new Vertex(1,"cucina", true ,2);
        Block block = new ConcreteBlockFactory().generateBlock(v.getId(), v.getEl());
        v.setBlock(block);
        assertNotNull(v.getBlock());
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