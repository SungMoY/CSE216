import java.util.Arrays;
import java.util.List;

public class Quadrilateral implements Positionable, TwoDShape {

    private final TwoDPoint[] vertices = new TwoDPoint[4];

    public TwoDPoint[] getVertices() {
        return vertices;
    }

    public Quadrilateral(double... vertices) {
        this(TwoDPoint.ofDoubles(vertices));
    }

    public Quadrilateral(List<TwoDPoint> vertices) {
        int n = 0;
        for (TwoDPoint p : vertices) {
            this.vertices[n++] = p;
        }


        if (!isMember(vertices))
            throw new IllegalArgumentException(String.format("Invalid set of vertices specified for %s",
                                                             this.getClass().getCanonicalName()));
    }

    /**
     * Given a list of four points, adds them as the four vertices of this quadrilateral in the order provided in the
     * list. This is expected to be a counterclockwise order of the four corners.
     *
     * @param points the specified list of points.
     * @throws IllegalStateException if the number of vertices provided as input is not equal to four.
     */
    @Override
    public void setPosition(List<? extends Point> points)  {
        // TODO

            if (points.size() != 4) {
                throw new IllegalStateException("Number of vertices is not equal to 4");
            }
            for (int i = 0; i<4;i++) {
                Point tempPoint = points.get(i);
                double[] tempPointArray = tempPoint.coordinates();

                vertices[i] = new TwoDPoint(tempPointArray[0], tempPointArray[1]);
            }



    }

    @Override
    public List<TwoDPoint> getPosition() {
        return Arrays.asList(vertices);
    }

    /**
     * @return the lengths of the four sides of the quadrilateral. Since the setter {@link Quadrilateral#setPosition(List)}
     *         expected the corners to be provided in a counterclockwise order, the side lengths are expected to be in
     *         that same order.
     */
    protected double[] getSideLengths() {
        double[] p0 = this.vertices[0].coordinates();
        double[] p1 = this.vertices[1].coordinates();
        double[] p2 = this.vertices[2].coordinates();
        double[] p3 = this.vertices[3].coordinates();
        // 4 points to find side length
        double[] sideLengths = new double[4];
        double xdiff;
        double ydiff;


        xdiff = p0[0] - p1[0]; //finds the distance between 0 and 1
        ydiff = p0[1] - p1[1];
        sideLengths[0] = (Math.round(Math.sqrt(xdiff*xdiff + ydiff*ydiff)*1000.))/1000.;
        xdiff = p1[0] - p2[0]; //fidns the distqance between 1 and 2
        ydiff = p1[1] - p2[1];
        sideLengths[1] = (Math.round(Math.sqrt(xdiff*xdiff + ydiff*ydiff)*1000))/1000.;
        xdiff = p2[0] - p3[0];
        ydiff = p2[1] - p3[1];
        sideLengths[2] = (Math.round(Math.sqrt(xdiff*xdiff + ydiff*ydiff)*1000))/1000.;
        xdiff = p3[0] - p0[0];
        ydiff = p3[1] - p0[1];
        //System.out.println(ydiff);
        sideLengths[3] = (Math.round(Math.sqrt(xdiff*xdiff + ydiff*ydiff)*1000))/1000.;

        return sideLengths; // TODO
    }

    @Override
    public int numSides() { return 4; }

    @Override
    public boolean isMember(List<? extends Point> vertices) { return vertices.size() == 4; }
}
