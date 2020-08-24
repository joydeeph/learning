import java.util.Arrays;
import java.util.Iterator;

import edu.princeton.cs.algs4.ResizingArrayBag;

public class FastCollinearPoints {

  private ResizingArrayBag<LineSegment> lineSegments = new ResizingArrayBag<>();

  // finds all line segments containing 4 points
  public FastCollinearPoints(Point[] points) {
    Point origin = null;
    Point p = null;
    int count = 0;
    Point[] tempArrary = null;
    for (int i = 0; i < points.length; i++) {
      origin = points[i];
      Double slope = Double.NEGATIVE_INFINITY;
      tempArrary = Arrays.copyOf(points, points.length);
      Arrays.sort(tempArrary, origin.slopeOrder());
      for (int j = 0; j < tempArrary.length; j++) {
        p = tempArrary[j];
        if (slope != p.slopeTo(origin)) {
          // Change Over : print it
          if (count >= 3) {
            lineSegments.add(new LineSegment(tempArrary[j - count],origin));
          }
          // Restart scanning
          count = 1;
          slope = p.slopeTo(origin);
        } else {
          count++;
        }
        // }
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
