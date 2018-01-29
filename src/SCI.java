import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import checkers.Filter;
import checkers.StringCondition;
import checkers.TRUE;
import gui.CompareBox;
import gui.PostIt;
import objects.ChampsDataParser;
import objects.DataParser;
import objects.Group;
import objects.TeamData;
import utilities.FileConverter;

public class SCI {
	public static DecimalFormat df = new DecimalFormat("#.###");
	/* NOT CHAMPS */
	public static DataParser dp = new DataParser();
	/* CHAMPS */
	//public static ChampsDataParser dp = new ChampsDataParser();
	public static FileConverter fc = new FileConverter();
	public static ArrayList<TeamData> masterList;
	public static HashMap<String, TeamData> teams = new HashMap<String, TeamData>();
	public static HashMap<String, Group> groups = new HashMap<String, Group>();
	/* NOT CHAMPS */
	
	public static String[] validArgs = { "Desc", "AutonGears", "AutonCross", "AutonHighGoalBalls", "AutonLowGoalBalls",
			"TeleGears", "TeleHighGoals", "TeleLowGoals", "ClimbPercentage", "ClimbTime", "CatchTime",
			"AutonHighGoalPoints", "AutonLowGoalPoints", "TeleHighGoalPoints", "TeleLowGoalPoints",
			"TotalAutonBallPoints", "TotalTeleBallPoints", "TotalBallPoints", "TotalGears", "TotalHighGoalBalls",
			"TotalLowGoalBalls", "TotalClimbTime" };
	
	/* CHAMPS */
	/*
	public static String[] validArgs = {
			"AutonGears",
			"AutonCross",
			"AutonBallPoints",
			"AverageTeleopGears",
			"MaxTeleopGears",
			"AverageFullCycleGears",
			"MaxFullCycleGears",
			"AverageTotalGears",
			"MaxTotalGears",
			"TeleBallPoints",
			"ClimbPercentage",
			"TotalClimbTime",
			"TotalBallPoints",
	};
	*/
	public static String[] validOps = { ">", "<", ">=", "<=", "=>", "=<", "=", "!", "?" };
	public static String PS1 = "==> ";

	public static void main(String[] args) {
		init();
		while(cycle(""));
		System.out.println("Exiting program");
	}
	
	public static void init() {
		System.out.println("Initializing Scouting Computer Interface...");
		masterList = dp.process(Calibration.DATA_FILE);
		for(TeamData td : masterList) {
			teams.put(td.getName(), td);
		}
		Filter always = new Filter(new TRUE());
		groups.put("master", new Group("master", masterList, always));
		System.out.println("Project S.C.I. Initialized");
		System.out.println("Type help for a list of commands.");
		System.out.print(PS1);
	}
	
	@SuppressWarnings("resource")
	public static boolean cycle(String in) {
		Scanner sc = new Scanner(System.in);
		String cmd = in;
		boolean silent = true;
		if(cmd.equals("")) {
			cmd = sc.nextLine();
			silent = false;
		}
		int space = cmd.indexOf(" ");
		if(cmd.startsWith("help")) {
			help();
		} else if(cmd.startsWith("quit")) {
			return false;
		} else if(cmd.startsWith("stats")) {
			stats();
		} else if(cmd.startsWith("ops")) {
			ops();
		} else if(cmd.startsWith("teams")) {
			teams();
		} else if(cmd.startsWith("groups")) {
			groups();
		} else if(cmd.startsWith("reload")) {
			reload();
			System.out.println("Data reloaded");
		} else if(cmd.startsWith("wipe")) {
			wipe();
			System.out.println("Save file wiped");
		} else if(cmd.startsWith("flush")) {
			flush();
		} else if(space == -1) {
			// incomplete error
			if(!silent)
				System.out.println("Command not recognized: \"" + cmd + "\"");
		} else {
			// if fine
			String act = cmd.substring(0, space); // action
			String sub1 = cmd.substring(space + 1); // remainder
			space = sub1.indexOf(" ");
			if(space == -1 &&(act.equals("mod") || act.equals("set") || act.equals("dsc"))) {
				// incomplete error
				if(!silent)
					System.out.println("Missing arguments for command \"" + act + "\"");
			} else {
				// if fine
				String name;
				String sub2;
				if(space != -1) {
					name = sub1.substring(0, space);
					sub2 = sub1.substring(space + 1);
				} else {
					name = sub1.substring(0);
					sub2 = "";
				}
				if(name.equals("master") && !(act.equals("dsp") || act.equals("srt") || act.equals("pin") || act.equals("rnk"))) {
					if(!silent)
						System.out.println("Group \"master\" is immutable");
				} else {
					if(!silent &&(act.equals("rem") || act.equals("rnk") || act.equals("cmp") || act.equals("crt") || act.equals("mod") || act.equals("srt") || act.equals("dsc") || act.equals("pin")))
						write(cmd);
					if(act.equals("crt")) {
						if(sub2.contains("-")) {
							if(sub2.indexOf(" ") == -1) {
								if(!silent)
									System.out.println("Missing arguments following flag");
							} else {
								int dash = sub2.indexOf("-");
								space = sub2.indexOf(" ");
								String flag = sub2.substring(dash + 1, space);
								String sub3 = sub2.substring(space + 1);
								if(flag.equals("cln")) {
									if(!(groups.containsKey(name) || teams.containsKey(name))) {
										if(groups.containsKey(sub3)) {
											Group g = new Group(name, groups.get(sub3));
											groups.put(name, g);
											if(!silent)
												System.out.println("Created clone of \"" + sub3 + "\" as \"" + name + "\"");
										} else {
											if(!silent)
												System.out.println("No group of name \"" + sub3 + "\" exists");
										}
									} else {
										if(!silent)
											System.out.println("An object of name \"" + name + "\" already exists");
									}
								} else {
									if(!silent)
										System.out.println("Flag not recognized: \"" + flag + "\"");
								}
							}
						} else {
							if(!(groups.containsKey(name) || teams.containsKey(name))) {
								Filter f = new Filter(new TRUE());
								Group g = new Group(name, new ArrayList<TeamData>(), f);
								groups.put(name, g);
								if(!silent)
									System.out.println("Created a group with name \"" + name + "\"");
							} else {
								if(!silent)
									System.out.println("An object of name \"" + name + "\" already exists");
							}
						}
					} else if(act.equals("mod")) {
						if(sub2.contains("-")) {
							if(sub2.indexOf(" ") == -1) {
								if(!silent)
									System.out.println("Missing arguments following flag");
							} else {
								int dash = sub2.indexOf("-");
								space = sub2.indexOf(" ");
								String flag = sub2.substring(dash + 1, space);
								String sub3 = sub2.substring(space + 1);
								if(flag.equals("flt")) {
									if(groups.containsKey(name)) {
										if(checkValidArg(sub3)) {
											Filter f = groups.get(name).getFilter();
											f.addChecker(sub3);
											if(!silent)
												System.out.println("Added argument \"" + sub3 + "\" to \"" + name + "\"");
										} else if(checkValidStringArg(sub3)) {
											Filter f = groups.get(name).getFilter();
											f.addChecker(new StringCondition(sub3));
											if(!silent)
												System.out.println("Added argument \"" + sub3 + "\" to \"" + name + "\"");
										} else {
											if(!silent)
												System.out.println("Incorrect condition format");
										}
									} else {
										if(!silent)
											System.out.println("No group of name \"" + name + "\" exists");
									}
								} else if(flag.equals("del")) {
									if(groups.containsKey(name)) {
										Filter f = groups.get(name).getFilter();
										boolean success;
										if(sub3.matches("-?\\d+(\\.\\d+)?")) {
											success = f.removeChecker((int) Double.parseDouble(sub3));
										} else {
											success = f.removeChecker(sub3);
										}
										if(success) {
											if(!silent)
												System.out.println("Deleted argument \"" + sub3 + "\" from \"" + name + "\"");
										} else {
											if(!silent)
												System.out.println("Argument \"" + sub3 + "\" not found");
										}
									} else {
										if(!silent)
											System.out.println("No group of name \"" + name + "\" exists");
									}
								} else if(flag.equals("set")) {
									if(groups.containsKey(name)) {
										if(checkValidArg(sub3)) {
											Filter f = new Filter(sub3);
											groups.get(name).setFilter(f);
											if(!silent)
												System.out.println("Set argument \"" + sub3 + "\" for \"" + name + "\"");
										} else {
											if(!silent)
												System.out.println("Incorrect condition format");
										}
									} else {
										if(!silent)
											System.out.println("No group of name \"" + name + "\" exists");
									}
								} else if(flag.equals("add")) {
									if(groups.containsKey(name)) {
										if(teams.containsKey(sub3)) {
											if(groups.get(name).addTeam(teams.get(sub3))) {
												if(!silent)
													System.out.println("Added team \"" + sub3 + "\" to \"" + name + "\"");
											} else {
												if(!silent)
													System.out.println("\"" + name + "\" already contains team \"" + sub3 + "\"");
											}
										} else if(groups.containsKey(sub3)) {
											groups.get(name).addTeams(groups.get(sub3));
											if(!silent)
												System.out.println("Added applicable teams from \"" + sub3 + "\" to \"" + name + "\"");
										} else {
											if(!silent)
												System.out.println("No group or team of name \"" + sub3 + "\" exists");
										}
									} else {
										if(!silent)
											System.out.println("No group of name \"" + name + "\" exists");
									}
								} else if(flag.equals("rem")) {
									if(groups.containsKey(name)) {
										if(teams.containsKey(sub3)) {
											if(groups.get(name).removeTeam(teams.get(sub3))) {
												if(!silent)
													System.out.println(
															"Removed team \"" + sub3 + "\" to \"" + name + "\"");
											} else {
												if(!silent)
													System.out.println("\"" + name + "\" does not contain team \"" + sub3 + "\"");
											}
										} else {
											if(!silent)
												System.out.println("No team of name \"" + sub3 + "\" exists");
										}
									} else {
										if(!silent)
											System.out.println("No group of name \"" + name + "\" exists");
									}
								} else {
									if(!silent)
										System.out.println("Flag not recognized: \"" + flag + "\"");
								}
							}
						} else {
							System.out.println("Missing arguments for command \"mod\"");
						}
					} else if(act.equals("dsc")) {
						if(sub2.contains("-")) {
							if(sub2.indexOf(" ") == -1) {
								if(!silent)
									System.out.println("Missing arguments following flag");
							} else {
								int dash = sub2.indexOf("-");
								space = sub2.indexOf(" ");
								String flag = sub2.substring(dash + 1, space);
								String sub3 = sub2.substring(space + 1);
								if(flag.equals("apn")) {
									if(teams.containsKey(name)) {
										teams.get(name).addDesc(sub3);
										if(!silent)
											System.out.println("Added description \"" + sub3 + "\" to \"" + name + "\"");
									} else {
										if(!silent)
											System.out.println("No team of name \"" + name + "\" exists");
									}
								} else if(flag.equals("set")) {
									if(teams.containsKey("name")) {
										teams.get(name).setDesc(sub3);
										if(!silent)
											System.out.println("Added description \"" + sub3 + "\" to \"" + name + "\"");
									} else {
										if(!silent)
											System.out.println("No team of name \"" + name + "\" exists");
									}
								} else {
									if(!silent)
										System.out.println("Flag not recognized: \"" + flag + "\"");
								}
							}
						} else {
							if(!silent)
								System.out.println("Missing arguments for command \"dsc\"");
						}
					} else if(act.equals("srt")) {
						if(sub2.contains("-")) {
							if(sub2.equals("-clr")) {
								if(groups.containsKey(name)) {
									groups.get(name).sort("");
									if(!silent)
										System.out.println("Cleared sorting for group \"" + name + "\"");
								} else {
									if(!silent)
										System.out.println("No group of name \"" + name + "\" exists");
								}
							} else if( sub2.equals("-rnk") ) {
								if(groups.containsKey(name)) {
									groups.get(name).sort("rank");
									if(!silent)
										System.out.println("Rank sorting for group \"" + name + "\"");
								} else {
									if(!silent)
										System.out.println("No group of name \"" + name + "\" exists");
								}
							} else if(sub2.indexOf(" ") == -1) {
								if(!silent)
									System.out.println("Missing arguments following flag");
							} else {
								int dash = sub2.indexOf("-");
								space = sub2.indexOf(" ");
								String flag = sub2.substring(dash + 1, space);
								String sub3 = sub2.substring(space + 1);
								if(flag.equals("inc")) {
									if(groups.containsKey(name)) {
										if(checkValidStat(sub3)) {
											groups.get(name).sort(sub3);
											if(!silent)
												System.out.println("Sorted \"" + name + "\" by " + sub3 + " in increasing order");
										} else {
											if(!silent)
												System.out.println("Argument does not exist");
										}
									} else {
										if(!silent)
											System.out.println("No group of name \"" + name + "\" exists");
									}
								} else if(flag.equals("dec")) {
									if(groups.containsKey(name)) {
										if(checkValidStat(sub3)) {
											groups.get(name).sortFlip(sub3);
											if(!silent)
												System.out.println("Sorted \"" + name + "\" by " + sub3 + " in decreasing order");
										} else {
											if(!silent)
												System.out.println("Argument does not exist");
										}
									} else {
										if(!silent)
											System.out.println("No group of name \"" + name + "\" exists");
									}
								} else {
									if(!silent)
										System.out.println("Flag not recognized: \"" + flag + "\"");
								}
							}
						} else {
							if(!silent)
								System.out.println("Missing arguments for command \"srt\"");
						}
					} else if(act.equals("rnk")) {
						if(sub2.contains("-")) {
							if(sub2.indexOf(" ") == -1) {
								if(!silent)
									System.out.println("Missing arguments following flag");
							} else {
								int dash = sub2.indexOf("-");
								space = sub2.indexOf(" ");
								String flag = sub2.substring(dash + 1, space);
								String sub3 = sub2.substring(space + 1);
								if(flag.equals("add")) {
									if(groups.containsKey(name)) {
										sub3 += " ";
										Scanner temp = new Scanner(sub3);
										temp.useDelimiter(" |, ");
										while(temp.hasNext()) {
											String next = temp.next();
											if(checkValidStat(next)) {
												groups.get(name).addRankFactor(next);
												if(!silent)
													System.out.println("Added rank factor \"" + next + "\" to \"" + name + "\"");
											} else {
												if(!silent)
													System.out.println("Rank factor \"" + next + "\" not found");
											}
										}
									} else {
										if(!silent)
											System.out.println("No group of name \"" + name + "\" exists");
									}
								} else if(flag.equals("del")) {
									if(groups.containsKey(name)) {
										if(sub3.matches("-?\\d+(\\.\\d+)?")) {
											groups.get(name).removeRankFactor((int) Double.parseDouble(sub3));
											if(!silent)
												System.out.println("Deleted rank factor \"" + sub3 + "\" from \"" + name + "\"");

										} else if(checkValidStat(sub3) && groups.get(name).getRankFactors().contains(sub3)) {
											groups.get(name).removeRankFactor(sub3);
											if(!silent)
												System.out.println("Deleted rank factor \"" + sub3 + "\" from \"" + name + "\"");
										} else {
											if(!silent)
												System.out.println("Rank factor \"" + sub3 + "\" not found");
										}
									} else {
										if(!silent)
											System.out.println("No group of name \"" + name + "\" exists");
									}
								} else if(flag.equals("set")) {
									if(groups.containsKey(name)) {
										sub3 += " ";
										Scanner temp = new Scanner(sub3);
										temp.useDelimiter(" |, ");
										ArrayList<String> nr = new ArrayList<String>();
										while(temp.hasNext()) {
											String n = temp.next();
											if( checkValidStat(n) ) {
												nr.add(n);
											}
										}
										groups.get(name).setRankFactors(nr);
										if(!silent)
										System.out.println("Set rank factors \"" + sub3 + "\" for \"" + name + "\"");
									} else {
										if(!silent)
											System.out.println("No group of name \"" + name + "\" exists");
									}
								} else {
									if(!silent)
										System.out.println("Flag not recognized: \"" + flag + "\"");
								}
							}
						} else {
							System.out.println("Missing arguments for command \"mod\"");
						}
					} else if(act.equals("rem")) {
						if(groups.containsKey(name)) {
							groups.remove(name);
							if(!silent)
								System.out.println("Removed group \"" + name + "\"");
						} else {
							if(!silent)
								System.out.println("No group of name \"" + name + "\" exists");
						}
					} else if(act.equals("dsp")) {
						if(groups.containsKey(name)) {
							if(!silent)
								System.out.println("Displaying group \"" + name + "\"");
							if(!silent)
								System.out.println(groups.get(name).toString());
						} else if(teams.containsKey(name)) {
							if(!silent)
								System.out.println("Displaying team \"" + name + "\"");
							if(!silent)
								System.out.println(teams.get(name).toString());
						} else {
							if(!silent)
								System.out.println("No group or team of name \"" + name + "\" exists");
						}
					} else if(act.equals("pin")) {
						if(groups.containsKey(name)) { 
							PostIt.generate(name, groups.get(name).toString());
							if(!silent)
								System.out.println("Pinning group \"" + name + "\"");
						} else if(teams.containsKey(name)) {
							PostIt.generate(name, teams.get(name).toString());
							if(!silent)
								System.out.println("Pinning team \"" + name + "\"");
						} else {
							if(!silent)
								System.out.println("No group or team of name \"" + name + "\" exists");
						}
					} else if( act.equals("cmp") ) {
						space = sub1.indexOf(" ");
						if( space == -1 ) {
							System.out.println("Please enter two teams");
						} else {
							String t1 = sub1.substring(0, space);
							String t2 = sub1.substring(space+1);
							if( !teams.containsKey(t1) ) {
								if(!silent)
									System.out.println("No team of name \"" + t1 + "\" exists");
							} else if( !teams.containsKey(t2) ) {
								if(!silent)
									System.out.println("No team of name \"" + t2 + "\" exists");
							} else {
								CompareBox.generate(t1 + " v " + t2, teams.get(t1), teams.get(t2));
							}
						}
					} else {
						if(!silent)
							System.out.println("Command not recognized: \"" + act + "\"");
					}
				}
			}
		}
		if( !silent ) {
			System.out.print(PS1);
		}
		return true;
	}
	
	public static void help() {
		System.out.println("Basic Commands:");
		System.out.println("    help    :     display this menu");
		System.out.println("    quit    :     exit the program");
		System.out.println("    stats   :     display a list of valid arguments");
		System.out.println("    ops     :     display a list of valid operators");
		System.out.println("    teams   :     display a list of teams");
		System.out.println("    groups  :     display a list of groups");
		System.out.println("    reload  :     reloads data from save file");
		System.out.println("    wipe    :     wipes save file data");
		System.out.println("    flush   :     clear console");
		System.out.println("Command Format:");
		System.out.println("    <action> <group> <modifiers> <arguments>");
		System.out.println("    i.e.:    md master add totalClimbTime < 15");
		System.out.println("Actions:");
		System.out.println("    crt     :     create a group");
		System.out.println("    mod     :     modify an existing group");
		System.out.println("    dsc     :     describe an existing group");
		System.out.println("    srt     :     sort a group by a condition");
		System.out.println("    rem     :     remove an existing group");
		System.out.println("    dsp     :     display the stats of a group or team in console");
		System.out.println("    pin     :     pin a group or team in a pop-up window");
		System.out.println("    cmp     :     compare two teams side-by-side");
		System.out.println("Modifiers / Arguments:");
		System.out.println("    crt");
		System.out.println("        -cln     :     clone      <existing group>");
		System.out.println("    mod");
		System.out.println("        -flt     :     filter     <new condition>");
		System.out.println("        -del     :     delete     <existing condition>");
		System.out.println("        -set     :     set        <new condition>");
		System.out.println("        -add     :     add        <team>");
		System.out.println("        -rem     :     remove     <team>");
		System.out.println("    dsc");
		System.out.println("        -apn     :     append     <description>");
		System.out.println("        -set     :     set        <description>");
		System.out.println("    srt");
		System.out.println("        -inc     :     increasing <statistic>");
		System.out.println("        -dec     :     decreasing <statistic>");
		System.out.println("        -clr     :     remove sorting order");
		System.out.println("        -rnk     :     sort by ranking factors");
		System.out.println("    rnk");
		System.out.println("        -add     :     add a ranking factor");
		System.out.println("        -del     :     delete a ranking factor");
		System.out.println("        -set     :     sest ranking factors");
	}
	public static void stats() {
		System.out.println("Valid Stats:");
		for(String s : validArgs) {
			System.out.println(s);
		}
	}
	public static void ops() {
		System.out.println("Valid Ops:");
		for(String o : validOps) {
			System.out.println(o);
		}
	}
	public static void teams() {
		for(TeamData td : masterList) {
			System.out.println(td.getName());
		}
	}
	public static void groups() {
		for(String s : groups.keySet()) {
			System.out.println(s);
		}
	}
	public static void write(String string) {
		try {
			FileWriter fstream = new FileWriter("save.txt", true);
			BufferedWriter fbw = new BufferedWriter(fstream);
			if(string != null) {
				fbw.write(string);
				fbw.newLine();
				fbw.close();
			}
		} catch(Exception e) {
			System.out.println("Couldn't print to the file");
		}
	}
	public static void reload() {
		ArrayList<String> pre = fc.convert("save.txt");
		for(String cmd : pre)
			cycle(cmd);
	}
	@SuppressWarnings("resource")
	public static void wipe() {
		try {
			FileWriter fstream = new FileWriter("save.txt");
			BufferedWriter fbw = new BufferedWriter(fstream);
			fbw.write("");
		} catch (Exception e) {
			System.out.println("Couldn't wipe the file");
		}
	}
	public static boolean checkValidArg(String s) {
		int i1 = s.indexOf(" ");
		int i2 = s.lastIndexOf(" ");
		if(i1 == i2 || i1 == -1 || i2 == s.length()) {
			System.out.println("Syntax error");
			return false;
		} else {
			String toCheck = s.substring(0, i1);
			String operator = s.substring(i1 + 1, i2);
			boolean vs = checkValidStat(toCheck);
			boolean vo = checkValidOp(operator);
			boolean d = s.substring(i2 + 1).matches("-?\\d+(\\.\\d+)?");
			return vs && vo && d;
		}
	}
	public static boolean checkValidStringArg(String s) {
		int i1 = s.indexOf(" ");
		int i2 = s.lastIndexOf(" ");
		if(i1 == i2 || i1 == -1 || i2 == s.length()) {
			System.out.println("Syntax error");
			return false;
		} else {
			String toCheck = s.substring(0, i1);
			String operator = s.substring(i1 + 1, i2);
			boolean vs = checkValidStat(toCheck);
			boolean vo = checkValidOp(operator);
			return vs && vo;
		}
	}
	public static boolean checkValidStat(String s) {
		for(String a : validArgs) {
			if(s.equals(a))
				return true;
		}
		return false;
	}
	public static boolean checkValidOp(String s) {
		for(String o : validOps) {
			if(s.equals(o))
				return true;
		}
		return false;
	}
	public static void flush() {
		for( int f=0; f<35; f++ )
			System.out.println();
	}
}
