package Unit.configuration;

import main.java.Vertex;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class vertexTest {

    @Test
    public void testThatANodeCanBeGreaterThenAnother(){
        Vertex v1 = new Vertex(1,"cucina",true,2);
        Vertex v2 = new Vertex(0,"salone",true ,1);
        int result = v1.compareTo(v2);
        assertEquals(result,-1);
    }

    @Test
    public void testThatANodeCanBeEqualThenAnother(){
        Vertex v1 = new Vertex(0,"cucina",true,2);
        Vertex v2 = new Vertex(1,"salone",true ,2);
        int result = v1.compareTo(v2);
        assertEquals(result,0);
    }

    @Test
    public void testThatANodeCanBeSmallerThenAnother(){
        Vertex v1 = new Vertex(1,"cucina",true,1);
        Vertex v2 = new Vertex(2,"salone",true ,2);
        int result = v1.compareTo(v2);
        assertEquals(result,1);
    }
}
