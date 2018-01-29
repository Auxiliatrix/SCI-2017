package checkers;

import objects.TeamData;

public class Condition implements Checker {
	private String toCheck;
	private String operator;
	private double threshold;
	
	public Condition() {
		
	}
	public Condition(String s) {
		int i1 = s.indexOf(" ");
		int i2 = s.lastIndexOf(" ");
		toCheck = s.substring(0, i1);
		operator = s.substring(i1+1,i2);
		threshold = Double.parseDouble(s.substring(i2+1));
	}
	public String toString() {
		return toCheck + operator + threshold;
	}
	public boolean check(TeamData td) {
		boolean tf = false;
		if( operator.equals(">") ) {
			tf = td.get(toCheck) > threshold;
		} else if( operator.equals("<") ) {
			tf = td.get(toCheck) < threshold;
		} else if( operator.equals("=") ) {
			tf = td.get(toCheck) == threshold;
		} else if( operator.equals(">=") || operator.equals("=>") ) {
			tf = td.get(toCheck) >= threshold;
		} else if( operator.equals("<=") || operator.equals("=<")) {
			tf = td.get(toCheck) <= threshold;
		}
		return tf;
	}
	public boolean equals(Checker c) {
		return c.toString().equals(this.toString());
	}
	public boolean equals(String s) {
		return s.equals(this.toString());
	}
}
