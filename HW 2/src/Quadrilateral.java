import java.util.*;

public class Quadrilateral implements TwoDShape, Positionable, Comparable<TwoDShape> {

    private List<TwoDPoint> vertices = new ArrayList<>();

    public Quadrilateral(List<TwoDPoint> vertices) {
        // TODO
        setPosition(vertices);
    }

    /**
     * Sets the position of this quadrilateral according to the first four elements in the specified list of points. The
     * quadrilateral is formed on the basis of these four points taken in a clockwise manner on the two-dimensional
     * x-y plane. If the input list has more than four elements, the subsequent elements are ignored.
     *
     * @param points the specified list of points.
     */
    @Override
    public void setPosition(List<? extends Point> points) {
        // TODO
        if (!isMember(points)) {
            throw new IllegalArgumentException("Not a valid convex quadrilateral");
        }
        TwoDPoint firstPoint = (TwoDPoint) points.get(0);
        TwoDPoint secondPoint = (TwoDPoint) points.get(1);
        TwoDPoint thirdPoint = (TwoDPoint) points.get(2);
        TwoDPoint fourthPoint = (TwoDPoint) points.get(3);

        TwoDPoint centerPoint = new TwoDPoint(((firstPoint.getxPoint()+secondPoint.getxPoint()+thirdPoint.getxPoint()+fourthPoint.getxPoint())/4),
                ((firstPoint.getyPoint()+secondPoint.getyPoint()+thirdPoint.getyPoint()+fourthPoint.getyPoint())/4)
                );

        double angleFirst = Math.atan2(firstPoint.getxPoint() - centerPoint.getxPoint(), firstPoint.getyPoint() - centerPoint.getyPoint());
        double angleSecond = Math.atan2(secondPoint.getxPoint() - centerPoint.getxPoint(), secondPoint.getyPoint() - centerPoint.getyPoint());
        double angleThird = Math.atan2(thirdPoint.getxPoint() - centerPoint.getxPoint(), thirdPoint.getyPoint() - centerPoint.getyPoint());
        double angleFourth = Math.atan2(fourthPoint.getxPoint() - centerPoint.getxPoint(), fourthPoint.getyPoint() - centerPoint.getyPoint());

        List<Double> tempListAngle = new ArrayList<>();
        tempListAngle.add(angleFirst);
        tempListAngle.add(angleSecond);
        tempListAngle.add(angleThird);
        tempListAngle.add(angleFourth);

        Collections.sort(tempListAngle);

        List<TwoDPoint> tempList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (tempListAngle.get(i) == angleFirst) {
                tempList.add(firstPoint);
            } else if (tempListAngle.get(i) == angleSecond) {
                tempList.add(secondPoint);
            } else if (tempListAngle.get(i) == angleThird) {
                tempList.add(thirdPoint);
            } else if (tempListAngle.get(i) == angleFourth) {
                tempList.add(fourthPoint);
            }
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
        //should be sorted upon construction, simply return the coordinates
        return this.vertices;
    }

    /**
     * @return the number of sides of this quadrilateral, which is always set to four
     */
    @Override
    public int numSides() {
        return 4;
    }

    /**
     * Checks whether or not a list of vertices forms a valid quadrilateral. The <i>trivial</i> quadrilateral, where all
     * four corner vertices are the same point, is considered to be an invalid quadrilateral.
     *
     * @param vertices the list of vertices to check against, where each vertex is a <code>Point</code> type.
     * @return <code>true</code> if <code>vertices</code> is a valid collection of points for a quadrilateral, and
     * <code>false</code> otherwise. For example, if three of the four vertices are in a straight line is invalid.
     */
    @Override
    public boolean isMember(List<? extends Point> vertices) {
        // TODO
        if (vertices.size() < 4) {
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
        if (!(vertices.get(3) instanceof TwoDPoint)) {
            return false;
        }

        TwoDPoint firstPoint = (TwoDPoint) vertices.get(0);
        TwoDPoint secondPoint = (TwoDPoint) vertices.get(1);
        TwoDPoint thirdPoint = (TwoDPoint) vertices.get(2);
        TwoDPoint fourthPoint = (TwoDPoint) vertices.get(3);

        TwoDPoint centerPoint = new TwoDPoint(((firstPoint.getxPoint()+secondPoint.getxPoint()+thirdPoint.getxPoint()+fourthPoint.getxPoint())/4),
                ((firstPoint.getyPoint()+secondPoint.getyPoint()+thirdPoint.getyPoint()+fourthPoint.getyPoint())/4)
        );

        double angleFirst = Math.atan2(firstPoint.getxPoint() - centerPoint.getxPoint(), firstPoint.getyPoint() - centerPoint.getyPoint());
        double angleSecond = Math.atan2(secondPoint.getxPoint() - centerPoint.getxPoint(), secondPoint.getyPoint() - centerPoint.getyPoint());
        double angleThird = Math.atan2(thirdPoint.getxPoint() - centerPoint.getxPoint(), thirdPoint.getyPoint() - centerPoint.getyPoint());
        double angleFourth = Math.atan2(fourthPoint.getxPoint() - centerPoint.getxPoint(), fourthPoint.getyPoint() - centerPoint.getyPoint());


        List<Double> tempListAngle = new ArrayList<>();
        tempListAngle.add(angleFirst);
        tempListAngle.add(angleSecond);
        tempListAngle.add(angleThird);
        tempListAngle.add(angleFourth);

        Map<Double, TwoDPoint> dictionary = new HashMap<>();
        dictionary.put(angleFirst, firstPoint);
        dictionary.put(angleSecond, secondPoint);
        dictionary.put(angleThird, thirdPoint);
        dictionary.put(angleFourth, fourthPoint);

        Collections.sort(tempListAngle);

        List<TwoDPoint> tempList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (tempListAngle.get(i) == angleFirst) {
                tempList.add(dictionary.get(angleFirst));
            } else if (tempListAngle.get(i) == angleSecond) {
                tempList.add(dictionary.get(angleSecond));
            } else if (tempListAngle.get(i) == angleThird) {
                tempList.add(dictionary.get(angleThird));
            } else if (tempListAngle.get(i) == angleFourth) {
                tempList.add(dictionary.get(angleFourth));
            }
        }

        double distanceFirstToSecond = Math.sqrt(
                Math.pow(tempList.get(1).getxPoint() - tempList.get(0).getxPoint(), 2) +
                        Math.pow(tempList.get(1).getyPoint() - tempList.get(0).getyPoint(), 2));

        double distanceSecondToThird = Math.sqrt(
                Math.pow(tempList.get(2).getxPoint() - tempList.get(1).getxPoint(), 2) +
                        Math.pow(tempList.get(2).getyPoint() - tempList.get(1).getyPoint(), 2));

        double distanceThirdToFourth = Math.sqrt(
                Math.pow(tempList.get(3).getxPoint() - tempList.get(2).getxPoint(), 2) +
                        Math.pow(tempList.get(3).getyPoint() - tempList.get(2).getyPoint(), 2));
        double distanceFourthToFirst = Math.sqrt(
                Math.pow(tempList.get(0).getxPoint() - tempList.get(3).getxPoint(), 2) +
                        Math.pow(tempList.get(0).getyPoint() - tempList.get(3).getyPoint(), 2));
        double sValue = distanceFirstToSecond+distanceSecondToThird+distanceThirdToFourth+distanceFourthToFirst;
        sValue *= 0.5;
        double area = Math.sqrt(sValue*(sValue-distanceFirstToSecond)*(sValue-distanceSecondToThird)*(sValue-distanceThirdToFourth)*(sValue-distanceFourthToFirst));
        if (area == 0.0) {
            return false;
        }
        //test different combinations of three points to test if three points form a line
        if (!(checkThreePoints(tempList.get(0), tempList.get(1), tempList.get(2)))) {
            return false;
        } else if ((!(checkThreePoints(tempList.get(1), tempList.get(2), tempList.get(3))))) {
            return false;
        } else if ((!(checkThreePoints(tempList.get(2), tempList.get(3), tempList.get(0))))) {
            return false;
        } else if ((!(checkThreePoints(tempList.get(3), tempList.get(0), tempList.get(1))))) {
            return false;
        }
        return true;
    }

    public boolean checkThreePoints(TwoDPoint first, TwoDPoint second, TwoDPoint third) {
        double distanceFirstToSecond = Math.sqrt(
                Math.pow(second.getxPoint() - first.getxPoint(), 2) +
                        Math.pow(second.getyPoint() - first.getyPoint(), 2));

        double distanceSecondToThird = Math.sqrt(
                Math.pow(third.getxPoint() - second.getxPoint(), 2) +
                        Math.pow(third.getyPoint() - second.getyPoint(), 2));

        double distanceThirdToFirst = Math.sqrt(
                Math.pow(first.getxPoint() - third.getxPoint(), 2) +
                        Math.pow(first.getyPoint() - third.getyPoint(), 2));
        double sValue = distanceFirstToSecond+distanceSecondToThird+distanceThirdToFirst;
        sValue *= 0.5;
        double area = Math.sqrt(sValue*(sValue-distanceFirstToSecond)*(sValue-distanceSecondToThird)*(sValue-distanceThirdToFirst));
        if (area == 0.0) {
            return false;
        }
        return true;
    }

    /**
     * This method snaps each vertex of this quadrilateral to its nearest integer-valued x-y coordinate. For example, if
     * a corner is at (0.8, -0.1), it will be snapped to (1,0). The resultant quadrilateral will thus have all four
     * vertices in positions with integer x and y values. If the snapping procedure described above results in this
     * quadrilateral becoming invalid (e.g., all four corners collapse to a single point), then it is left unchanged.
     * Snapping is an in-place procedure, and the current instance is modified.
     */
    public void snap() {
        // TODO
        TwoDPoint tempFirst = new TwoDPoint(0,0);
        TwoDPoint tempSecond = new TwoDPoint(0,0);
        TwoDPoint tempThird = new TwoDPoint(0,0);
        TwoDPoint tempFourth = new TwoDPoint(0,0);

        tempFirst.setxPoint(Math.round(this.vertices.get(0).getxPoint()));
        tempFirst.setyPoint(Math.round(this.vertices.get(0).getyPoint()));

        tempSecond.setxPoint(Math.round(this.vertices.get(1).getxPoint()));
        tempSecond.setyPoint(Math.round(this.vertices.get(1).getyPoint()));

        tempThird.setxPoint(Math.round(this.vertices.get(2).getxPoint()));
        tempThird.setyPoint(Math.round(this.vertices.get(2).getyPoint()));

        tempFourth.setxPoint(Math.round(this.vertices.get(3).getxPoint()));
        tempFourth.setyPoint(Math.round(this.vertices.get(3).getyPoint()));

        List<TwoDPoint> tempList = new ArrayList<>();
        tempList.add(tempFirst);
        tempList.add(tempSecond);
        tempList.add(tempThird);
        tempList.add(tempFourth);

        if ((isMember(tempList))) {
            this.vertices = tempList;
        }
    }

    /**
     * @return the area of this quadrilateral
     */
    @Override
    public double area() {
        // TODO
        double distanceFirstToSecond = Math.sqrt(
                Math.pow(this.vertices.get(1).getxPoint() - this.vertices.get(0).getxPoint(), 2) +
                        Math.pow(this.vertices.get(1).getyPoint() - this.vertices.get(0).getyPoint(), 2)
        );
        double distanceSecondToThird = Math.sqrt(
                Math.pow(this.vertices.get(2).getxPoint() - this.vertices.get(1).getxPoint(), 2) +
                        Math.pow(this.vertices.get(2).getyPoint() - this.vertices.get(1).getyPoint(), 2)
        );
        double distanceThirdToFourth = Math.sqrt(
                Math.pow(this.vertices.get(3).getxPoint() - this.vertices.get(2).getxPoint(), 2) +
                        Math.pow(this.vertices.get(3).getyPoint() - this.vertices.get(2).getyPoint(), 2)
        );
        double distanceFourthToFirst = Math.sqrt(
                Math.pow(this.vertices.get(0).getxPoint() - this.vertices.get(3).getxPoint(), 2) +
                        Math.pow(this.vertices.get(0).getyPoint() - this.vertices.get(3).getyPoint(), 2)
        );
        double sValue = distanceFirstToSecond+distanceSecondToThird+distanceThirdToFourth+distanceFourthToFirst;
        sValue *= 0.5;
        double area = Math.sqrt((sValue-distanceFirstToSecond)*(sValue-distanceSecondToThird)*(sValue-distanceThirdToFourth)*(sValue-distanceFourthToFirst));
        return Math.round(area*100.0) / 100.0;
    }

    /**
     * @return the perimeter (i.e., the total length of the boundary) of this quadrilateral
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
        double distanceThirdToFourth = Math.sqrt(
                Math.pow(this.vertices.get(3).getxPoint() - this.vertices.get(2).getxPoint(), 2) +
                        Math.pow(this.vertices.get(3).getyPoint() - this.vertices.get(2).getyPoint(), 2)
        );
        double distanceFourthToFirst = Math.sqrt(
                Math.pow(this.vertices.get(0).getxPoint() - this.vertices.get(3).getxPoint(), 2) +
                        Math.pow(this.vertices.get(0).getyPoint() - this.vertices.get(3).getyPoint(), 2)
        );
        double perimeter = distanceFirstToSecond+distanceSecondToThird+distanceThirdToFourth+distanceFourthToFirst;
        return Math.round(perimeter*100.0) / 100.0;
    }

    @Override
    public int compareTo(TwoDShape o) {
        return Double.compare(this.area(), o.area());
    }

    @Override
    public String toString() {
        return "Quadrilateral["+this.vertices.get(0).toString()+", "+this.vertices.get(1).toString()+", "+this.vertices.get(2)+", "+this.vertices.get(3)+"]";
    }
}
