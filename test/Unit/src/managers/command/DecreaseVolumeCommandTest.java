package Unit.src.managers.command;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import managers.MusicManager;
import managers.ResourceManager;
import managers.command.Broker;
import managers.command.DecreaseVolumeCommand;

public class DecreaseVolumeCommandTest {
	private MusicManager musicManager;
	private DecreaseVolumeCommand dvc;
	private Broker b;

	@Before
	public void setUp() throws Exception {
		musicManager = MusicManager.getInstance(ResourceManager.getInstance());
		musicManager.setVolume(0.42f);
		dvc = DecreaseVolumeCommand.getInstance();
		b = new Broker();
	}

	@Test
	public void testSingleton() {
		assertNotNull(dvc);
	}
	@Test
	public void testExecute() {
		b.takeCommand(dvc);
		b.executeCommand();
		assertEquals(0.41f, musicManager.getVolume(), 0.0002);
	}
	
	@Test(expected = NullPointerException.class)
    public void testVisitorThrowsExceptionIfPlayerIsNull(){
        b.takeCommand(null);
        b.executeCommand();
    }
	
}
