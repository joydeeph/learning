import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Fast {

  public static void main(String[] args) {

    // rescale coordinates and turn on animation mode
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    StdDraw.show(0);
    StdDraw.setPenRadius(0.01); // make the points a bit larger

    Point[] points = null;
    In in = new In(args[0]); // input file
    int no = in.readInt();
    points = new Point[no];
    int count = 0;
    while (!in.isEmpty()) {
      int i = in.readInt();
      int j = in.readInt();
      points[count] = new Point(i, j);
      points[count].draw();
      count++;
    }
    // display to screen all at once
    StdDraw.show(0);

    // reset the pen radius
    StdDraw.setPenRadius();

    Point origin = null;
    Point p = null;
    count = 0;
    Point[] tempArrary = null;
    //Map<Double, Double> processedSlope = new HashMap<Double, Double>();
    for (int i = 0; i < points.length; i++) {
      origin = points[i];
      Double slope = Double.NEGATIVE_INFINITY;
      tempArrary = Arrays.copyOf(points, points.length);
      Arrays.sort(tempArrary, origin.SLOPE_ORDER);
      for (int j = 0, k = 0; j < tempArrary.length; j++) {
        p = tempArrary[j];
        //if (!processedSlope.containsKey(slope)) {
          if (slope != p.slopeTo(origin)) {
            // Change Over : print it
            if (count >= 3) {
              //processedSlope.put(slope, slope);
              print(origin, tempArrary, k, count);
            }
            // Restart scanning
            count = 1;
            k = j;
            slope = p.slopeTo(origin);
          } else {
            count++;
          }
        //}
      }
    }
    // display to screen all at once
    StdDraw.show(0);

    // reset the pen radius
    StdDraw.setPenRadius();
  }

  private static void print(Point origin, Point[] points, int k, int count) {
    StdOut.print(origin + "->");
    for (int i = k; i < k + count; i++) {
      origin.drawTo(points[i]);
      if (i == (k + count - 1)) {
        StdOut.print(points[i]);
        StdOut.println();
      } else {
        StdOut.print(points[i] + "->");
      }
    }
  }

}
