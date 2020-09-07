import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ResizingArrayQueue;
import edu.princeton.cs.algs4.StdOut;

public class Board {
  // create a board from an n-by-n array of tiles,
  // where tiles[row][col] = tile at (row, col)
  private final int[][] tiles;
  private int hamming = 0;
  private int manhattan = 0;
  private final String tilesStr;

  public Board(int[][] tiles) {
    validate(tiles);
    this.tiles = tiles;
    StringBuilder builder = new StringBuilder(tiles.length + "");
    for (int i = 0; i < tiles.length; i++) {
      builder.append("\n");
      for (int j = 0; j < tiles[i].length; j++) {
        builder.append(String.format("%2d ", tiles[i][j]));
        // calculating hamming and manhattan distance
        if (tiles[i][j] != 0 && tiles[i][j] != i * tiles[i].length + j + 1) {
          hamming++;
          // Manhattan distance
          int iAtGoal;
          int jAtGoal;
          if (tiles[i][j] % tiles.length == 0) {
            iAtGoal = tiles[i][j] / tiles.length - 1;
            jAtGoal = tiles.length - 1;
          } else {
            iAtGoal = tiles[i][j] / tiles.length;
            jAtGoal = tiles[i][j] % tiles.length - 1;
          }
          manhattan += Math.abs(i - iAtGoal) + Math.abs(j - jAtGoal);
        }
      }
    }
    tilesStr = builder.toString();
  }

  private void validate(int[][] tiles) {
    // TODO Auto-generated method stub

  }

  // string representation of this board
  public String toString() {

    return tilesStr;
  }

  // board dimension n
  public int dimension() {
    return tiles.length;
  }

  // number of tiles out of place
  public int hamming() {
    return hamming;
  }

  // sum of Manhattan distances between tiles and goal
  public int manhattan() {
    return manhattan;
  }

  // is this board the goal board?
  public boolean isGoal() {
    return hamming == 0;
  }

  // does this board equal y?
  public boolean equals(Object y) {
    if (y == this)
      return true;
    if (!(y instanceof Board)) {
      return false;
    }
    Board that = (Board) y;
    if (this.dimension() != that.dimension())
      return false;
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[i].length; j++) {
        if (this.tiles[i][j] != that.tiles[i][j]) {
          return false;
        }
      }
    }

    return true;
  }

  // all neighboring boards
  public Iterable<Board> neighbors() {
    ResizingArrayQueue<Board> queue = new ResizingArrayQueue<>();
    // Find blank
    boolean found = false;
    for (int i = 0; i < tiles.length && !found; i++) {
      for (int j = 0; j < tiles[i].length && !found; j++) {
        if (tiles[i][j] == 0) {
          if (i - 1 >= 0) {
            queue.enqueue(new Board(copyAndSwap(i, j, i - 1, j)));
          }
          if (j - 1 >= 0) {
            queue.enqueue(new Board(copyAndSwap(i, j, i, j - 1)));
          }
          if (i + 1 < tiles.length) {
            queue.enqueue(new Board(copyAndSwap(i, j, i + 1, j)));
          }
          if (j + 1 < tiles.length) {
            queue.enqueue(new Board(copyAndSwap(i, j, i, j + 1)));
          }
          found = true;
        }
      }
    }

    return queue;
  }

  // a board that is obtained by exchanging any pair of tiles
  public Board twin() {
    // if blank exists in 1st row 1st 2 elements
    if (tiles[0][0] == 0 || tiles[0][1] == 0) {
      // Swap 1st 2 elements in the 2nd row
      return new Board(copyAndSwap(1, 0, 1, 1));
    } else {
      // Swap 1st 2 elements in the 1st row
      return new Board(copyAndSwap(0, 0, 0, 1));
    }
  }

  private int[][] copyTiles() {
    int twinTiles[][] = new int[this.tiles.length][this.tiles.length];
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[i].length; j++) {
        twinTiles[i][j] = tiles[i][j];
      }
    }
    return twinTiles;
  }

  private int[][] copyAndSwap(int i, int j, int k, int l) {
    int[][] twinTiles = copyTiles();
    int temp = twinTiles[i][j];
    twinTiles[i][j] = twinTiles[k][l];
    twinTiles[k][l] = temp;
    return twinTiles;
  }

  // unit testing (not graded)
  public static void main(String[] args) {
    // read in the board specified in the filename
    if (args.length > 0) {
      In in = new In(args[0]);
      int n = in.readInt();
      int[][] tiles = new int[n][n];
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          tiles[i][j] = in.readInt();
        }
      }

      // solve the slider puzzle
      Board initial = new Board(tiles);
      StdOut.println(initial);
      StdOut.println("Dimension " + initial.dimension());
      StdOut.println("Hamming " + initial.hamming());
      StdOut.println("Manhattan " + initial.manhattan());
      StdOut.println("Goal " + initial.isGoal());
      Iterator<Board> iterator = initial.neighbors().iterator();
      while (iterator.hasNext()) {
        StdOut.println(iterator.next());
      }
      StdOut.println(initial.twin());
      StdOut.println("Equals " + initial.twin().equals(initial));
    }
  }

}
