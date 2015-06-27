package net.timeless.unilib.common.structure;

class BlockCoords {
    int x;
    int y;
    int z;

    BlockCoords() {

    }

    BlockCoords(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean equals(Object o) {
        if(o instanceof BlockCoords) {
            BlockCoords coords = (BlockCoords)o;
            return coords.x == x && coords.y == y && coords.z == z;
        }
        return false;
    }

    public int hashCode() {
        final int BASE = 17;
        final int MULTIPLIER = 31;

        int result = BASE;
        result = MULTIPLIER * result + x;
        result = MULTIPLIER * result + y;
        result = MULTIPLIER * result + z;
        return result;
    }
}
