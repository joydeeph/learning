import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
  public static void main(String[] args) {
    if (args.length < 1) {
      throw new IllegalArgumentException();
    }
    int noToPrint = Integer.parseInt(args[0]);
    RandomizedQueue<String> rndQueue = new RandomizedQueue<String>();
    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
      rndQueue.enqueue(item);
    }
    for (Iterator<String> itr = rndQueue.iterator(); itr.hasNext()
        && noToPrint > 0;) {
      StdOut.println(itr.next());
      noToPrint--;
    }
  }
}
