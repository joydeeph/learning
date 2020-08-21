import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private boolean[][] open = null;
  private final WeightedQuickUnionUF unionFind;
  private final WeightedQuickUnionUF unionFindSingleTop;
  private final int size;
  private int numberOfOpenSites = 0;
  /**
   * Initializes an empty percolation data structure with N-by-N grid, with all sites blocked.
   * 
   * @param size
   *          the size of the grid
   * @throws java.lang.IllegalArgumentException
   *           if size <= 0
   */
  public Percolation(int size) {
    if (size <= 0) {
      throw new IllegalArgumentException("Value of N should be > 0");
    }
    this.size = size;
    open = new boolean[size][size];
    unionFind = new WeightedQuickUnionUF(size * size + 2);
    unionFindSingleTop = new WeightedQuickUnionUF(size * size + 1);
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        open[i][j] = false;
      }
    }
  }

  /**
   * Open site (row i, column j) if it is not open already.
   * 
   * @param row
   *          The row number for the site
   * @param col
   *          The column number for the site
   * @throws java.lang.IndexOutOfBoundsException
   *           if i,j<0 and i,j>size
   */
  public void open(int row, int col) {
    validate(row, col);
    if (!isOpen(row, col)) {
      open[row - 1][col - 1] = true;
      int adjrow;
      int adjcol;
      try {
        adjrow = row + 1;
        adjcol = col;
        if (isOpen(adjrow, adjcol)) {
          unionFind.union((adjrow - 1) * size + adjcol, (row - 1) * size + col);
          unionFindSingleTop.union((adjrow - 1) * size + adjcol, (row - 1) * size + col);
        }
      } catch (IllegalArgumentException ex) {
        // do nothing
      }
      try {
        adjrow = row - 1;
        adjcol = col;
        if (isOpen(adjrow, adjcol)) {
          unionFind.union((adjrow - 1) * size + adjcol, (row - 1) * size + col);
          unionFindSingleTop.union((adjrow - 1) * size + adjcol, (row - 1) * size + col);
        }
      } catch (IllegalArgumentException ex) {
        // do nothing
      }
      try {
        adjrow = row;
        adjcol = col + 1;
        if (isOpen(adjrow, adjcol)) {
          unionFind.union((adjrow - 1) * size + adjcol, (row - 1) * size + col);
          unionFindSingleTop.union((adjrow - 1) * size + adjcol, (row - 1) * size + col);
        }
      } catch (IllegalArgumentException ex) {
        // do nothing
      }
      try {
        adjrow = row;
        adjcol = col - 1;
        if (isOpen(adjrow, adjcol)) {
          unionFind.union((adjrow - 1) * size + adjcol, (row - 1) * size + col);
          unionFindSingleTop.union((adjrow - 1) * size + adjcol, (row - 1) * size + col);
        }
      } catch (IllegalArgumentException ex) {
        // do nothing
      }
      if (row == 1) {
        unionFind.union(col, 0);
        unionFindSingleTop.union(col, 0);
      } 
      if (row == size) {
        unionFind.union((row - 1) * size + col, size * size + 1);
      }
      numberOfOpenSites++;
    }
  }

  /**
   * Checks if the site (row i, column j) is open?
   * 
   * @param row
   *          The row number for the site
   * @param col
   *          The column number for the site
   * @throws java.lang.IndexOutOfBoundsException
   *           if i,j<0 and i,j>size
   */
  public boolean isOpen(int row, int col) {
    validate(row, col);
    return open[row - 1][col - 1];
  }

  private void validate(int row, int col) {
    if (row < 1 || col < 1 || row > size || col > size) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Checks if the site (row i, column j) is full?
   * 
   * @param row
   *          The row number for the site
   * @param col
   *          The column number for the site
   * @throws java.lang.IndexOutOfBoundsException
   *           if i,j<0 and i,j>size
   */
  public boolean isFull(int row, int col) {
    validate(row, col);

    return unionFindSingleTop.find(0) == unionFindSingleTop.find((row - 1) * size + col);
  }
  // returns the number of open sites
  public int numberOfOpenSites() {
    return numberOfOpenSites;
  }
  // does the system percolate?
  public boolean percolates() {
    return unionFind.find(0) == unionFind.find(size * size + 1);
  }

  // test client (optional)
  public static void main(String[] args) {
      System.out.println("Hello percolation");
  }

}
