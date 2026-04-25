package components.pitcher;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;

/**
 * JUnit test fixture for {@code Pitcher}'s kernel methods
 */
public class PitcherKernelTest {

    @Test
    public void testSetStats() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        Pitcher berriosToArrieta = new Pitcher1("Jake", "Arrieta", 621244,
                2025);
        berriosToArrieta.setStats(453562, 2015);

        assertEquals(jakeArrieta, berriosToArrieta);
    }

    @Test
    public void testGetStamina() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        int stamina = jakeArrieta.getStamina();
        Pitcher arrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals(100, stamina);
        assertEquals(arrietaCopy, jakeArrieta);
    }

    @Test
    public void testAdjustStaminaNegative() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        jakeArrieta.adjustStamina(-30);
        Pitcher arrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals(arrietaCopy, jakeArrieta);

        int stamina = jakeArrieta.getStamina();

        assertEquals(70, stamina);
        assertEquals(arrietaCopy, jakeArrieta);
    }

    @Test
    public void testAdjustStaminaNegPastZero() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        jakeArrieta.adjustStamina(-140);
        Pitcher arrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals(arrietaCopy, jakeArrieta);

        int stamina = jakeArrieta.getStamina();

        assertEquals(0, stamina);
        assertEquals(arrietaCopy, jakeArrieta);
    }

    @Test
    public void testAdjustStaminaNeutral() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        jakeArrieta.adjustStamina(0);
        Pitcher arrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals(arrietaCopy, jakeArrieta);

        int stamina = jakeArrieta.getStamina();

        assertEquals(100, stamina);
        assertEquals(arrietaCopy, jakeArrieta);
    }

    @Test
    public void testAdjustStaminaPositive() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        jakeArrieta.adjustStamina(-30);
        jakeArrieta.adjustStamina(15);
        Pitcher arrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals(arrietaCopy, jakeArrieta);

        int stamina = jakeArrieta.getStamina();

        assertEquals(85, stamina);
        assertEquals(arrietaCopy, jakeArrieta);
    }

    @Test
    public void testAdjustStaminaPosPastMax() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        jakeArrieta.adjustStamina(20);
        Pitcher arrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals(arrietaCopy, jakeArrieta);

        int stamina = jakeArrieta.getStamina();

        assertEquals(100, stamina);
        assertEquals(arrietaCopy, jakeArrieta);
    }

    @Test
    public void testGetStatHRAllowed() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        double homeRunsAllowed = jakeArrieta.getStat("homeRuns");
        Pitcher arrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals(10.0, homeRunsAllowed, 0.00001);
        assertEquals(arrietaCopy, jakeArrieta);
    }

    @Test
    public void testGetStatwOBAAllowed() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        double wOBAAllowed = jakeArrieta.getStat("wOBAAllowed");
        Pitcher arrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals(0.24668972332015832, wOBAAllowed, 0.00001);
        assertEquals(arrietaCopy, jakeArrieta);
    }

    @Test
    public void testGetStatAvgFastballVelo() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        double avgFastballVelo = jakeArrieta.getStat("avgFastballVelo");
        Pitcher arrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals(91.53097274184931, avgFastballVelo, 0.00001);
        assertEquals(arrietaCopy, jakeArrieta);
    }

    @Test
    public void testHasStatERATrue() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        boolean era = jakeArrieta.hasStat("era");
        Pitcher arrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals(true, era);
        assertEquals(arrietaCopy, jakeArrieta);
    }

    @Test
    public void testHasStatNoHittersFalse() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        boolean noHitters = jakeArrieta.hasStat("noHitters");
        Pitcher arrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals(false, noHitters);
        assertEquals(arrietaCopy, jakeArrieta);
    }

    @Test
    public void testGetName() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        String name = jakeArrieta.getName();
        Pitcher arrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals("Jake Arrieta", name);
        assertEquals(arrietaCopy, jakeArrieta);
    }

    @Test
    public void testGetID() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        int id = jakeArrieta.getID();
        Pitcher arrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals(453562, id);
        assertEquals(arrietaCopy, jakeArrieta);
    }

    @Test
    public void testGetYear() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        int year = jakeArrieta.getYear();
        Pitcher arrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        assertEquals(2015, year);
        assertEquals(arrietaCopy, jakeArrieta);
    }

    @Test
    public void testGetPitchMix() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        Map<String, Double> pitchMix = jakeArrieta.getPitchMix();
        Pitcher arrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        Map<String, Double> pitchMixExpected = new Map1L<>();
        pitchMixExpected.add("FF", 0.18089376505218088);
        pitchMixExpected.add("CU", 0.14771206850414773);
        pitchMixExpected.add("CH", 0.04575862991704576);
        pitchMixExpected.add("IN", 0.002675943270002676);
        pitchMixExpected.add("SI", 0.33261974846133263);
        pitchMixExpected.add("SL", 0.29033984479529035);

        assertEquals(6, pitchMix.size());
        boolean matching = true;
        for (Map.Pair<String, Double> pair : pitchMix) {
            if (!pitchMixExpected.hasKey(pair.key()) || !pitchMixExpected
                    .value(pair.key()).equals(pair.value())) {
                matching = false;
            }
        }
        assertEquals(true, matching);
        assertEquals(arrietaCopy, jakeArrieta);
    }

    @Test
    public void testGetPitchLocs() {
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        Map<String, Double> pitchLocs = jakeArrieta.getPitchLocs();
        Pitcher arrietaCopy = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        Map<String, Double> pitchLocsExpected = new Map1L<>();
        pitchLocsExpected.add("CH_x", -0.662998971697418);
        pitchLocsExpected.add("CU_z", 1.7227426321062318);
        pitchLocsExpected.add("IN_z", 4.380207816158793);
        pitchLocsExpected.add("SL_z", 2.194581239489354);
        pitchLocsExpected.add("CU_x", -0.11026755571186912);
        pitchLocsExpected.add("SL_x", 0.3520967107952427);
        pitchLocsExpected.add("SI_z", 2.4847902538658238);
        pitchLocsExpected.add("FF_x", -0.0773321596636216);
        pitchLocsExpected.add("SI_x", -0.24774549692287512);
        pitchLocsExpected.add("FF_z", 2.675973471890135);
        pitchLocsExpected.add("IN_x", 0.5374933221352399);
        pitchLocsExpected.add("CH_z", 1.6248450005123538);

        assertEquals(12, pitchLocs.size());
        boolean matching = true;
        for (Map.Pair<String, Double> pair : pitchLocs) {
            if (!pitchLocsExpected.hasKey(pair.key()) || !pitchLocsExpected
                    .value(pair.key()).equals(pair.value())) {
                matching = false;
            }
        }
        assertEquals(true, matching);
        assertEquals(arrietaCopy, jakeArrieta);
    }

    @Test
    public void testNewInstance() {
        Pitcher randyJohnson = new Pitcher1("Randy", "Johnson", 116615, 2008);
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        Pitcher randyJohnsonCopy = new Pitcher1("Randy", "Johnson", 116615,
                2008);

        Pitcher newInst = randyJohnson.newInstance();
        assertEquals(jakeArrieta, newInst);
        assertEquals(randyJohnsonCopy, randyJohnson);
    }

    @Test
    public void testClear() {
        Pitcher randyJohnson = new Pitcher1("Randy", "Johnson", 116615, 2008);
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);
        randyJohnson.clear();

        assertEquals(jakeArrieta, randyJohnson);
    }

    @Test
    public void testTransferFrom() {
        Pitcher joseBerrios = new Pitcher1("Jose", "Berrios", 621244, 2025);
        Pitcher joseBerriosCopy = new Pitcher1("Jose", "Berrios", 621244, 2025);
        Pitcher randyJohnson = new Pitcher1("Randy", "Johnson", 116615, 2008);
        Pitcher jakeArrieta = new Pitcher1("Jake", "Arrieta", 453562, 2015);

        randyJohnson.transferFrom(joseBerrios);

        assertEquals(joseBerriosCopy, randyJohnson);
        assertEquals(jakeArrieta, joseBerrios);
    }
}
