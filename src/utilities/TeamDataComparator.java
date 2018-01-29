package utilities;

import java.util.Comparator;

import objects.Sorter;
import objects.TeamData;

public class TeamDataComparator implements Comparator<TeamData> {
	private String stat;
	private boolean flip;
	
	public TeamDataComparator(Sorter s) {
		stat = s.getSortStat();
		flip = s.getFlip();
	}
	
	@Override
	public int compare(TeamData o1, TeamData o2) {
		if( stat.equals("") ) {
			return 0;
		}
		double o1v = o1.get(stat);
		double o2v = o2.get(stat);
		if( o1v > o2v ) {
			if( flip )
				return -1;
			else
				return 1;
		} else if( o1v == o2v )
			return 0;
		else {
			if( flip )
				return 1;
			else
				return -1;
		}
	}

}
