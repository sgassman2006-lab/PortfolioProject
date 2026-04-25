import components.pitcher.Pitcher;
import components.pitcher.Pitcher1;
import components.set.Set;
import components.set.Set1L;

/**
 * BullPen of pitchers.
 */
public class BullPen {

    /**
     * Set of Pitchers, represents bullpen.
     */
    private Set<Pitcher> pen;

    /**
     * Represents team name.
     */
    private String team;

    /**
     * Default constructor.
     */
    public BullPen() {
        this.team = "None";
        this.pen = new Set1L<Pitcher>();
    }

    /**
     * Constructor with team name.
     * 
     * @param teamName
     */
    public BullPen(String teamName) {
        this.team = teamName;
        this.pen = new Set1L<Pitcher>();
    }

    /**
     * Adds pitcher to bullpen.
     * 
     * @param first
     * @param last
     * @param idNum
     * @param year
     */
    public void addPitcher(String first, String last, int idNum, int year) {
        this.pen.add(new Pitcher1(first, last, idNum, year));
    }

    /**
     * Finds pitcher within bullpen.
     * 
     * @param idNum
     * @param year
     * @return pitcher with specifications
     */
    public Pitcher findPitcher(int idNum, int year) {
        for (Pitcher pitch : this.pen) {
            if (pitch.getID() == idNum && pitch.getYear() == year) {
                return pitch;
            }
        }

        return new Pitcher1();
    }

    /**
     * changes name of the team.
     * 
     * @param change
     */
    public void changeTeam(String change) {
        this.team = change;
    }
}
