import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private Item[] array; // array of items
  private int size = 0; // number of elements on stack

  // construct an empty randomized queue
  public RandomizedQueue() {
    array = (Item[]) new Object[2];
  }

  // is the queue empty?
  public boolean isEmpty() {
    return size == 0;
  }

  // return the number of items on the queue
  public int size() {
    return size;
  }

  // add the item
  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    if (size == array.length) {
      resize(2 * array.length); // double size of array if necessary
    }
    array[size++] = item; // add item
  }

  // remove and return a random item
  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException("Queue underflow");
    }
    int randomPosition = StdRandom.uniform(size);
    Item item = array[randomPosition];
    array[randomPosition] = array[size - 1];
    array[size - 1] = null; // to avoid loitering
    size--;
    // shrink size of array if necessary
    if (size > 0 && size == array.length / 4) {
      resize(array.length / 2);
    }
    return item;
  }

  // return (but do not remove) a random item
  public Item sample() {
    if (isEmpty()) {
      throw new NoSuchElementException("Queue underflow");
    }
    return array[StdRandom.uniform(size)];
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new ArrayIterator();
  }

  // an iterator, doesn't implement remove() since it's optional
  private class ArrayIterator implements Iterator<Item> {
    private int idx = 0;
    private final int[] index;

    public ArrayIterator() {
      index = new int[size];
      for (int i = 0; i < size; i++) {
        index[i] = i;
      }
      shuffle(index);
    }

    public boolean hasNext() {
      return idx < size;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return array[index[idx++]];
    }

    // Knuth suffle
    private void shuffle(int[] inputArray) {
      int size = inputArray.length;
      for (int idx = 0; idx < size; idx++) {
        // choose index uniformly in [i, N-1]
        int randomIdx = idx + StdRandom.uniform(size - idx);
        int swap = inputArray[randomIdx];
        inputArray[randomIdx] = inputArray[idx];
        inputArray[idx] = swap;
      }
    }
  }

  // resize the underlying array holding the elements
  private void resize(int capacity) {
    assert capacity >= size;
    Item[] temp = (Item[]) new Object[capacity];
    for (int i = 0; i < size; i++) {
      temp[i] = array[i];
    }
    array = temp;
  }

  public static void main(String[] args) {
    RandomizedQueue<String> rndQueue = new RandomizedQueue<String>();
    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
      if (!item.equals("-")) {
        rndQueue.enqueue(item);
      } else if (!rndQueue.isEmpty()) {
        StdOut.print(rndQueue.dequeue() + " ");
      }
    }
    StdOut.println("(" + rndQueue.size() + " left on queue)");
  } // unit testing
}