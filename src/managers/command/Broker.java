package managers.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Asks the command to carry out the request.
 * 
 * @author Ilaria
 */
public class Broker {

	private List<CommandInterface> commandList = new ArrayList<CommandInterface>();
	
	/**
     * Constructor
     */
	public Broker() {
	}

	/**
     * Append command to a list to be executed
     * @param c add command to execute
     */
	public void takeCommand( CommandInterface c ) {
		commandList.add(c);
	}
	
	/*
	 * Calls execute method per each command in the list
	 */
	public void executeCommand() {
		for (CommandInterface command : commandList) {
			command.execute();
		}
		commandList.clear();
	}
}
