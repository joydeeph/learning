import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  // perform T independent experiments on an N-by-N grid

  private static final double CONFIDENCE_95 = 1.96;
  private final int size;
  private final int noOfExperiments;
  private double[] threshold;

  /**
   * Initializes an simulation runner for T independent experiments on an N-by-N
   * grid.
   * 
   * @param size
   *          the size of the grid
   * @param noOfExperiments
   *          no of independent experiments
   * @throws java.lang.IllegalArgumentException
   *           if size <= 0 & noOfExperiments <= 0
   */
  public PercolationStats(int size, int noOfExperiments) {
    if (size <= 0 || noOfExperiments <= 0) {
      throw new IllegalArgumentException("Value of N/T should be > 0");
    }
    this.size = size;
    this.noOfExperiments = noOfExperiments;
    threshold = new double[noOfExperiments];
    excecute();
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(threshold);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(threshold);
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return (mean() - (CONFIDENCE_95 * stddev()) / Math.sqrt(noOfExperiments));
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return (mean() + (CONFIDENCE_95 * stddev()) / Math.sqrt(noOfExperiments));
  }

  private void excecute() {
    Percolation perc = null;
    int count = 0;
    for (int i = 0; i < noOfExperiments; i++) {
      perc = new Percolation(size);
      count = 0;
      while (!perc.percolates()) {

        int row = StdRandom.uniform(size) + 1;
        int col = StdRandom.uniform(size) + 1;
        while (perc.isOpen(row, col)) {
          row = StdRandom.uniform(size) + 1;
          col = StdRandom.uniform(size) + 1;
        }
        perc.open(row, col);
        count++;
      }
      threshold[i] = (double) count / (double) (size * size);
    }
  }

  /**
   * This is the test client for the simulation.
   */
  public static void main(String[] args) {
    int size;
    int noOfExperiments;
    if (args.length < 2) {
      throw new IllegalArgumentException();
    }

    try {
      size = Integer.parseInt(args[0]); // N-by-N percolation system;
      noOfExperiments = Integer.parseInt(args[1]); // T
    } catch (NumberFormatException ex) {
      throw new IllegalArgumentException(ex);
    }
    PercolationStats stats = new PercolationStats(size, noOfExperiments);
    System.out.println("mean                    = " + stats.mean());
    System.out.println("stddev                  = " + stats.stddev());
    System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", "
        + stats.confidenceHi() + "]");

  }
}