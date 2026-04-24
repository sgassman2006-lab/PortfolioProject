package components.pitcher;

import components.map.Map;

/**
 * Kernel Class of Pitcher Simulator which implements all kernel methods.
 */
public class Pitcher1 extends PitcherSecondary {

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
     * Pitch type usage percentages keyed by pitch type abbreviation. e.g. "FF"
     * -> 0.45
     */
    private Map<String, Double> pitchMix;

    /**
     * Average pitch location by pitch type. Keys are "PITCHTYPE_x" and
     * "PITCHTYPE_z". e.g. "FF_x" -> -0.12, "FF_z" -> 2.45
     */
    private Map<String, Double> pitchLocations;

    /**
     * Stores level of stamina player is at in game.
     */
    private int stamina;

    /**
     * Automatic starting stamina for all new pitchers.
     */
    private static final int START_STAMINA = 100;

    /**
     * Constructor for a Pitcher.
     *
     * @param first
     * @param last
     * @param id
     * @param y
     */
    public Pitcher1(String first, String last, int id, int y) {
        this.name = first.substring(0, 1).toUpperCase()
                + first.substring(1).toLowerCase() + " "
                + last.substring(0, 1).toUpperCase()
                + last.substring(1).toLowerCase();
        this.idNum = id;
        this.year = y;
        this.stamina = Pitcher1.START_STAMINA;

        this.setStats(id, y);
    }

    /**
     * Sets stats of a pitcher in a season in a Map<String, Double>.
     *
     * @param id
     * @param y
     */
    @Override
    public void setStats(int id, int y) {
        this.stats = DataFetcher.fetchAllStats(id, y);
        this.pitchMix = DataFetcher.fetchPitchMix(id, y);
        this.pitchLocations = DataFetcher.fetchPitchLocations(id, y);
    }

    /**
     * Returns name.
     *
     * @return name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns ID number.
     *
     * @return idNum
     */
    @Override
    public int getID() {
        return this.idNum;
    }

    /**
     * Returns year.
     *
     * @return year
     */
    @Override
    public int getYear() {
        return this.year;
    }

    /**
     * Returns stamina.
     *
     * @return stamina
     */
    @Override
    public int getStamina() {
        return this.stamina;
    }

    /**
     * Returns Stats.
     *
     * @return stats
     */
    public Map<String, Double> getStats() {
        return this.stats;
    }

    /**
     * Returns Pitch Mix.
     *
     * @return pitchMix
     */
    @Override
    public Map<String, Double> getPitchMix() {
        return this.pitchMix;
    }

    /**
     * Returns pitch locations.
     *
     * @return pitchLocations
     */
    @Override
    public Map<String, Double> getPitchLocs() {
        return this.pitchLocations;
    }

    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) {
        Pitcher1 testPitcher = new Pitcher1("Shohei", "Ohtani", 660271, 2022);
        System.out.println(testPitcher.pitchLocations.toString());
        System.out.println(testPitcher.pitchMix.toString());
        System.out.println(testPitcher.stats.toString());
    }
}
