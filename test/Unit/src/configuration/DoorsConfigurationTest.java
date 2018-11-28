package Unit.src.configuration;

import configuration.DoorsConfiguration;
import configuration.NoSuchElementInConfigurationException;
import org.junit.*;


import static org.junit.Assert.*;

public class DoorsConfigurationTest {
    private DoorsConfiguration d;

    @Before
    public void setUp(){
        this.d = new DoorsConfiguration();
    }

    @Test
    public void testMapNamesAreReadCorrectly(){
        String[] ref = new String[]{
                "Classroom",
                "Hall",
                "Hallway",
                "Lab",
                "Lab162",
                "Library1",
                "Library2",
                "Parking",
                "Square",
                "Toilet"
        };
        String[] res = new String[this.d.getMapNames().toArray().length];
        for(int i=0; i<res.length; i++){
            res[i] = (String) this.d.getMapNames().toArray()[i];
        }
        assertArrayEquals(res, ref);
    }

    @Test
    public void testDoorsNumberAreReadCorrectly() throws NoSuchElementInConfigurationException {
        int doors = 2;
        assertEquals(this.d.getDoors("Hall"), doors);
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testGettingDoorsNumberCanThrowExceptionIfMapDoesntExists() throws NoSuchElementInConfigurationException{
        this.d.getDoors("No");
    }

    @Test
    public void testRandomDoorsAreGivenCorrectly() throws NoSuchElementInConfigurationException {
        int numDoors = 2;
        String map = this.d.getRandomGivenDoors(numDoors);
        assertEquals(numDoors, d.getDoors(map));
    }

    @Test
    public void testRandomDoorsWithInconsistentDoorsNumberReturnsNull() {
        assertNull(this.d.getRandomGivenDoors(34));
    }

    @Test
    public void testRandomDoorsWithNegativeDoorsNumberReturnsNull() {
        assertNull(this.d.getRandomGivenDoors(-1));
    }
}
