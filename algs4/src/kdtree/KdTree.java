import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class KdTree {
  private Node root; // root of KdTree

  private static class Node {
    private final Point2D p; // the point
    private RectHV rect; // the axis-aligned rectangle corresponding to this node
    private Node lb; // the left/bottom subtree
    private Node rt; // the right/top subtree
    private int size = 0;

    public Node(Point2D p, int size) {
      this.p = p;
      this.size = size;
    }
  }

  // construct an empty set of points
  public KdTree() {

  }

  // is the set empty?
  public boolean isEmpty() {
    return size() == 0;
  }

  // number of points in the set
  public int size() {
    return size(root);
  }

  // return number of key-value pairs in BST rooted at x
  private int size(Node x) {
    if (x == null)
      return 0;
    else
      return x.size;
    // return size(x.lb) + size(x.rt) + 1;
  }

  public void insert(Point2D p) {
    if (p == null)
      throw new IllegalArgumentException();
    if (!contains(p)) {
      root = insert(root, p, true);
      root.rect = new RectHV(0, 0, 1, 1);
    }
  }

  private Node insert(Node node, Point2D p, boolean isX) {
    if (node == null)
      return new Node(p, 1);
    double cmp;
    if (isX) {
      cmp = p.x() - node.p.x();
    } else {
      cmp = p.y() - node.p.y();
    }
    if (cmp < 0.0) {
      node.lb = insert(node.lb, p, !isX);
      if (node.lb.rect == null) {
        if (isX)
          node.lb.rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.p.x(),
              node.rect.ymax());
        else
          node.lb.rect = new RectHV(node.rect.xmin(), node.rect.ymin(),
              node.rect.xmax(), node.p.y());
      }
    } else {
      node.rt = insert(node.rt, p, !isX);
      if (node.rt.rect == null) {
        if (isX)
          node.rt.rect = new RectHV(node.p.x(), node.rect.ymin(), node.rect.xmax(),
              node.rect.ymax());
        else
          node.rt.rect = new RectHV(node.rect.xmin(), node.p.y(), node.rect.xmax(),
              node.rect.ymax());
      }
    }
    node.size = size(node.lb) + size(node.rt) + 1;
    return node;
  }

  // does the tree contain point p?
  public boolean contains(Point2D p) {
    if (p == null)
      throw new IllegalArgumentException();

    return contains(root, p, true);
  }

  private boolean contains(Node x, Point2D p, boolean isX) {
    if (x == null)
      return false;
    if(x.p.x() == p.x() && x.p.y() == p.y())
      return true;
    double cmp;
    if (isX)
      cmp = p.x() - x.p.x();
    else
      cmp = p.y() - x.p.y();
    if (cmp < 0.0) {
      return contains(x.lb, p, !isX);
    } else {
      return contains(x.rt, p, !isX);
    } 
  }

  public void draw() {
    draw(root, true);
  }

  private void draw(Node x, boolean isX) {
    if (x != null) {
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.setPenRadius(0.01);
      x.p.draw();
      StdDraw.setPenRadius();
      if (isX) {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
      } else {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
      }
      draw(x.lb, !isX);
      draw(x.rt, !isX);
    }
  }

  public Point2D nearest(Point2D query) {
    if (query == null)
      throw new IllegalArgumentException();
    if(root == null)
        return null;
    return nearest(root, query, root.p);
  }

  private Point2D nearest(Node node, Point2D query, Point2D nearest) {
    if (node == null)
      return nearest;
    double distance = nearest.distanceSquaredTo(query);
    double nodeDistance = node.p.distanceSquaredTo(query);
    if (nodeDistance < distance) {
      distance = nodeDistance;
      nearest = node.p;
    }
    Node containingNode = null;
    Node otherNode = null;

    if (node.lb != null && node.rt != null) {
      if (node.lb.rect.contains(query)) {
        containingNode = node.lb;
        otherNode = node.rt;
      } else {
        containingNode = node.rt;
        otherNode = node.lb;
      }

    } else if (node.lb == null && node.rt != null) {
      if (node.rt.rect.contains(query)) {
        containingNode = node.rt;
      } else {
        otherNode = node.rt;
      }
    } else if (node.lb != null && node.rt == null) {
      if (node.lb.rect.contains(query)) {
        containingNode = node.lb;
      } else {
        otherNode = node.lb;
      }
    } else {
      return nearest;
    }
    if (containingNode != null) {
      nearest = nearest(containingNode, query, nearest);
      distance = nearest.distanceSquaredTo(query);
    }
    if (otherNode != null && otherNode.rect.distanceSquaredTo(query) < distance) {
      nearest = nearest(otherNode, query, nearest);
    }
    return nearest;
  }

  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null)
      throw new IllegalArgumentException();
    SET<Point2D> points = new SET<>();
    range(rect, root, points);
    return points;
  }

  private void range(RectHV rect, KdTree.Node node, SET<Point2D> points) {
    if(node == null)
        return;
    if (rect.contains(node.p)) {
      points.add(node.p);
    }
    if (node.lb != null && rect.intersects(node.lb.rect)) {
      range(rect, node.lb, points);
    }
    if (node.rt != null && rect.intersects(node.rt.rect)) {
      range(rect, node.rt, points);
    }
  }

  // unit testing of the methods (optional)
  public static void main(String[] args) {
    StdOut.printf("%s %d\n", "Starting time : ", System.currentTimeMillis());
    String filename = args[0];
    In in = new In(filename);
    KdTree kdtree = new KdTree();
    while (!in.isEmpty()) {
      double x = in.readDouble();
      double y = in.readDouble();
      Point2D p = new Point2D(x, y);
      kdtree.insert(p);
    }
    StdOut.printf("%s %d\n", "Insert time   : ", System.currentTimeMillis());
    double x = 0;
    double y = 0;
    long counter = 0;
    long endTimeMillis = System.currentTimeMillis() + 1000;
    while (System.currentTimeMillis() < endTimeMillis) {
      x = StdRandom.uniform(0.0, 1.0);
      y = StdRandom.uniform(0.0, 1.0);
      kdtree.nearest(new Point2D(x, y));
      counter++;
    }
    StdOut.printf("%s %d\n", "End time      : ", System.currentTimeMillis());
    StdOut.printf("%s %d\n", "Total count   : ", counter);
  }
}
