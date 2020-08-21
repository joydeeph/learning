import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
  private static class Node<I> {
    private I item;
    private Node<I> next;
    private Node<I> prev;
  }

  private Node<Item> first;
  private Node<Item> last;

  private int size;

  // construct an empty deque
  public Deque() {
    first = null;
    last = null;
    size = 0;
  }

  // is the deque empty?
  public boolean isEmpty() {
    return size == 0;
  }

  // return the number of items on the deque
  public int size() {
    return size;
  }

  // add the item to the front
  public void addFirst(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    if (isEmpty()) {
      addToEmptyDeque(item);
    } else {
      Node<Item> oldFirst = first;
      first = new Node<Item>();
      first.item = item;
      first.next = oldFirst;
      oldFirst.prev = first;
    }
    size++;
  }

  // add the item to the end
  public void addLast(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    if (isEmpty()) {
      addToEmptyDeque(item);
    } else {
      Node<Item> oldLast = last;
      last = new Node<Item>();
      last.item = item;
      last.prev = oldLast;
      oldLast.next = last;
    }
    size++;
  }

  // remove and return the item from the front
  public Item removeFirst() {
    Item result = null;
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    if (size() == 1) {
      result = removeSingle();
    } else {
      first = first.next;
      result = removeNode(first.prev);
      first.prev = null;
    }
    size--;
    return result;
  }

  // remove and return the item from the end
  public Item removeLast() {
    Item result = null;
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    if (size() == 1) {
      result = removeSingle();
    } else {
      last = last.prev;
      result = removeNode(last.next);
      last.next = null;
    }
    size--;
    return result;

  }

  // return an iterator over items in order from front to end
  public Iterator<Item> iterator() {
    return new ListIterator<Item>(first);
  }

  // an iterator, doesn't implement remove() since it's optional
  private class ListIterator<I> implements Iterator<I> {
    private Node<I> current;

    public ListIterator(Node<I> first) {
      current = first;
    }

    public boolean hasNext() {
      return current != null;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public I next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      I item = current.item;
      current = current.next;
      return item;
    }
  }

  private void addToEmptyDeque(Item item) {
    last = new Node<Item>();
    first = last;
    first.item = item;
  }

  private Item removeSingle() {
    Node<Item> theOneNode = first;
    last = null;
    first = last;
    return removeNode(theOneNode);
  }

  private Item removeNode(Node<Item> node) {
    Item item = node.item;
    node.prev = null;
    node.next = null;
    node.item = null;
    return item;

  }

  // unit testing
  public static void main(String[] args) {
    Deque<String> deque = new Deque<String>();
    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
      if (!item.equals("-")) {
        deque.addFirst(item);
      } else if (!deque.isEmpty()) {
        StdOut.print(deque.removeFirst() + " ");
      }
    }
    StdOut.println("(" + deque.size() + " left on queue)");
  } // unit testing
}