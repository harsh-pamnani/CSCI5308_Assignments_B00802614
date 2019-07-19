
public class PrintCommand extends Command {

	@Override
	public String execute() {
		return "print -f <path> [-colour=0/1] [-two-sided=0/1]";
	}
}
