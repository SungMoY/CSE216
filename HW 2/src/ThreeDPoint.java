import java.util.Arrays;

/**
 * An unmodifiable point in the three-dimensional space. The coordinates are specified by exactly three doubles (its
 * <code>x</code>, <code>y</code>, and <code>z</code> values).
 */
public class ThreeDPoint implements Point, Comparable<ThreeDPoint> {

    private double xPoint;
    private double yPoint;
    private double zPoint;

    public ThreeDPoint(double x, double y, double z) {
        // TODO
        this.xPoint = x;
        this.yPoint = y;
        this.zPoint = z;
    }

    /**
     * @return the (x,y,z) coordinates of this point as a <code>double[]</code>.
     */
    @Override
    public double[] coordinates() {
        // TODO
        return new double[]{this.xPoint, this.yPoint, this.zPoint};
    }

    public double getxPoint() {
        return xPoint;
    }

    public void setxPoint(double xPoint) {
        this.xPoint = xPoint;
    }

    public double getyPoint() {
        return yPoint;
    }

    public void setyPoint(double yPoint) {
        this.yPoint = yPoint;
    }

    public double getzPoint() {
        return zPoint;
    }

    public void setzPoint(double zPoint) {
        this.zPoint = zPoint;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.coordinates());
    }

    @Override
    public int compareTo(ThreeDPoint o) {
        double currentPointDistance = Math.sqrt(Math.pow(this.getxPoint(),2 ) + Math.pow(this.getyPoint(), 2) + Math.pow(this.getzPoint(),2));
        double otherPointDistance = Math.sqrt(Math.pow(o.getxPoint(),2 ) + Math.pow(o.getyPoint(), 2) + Math.pow(o.getzPoint(), 2));
        return Double.compare(currentPointDistance, otherPointDistance);
    }
}
