package managers.command;

import java.util.ArrayList;
import java.util.List;

public class Broker {

	private List<CommandInterface> commandList = new ArrayList<CommandInterface>();
	
	public Broker() {
	}

	public void takeCommand( CommandInterface c ) {
		commandList.add(c);
	}
	
	public void executeCommand() {
		for (CommandInterface command : commandList) {
			command.execute();
		}
		commandList.clear();
	}
}
