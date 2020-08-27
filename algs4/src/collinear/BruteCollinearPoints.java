import java.util.Arrays;
import java.util.Iterator;

import edu.princeton.cs.algs4.ResizingArrayBag;

public class BruteCollinearPoints {

  private ResizingArrayBag<LineSegment> lineSegments = new ResizingArrayBag<>();

  // finds all line segments containing 4 points
  public BruteCollinearPoints(Point[] points) {
    Point[] sortedPoints = validate(points);
    
    Point min = null;
    for (int i = 0; i < sortedPoints.length; i++) {
      for (int j = i + 1; j < sortedPoints.length; j++) {
        for (int k = j + 1; k < sortedPoints.length; k++) {
          for (int m = k + 1; m < sortedPoints.length; m++) {
            Point p = sortedPoints[i];
            Point q = sortedPoints[j];
            Point r = sortedPoints[k];
            Point s = sortedPoints[m];
            double pSlopeToQ = p.slopeTo(q);
            if (pSlopeToQ == p.slopeTo(r) && pSlopeToQ == p.slopeTo(s)) {
                min = p;
              if (min.compareTo(q) > 0)
                min = q;
              if (min.compareTo(r) > 0)
                min = r;
              if (min.compareTo(s) > 0)
                min = s;
              Point max = p;
              if (max.compareTo(q) <= 0)
                max = q;
              if (max.compareTo(r) <= 0)
                max = r;
              if (max.compareTo(s) <= 0)
                max = s;
              lineSegments.add(new LineSegment(min, max));
            }
          }
        }
      }
    }
  }

  // the number of line segments
  public int numberOfSegments() {
    return lineSegments.size();
  }

  // the line segments
  public LineSegment[] segments() {
    LineSegment[] segmentArray = new LineSegment[lineSegments.size()];
    Iterator<LineSegment> itr = lineSegments.iterator();
    for (int i = 0; i < segmentArray.length && itr.hasNext(); i++) {
      segmentArray[i] = itr.next();
    }
    return segmentArray;
  }
  private Point[] validate(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException();
    }
    for (int k = 0; k < points.length; k++) {
      if (points[k] == null)
        throw new IllegalArgumentException();
    }
    Point[] sortedPoints = Arrays.copyOf(points, points.length);
    Arrays.sort(sortedPoints);
    Point min = sortedPoints[0];
    for (int k = 1; k < sortedPoints.length; k++) {
      if (sortedPoints[k].compareTo(min) == 0)
        throw new IllegalArgumentException();
      else
        min = sortedPoints[k];
    }
    return sortedPoints;
  }
}
