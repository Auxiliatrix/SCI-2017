package objects;

import java.util.ArrayList;
import java.util.Scanner;

import utilities.FileConverter;

public class ChampsDataParser {
	private static FileConverter fc;
	
	public ChampsDataParser() {
		fc = new FileConverter();
	}
	
	@SuppressWarnings("resource")
	public ArrayList<TeamData> process(String fileName) {
		ArrayList<TeamData> teams = new ArrayList<TeamData>();
		ArrayList<String> lines = fc.convert(fileName);
		boolean init = true;
		for( String l : lines ) {
			if( init ) {
				init = false;
				continue;
			}
			Scanner sc = new Scanner(l);
			sc.useDelimiter(",|%,");
			String name = sc.next();
			String[] stats = new String[21];
			Double[] conv = new Double[21];
			for( int f=0; f<21; f++ ) {
				stats[f] = sc.next();
				if( stats[f].equals("#DIV/0!") || stats[f].equals("") ) {
					conv[f] = 0D;
				} else {
					conv[f] = Double.parseDouble(stats[f]);
				}
			}
			TeamData td = new TeamData(name, conv);
			teams.add(td);
//			AutonGears 				0
//			AutonCross 				1
//			CrossPoints 			2
//			AutonHighGoalBalls 		3
//			AutonHighGoalPoints 	4
//			AutonLowGoalBalls 		5
//			AutonLowGoalPoints 		6
//			AvgTeleopGears 			7
//			MaxTeleopGears 			8
			
			// AvgFullCycleGears	9
			// MaxFullCycleGears	10
			
//			AvgTotalGears 			11
//			MaxTotalGears 			12
//			TeleHighGoalBalls 		13
//			TeleHighGoalPoints 		14
//			TeleLowGoalBalls 		15
//			TeleLowGoalPoints 		16
//			ClimbPercentage 		17
//			AvgClimbPoints 			18
//			ClimbTime 				19
//			CatchTime 				20
		}
		
		return teams;
	}
}
