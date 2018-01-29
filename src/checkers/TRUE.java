package checkers;

import objects.TeamData;

public class TRUE extends Condition {
	public TRUE() {
		
	}
	public boolean check(TeamData td) {
		return true;
	}
	public String toString() {
		return "ALWAYS";
	}
}
