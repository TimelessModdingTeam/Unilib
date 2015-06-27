package net.timeless.unilib.common.structure;

public class FixedRule extends RepeatRule {
    private final int times;
    private int countdown;

    public FixedRule(int times) {
        super();
        this.times = times;
        this.countdown = times;
    }

    public boolean continueRepeating() {
        return countdown > 0;
    }

    public void repeat() {
        countdown--;
    }

}
