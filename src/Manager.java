import components.pitcher.Pitcher;
import components.pitcher.Pitcher1;

/**
 * Person who controls the players playing in a baseball game.
 */
public class Manager {

    /**
     * Group of pitchers to use over the course of the game.
     */
    private BullPen pen;

    /**
     * Pitcher currently on the mound.
     */
    private Pitcher currentPitcher;

    /**
     * Name of manager.
     */
    private String name;

    /**
     * Default constructor for new Manager.
     */
    public Manager() {
        this.name = "None";
        this.currentPitcher = new Pitcher1();
        this.pen = new BullPen();
    }

    /**
     * Constructor for new Manager.
     * 
     * @param managerName
     * @param pitcherFirst
     * @param pitcherLast
     * @param idNum
     * @param year
     * @param teamName
     */
    public Manager(String managerName, String pitcherFirst, String pitcherLast,
            int idNum, int year, String teamName) {
        this.name = managerName;
        this.currentPitcher = new Pitcher1(pitcherFirst, pitcherLast, idNum,
                year);
        this.pen = new BullPen(teamName);
    }

    /**
     * Allows manager to see stamina status of current pitcher.
     * 
     * @return the pitcher's stamina level
     */
    public int pitcherStaminaStatus() {
        return this.currentPitcher.getStamina();
    }

    /**
     * Switches current pitcher with one from the bullpen.
     * 
     * @param idNum
     * @param year
     */
    public void switchPitcher(int idNum, int year) {
        Pitcher newPitch = this.pen.findPitcher(idNum, year);
        this.currentPitcher = newPitch;
    }
}
