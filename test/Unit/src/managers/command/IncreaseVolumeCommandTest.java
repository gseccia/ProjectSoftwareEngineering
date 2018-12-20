package Unit.src.managers.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import managers.MusicManager;
import managers.command.Broker;
import managers.command.IncreaseVolumeCommand;

class IncreaseVolumeCommandTest {

	private MusicManager mockMusicManager;
	private IncreaseVolumeCommand ivc;
	private Broker b;

	@Before
	void setUp() throws Exception {
		mockMusicManager = Mockito.mock(MusicManager.class);
		ivc = Mockito.mock(IncreaseVolumeCommand.class);
		Mockito.when(this.mockMusicManager.getVolume()).thenReturn(42f);

		b = new Broker();
		b.takeCommand(ivc);
	}

	@Test
	void testSingleton() {
		IncreaseVolumeCommand d = IncreaseVolumeCommand.getInstance();
		assertNotNull(d);
	}
	@Test
	void testExecute() {
		b.executeCommand();
		assertEquals(42.01f, mockMusicManager.getVolume());
	}
}
