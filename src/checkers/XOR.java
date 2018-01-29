package checkers;

import objects.TeamData;

public class XOR extends Condition {
	private Checker c1;
	private Checker c2;
	
	public XOR(Checker a, Checker b) {
		c1 = a;
		c2 = b;
	}
	public boolean check(TeamData td) {
		return (c1.check(td) || c2.check(td)) && !(c1.check(td) && c2.check(td));
	}
	public String toString() {
		return c1.toString() + " ^ " + c2.toString();
	}
}
