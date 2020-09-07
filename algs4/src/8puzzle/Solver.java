import java.util.Comparator;
import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.ResizingArrayStack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

  private class SearchNode {
    SearchNode(Board board, int moves, SearchNode previousNode) {
      this.board = board;
      this.moves = moves;
      this.previousNode = previousNode;
    }

    Board board;
    int moves;
    SearchNode previousNode;
  }

  private class ManhattanComparator implements Comparator<SearchNode> {

    @Override
    public int compare(SearchNode o1, SearchNode o2) {
      int o1manhattan = o1.board.manhattan();
      int o2manhattan = o2.board.manhattan();
      int o1p = o1.moves + o1manhattan;
      int o2p = o2.moves + o2manhattan;

      if (o1p > o2p)
        return 1;
      else if (o1p < o2p)
        return -1;
      else if (o1manhattan > o2manhattan)
        return 1;
      else if (o1manhattan < o2manhattan)
        return -1;
      else
        return 0;
    }

  }

  private ResizingArrayStack<Board> solution = new ResizingArrayStack<>();
  private SearchNode goal = null;

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    if (initial == null)
      throw new IllegalArgumentException();
    this.goal = search(initial);
    SearchNode temp = goal;
    while (temp != null) {
      solution.push(temp.board);
      temp = temp.previousNode;
    }

  }

  private SearchNode search(Board initial) {

    MinPQ<SearchNode> pqOriginal = new MinPQ<>(new ManhattanComparator());
    MinPQ<SearchNode> pqTwin = new MinPQ<>(new ManhattanComparator());

    SearchNode goal = null;

    pqOriginal.insert(new SearchNode(initial, 0, null));
    pqTwin.insert(new SearchNode(initial.twin(), 0, null));

    while (!pqOriginal.isEmpty() || !pqTwin.isEmpty()) {
      SearchNode nodeOriginal = pqOriginal.delMin();
      SearchNode nodeTwin = pqTwin.delMin();
      if (nodeOriginal.board.isGoal()) {
        goal = nodeOriginal;
        break;
      }
      if (nodeTwin.board.isGoal()) {
        // Not solvable should return null
        break;
      }
      Iterator<Board> itrOriginal = nodeOriginal.board.neighbors().iterator();
      while (itrOriginal.hasNext()) {
        Board child = itrOriginal.next();
        if (nodeOriginal.previousNode == null
            || !nodeOriginal.previousNode.board.equals(child))
          pqOriginal
              .insert(new SearchNode(child, nodeOriginal.moves + 1, nodeOriginal));
      }
      Iterator<Board> itrTwin = nodeTwin.board.neighbors().iterator();
      while (itrTwin.hasNext()) {
        Board child = itrTwin.next();
        if (nodeTwin.previousNode == null
            || !nodeTwin.previousNode.board.equals(child))
          pqTwin.insert(new SearchNode(child, nodeTwin.moves + 1, nodeTwin));
      }

    }
    return goal;
  }

  // is the initial board solvable? (see below)
  public boolean isSolvable() {
    return goal != null;
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    if (!isSolvable())
      return -1;
    return solution.size() - 1;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    if (!isSolvable())
      return null;
    return solution;
  }

  // test client (see below)
  public static void main(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] tiles = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        tiles[i][j] = in.readInt();
    Board initial = new Board(tiles);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }
}
