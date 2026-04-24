package components.pitcher;

/**
 * Enhanced interface for Pitcher component. Provides secondary methods on top
 * of PitcherKernel and Standard.
 *
 * @author Samuel Gassman
 */
public interface Pitcher extends PitcherKernel {

    /**
     * Determines recommended pitch type for this pitcher to throw based on
     * statistical profile.
     *
     * @return {@code String} of the pitch type of next pitch based on
     *         probability of pitch type.
     */
    String decidePitchType();

    /**
     * Calculates the recommended location for the next pitch as a coordinate
     * pair within the strike zone plane. The plane ranges from [-1.0, 1.0] on
     * the x axis and [-1.0, 1.0] on the y-axis. values outside this range are
     * outside the strike zone.
     *
     * @return the {@code double[]} that shows the spot of the next pitch based
     *         off statistical probabilities
     */
    double[] pitchSpot();

    /**
     * Returns the effective percentage of original velocity the next pitch can
     * be accounting for stamina. As stamina decreases, velocity decreases
     * proportionally of the pitcher's recorded average fastball velocity.
     *
     * @return a {@code double} of the effective velocity
     */
    double velocityPercentOfOriginal();

    /**
     * Calculates this pitcher's current WHIP using kernel stat values for
     * walks, hits, and innings pitched accumulated during the current session.
     *
     * @return the pitcher's WHIP in the current game
     */
    double calculateWHIP();

    /**
     * Reports whether this pitcher is considered tired, defined as stamina at
     * or below TIRED_THRESHOLD.
     *
     * @return a {@code boolean} of whether pitcher is tired or not
     */
    boolean isTired();

    /**
     * Resets pitcher's in-game state without altering the stored season
     * statistics or identity fields.
     */
    void resetOutingState();

}
