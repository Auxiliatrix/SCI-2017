package checkers;

import objects.TeamData;

public interface Checker {
	public boolean check(TeamData td);
	public String toString();
	public boolean equals(Checker c);
	public boolean equals(String s);
}
