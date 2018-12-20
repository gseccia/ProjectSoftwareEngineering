package Unit.src.managers.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import managers.MusicManager;
import managers.ResourceManager;
import managers.command.Broker;
import managers.command.IncreaseVolumeCommand;

public class IncreaseVolumeCommandTest {

	private MusicManager musicManager;
	private IncreaseVolumeCommand ivc;
	private Broker b;

	@Before
	public void setUp() throws Exception {
		musicManager = MusicManager.getInstance(ResourceManager.getInstance());
		musicManager.setVolume(0.42f);
		ivc = IncreaseVolumeCommand.getInstance();
		b = new Broker();
	}

	@Test
	public void testSingleton() {
		assertNotNull(ivc);
	}
	@Test
	public void testExecute() {
		b.takeCommand(ivc);
		b.executeCommand();
		assertEquals(0.43f, musicManager.getVolume(), 0.0002);
	}
	
	@Test(expected = NullPointerException.class)
    public void testVisitorThrowsExceptionIfPlayerIsNull(){
        b.takeCommand(null);
        b.executeCommand();
    }
}
