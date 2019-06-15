
public class FlyingInsect implements IFlyingInsect, IInsect {

	@Override
	public void fly() {
		System.out.println("Flap flap!");
	}

	@Override
	public void moveAntennae() {
		System.out.println("Moving my antennae in the air!");
	}
}
