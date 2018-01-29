package objects;
import java.text.DecimalFormat;
import java.util.HashMap;

public class TeamData {
	// Given
	private DecimalFormat df = new DecimalFormat("#.###");
	private String teamNumber;
	private HashMap<String, Double> data;
	private String desc;
	
	public TeamData() {
		
	}
	
	public TeamData(String name, double ag, double ac, double ahgb, double algb, double g, double hg, double lg, double cp, double clt, double cat) {
		data = new HashMap<String, Double>();
		teamNumber = name;
		data.put("AutonGears", ag);
		data.put("AutonCross", ac);
		data.put("AutonHighGoalBalls", ahgb);
		data.put("AutonLowGoalBalls", algb);
		data.put("TeleGears", g);
		data.put("TeleHighGoals", hg);
		data.put("TeleLowGoals", lg);
		data.put("ClimbPercentage", cp);
		data.put("ClimbTime", clt);
		data.put("CatchTime", cat);
		
		data.put("AutonHighGoalPoints", ahgb);
		data.put("AutonLowGoalPoints", algb/3);
		data.put("TeleHighGoalPoints", hg/3);
		data.put("TeleLowGoalPoints", lg/9);
		
		data.put("TotalAutonBallPoints", ahgb + algb/3);
		data.put("TotalTeleBallPoints", hg/3 + lg/9);
		data.put("TotalBallPoints", ahgb + algb/3 + hg/3 + lg/9);
		
		data.put("TotalGears", ag + g);
		data.put("TotalHighGoalBalls", ahgb + hg);
		data.put("TotalLowGoalBalls", algb + lg);
		data.put("TotalClimbTime", clt + cat);
		
		desc = "";
	}
	
	public TeamData( String name, Double[] conv ) {
		data = new HashMap<String, Double>();
		teamNumber = name;
		data.put("AutonGears", conv[0]);
		data.put("AutonCross", conv[1]);
		data.put("AutonBallPoints", conv[4] + conv[6]);
		data.put("AverageFullCycleGears", conv[9]);
		data.put("MaxFullCycleGears", conv[10]);
		data.put("AverageTeleopGears", conv[7]);
		data.put("MaxTeleopGears", conv[8]);
		data.put("AverageTotalGears", conv[11]);
		data.put("MaxTotalGears", conv[12]);
		data.put("TeleBallPoints", conv[14] + conv[16]);
		data.put("ClimbPercentage", conv[17]);
		data.put("TotalClimbTime", conv[19] + conv[20]);
		data.put("TotalBallPoints", conv[4] + conv[6] + conv[14] + conv[16]);
		desc = "";
	}
	
	public String getName() {
		return teamNumber;
	}
	public String getDesc() {
		return desc;
	}
	public double get(String data) {
		return this.data.get(data);
	}
	public String toComparison(TeamData td) {
		String ret = "";
		ret += teamNumber + ": " + desc;
		ret += "\n" + td.getName() + ": " + td.getDesc();
		for( String s : data.keySet() ) {
			ret += "\n" + (s + ":");
			for( int f=s.length(); f<21; f++ )
				ret += " ";
			if( data.get(s) > td.get(s) ) {
				if( !s.equals("TotalClimbTime") )
					ret += teamNumber;
				else
					ret += td.getName();
				ret += "\t(" + df.format(data.get(s)) + " : " + df.format(td.get(s)) + ")";
			} else if(data.get(s) < td.get(s)) {
				if( s.equals("TotalClimbTime") )
					ret += teamNumber;
				else
					ret += td.getName();
				ret += "\t(" + df.format(td.get(s)) + " : " + df.format(data.get(s)) + ")";
			} else {
				ret += "tie\t(" + td.get(s) + ")";
			}
		}
		return ret;
	}
	public String toString() {
		String ret = "-=-=-=-=-={[" + teamNumber + "]}=-=-=-=-=-";
		ret += "\nNotes:                "  + desc;
		for( String s : data.keySet() ) {
			ret += "\n" + (s + ":");
			for( int f=s.length(); f<21; f++ )
				ret += " ";
			ret += df.format(data.get(s));
		}
		ret += "\n-=-=-=-=-={[";
		for( int f=0; f<teamNumber.length(); f++ )
			ret += "#";
		ret += "]}=-=-=-=-=-";
		return ret;
	}
	// TODO: calculate ranking based on given factor list by looking at individual rankings
	public void addDesc(String s) {
		desc += s;
	}
	public void setDesc(String s) {
		desc = s;
	}
}
