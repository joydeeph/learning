import java.util.Arrays;
import java.util.Iterator;

import edu.princeton.cs.algs4.ResizingArrayBag;

public class FastCollinearPoints {

  private final ResizingArrayBag<LineSegment> lineSegments = new ResizingArrayBag<>();

  // finds all line segments containing 4 points
  public FastCollinearPoints(Point[] points) {
    Point[] sortedPoints = validate(points);
    Point origin = null;
    Point p = null;
    int count = 0;
    for (int i = 0; i < sortedPoints.length; i++) {
      count = 0;
      double slope = Double.NEGATIVE_INFINITY;
      Arrays.sort(sortedPoints, i, sortedPoints.length);
      origin = sortedPoints[i];
      Arrays.sort(sortedPoints, i, sortedPoints.length, origin.slopeOrder());
      int j = i + 1;
      for (; j < sortedPoints.length; j++) {
        p = sortedPoints[j];
        if (slope != p.slopeTo(origin)) {
          // Change Over
          if (count >= 3) {
            // If the 1st element is origin; note origin will always be at the
            // beginning of the array
            LineSegment segment = null;
            if (origin.compareTo(sortedPoints[j - count]) <= 0) {
              segment = new LineSegment(origin, sortedPoints[j - 1]);
            } else if (origin.compareTo(sortedPoints[j - 1]) > 0) {
              segment = new LineSegment(sortedPoints[j - count], origin);
            } else {
              segment = new LineSegment(sortedPoints[j - count],
                  sortedPoints[j - 1]);
            }
            if (!segmentVisited(sortedPoints, sortedPoints[j - count], i)) {
              lineSegments.add(segment);
            }
          }
          // Restart scanning
          count = 1;
          slope = p.slopeTo(origin);
        } else {
          count++;
        }
        // }
      }
      // corner case:collinear points are at the end of the array
      if (count >= 3) {
        // If the 1st element is origin; note origin will always be at the
        // beginning of the array
        LineSegment segment = null;
        if (origin.compareTo(sortedPoints[j - count]) <= 0) {
          segment = new LineSegment(origin, sortedPoints[j - 1]);
        } else if (origin.compareTo(sortedPoints[j - 1]) > 0) {
          segment = new LineSegment(sortedPoints[j - count], origin);
        } else {
          segment = new LineSegment(sortedPoints[j - count], sortedPoints[j - 1]);
        }
        if (!segmentVisited(sortedPoints, sortedPoints[j - count], i)) {
          lineSegments.add(segment);
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

  private boolean segmentVisited(Point[] points, Point p, int originIndex) {
    for (int i = 0; i < originIndex; i++) {
      if (points[i].slopeTo(points[originIndex]) == points[i].slopeTo(p)) {
        return true;
      }
    }
    return false;
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
