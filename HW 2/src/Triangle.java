import java.util.ArrayList;
import java.util.List;

public class Triangle implements TwoDShape, Positionable, Comparable<TwoDShape> {

    private List<TwoDPoint> vertices = new ArrayList<>();

    public Triangle(List<TwoDPoint> vertices) {
        // TODO
        setPosition(vertices);
    }

    /**
     * Sets the position of this triangle according to the first three elements in the specified list of points. The
     * triangle is formed on the basis of these three points taken in a clockwise manner on the two-dimensional
     * x-y plane. If the input list has more than three elements, the subsequent elements are ignored.
     *
     * @param points the specified list of points.
     */
    @Override
    public void setPosition(List<? extends Point> points) {
        // TODO
        // sort upon construction
        if (!isMember(points)) {
            throw new IllegalArgumentException("Not a valid triangle");
        }
        List<TwoDPoint> tempList = new ArrayList<>();
        tempList.add((TwoDPoint) points.get(0));
        tempList.add((TwoDPoint) points.get(1));
        tempList.add((TwoDPoint) points.get(2));

        //bubble sort by x values
        for (int i = 0; i < tempList.size()-1; i++)
            for (int j = 0; j < tempList.size()-i-1; j++)
                if (tempList.get(j).getxPoint() > tempList.get(j+1).getxPoint())
                {
                    TwoDPoint temp = tempList.get(j);
                    tempList.set(j, tempList.get(j+1));
                    tempList.set(j+1, temp);
                }
        //bubble sort of y values if x values are the same
        for (int i = 0; i < tempList.size()-1; i++)
            for (int j = 0; j < tempList.size()-i-1; j++)
                if (tempList.get(j).getxPoint() == tempList.get(j+1).getxPoint())
                {
                    if (tempList.get(j).getyPoint() > tempList.get(j+1).getyPoint()) {
                        // swap arr[j+1] and arr[j]
                        TwoDPoint temp = tempList.get(j);
                        tempList.set(j, tempList.get(j + 1));
                        tempList.set(j + 1, temp);
                    }
                }
        //at this point the 0th index is the least x value and the least y value or, the starting point

        //check to maintain clockwise manner of points. If the point3 x value is >= to point2 x value, must check to see if
        //point3 is ABOVE point2. In this case, the clockwise pattern will use point3 before point2 so they must be swapped.
        if (tempList.get(1).getyPoint() < tempList.get(2).getyPoint() && tempList.get(1).getxPoint() <= tempList.get(2).getxPoint()) {
            TwoDPoint temp = tempList.get(2);
            tempList.set(2, tempList.get(1));
            tempList.set(1, temp);
        }
        this.vertices = tempList;
    }

    /**
     * Retrieve the position of an object as a list of points. The points are be retrieved and added to the returned
     * list in a clockwise manner on the two-dimensional x-y plane, starting with the point with the least x-value. If
     * two points have the same least x-value, then the clockwise direction starts with the point with the lower y-value.
     *
     * @return the retrieved list of points.
     */
    @Override
    public List<? extends Point> getPosition() {
        // TODO
        //already sorted upon construction so simply return the list
        return this.vertices;
    }

    /**
     * @return the number of sides of this triangle, which is always set to three
     */
    @Override
    public int numSides() {
        return 3;
    }

    /**
     * Checks whether or not a list of vertices forms a valid triangle. The <i>trivial</i> triangle, where all three
     * corner vertices are the same point, is considered to be an invalid triangle.
     *
     * @param vertices the list of vertices to check against, where each vertex is a <code>Point</code> type.
     * @return <code>true</code> if <code>vertices</code> is a valid collection of points for a triangle, and
     * <code>false</code> otherwise. For example, three vertices are in a straight line is invalid.
     */
    @Override
    public boolean isMember(List<? extends Point> vertices) {
        // TODO
        if (vertices.size() < 3) {
            return false;
        }
        if (!(vertices.get(0) instanceof TwoDPoint)) {
            return false;
        }
        if (!(vertices.get(1) instanceof TwoDPoint)) {
            return false;
        }
        if (!(vertices.get(2) instanceof TwoDPoint)) {
            return false;
        }
        double distanceFirstToSecond = Math.sqrt(
                Math.pow(((TwoDPoint)vertices.get(1)).getxPoint() - ((TwoDPoint)vertices.get(0)).getxPoint(), 2) +
                        Math.pow(((TwoDPoint)vertices.get(1)).getyPoint() - ((TwoDPoint)vertices.get(0)).getyPoint(), 2));

        double distanceSecondToThird = Math.sqrt(
                Math.pow(((TwoDPoint)vertices.get(2)).getxPoint() - ((TwoDPoint)vertices.get(1)).getxPoint(), 2) +
                        Math.pow(((TwoDPoint)vertices.get(2)).getyPoint() - ((TwoDPoint)vertices.get(1)).getyPoint(), 2));

        double distanceThirdToFirst = Math.sqrt(
                Math.pow(((TwoDPoint)vertices.get(0)).getxPoint() - ((TwoDPoint)vertices.get(2)).getxPoint(), 2) +
                        Math.pow(((TwoDPoint)vertices.get(0)).getyPoint() - ((TwoDPoint)vertices.get(2)).getyPoint(), 2));
        double sValue = distanceFirstToSecond+distanceSecondToThird+distanceThirdToFirst;
        sValue *= 0.5;
        double area = Math.sqrt(sValue*(sValue-distanceFirstToSecond)*(sValue-distanceSecondToThird)*(sValue-distanceThirdToFirst));
        if (area == 0.0) {
            return false;
        }
        return true;
    }

    /**
     * This method snaps each vertex of this triangle to its nearest integer-valued x-y coordinate. For example, if
     * a corner is at (0.8, -0.1), it will be snapped to (1,0). The resultant triangle will thus have all four
     * vertices in positions with integer x and y values. If the snapping procedure described above results in this
     * triangle becoming invalid (e.g., all corners collapse to a single point), then it is left unchanged. Snapping is
     * an in-place procedure, and the current instance is modified.
     */
    public void snap() {
        // TODO
        TwoDPoint tempFirst = new TwoDPoint(0,0);
        TwoDPoint tempSecond = new TwoDPoint(0,0);
        TwoDPoint tempThird = new TwoDPoint(0,0);

        tempFirst.setxPoint(Math.round(this.vertices.get(0).getxPoint()));
        tempFirst.setyPoint(Math.round(this.vertices.get(0).getyPoint()));

        tempSecond.setxPoint(Math.round(this.vertices.get(1).getxPoint()));
        tempSecond.setyPoint(Math.round(this.vertices.get(1).getyPoint()));

        tempThird.setxPoint(Math.round(this.vertices.get(2).getxPoint()));
        tempThird.setyPoint(Math.round(this.vertices.get(2).getyPoint()));

        List<TwoDPoint> tempList = new ArrayList<>();
        tempList.add(tempFirst);
        tempList.add(tempSecond);
        tempList.add(tempThird);

        if ((isMember(tempList))) {
            this.vertices = tempList;
        }
    }

    /**
     * @return the area of this triangle
     */
    @Override
    public double area() {
        // TODO
        double distanceFirstToSecond = Math.sqrt(
                Math.pow(((TwoDPoint)vertices.get(1)).getxPoint() - ((TwoDPoint)vertices.get(0)).getxPoint(), 2) +
                        Math.pow(((TwoDPoint)vertices.get(1)).getyPoint() - ((TwoDPoint)vertices.get(0)).getyPoint(), 2));
        double distanceSecondToThird = Math.sqrt(
                Math.pow(((TwoDPoint)vertices.get(2)).getxPoint() - ((TwoDPoint)vertices.get(1)).getxPoint(), 2) +
                        Math.pow(((TwoDPoint)vertices.get(2)).getyPoint() - ((TwoDPoint)vertices.get(1)).getyPoint(), 2));
        double distanceThirdToFirst = Math.sqrt(
                Math.pow(((TwoDPoint)vertices.get(0)).getxPoint() - ((TwoDPoint)vertices.get(2)).getxPoint(), 2) +
                        Math.pow(((TwoDPoint)vertices.get(0)).getyPoint() - ((TwoDPoint)vertices.get(2)).getyPoint(), 2));
        double sValue = distanceFirstToSecond+distanceSecondToThird+distanceThirdToFirst;
        sValue *= 0.5;
        double area = Math.sqrt(sValue*(sValue-distanceFirstToSecond)*(sValue-distanceSecondToThird)*(sValue-distanceThirdToFirst));
        return Math.round(area*100.0) / 100.0;
    }

    /**
     * @return the perimeter (i.e., the total length of the boundary) of this triangle
     */
    public double perimeter() {
        // TODO
        //Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) * 1.0);
        double distanceFirstToSecond = Math.sqrt(
                                        Math.pow(this.vertices.get(1).getxPoint() - this.vertices.get(0).getxPoint(), 2) +
                                        Math.pow(this.vertices.get(1).getyPoint() - this.vertices.get(0).getyPoint(), 2)
                                                );
        double distanceSecondToThird = Math.sqrt(
                                        Math.pow(this.vertices.get(2).getxPoint() - this.vertices.get(1).getxPoint(), 2) +
                                        Math.pow(this.vertices.get(2).getyPoint() - this.vertices.get(1).getyPoint(), 2)
                                                );
        double distanceThirdToFirst = Math.sqrt(
                                        Math.pow(this.vertices.get(0).getxPoint() - this.vertices.get(2).getxPoint(), 2) +
                                        Math.pow(this.vertices.get(0).getyPoint() - this.vertices.get(2).getyPoint(), 2)
                                                );
        double perimeter = distanceFirstToSecond+distanceSecondToThird+distanceThirdToFirst;
        return Math.round(perimeter*100.0) / 100.0;
    }

    @Override
    public int compareTo(TwoDShape o) {
        return Double.compare(this.area(), o.area());
    }

    @Override
    public String toString() {
        return "Triangle["+this.vertices.get(0).toString()+", "+this.vertices.get(1).toString()+", "+this.vertices.get(2)+"]";
    }

}
