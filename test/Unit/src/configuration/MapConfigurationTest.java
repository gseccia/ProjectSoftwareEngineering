package Unit.src.configuration;

import configuration.MapConfiguration;
import configuration.NoSuchElementInConfigurationException;
import org.junit.*;


import static org.junit.Assert.*;

public class MapConfigurationTest {
    private MapConfiguration d;

    @Before
    public void setUp(){
        this.d = MapConfiguration.getInstance();
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

    @Test
    public void testMobCapacityIsGivenCorrectly() throws NoSuchElementInConfigurationException {
        assertEquals(2, d.getMobCapacity("Classroom"));
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testMobcapacityThrowsExceptionWithWrongNamePassed() throws NoSuchElementInConfigurationException {
        d.getMobCapacity("no");
    }

    @Test
    public void testItemCapacityIsGivenCorrectly() throws NoSuchElementInConfigurationException {
        assertEquals(3, d.getItemCapacity("Classroom"));
    }

    @Test(expected = NoSuchElementInConfigurationException.class)
    public void testItemcapacityThrowsExceptionWithWrongNamePassed() throws NoSuchElementInConfigurationException {
        d.getMobCapacity("no");
    }
}

