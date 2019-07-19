import java.util.HashMap;

public class Help
{
	private static HashMap<String, Command> commandsMap;
	private static String allCommands;
	
	public Help() {
		commandsMap = new HashMap<String, Command>();
		commandsMap.put("print", new PrintCommand());
		commandsMap.put("open", new OpenCommand());
		commandsMap.put("close", new CloseCommand());
		
		allCommands = "Commands: print, open, close";
	}
	
	public String getHelp(String command)
	{
		if (command != null && command.length() != 0)
		{
			Command commandReceived =  commandsMap.get(command);
			return commandReceived.execute();
		}
		return allCommands;
	}
}