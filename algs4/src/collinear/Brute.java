import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Brute {

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
    for (int i = 0; i < points.length; i++) {
      for (int j = i + 1; j < points.length; j++) {
        for (int k = j + 1; k < points.length; k++) {
          for (int l = k + 1; l < points.length; l++) {
            Point p = points[i];
            Point q = points[j];
            Point r = points[k];
            Point s = points[l];
            double pSlopeToQ = p.slopeTo(q);
            if (pSlopeToQ == p.slopeTo(r) && pSlopeToQ == p.slopeTo(s)) {
              p.drawTo(q);
              p.drawTo(r);
              p.drawTo(s);
              StdOut.println(p + " -> " + q + " -> " + r + " -> " + s);
            }
          }
        }
      }
    }
    // display to screen all at once
    StdDraw.show(0);

    // reset the pen radius
    StdDraw.setPenRadius();

  }
}
