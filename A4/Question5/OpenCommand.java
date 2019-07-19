
public class OpenCommand extends Command {

	@Override
	public String execute() {
		return "open -f <path> [-create=0/1]";
	}
}
