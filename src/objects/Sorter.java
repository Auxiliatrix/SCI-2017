package objects;

public class Sorter {
	private String sortStat;
	private boolean flip;
	
	public Sorter() {
		sortStat = "";
		flip = true;
	}
	public Sorter(String ss) {
		sortStat = ss;
		flip = true;
	}
	public Sorter(String ss, boolean f) {
		sortStat = ss;
		flip = f;
	}
	
	public String getSortStat() {
		return sortStat;
	}
	public boolean getFlip() {
		return flip;
	}
	public String toString() {
		String ret = "";
		ret += "Sorting Method: ";
		ret += "\n" + sortStat + ", ";
		if( flip )
			ret += "increasing";
		else
			ret += "decreasing";
		return ret;
	}
}
