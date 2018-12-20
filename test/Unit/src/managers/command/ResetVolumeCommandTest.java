package Unit.src.managers.command;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import managers.MusicManager;
import managers.ResourceManager;
import managers.command.Broker;
import managers.command.ResetVolumeCommand;

public class ResetVolumeCommandTest {

	private MusicManager musicManager;
	private ResetVolumeCommand rvc;
	private Broker b;

	@Before
	public void setUp() throws Exception {
		musicManager = MusicManager.getInstance(ResourceManager.getInstance());
		musicManager.setVolume(0.42f);
		rvc = ResetVolumeCommand.getInstance();
		b = new Broker();
	}

	@Test
	public void testSingleton() {
		assertNotNull(rvc);
	}
	@Test
	public void testExecute() {
		b.takeCommand(rvc);
		b.executeCommand();
		assertEquals(musicManager.DEFAULT_VOLUME, musicManager.getVolume(), 0.0002);
	}

	@Test(expected = NullPointerException.class)
    public void testVisitorThrowsExceptionIfPlayerIsNull(){
        b.takeCommand(null);
        b.executeCommand();
    }
}
