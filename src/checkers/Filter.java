package checkers;
import java.util.ArrayList;

import objects.TeamData;

public class Filter extends Condition {
	private ArrayList<Checker> checkers;
	
	public Filter() {
		checkers = new ArrayList<Checker>();
	}
	
	public Filter(Checker c) {
		checkers = new ArrayList<Checker>();
		checkers.add(c);
	}
	
	public Filter(String s) {
		checkers = new ArrayList<Checker>();
		checkers.add(new Condition(s));
	}
	
	public Filter(ArrayList<Checker> checkers) {
		this.checkers = checkers;
	}
	
	public void addChecker(Checker c) {
		checkers.add(c);
	}
	public void addChecker(String s) {
		checkers.add(new Condition(s));
	}
	public ArrayList<Checker> getCheckers() {
		return checkers;
	}
	public boolean removeChecker(Checker c) {
		int index = -1;
		for( int f=0; f<checkers.size(); f++ ) {
			if( checkers.get(f).equals(c) )
				index = f;
		}
		if( index != -1 ) {
			checkers.remove(index);
			return true;
		} else {
			return false;
		}
	}
	public boolean removeChecker(String s) {
		int index = -1;
		for( int f=0; f<checkers.size(); f++ ) {
			if( checkers.get(f).equals(s) )
				index = f;
		}
		if( index != -1 ) {
			checkers.remove(index);
			return true;
		} else {
			return false;
		}
	}
	public boolean removeChecker(int i) {
		if( 0 < i && i <= checkers.size() ) {
			checkers.remove(i-1);
			return true;
		} else {
			return false;
		}
	}
	public boolean check(TeamData td) {
		for( Checker c : checkers ) {
			if( !c.check(td) ) {
				return false;
			}
		}
		return true;
	}
	public String toString() {
		String ret = "Checkers: ";
		int count = 0;
		for( Checker c : checkers ) {
			ret += "\n" + (count+1) + ": ";
			ret += c.toString();
			count++;
		}
		return ret;
	}
}
