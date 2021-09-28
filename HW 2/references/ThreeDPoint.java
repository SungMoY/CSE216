/**
 * An unmodifiable point in the three-dimensional space. The coordinates are specified by exactly three doubles (its
 * <code>x</code>, <code>y</code>, and <code>z</code> values).
 */
public class ThreeDPoint implements Point {
    private double x;
    private double y;
    private double z;


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public ThreeDPoint(double x, double y, double z) {
        // TODO
        this.x = x;
        this.y = y;
        this.z = z;

    }

    /**
     * @return the (x,y,z) coordinates of this point as a <code>double[]</code>.
     */
    @Override
    public double[] coordinates() {
        double[] ans = new double[3];
        ans[0] = x;
        ans[1] = y;
        ans[2] = z;

        return ans; // TODO
    }

    @Override
    public String toString() {
        return "ThreeDPoint{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
