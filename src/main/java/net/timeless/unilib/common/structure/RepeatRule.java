package net.timeless.unilib.common.structure;

public abstract class RepeatRule {
    private int spacingX;
    private int spacingY;
    private int spacingZ;

    public void setSpacing(int spacingX, int spacingY, int spacingZ) {
        this.spacingX = spacingX;
        this.spacingY = spacingY;
        this.spacingZ = spacingZ;
    }

    public int getSpacingX() {
        return spacingX;
    }

    public int getSpacingY() {
        return spacingY;
    }

    public int getSpacingZ() {
        return spacingZ;
    }
}
