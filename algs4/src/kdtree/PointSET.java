import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PointSET {
  private final SET<Point2D> pointSet = new SET<>();

  // construct an empty set of points
  public PointSET() {

  }

  // is the set empty?
  public boolean isEmpty() {
    return pointSet.isEmpty();
  }

  // number of points in the set
  public int size() {
    return pointSet.size();
  }

  // add the point to the set (if it is not already in
  // the set)
  public void insert(Point2D p) {
    if (p == null)
      throw new IllegalArgumentException();

    pointSet.add(p);

  }

  // does the set contain point p?
  public boolean contains(Point2D p) {
    if (p == null)
      throw new IllegalArgumentException();

    return pointSet.contains(p);
  }

  // draw all points to standard draw
  public void draw() {
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(0.01);
    for (Point2D p : pointSet) {
      p.draw();
    }
  }

  // all points that are inside the
  // rectangle (or on the boundary)
  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null)
      throw new IllegalArgumentException();
    SET<Point2D> points = new SET<>();
    for (Point2D p : pointSet) {
      if (rect.contains(p)) {
        points.add(p);
      }
    }
    return points;
  }

  // a nearest neighbor in the set to point p; null
  // if the set is empty
  public Point2D nearest(Point2D p) {
    Point2D nearest = null;
    double distanceSquare = Double.POSITIVE_INFINITY;
    if (p == null)
      throw new IllegalArgumentException();
    for (Point2D other : pointSet) {
      if (other != p && p.distanceSquaredTo(other) < distanceSquare) {
        nearest = other;
        distanceSquare = p.distanceSquaredTo(other);
      }
    }
    return nearest;
  }

  // unit testing of the methods (optional)
  public static void main(String[] args) {
    StdOut.printf("%s %d\n", "Starting time : ", System.currentTimeMillis());
    String filename = args[0];
    In in = new In(filename);
    PointSET pointset = new PointSET();
    while (!in.isEmpty()) {
      double x = in.readDouble();
      double y = in.readDouble();
      Point2D p = new Point2D(x, y);
      pointset.insert(p);
    }
    StdOut.printf("%s %d\n", "Insert time   : ", System.currentTimeMillis());
    double x = 0;
    double y = 0;
    long counter = 0;
    long endTimeMillis = System.currentTimeMillis() + 1000;
    while (System.currentTimeMillis() < endTimeMillis) {
      x = StdRandom.uniform(0.0, 1.0);
      y = StdRandom.uniform(0.0, 1.0);
      pointset.nearest(new Point2D(x, y));
      counter++;
    }
    StdOut.printf("%s %d\n", "End time      : ", System.currentTimeMillis());
    StdOut.printf("%s %d\n", "Total count   : ", counter);

  }
}