package Unit.src.managers.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import managers.MusicManager;
import managers.command.Broker;
import managers.command.DecreaseVolumeCommand;

class DecreaseVolumeCommandTest {
	private MusicManager mockMusicManager;
	private DecreaseVolumeCommand dvc;
	private Broker b;

	@Before
	void setUp() throws Exception {
		mockMusicManager = Mockito.mock(MusicManager.class);
		dvc = Mockito.mock(DecreaseVolumeCommand.class);
		Mockito.when(this.mockMusicManager.getVolume()).thenReturn(42f);

		b = new Broker();
		b.takeCommand(dvc);
	}

	@Test
	void testSingleton() {
		DecreaseVolumeCommand d = DecreaseVolumeCommand.getInstance();
		assertNotNull(d);
	}
	@Test
	void testExecute() {
		b.executeCommand();
		assertEquals(41.99f, mockMusicManager.getVolume());
	}
}
