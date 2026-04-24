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
     * 4-parameter Constructor for a Pitcher.
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
        this.stamina = PitcherKernel.START_STAMINA;

        this.setStats(id, y);
    }

    /**
     * Default player for the clear() method.
     */
    private void createNewRep() {
        this.name = "Jake Arrieta";
        this.idNum = 453562;
        this.year = 2015;
        this.stamina = PitcherKernel.START_STAMINA;

        this.setStats(453562, 2015);
    }

    @Override
    public final void setStats(int id, int y) {
        this.stats = DataFetcher.fetchAllStats(id, y);
        this.pitchMix = DataFetcher.fetchPitchMix(id, y);
        this.pitchLocations = DataFetcher.fetchPitchLocations(id, y);
    }

    @Override
    public final int getStamina() {
        return this.stamina;
    }

    @Override
    public final void adjustStamina(int change) {
        this.stamina += change;
    }

    @Override
    public final double getStat(String key) {
        return this.stats.value(key);
    }

    @Override
    public final boolean hasStat(String key) {
        return this.stats.hasKey(key);
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final int getID() {
        return this.idNum;
    }

    @Override
    public final int getYear() {
        return this.year;
    }

    @Override
    public final Map<String, Double> getPitchMix() {
        return this.pitchMix;
    }

    @Override
    public final Map<String, Double> getPitchLocs() {
        return this.pitchLocations;
    }

    @Override
    public final Pitcher1 newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Pitcher source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Pitcher1 : "Violation of: source is"
                + "of dynamic type Pitcher1";

        Pitcher1 localSource = (Pitcher1) source;
        this.name = source.getName();
        this.idNum = source.getID();
        this.year = source.getYear();
        this.setStats(source.getID(), source.getYear());

        localSource.createNewRep();
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
