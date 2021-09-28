import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An unmodifiable point in the standard two-dimensional Euclidean space. The coordinates of such a point is given by
 * exactly two doubles specifying its <code>x</code> and <code>y</code> values.
 */
public class TwoDPoint implements Point, Comparable<TwoDPoint> {
    private double xPoint;
    private double yPoint;

    public TwoDPoint(double x, double y) {
        // TODO
        this.xPoint = x;
        this.yPoint = y;
    }

    /**
     * @return the coordinates of this point as a <code>double[]</code>.
     */
    @Override
    public double[] coordinates() {
        // TODO
        return new double[]{this.xPoint, this.yPoint};
    }

    /**
     * Returns a list of <code>TwoDPoint</code>s based on the specified array of doubles. A valid argument must always
     * be an even number of doubles so that every pair can be used to form a single <code>TwoDPoint</code> to be added
     * to the returned list of points.
     *
     * @param coordinates the specified array of doubles.
     * @return a list of two-dimensional point objects.
     * @throws IllegalArgumentException if the input array has an odd number of doubles.
     */
    public static List<TwoDPoint> ofDoubles(double... coordinates) throws IllegalArgumentException {
        // TODO
        if (coordinates.length % 2 == 1) {
            throw new IllegalArgumentException("Odd number of doubles");
        }
        List<TwoDPoint> returnList = new ArrayList<>();
        for (int i = 0;i < coordinates.length; i+= 2) {
            returnList.add(new TwoDPoint(coordinates[i], coordinates[i+1]));
        }
        return returnList;
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

    @Override
    public String toString() {
        return "("+this.getxPoint()+", "+this.getyPoint()+")";
    }

    @Override
    public int compareTo(TwoDPoint o) {
        double currentPointDistance = Math.sqrt(Math.pow(this.getxPoint(),2 ) + Math.pow(this.getyPoint(), 2));
        double otherPointDistance = Math.sqrt(Math.pow(o.getxPoint(),2 ) + Math.pow(o.getyPoint(), 2));
        return Double.compare(currentPointDistance, otherPointDistance);
    }
}
