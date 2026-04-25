package components.pitcher;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * JUnit test fixture for {@code PitcherSecondary}'s enhanced methods
 */
public class PitcherSecondaryTest {

    @Test
    public void testDecidePitchType() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        String type1 = jakeArrieta.decidePitchType();
        String type2 = jakeArrieta.decidePitchType();
        String type3 = jakeArrieta.decidePitchType();
        Pitcher jakeArrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        boolean different = (!type1.equals(type2)) || (!type1.equals(type3))
                || (!type2.equals(type3));

        assertEquals(true, different);
        assertEquals(jakeArrietaCopy, jakeArrieta);
    }

    @Test
    public void testPitchSpot() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        double[] spot1 = jakeArrieta.pitchSpot();
        double[] spot2 = jakeArrieta.pitchSpot();
        double[] spot3 = jakeArrieta.pitchSpot();
        Pitcher jakeArrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        boolean isAllowed1 = (spot1[0] <= 1.5 && spot1[0] >= -1.5)
                && (spot1[1] <= 1.5 && spot1[1] >= -1.5);
        boolean isAllowed2 = (spot2[0] <= 1.5 && spot2[0] >= -1.5)
                && (spot2[1] <= 1.5 && spot2[1] >= -1.5);
        boolean isAllowed3 = (spot3[0] <= 1.5 && spot3[0] >= -1.5)
                && (spot3[1] <= 1.5 && spot3[1] >= -1.5);

        boolean different = (!spot1.equals(spot2)) || (!spot1.equals(spot3))
                || (!spot2.equals(spot3));
        boolean result = isAllowed1 && isAllowed2 && isAllowed3 && different;

        assertEquals(true, result);
        assertEquals(jakeArrietaCopy, jakeArrieta);
    }

    @Test
    public void testVelocityPercentOfOriginalStamina100() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        double veloPercent = jakeArrieta.velocityPercentOfOriginal();
        Pitcher jakeArrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals(1.0, veloPercent, 0.01);
        assertEquals(jakeArrietaCopy, jakeArrieta);
    }

    @Test
    public void testVelocityPercentOfOriginalStamina20() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        jakeArrieta.adjustStamina(-80);
        double veloPercent = jakeArrieta.velocityPercentOfOriginal();
        Pitcher jakeArrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals(0.95, veloPercent, 0.01);
        assertEquals(jakeArrietaCopy, jakeArrieta);
    }

    @Test
    public void testVelocityPercentOfOriginalStamina0() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        jakeArrieta.adjustStamina(-100);
        double veloPercent = jakeArrieta.velocityPercentOfOriginal();
        Pitcher jakeArrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals(0.85, veloPercent, 0.01);
        assertEquals(jakeArrietaCopy, jakeArrieta);
    }

    @Test
    public void testCalculateWHIP() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        double whip = jakeArrieta.calculateWHIP();
        Pitcher jakeArrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        double whipExpected = jakeArrieta.getStat("whip");

        assertEquals(whipExpected, whip, 0.001);
        assertEquals(jakeArrietaCopy, jakeArrieta);
    }

    @Test
    public void testIsTiredTrue() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        jakeArrieta.adjustStamina(-80);
        boolean isTired = jakeArrieta.isTired();
        Pitcher jakeArrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals(true, isTired);
        assertEquals(jakeArrietaCopy, jakeArrieta);
    }

    @Test
    public void testIsTiredFalse() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        jakeArrieta.adjustStamina(-50);
        boolean isTired = jakeArrieta.isTired();
        Pitcher jakeArrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals(false, isTired);
        assertEquals(jakeArrietaCopy, jakeArrieta);
    }

    @Test
    public void testResetOutingStateAlreadyFull() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        jakeArrieta.resetOutingState();
        Pitcher jakeArrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        int stamina = jakeArrieta.getStamina();

        assertEquals(100, stamina);
        assertEquals(jakeArrietaCopy, jakeArrieta);
    }

    @Test
    public void testResetOutingStateTired() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        jakeArrieta.adjustStamina(-90);
        jakeArrieta.resetOutingState();
        Pitcher jakeArrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        int stamina = jakeArrieta.getStamina();

        assertEquals(100, stamina);
        assertEquals(jakeArrietaCopy, jakeArrieta);
    }

    @Test
    public void testEqualsFalse1() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        Pitcher randyJohnson = new Pitcher1("Randy", "Johnson", 116615, 2008);
        boolean equal = jakeArrieta.equals(randyJohnson);
        Pitcher jakeArrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        Pitcher randyJohnsonCopy = new Pitcher1("Randy", "Johnson", 116615,
                2008);

        assertEquals(false, equal);
        assertEquals(jakeArrietaCopy, jakeArrieta);
        assertEquals(randyJohnsonCopy, randyJohnson);
    }

    @Test
    public void testEqualsTrue() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        Pitcher randyJohnsonDisguise = new Pitcher1("Randy", "Johnson", 453562,
                2015);
        boolean equal = jakeArrieta.equals(randyJohnsonDisguise);
        Pitcher jakeArrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        Pitcher randyJohnsonDisguiseCopy = new Pitcher1("Randy", "Johnson",
                453562, 2015);

        assertEquals(true, equal);
        assertEquals(jakeArrietaCopy, jakeArrieta);
        assertEquals(randyJohnsonDisguiseCopy, randyJohnsonDisguise);
    }

    @Test
    public void testEqualsFalse2() {
        Pitcher jakeArrieta2015 = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        Pitcher jakeArrieta2016 = new Pitcher1("Randy", "Johnson", 453562,
                2016);
        boolean equal = jakeArrieta2015.equals(jakeArrieta2016);
        Pitcher jakeArrieta2015Copy = new Pitcher1("Jake", "Arrieta", 453562,
                2015);
        Pitcher jakeArrieta2016Copy = new Pitcher1("Randy", "Johnson", 453562,
                2016);

        assertEquals(false, equal);
        assertEquals(jakeArrieta2015Copy, jakeArrieta2015);
        assertEquals(jakeArrieta2016Copy, jakeArrieta2016);
    }

    @Test
    public void testHashCode() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        int hash = jakeArrieta.hashCode();
        Pitcher jakeArrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals(25, hash);
        assertEquals(jakeArrietaCopy, jakeArrieta);
    }

    @Test
    public void testToString() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        String str = jakeArrieta.toString();
        String strExpected = "Name: Jake Arrieta\nSeason Year: 2015\nPosition: Pitcher"
                + "\nCurrent Stamina: 100";
        Pitcher jakeArrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals(strExpected, str);
        assertEquals(jakeArrietaCopy, jakeArrieta);
    }
}
