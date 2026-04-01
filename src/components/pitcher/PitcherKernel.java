package components.pitcher;

import components.map.Map;

/**
 * Kernel Class of Pitcher Simulator which implements all kernel methods.
 */
public class PitcherKernel {

    /**
     * Full Name of Player with correct Capitalization.
     */
    private static String name;

    /**
     * Year of season for the Player.
     */
    private static int year;

    /**
     * Map to hold the key data of the player for the season.
     */
    private static Map<String, Double> stats;

    /**
     * Stores level of stamina player is at in game.
     */
    private static int stamina;

    /**
     * Automatic starting stamina for all new pitchers.
     */
    private final int startStamina = 100;

    /**
     * Constructor for a Pitcher.
     *
     * @param firstName
     * @param lastName
     * @param y
     */
    public PitcherKernel(String firstName, String lastName, int y) {
        name = firstName.substring(0, 1).toUpperCase()
                + firstName.substring(1).toLowerCase() + " "
                + lastName.substring(0, 1).toUpperCase()
                + lastName.substring(1).toLowerCase();
        year = y;
        stamina = this.startStamina;

        //Missing Declaration: SavantDataFetcher sdf = new SavantDataFetcher();
        //sdf.fin
    }

    /**
     * Changes stats/identity of a pitcher that is already instantiated.
     *
     * @param firstName
     * @param lastName
     * @param y
     */
    public void setStats(String firstName, String lastName, int y) {
        name = firstName.substring(0, 1).toUpperCase()
                + firstName.substring(1).toLowerCase() + " "
                + lastName.substring(0, 1).toUpperCase()
                + lastName.substring(1).toLowerCase();
        year = y;
        stamina = this.startStamina;

    }

}
