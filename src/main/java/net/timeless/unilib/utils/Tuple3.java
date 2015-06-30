package net.timeless.unilib.utils;

public class Tuple3<X, Y, Z> {

    private X x;
    private Y y;
    private Z z;

    public Tuple3() {
        this(null, null, null);
    }

    public Tuple3(X x, Y y, Z z) {
        set(x, y, z);
    }

    public X getX() {
        return x;
    }

    public void setX(X x) {
        this.x = x;
    }

    public Y getY() {
        return y;
    }

    public void setY(Y y) {
        this.y = y;
    }

    public Z getZ() {
        return z;
    }

    public void setZ(Z z) {
        this.z = z;
    }

    public void set(X x, Y y, Z z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
