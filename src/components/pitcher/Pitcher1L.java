package components.pitcher;

import components.map.Map;

/**
 * Kernel Class of Pitcher Simulator which implements all kernel methods.
 */
public class Pitcher1L {

    /**
     * Full Name of Player with correct Capitalization.
     */
    private String name;

    /**
     * MLB official player ID number.
     */
    private int idNum;

    /**
     * Year of season for the Player.
     */
    private int year;

    /**
     * Map to hold the key data of the player for the season.
     */
    private Map<String, Double> stats;

    /**
     * Stores level of stamina player is at in game.
     */
    private int stamina;

    /**
     * Automatic starting stamina for all new pitchers.
     */
    private final int START_STAMINA = 100;

    /**
     * Constructor for a Pitcher.
     *
     * @param first
     * @param last
     * @param id
     * @param y
     */
    public Pitcher1L(String first, String last, int id, int y) {
        this.name = first.substring(0, 1).toUpperCase()
                + first.substring(1).toLowerCase() + " "
                + last.substring(0, 1).toUpperCase()
                + last.substring(1).toLowerCase();
        this.idNum = id;
        this.year = y;
        this.stamina = this.START_STAMINA;

        this.setStats(id, y);
    }

    /**
     * Sets stats of a pitcher in a season in a Map<String, Double>.
     *
     * @param id
     * @param y
     */
    public void setStats(int id, int y) {
        DataFetcher fetcher = new DataFetcher();
        this.stats = fetcher.fetchAllStats(id, y);
    }

    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) {
        Pitcher1L testPitcher = new Pitcher1L("Shohei", "Ohtani", 660271, 2022);
        System.out.print(testPitcher.stats.toString());
    }
}
