package objects;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import checkers.Checker;
import checkers.Filter;
import utilities.TeamDataComparator;

public class Group {
	private String name;
	private ArrayList<TeamData> tdl;
	private ArrayList<TeamData> ogl;
	private Filter f;
	private String desc;
	private Sorter s;
	private DecimalFormat df = new DecimalFormat("#.###");
	private ArrayList<String> rankFactors;
	HashMap<String, Integer> ranking;
	
	public Group() {
		name = "";
		desc = "";
		rankFactors = new ArrayList<String>();
		ranking = new HashMap<String, Integer>();
		s = new Sorter();
	}
	
	public Group(String name) {
		this.name = name;
		desc = "";
		rankFactors = new ArrayList<String>();
		ranking = new HashMap<String, Integer>();
		s = new Sorter();
	}
	
	public Group(String name, ArrayList<TeamData> teamData, Filter filter) {
		this.name = name;
		tdl = teamData;
		ogl = teamData;
		f = filter;
		rankFactors = new ArrayList<String>();
		ranking = new HashMap<String, Integer>();
		s = new Sorter();
		update();
	}
	
	public Group(String name, ArrayList<TeamData> teamData, Filter filter, Sorter sorter) {
		this.name = name;
		tdl = teamData;
		ogl = teamData;
		f = filter;
		rankFactors = new ArrayList<String>();
		ranking = new HashMap<String, Integer>();
		s = sorter;
		update();
	}
	
	public Group(String name, Group g) {
		this.name = name;
		desc = "";
		ArrayList<TeamData> tmp = g.getTeamList();
		ArrayList<TeamData> tmpo = g.getOriginal();
		ArrayList<Checker> c = g.getFilter().getCheckers();
		ArrayList<String> r = g.getRankFactors();
		Sorter sorter = g.getSorter();
		f = new Filter();
		tdl = new ArrayList<TeamData>();
		ogl = new ArrayList<TeamData>();
		s = new Sorter(sorter.getSortStat(), sorter.getFlip());
		rankFactors = new ArrayList<String>();
		ranking = new HashMap<String, Integer>();
		for( Checker ch : c )
			f.addChecker(ch);
		for( TeamData td : tmp )
			tdl.add(td);
		for( TeamData td : tmpo )
			ogl.add(td);
		for( String s : r )
			rankFactors.add(s);
		update();
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public void setList(ArrayList<TeamData> teamData) {
		tdl = teamData;
		ogl = teamData;
		update();
	}
	
	public void setFilter(Filter filter) {
		f = filter;
		update();
	}
	
	public void addTeams( Group g ) {
		for( TeamData td : g.getTeamList() ) {
			this.addTeam(td);
		}
	}
	
	public boolean addTeam(TeamData td) {
		if( !ogl.contains(td) ) {
			ogl.add(td);
			tdl.add(td);
			return true;
		}
		return false;
	}
	
	public boolean removeTeam(TeamData td) {
		if( ogl.contains(td) ) {
			ogl.remove(td);
			return true;
		}
		return false;
	}
	
	private void update() {
		ArrayList<TeamData> temp = new ArrayList<TeamData>();
		for( TeamData td : ogl ) {
			if(f.check(td)) {
				temp.add(td);
			}
		}
		TeamData[] arr = new TeamData[temp.size()];
		for( int f=0; f<temp.size(); f++ )
			arr[f] = temp.get(f);
		if( !s.getSortStat().equals("rank") ) {
			Arrays.sort(arr, new TeamDataComparator(s));
			ArrayList<TeamData> newTemp = new ArrayList<TeamData>();
			for( TeamData td : arr ) {
				newTemp.add(td);
			}
			tdl = newTemp;
		} else {
			rank(arr);
		}
	}
	
	public void rank(TeamData[] arr) {
		HashMap<TeamData, Integer> access = new HashMap<TeamData, Integer>();
		for( TeamData td : arr ) {
			access.put(td, 0);
			ranking.put(td.getName(), 0);
		}
		for( String rf : rankFactors ) {
			Sorter t;
			if( rf.equals("TotalClimbTime") ) {
				t = new Sorter(rf, false);
			} else {
				t = new Sorter(rf);
			}
			Arrays.sort(arr, new TeamDataComparator(t));
			double last = -1;
			int lastIndex = 0;
			int repeats = 0;
			for( int f=0; f<arr.length; f++ ) {
				int temp = access.get(arr[f]);
				int add = f;
				if( arr[f].get(rf) == last && f != 0 ) {
					add -= f-lastIndex;
					repeats++;
				} else {
					last = arr[f].get(rf);
					lastIndex = f-repeats;;
					add -= repeats;
				}
				access.put(arr[f], temp+add);
				ranking.put(arr[f].getName(), temp+add);
			}
		}
		tdl = new ArrayList<TeamData>();
		for( int f=0; f<arr.length; f++ ) {
			int min = Integer.MAX_VALUE;
			TeamData temp = new TeamData();
			for( TeamData td : access.keySet() ) {
				int rank = ranking.get(td.getName());
				if( rank <= min ) {
					min = rank;
					temp = td;
				}
			}
			tdl.add(temp);
			access.remove(temp);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void sort(String stat) {
		s = new Sorter(stat, true);
	}
	
	public void sortFlip(String stat) {
		s = new Sorter(stat, false);
	}
	
	public ArrayList<TeamData> getOriginal() {
		return ogl;
	}
	
	public ArrayList<TeamData> getTeamList() {
		update();
		return tdl;
	}
	
	public ArrayList<String> getRankFactors() {
		return rankFactors;
	}
	
	public ArrayList<String> getTeams() {
		update();
		ArrayList<String> teams = new ArrayList<String>();
		for( TeamData td : tdl )
			teams.add(td.getName());
		return teams;
	}
	
	public void addRankFactor(String s) {
		rankFactors.add(s);
	}
	
	public void removeRankFactor(String s) {
		rankFactors.remove(s);
	}
	
	public void removeRankFactor(int i) {
		rankFactors.remove(i);
	}
	
	public void setRankFactors( ArrayList<String> r ) {
		rankFactors = r;
	}
	
	public void addRankFactors(ArrayList<String> r) {
		for( String a : r ) {
			if( !rankFactors.contains(a) )
				rankFactors.add(a);
		}
	}
	
	public Filter getFilter() {
		return f;
	}
	
	public Sorter getSorter() {
		return s;
	}
	public String toString() {
		update();
		String ret = "-=-=-=-=-={[" + name + "]}=-=-=-=-=-";
		ret += "\n" + (f.toString());
		if (!s.getSortStat().equals("")) {
			ret += "\n" + (s.toString());
		}
		else {
			ret += "\n" + ("Sorting Method:");
			ret += "\n" + ("None");
		}
		ret += "\n" + ("Ranking factors: ");
		for( String r : rankFactors )
			ret += r + ", ";
		if( rankFactors.size() == 0 )
			ret += "None";
		ret += "\n" + ("Teams:");
		for (int g = 0; g < tdl.size(); g++) {
			ret += "\n";
			ret += ((g + 1) + ": " + tdl.get(g).getName());
			if (!(s.getSortStat().equals("") || s.getSortStat().equals("rank"))) {
				if (g < 9) {
					ret += ("\t");
				} else if (tdl.get(g).getName().length() < 4) {
					ret += ("\t");
				}
				ret += ("\t(" + df.format(tdl.get(g).get(s.getSortStat())) + ")");
			} else if( s.getSortStat().equals("rank") ) {
				if (g < 9) {
					ret += ("\t");
				} else if (tdl.get(g).getName().length() < 4) {
					ret += ("\t");
				}
				ret += ("\t" + ranking.get(tdl.get(g).getName()));
			} else {
				if (g < 9) {
					ret += ("\t");
				} else if (tdl.get(g).getName().length() < 4) {
					ret += ("\t");
				}
				ret += ("\t" + tdl.get(g).getDesc());
			}
		}
		ret += "\n-=-=-=-=-={[";
		for( int f=0; f<name.length(); f++ )
			ret += "#";
		ret += "]}=-=-=-=-=-";
		return ret;
	}
}
