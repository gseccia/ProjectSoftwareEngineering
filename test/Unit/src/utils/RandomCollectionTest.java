package Unit.src.utils;

import org.junit.Before;
import org.junit.Test;
import utils.RandomCollection;

import java.util.Iterator;

import static org.junit.Assert.*;

public class RandomCollectionTest {

    private long seed = 123456789;
    private RandomCollection<Integer> rhs;

    @Before
    public void setUp(){
        rhs = new RandomCollection<>(seed);
        for(int i=0; i<5; i++){
            rhs.add(i);
        }
    }

    @Test
    public void testObjectsOfTheSetAreReturnedPseudoRandomly(){
        int[] res = new int[]{0,3,2,4,1};
        int i = 0;
        Iterator it = rhs.iterator();
        while(it.hasNext() && i<res.length){
            assertEquals(it.next(), res[i]);
            i++;
        }
        assertFalse(it.hasNext());
    }

}
