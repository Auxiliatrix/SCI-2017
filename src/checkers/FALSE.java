package checkers;

public class FALSE extends Condition {
	public FALSE() {
		
	}
	public boolean check() {
		return false;
	}
	public String toString() {
		return "never";
	}
}
