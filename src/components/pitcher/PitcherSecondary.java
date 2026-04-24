package components.pitcher;

import components.map.Map;

/**
 * Layered implementation of secondary methods for {@code Pitcher}.
 */
public abstract class PitcherSecondary implements Pitcher {

    /**
     * Constructor called when subclass Pitcher1 is called.
     */
    PitcherSecondary() {

    }

    @Override
    public final String decidePitchType() {
        double roll = Math.random();
        double cumulative = 0.0;
        String key = "FF";

        Map<String, Double> pitchMix = this.getPitchMix();
        for (Map.Pair<String, Double> entry : pitchMix) {
            cumulative += entry.value();
            if (roll < cumulative) {
                key = entry.key();
            }
        }

        return key;
    }

    @Override
    public final double[] pitchSpot() {
        String pitchType = this.decidePitchType();

        double avgX = 0.0;
        double avgZ = 0.0;

        Map<String, Double> pitchLocs = this.getPitchLocs();
        if (pitchLocs.hasKey(pitchType + "_x")) {
            avgX = pitchLocs.value(pitchType + "_x");
            avgX = avgX / 0.835; //Updates to 2 by 2
        }

        if (pitchLocs.hasKey(pitchType + "_z")) {
            avgZ = pitchLocs.value(pitchType + "_z");
            avgZ = avgZ - 2.5; //Updates to 2 by 2
        }

        // To make inconsistent
        double variance = 0.15;
        double x = avgX + (Math.random() * 2 - 1) * variance;
        double z = avgZ + (Math.random() * 2 - 1) * variance;

        // Restricts to [-1.5, 1.5]
        x = Math.max(-1.5, Math.min(1.5, x));
        z = Math.max(-1.5, Math.min(1.5, z));

        return new double[] { x, z };
    }

    @Override
    public final double velocityPercentOfOriginal() {
        // Only allows for a 5% dropoff when at tired status,
        // declining quicker when drained
        int staminaLoss = 100 - this.getStamina();
        double percentCo = 1.0;
        if (staminaLoss <= 80) {
            double veloDropFactor = 0.000625;
            percentCo -= (staminaLoss * veloDropFactor);
        } else {
            double veloDropFactor = 0.005;
            percentCo -= (staminaLoss * veloDropFactor) + 0.35;
        }

        return percentCo;
    }

    @Override
    public final double calculateWHIP() {
        double walks = this.getStat("baseOnBalls");
        double hits = this.getStat("hits");
        double inningsPitched = this.getStat("inningsPitched");

        double whip = 0.0;
        if (inningsPitched != 0.0) {
            whip = (walks + hits) / inningsPitched;
        }

        return whip;
    }

    @Override
    public final boolean isTired() {
        boolean tired = false;
        if (this.getStamina() <= 20) {
            tired = true;
        }
        return tired;
    }

    @Override
    public final void resetOutingState() {
        int change = PitcherKernel.START_STAMINA - this.getStamina();
        this.adjustStamina(change);
    }

    @Override
    public final boolean equals(Object obj) {
        boolean eq = true;
        if (this == obj) {
            eq = true;
        } else if (obj == null) {
            eq = false;
        } else if (!(obj instanceof Pitcher)) {
            eq = false;
        } else {
            Pitcher p = (Pitcher) obj;
            if (this.getID() != p.getID()) {
                eq = false;
            } else if (this.getYear() != p.getYear()) {
                eq = false;
            }
        }

        return eq;
    }

    @Override
    public final int hashCode() {
        int id = this.getID();
        int sum = 0;
        while (id != 0) {
            sum += id % 10;
            id /= 10;
        }

        return sum;
    }

    @Override
    public final String toString() {
        String str = "Name: " + this.getName() + "\nSeason Year: "
                + this.getYear() + "\nPosition: Pitcher" + "\nCurrent Stamina: "
                + this.getStamina();
        return str;
    }

    @Override
    public abstract void setStats(int id, int y);

    @Override
    public abstract int getStamina();

    @Override
    public abstract void adjustStamina(int change);

    @Override
    public abstract int getPitchCount();

    @Override
    public abstract void pitchThrown(boolean strike, boolean foul,
            boolean hitByPitch, int outsRecorded, int basesAdvanced,
            boolean error, int earnedRuns);

    @Override
    public abstract double getStat(String key);

    @Override
    public abstract boolean hasStat(String key);

    @Override
    public abstract String getName();

    @Override
    public abstract int getID();

    @Override
    public abstract void clear();

    @Override
    public abstract void transferFrom(Pitcher source);

    @Override
    public abstract Pitcher newInstance();
}
