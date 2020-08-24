import java.util.Iterator;

import edu.princeton.cs.algs4.ResizingArrayBag;

public class BruteCollinearPoints {

  private ResizingArrayBag<LineSegment> lineSegments = new ResizingArrayBag<>();

  // finds all line segments containing 4 points
  public BruteCollinearPoints(Point[] points) {
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
              Point min = p;
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
}
