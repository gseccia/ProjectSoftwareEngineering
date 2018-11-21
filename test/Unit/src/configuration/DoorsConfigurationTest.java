package Unit.src.configuration;

import configuration.DoorsConfiguration;
import configuration.NoSuchElementInConfigurationException;
import org.junit.Test;


import static org.junit.Assert.*;

public class DoorsConfigurationTest {

    @Test
    public void testMapNamesAreReadCorrectly(){
        DoorsConfiguration d = new DoorsConfiguration();
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
        String[] res = new String[d.getMapNames().toArray().length];
        for(int i=0; i<res.length; i++){
            res[i] = (String) d.getMapNames().toArray()[i];
        }
        assertArrayEquals(res, ref);
    }

    @Test
    public void testDoorsNumberAreReadCorrectly() throws NoSuchElementInConfigurationException {
        DoorsConfiguration d = new DoorsConfiguration();
        int doors = 2;
        assertEquals(d.getDoors("Hall"), doors);
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testGettingDoorsNumberCanThrowExceptionIfMapDoesntExists() throws NoSuchElementInConfigurationException{
        DoorsConfiguration d = new DoorsConfiguration();
        d.getDoors("No");
    }

    @Test
    public void testRandomDoorsAreGivenCorrectly() throws NoSuchElementInConfigurationException {
        DoorsConfiguration d = new DoorsConfiguration();
        int numDoors = 2;
        String map = d.getRandomGivenDoors(numDoors);
        assertEquals(numDoors, d.getDoors(map));
    }

    @Test
    public void testRandomDoorsWithInconsistentDoorsNumberReturnsNull() {
        DoorsConfiguration d = new DoorsConfiguration();
        assertEquals(d.getRandomGivenDoors(34), null);
    }

    @Test
    public void testRandomDoorsWithNegativeDoorsNumberReturnsNull() {
        DoorsConfiguration d = new DoorsConfiguration();
        assertEquals(d.getRandomGivenDoors(-1), null);
    }
}
