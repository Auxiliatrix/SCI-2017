package objects;

import java.util.ArrayList;
import java.util.Scanner;

import utilities.FileConverter;

public class ChezyDataParser {
	private static FileConverter fc;
	
	public ChezyDataParser() {
		
	}
	
	@SuppressWarnings("resource")
	public ArrayList<TeamData> process(String fileName) {
		ArrayList<TeamData> teams = new ArrayList<TeamData>();
		ArrayList<String> lines = fc.convert(fileName);
		
		ArrayList<String> teamNumbers = new ArrayList<String>();
		ArrayList<Double> autonGears = new ArrayList<Double>();
		ArrayList<Double> autonCross = new ArrayList<Double>();
		ArrayList<Double> autonHighGoalBalls = new ArrayList<Double>();
		ArrayList<Double> autonLowGoalBalls = new ArrayList<Double>();
		ArrayList<Double> gears = new ArrayList<Double>();
		ArrayList<Double> highGoals = new ArrayList<Double>();
		ArrayList<Double> lowGoals = new ArrayList<Double>();
		ArrayList<Double> climbPercentage = new ArrayList<Double>();
		ArrayList<Double> climbTime = new ArrayList<Double>();
		ArrayList<Double> catchTime = new ArrayList<Double>();
		
		Scanner tn = new Scanner(lines.get(0));
		tn.useDelimiter(",");
		while( tn.hasNext() ) {
			String next = tn.next();
			if( !(next.equals("") || next.contains("Team Name")) ) {
				teamNumbers.add(next);
			}
		}
		
		Scanner ag = new Scanner(lines.get(1));
		ag.useDelimiter(",");
		while(ag.hasNext()) {
			String next = ag.next();
			if( !(next.equals("") || next.contains("Auton Gears")) ) {
				autonGears.add(Double.parseDouble(next));
			}
		}
		
		Scanner ac = new Scanner(lines.get(2));
		ac.useDelimiter(",");
		while(ac.hasNext()) {
			String next = ac.next();
			if( !(next.equals("") || next.contains("Auton Cross Baseline")) ) {
				autonCross.add(Double.parseDouble(next));
			}
		}
		
		Scanner ahgb = new Scanner(lines.get(4));
		ahgb.useDelimiter(",");
		while(ahgb.hasNext()) {
			String next = ahgb.next();
			if( !(next.equals("") || next.contains("Auton High Goal Balls")) ) {
				autonHighGoalBalls.add(Double.parseDouble(next));
			}
		}
		
		Scanner algb = new Scanner(lines.get(6));
		algb.useDelimiter(",");
		while(algb.hasNext()) {
			String next = algb.next();
			if( !(next.equals("") || next.contains("Auton Low Goal Balls")) ) {
				autonLowGoalBalls.add(Double.parseDouble(next));
			}
		}
		
		Scanner g = new Scanner(lines.get(8));
		g.useDelimiter(",");
		while(g.hasNext()) {
			String next = g.next();
			if( !(next.equals("") || next.contains("Teleop Gears")) ) {
				gears.add(Double.parseDouble(next));
			}
		}
		
		Scanner hg = new Scanner(lines.get(9));
		hg.useDelimiter(",");
		while(hg.hasNext()) {
			String next = hg.next();
			if( !(next.equals("") || next.contains("Teleop High Goal Balls")) ) {
				highGoals.add(Double.parseDouble(next));
			}
		}
		
		Scanner lg = new Scanner(lines.get(11));
		lg.useDelimiter(",");
		while(lg.hasNext()) {
			String next = lg.next();
			if( !(next.equals("") || next.contains("Teleop Low Goal Balls")) ) {
				lowGoals.add(Double.parseDouble(next));
			}
		}
		
		Scanner cp = new Scanner(lines.get(13));
		cp.useDelimiter("%,|,");
		while(cp.hasNext()) {
			String next = cp.next();
			if( !(next.equals("") || next.contains("Climb Percentage")) ) {
				climbPercentage.add(Double.parseDouble(next));
			}
		}
		
		Scanner clt = new Scanner(lines.get(15));
		clt.useDelimiter(",");
		while(clt.hasNext()) {
			String next = clt.next();
			if( !(next.equals("") || next.contains("Climb Time")) ) {
				climbTime.add(Double.parseDouble(next));
			}
		}
		
		Scanner cat = new Scanner(lines.get(16));
		cat.useDelimiter(",");
		while(cat.hasNext()) {
			String next = cat.next();
			if( !(next.equals("") || next.contains("Catch Time")) ) {
				catchTime.add(Double.parseDouble(next));
			}
		}
		
		for( int f=0; f<teamNumbers.size(); f++ ) {
			String nv = teamNumbers.get(f);
			double agv = autonGears.get(f);
			double acv = autonCross.get(f);
			double ahgbv = autonHighGoalBalls.get(f);
			double algbv = autonLowGoalBalls.get(f);
			double gv = gears.get(f);
			double hgv = highGoals.get(f);
			double lgv = lowGoals.get(f);
			double cpv = climbPercentage.get(f);
			double cltv = climbTime.get(f);
			double catv = catchTime.get(f);
			TeamData td = new TeamData(nv, agv, acv, ahgbv, algbv, gv, hgv, lgv, cpv, cltv, catv);
			teams.add(td);
		}
		
		return teams;
	}
}
