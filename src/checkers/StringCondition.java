package checkers;

import objects.TeamData;

public class StringCondition extends Condition {
	private String toCheck;
	private String operator;
	private String threshold;
	
	public StringCondition() {
		
	}
	public StringCondition(String s) {
		int i1 = s.indexOf(" ");
		int i2 = s.lastIndexOf(" ");
		toCheck = s.substring(0, i1);
		operator = s.substring(i1+1,i2);
		threshold = s.substring(i2+1);
	}
	public String toString() {
		return toCheck + operator + threshold;
	}
	public boolean check(TeamData td) {
		boolean tf = false;
		if( operator.equals("?") ) {
			tf = td.getDesc().contains(threshold);
		} else if( operator.equals("!") ) {
			tf = !td.getDesc().contains(threshold);
		}
		return tf;
	}
}
