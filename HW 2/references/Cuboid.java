import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.Math;

// TODO : a missing interface method must be implemented in this class to make it compile. This must be in terms of volume().
public class Cuboid extends Ordering.SurfaceAreaComparator implements ThreeDShape, SurfaceArea  {

    private final ThreeDPoint[] vertices = new ThreeDPoint[8];


    /**
     * Creates a cuboid out of the list of vertices. It is expected that the vertices are provided in
     * the order as shown in the figure given in the homework document (from v0 to v7).
     * 
     * @param vertices the specified list of vertices in three-dimensional space.
     */
    public Cuboid(List<ThreeDPoint> vertices) {
        if (vertices.size() != 8)
            throw new IllegalArgumentException(String.format("Invalid set of vertices specified for %s",
                                                             this.getClass().getName()));
        int n = 0;
        for (ThreeDPoint p : vertices) this.vertices[n++] = p;
    }

    @Override
    public double volume() {
        double[] p0 = this.vertices[0].coordinates();
        double[] p1 = this.vertices[1].coordinates();
        double[] p3 = this.vertices[3].coordinates();
        double[] p5 = this.vertices[5].coordinates();
        // 4 points to find volume
        double height;
        double length;
        double width;
        double xdiff;
        double ydiff;
        double zdiff;

        xdiff = p0[0] - p1[0];
        ydiff = p0[1] - p1[1];
        zdiff = p0[2] - p1[2];
        length = Math.sqrt(xdiff*xdiff + ydiff*ydiff + zdiff*zdiff);

        xdiff = p0[0] - p3[0];
        ydiff = p0[1] - p3[1];
        zdiff = p0[2] - p3[2];
        height = Math.sqrt(xdiff*xdiff + ydiff*ydiff + zdiff*zdiff);

        xdiff = p0[0] - p5[0];
        ydiff = p0[1] - p5[1];
        zdiff = p0[2] - p5[2];

        width = Math.sqrt(xdiff*xdiff + ydiff*ydiff + zdiff*zdiff);


        return (Math.round(height*width*length*1000))/1000.; // TODO


    }

    @Override
    public ThreeDPoint center() {
        double[] p0 = this.vertices[0].coordinates();
        double[] p7 = this.vertices[7].coordinates();

        double xsum;
        double ysum;
        double zsum;


        xsum = Math.round ( (p0[0] + p7[0])*1000.)/2000.;
        ysum = Math.round ( (p0[1] + p7[1])*1000.)/2000.;
        zsum = Math.round ( (p0[2] + p7[2])*1000.)/2000.;

        /*
        System.out.println("xsum: " + xsum);
        System.out.println("ysum: " + ysum);
        System.out.println("zsum: " + zsum);


         */



        return new ThreeDPoint(xsum, ysum, zsum); // TODO
    }
    @Override
    public int compareTo(ThreeDShape shape){
        if (volume() > shape.volume())
            return 1;
        else if (volume() <shape.volume())
            return -1;
        else
            return 0;
    }

    @Override
    public double surfaceArea() {
        double[] p0 = this.vertices[0].coordinates();
        double[] p1 = this.vertices[1].coordinates();
        double[] p3 = this.vertices[3].coordinates();
        double[] p5 = this.vertices[5].coordinates();
        // 4 points to find volume

        double height;
        double length;
        double width;
        double xdiff;
        double ydiff;
        double zdiff;

        xdiff = p0[0] - p1[0];
        ydiff = p0[1] - p1[1];
        zdiff = p0[2] - p1[2];
        length = Math.sqrt(xdiff*xdiff + ydiff*ydiff + zdiff*zdiff);

        xdiff = p0[0] - p3[0];
        ydiff = p0[1] - p3[1];
        zdiff = p0[2] - p3[2];
        height = Math.sqrt(xdiff*xdiff + ydiff*ydiff + zdiff*zdiff);

        xdiff = p0[0] - p5[0];
        ydiff = p0[1] - p5[1];
        zdiff = p0[2] - p5[2];

        width = Math.sqrt(xdiff*xdiff + ydiff*ydiff + zdiff*zdiff);

        return Math.round((2*(width*length)+2*(width*height)+2*(height*length))  * 1000.) /1000.;
    }
    public static Cuboid random(){
        double length = Math.round((Math.random() * 201 ) * 1000.)/1000.;
        double width = Math.round((Math.random() * 201 ) * 1000.)/1000.;
        double height = Math.round((Math.random()* 201 ) * 1000.)/1000.;
        double xcoord = 0;
        double ycoord = 0;
        double zcoord = 0;
        ArrayList<ThreeDPoint> vertexList = new ArrayList(8);
        vertexList.add(new ThreeDPoint(xcoord,ycoord,zcoord));
        ycoord = ycoord - length;
        vertexList.add(new ThreeDPoint(xcoord,ycoord,zcoord));
        zcoord = zcoord - height;
        vertexList.add(new ThreeDPoint(xcoord,ycoord,zcoord));
        ycoord = 0;
        vertexList.add(new ThreeDPoint(xcoord,ycoord,zcoord));
        //first 4 ponts
        xcoord= xcoord - width;
        vertexList.add(new ThreeDPoint(xcoord,ycoord,zcoord));
        zcoord = 0;
        vertexList.add(new ThreeDPoint(xcoord,ycoord,zcoord));
        ycoord = ycoord - length;
        vertexList.add(new ThreeDPoint(xcoord,ycoord,zcoord));
        zcoord = zcoord - height;
        vertexList.add(new ThreeDPoint(xcoord,ycoord,zcoord));






        return new Cuboid(vertexList);

    }

    @Override
    public String toString() {
        return "Cuboid{" +
                "vertices=" + Arrays.toString(vertices) +
                '}';
    }
}
