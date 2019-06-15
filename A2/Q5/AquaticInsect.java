
public class AquaticInsect implements IAquaticInsect, IInsect {
	
	@Override
	public void swim() {
		System.out.println("Sploosh!");
	}

	@Override
	public void moveAntennae() {
		System.out.println("Moving my antennae underwater!");
	}
}