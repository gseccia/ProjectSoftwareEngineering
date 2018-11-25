package Unit.src.utils;

import org.junit.Before;
import org.junit.Test;
import utils.RandomHashSet;

import java.util.Iterator;

import static org.junit.Assert.*;

public class RandomHashSetTest {

    private long seed = 123456789;
    private RandomHashSet<Integer> rhs;

    @Before
    public void setUp(){
        rhs = new RandomHashSet<>(seed);
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
