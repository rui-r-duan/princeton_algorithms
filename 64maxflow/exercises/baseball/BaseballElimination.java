import java.util.ArrayList;
import java.util.HashMap;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {
    private int numOfTeams;
    private static class TeamRec {
        String name;
        int    wins;
        int    losses;
        int    remaining;
        int[]  against;
    }
    private TeamRec[] teamData;
    private HashMap<String, Integer> nameToIndexTable;

    /**
     * create a baseball division from given filename in format specified below
     */
    public BaseballElimination(String filename) {
        In in = new In(filename);
        numOfTeams = in.readInt();
        teamData = new TeamRec[numOfTeams];
        nameToIndexTable = new HashMap<>();
        for (int i = 0; i < numOfTeams; i++) {
            TeamRec rec = new TeamRec();
            rec.name = in.readString();
            rec.wins = in.readInt();
            rec.losses = in.readInt();
            rec.remaining = in.readInt();
            rec.against = new int[numOfTeams];
            for (int j = 0; j < numOfTeams; j++) {
                rec.against[j] = in.readInt();
            }
            teamData[i] = rec;
            nameToIndexTable.put(rec.name, i);
        }
    }
    
    /**
     * number of teams
     */
    public int numberOfTeams() {
        return numOfTeams;
    }
    
    /**
     * all teams
     */
    public Iterable<String> teams() {
        ArrayList<String> list = new ArrayList<>(numOfTeams);
        for (int i = 0; i < numOfTeams; i++) {
            list.add(teamData[i].name);
        }
        return list;
    }
    
    /**
     * number of wins for given team
     */
    public int wins(String team) {
        Integer i = nameToIndexTable.get(team);
        if (i == null) throw new IllegalArgumentException("illegal team name");
        return teamData[i].wins;
    }

    /**
     * number of losses for given team
     */
    public int losses(String team) {
        Integer i = nameToIndexTable.get(team);
        if (i == null) throw new IllegalArgumentException("illegal team name");
        return teamData[i].losses;
    }
    
    /**
     * number of remaining games for given team
     */
    public int remaining(String team) {
        Integer i = nameToIndexTable.get(team);
        if (i == null) throw new IllegalArgumentException("illegal team name");
        return teamData[i].remaining;
    }
    
    /**
     * number of remaining games between team1 and team2
     */
    public int against(String team1, String team2) {
        Integer i = nameToIndexTable.get(team1);
        if (i == null) throw new IllegalArgumentException("illegal team name");
        Integer j = nameToIndexTable.get(team2);
        if (j == null) throw new IllegalArgumentException("illegal team name");
        return teamData[i].against[j];
    }

    /**
     * is given team eliminated?
     */
    public boolean isEliminated(String team) {
        Integer i = nameToIndexTable.get(team);
        if (i == null) throw new IllegalArgumentException("illegal team name");
        return false;
    }
    
    /**
     * subset R of teams that eliminates given team; null if not eliminated
     */
    public Iterable<String> certificateOfElimination(String team) {
        Integer i = nameToIndexTable.get(team);
        if (i == null) throw new IllegalArgumentException("illegal team name");
        return null;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        // for (String team : division.teams()) {
        //     if (division.isEliminated(team)) {
        //         StdOut.print(team + " is eliminated by the subset R = { ");
        //         for (String t : division.certificateOfElimination(team)) {
        //             StdOut.print(t + " ");
        //         }
        //         StdOut.println("}");
        //     }
        //     else {
        //         StdOut.println(team + " is not eliminated");
        //     }
        // }
        // DEBUG START
        StdOut.println("number of teams = " + division.numberOfTeams());
        for (String team : division.teams()) {
            StdOut.print(team + "   "
                         + division.wins(team) + " "
                         + division.losses(team) + " "
                         + division.remaining(team) + "   ");
            for (String team2 : division.teams()) {
                StdOut.print(division.against(team, team2) + " ");
            }
            StdOut.println();
        }
        // DEBUG END
    }
}
