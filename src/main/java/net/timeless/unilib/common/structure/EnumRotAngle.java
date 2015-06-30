package net.timeless.unilib.common.structure;

public enum EnumRotAngle {
    DEGREES_90(90),
    DEGREES_180(180),
    DEGREES_270(270);

    private final int angle;

    EnumRotAngle(int angle) {
        this.angle = angle;
    }

    public int value() {
        return angle;
    }
}
