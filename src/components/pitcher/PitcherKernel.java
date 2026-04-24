package components.pitcher;

import components.map.Map;
import components.standard.Standard;

/**
 * Pitcher kernel component with primary methods.
 *
 * @author Samuel Gassman
 *
 */
public interface PitcherKernel extends Standard<Pitcher> {

    /**
     * Initial stamina for any pitcher, maximum value.
     */
    int START_STAMINA = 100;

    /**
     * Stamina threshold for when a pitcher is considered tired.
     */
    int TIRED_THRESHOLD = 20;

    /**
     * Loads pitcher's season stats from data using given player ID and season
     * year, and replaces any previously stored statistics for pitcher.
     *
     * @param id
     * @param y
     *
     * @replaces this.stats
     */
    void setStats(int id, int y);

    /**
     * Returns the current stamina level of pitcher.
     *
     * @return current stamina of {@code this} pitcher
     */
    int getStamina();

    /**
     * Adjusts stamina of pitcher by integer change. Positive value increases
     * and negative value decreases. Cannot go below 0 or above START_STAMINA.
     *
     * @param change
     */
    void adjustStamina(int change);

    /**
     * Returns the value of the statistic with the given key fo rthis pitcher,
     * or 0.0 if the statistic is not present.
     *
     * @param key
     * @return value of statistic from statMap based on key
     */
    double getStat(String key);

    /**
     * Reports whether pitcher has a recorded value for the statistic with the
     * given key.
     *
     * @param key
     * @return whether whether pitcher has a certain stat recorded
     */
    boolean hasStat(String key);

    /**
     * Returns the full name of the pitcher.
     *
     * @return full name of pitcher.
     */
    String getName();

    /**
     * Returns the ID of the player in MLB's database.
     *
     * @return MLB ID of {@code this} pitcher
     */
    int getID();

    /**
     * Returns season year of the player.
     *
     * @return {@code int} for the season year of {@code this}
     */
    int getYear();

    /**
     * Returns average pitch locations for each type of pitch by a map.
     *
     * @return {@code map} keyed by pitch type and x or y coordinate with value
     *         of average location on strike zone.
     */
    Map<String, Double> getPitchLocs();

    /**
     * Returns the pitch mix that the pitcher has.
     *
     * @return {@code map} keyed by pitch type and value is the percentage of
     *         its usage frequency
     */
    Map<String, Double> getPitchMix();
}
