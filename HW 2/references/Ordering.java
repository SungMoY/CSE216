

import java.util.*;
import java.util.function.Predicate;

public class Ordering {

    static class XLocationComparator implements Comparator<TwoDShape> {
        @Override public int compare(TwoDShape o1, TwoDShape o2) {
            Double xLocation1 = 0d;
            Double xLocation2 = 0d;

        if (o1 instanceof Quadrilateral){
            Quadrilateral obj1 = (Quadrilateral) o1;
            TwoDPoint[] vertexes = obj1.getVertices();
            xLocation1 = vertexes[0].coordinates()[0];


            for (TwoDPoint vertex:vertexes) {
                //System.out.println("vertex xcoord: " + vertex.coordinates()[0]);
                if (xLocation1> vertex.coordinates()[0]){
                    xLocation1 = vertex.coordinates()[0];


                }
            }


        }
        else {
            Circle obj1 = (Circle) o1;
            xLocation1= Math.round((obj1.center().coordinates()[0] - obj1.getRadius() )*10000.) /10000.;//Math.round((obj1.center().coordinates()[0] - obj1.getRadius() )*1000)/1000;
        }


        if (o2 instanceof Quadrilateral){
                Quadrilateral obj2 = (Quadrilateral) o2;
                TwoDPoint[] vertexes = obj2.getVertices();
                xLocation2 = vertexes[0].coordinates()[0];


                for (TwoDPoint vertex:vertexes) {

                    if (xLocation2> vertex.coordinates()[0]){
                        xLocation2 = vertex.coordinates()[0];

                }

            }


            }
        else {
                Circle obj2 = (Circle) o2;
                xLocation2= Math.round((obj2.center().coordinates()[0] - obj2.getRadius() )*10000.) /10000.;

        }

/*
            System.out.println("xLocation1: " + xLocation1);
            System.out.println("xLocation2: " + xLocation2);
            System.out.println(xLocation1>xLocation2);

 */





        if (xLocation1>xLocation2){
            return 1;
        } else if(xLocation1<xLocation2){
            return -1;
        }
            return 0; // TODO
        }
    }

    static class AreaComparator implements Comparator<SymmetricTwoDShape> {
        @Override public int compare(SymmetricTwoDShape o1, SymmetricTwoDShape o2) {
            if (o1.area() >o2.area()){
                return 1;
            } else if (o1.area() < o2.area()){
                return -1;
            }
            return 0; // TODO
        }
    }

    static class SurfaceAreaComparator implements Comparator<ThreeDShape> {
        @Override public int compare(ThreeDShape o1, ThreeDShape o2) {
            if (!(o1 instanceof SurfaceArea) || !(o2 instanceof  SurfaceArea))
                throw new UnsupportedOperationException("Implement this yourself foo");
            SurfaceArea obj1 = (SurfaceArea) o1;
            SurfaceArea obj2 = (SurfaceArea) o2;

            double sa1 = obj1.surfaceArea();
            double sa2 = obj2.surfaceArea();
            if (sa1 > sa2) {
                return 1;
            } else if (sa1 < sa2){
                return -1;
            }
            return 0; // TODO


        }
    }

    // TODO: there's a lot wrong with this method. correct it so that it can work properly with generics.
    static <T> void copy(Collection<? extends T> source, Collection<T> destination) {

        destination.addAll(source);
    }






    public static void main(String[] args) {
        Predicate<Integer> compute = p -> p > 5;
        System.out.println(compute.test(5));

        List<TwoDShape>          shapes          = new ArrayList<>();
        List<SymmetricTwoDShape> symmetricshapes = new ArrayList<>();
        List<ThreeDShape>        threedshapes    = new ArrayList<>();

        ArrayList<TwoDPoint> vertices = new ArrayList<TwoDPoint>();
        vertices.add(new TwoDPoint(-2, -2));
        vertices.add(new TwoDPoint(2, -2));
        vertices.add(new TwoDPoint(2, 2));
        vertices.add(new TwoDPoint(-2, 2));


        ArrayList<TwoDPoint> vertices1 = new ArrayList<TwoDPoint>();
        vertices1.add(new TwoDPoint(-150, 0));
        vertices1.add(new TwoDPoint(-150, 1));
        vertices1.add(new TwoDPoint(1, 1));
        vertices1.add(new TwoDPoint(1, 0));









        /*
         * uncomment the following block and fill in the "..." constructors to create actual instances. If your
         * implementations are correct, then the code should compile and yield the expected results of the various
         * shapes being ordered by their smallest x-coordinate, area, volume, surface area, etc. */


        symmetricshapes.add(new Rectangle(vertices1));
        symmetricshapes.add(new Square(vertices));
        symmetricshapes.add(new Circle(0, 0, 100));

        copy(symmetricshapes, shapes); // note-1 //
        //shapes.add(new Quadrilateral(new ArrayList<>()));


        // sorting 2d shapes according to various criteria
        shapes.sort(new XLocationComparator());
        symmetricshapes.sort(new XLocationComparator());
        symmetricshapes.sort(new AreaComparator());

        // sorting 3d shapes according to various criteria
        Collections.sort(threedshapes);
        threedshapes.sort(new SurfaceAreaComparator());

        /*
         * if your changes to copy() are correct, uncommenting the following block will also work as expected note that
         * copy() should work for the line commented with 'note-1' while at the same time also working with the lines
         * commented with 'note-2' and 'note-3'. */


        List<Number> numbers = new ArrayList<>();
        List<Double> doubles = new ArrayList<>();
        Set<Square>        squares = new HashSet<>();
        Set<Quadrilateral> quads   = new LinkedHashSet<>();

        copy(doubles, numbers); // note-2 //
        copy(squares, quads);   // note-3 //







    }
    public static <T> void printList(List<T> shapes)
    {
        for(T a:shapes)
        {
            System.out.println(a);
        }
    }
}
