package math;

/**
 * Created by виталий on 29.11.2016.
 */
public class Point {
    BigFraction X;
    BigFraction Y;
    public Point(BigFraction x, BigFraction y) {
        X = x;
        Y = y;
    }

    public BigFraction getX() {
        return X;
    }

    public void setX(BigFraction x) {
        X = x;
    }

    public BigFraction getY() {
        return Y;
    }

    public void setY(BigFraction y) {
        Y = y;
    }
}
