public class Heaps {

  public static void main(String[] args) {

    MyMaxHeap<Integer> maxHeap = new MyMaxHeap<Integer>();

    for (int i=5; i<10; i++) {

      maxHeap.push(i);

      System.out.println(maxHeap);

      System.out.println("Valid: " + maxHeap.isValid());
    }

    //

    for (int i=0; i<5; i++) {

      maxHeap.push(i);

      System.out.println(maxHeap);

      System.out.println("Valid: " + maxHeap.isValid());
    }

    //

    while (!maxHeap.isEmpty()) {

      System.out.println(maxHeap.peek() + " " + maxHeap.pop());

      System.out.println(maxHeap);

      System.out.println("Valid: " + maxHeap.isValid());
    }

    MyMinHeap<Integer> minHeap = new MyMinHeap<Integer>();

    for (int i=5; i<10; i++) {

      minHeap.push(i);

      System.out.println(minHeap);

      System.out.println("Valid: " + minHeap.isValid());
    }

    //

    for (int i=0; i<5; i++) {

      minHeap.push(i);

      System.out.println(minHeap);

      System.out.println("Valid: " + minHeap.isValid());
    }

    //

    while (!minHeap.isEmpty()) {

      System.out.println(minHeap.peek() + " " + minHeap.pop());

      System.out.println(minHeap);

      System.out.println("Valid: " + minHeap.isValid());
    }

    // priority queue is exactly the same as MaxHeap, except rather
    // than containing items of type T, it contains items that have
    // data (of type T) and a priority (e.g., an int)
  }

  public static class MyMaxHeap<T extends Comparable<T>> {

    private T[] array; // items stored in array
    private int size; // number of items

    @SuppressWarnings("unchecked")
    public MyMaxHeap(int maxSize) {

      // can't do "this.array = new T[size]" because of "generic array
      // creation" compilation errors; code below generates an
      // "unchecked cast" warning, hence the suppression

      this.array = (T[]) new Comparable[maxSize];
      this.size = 0;
    }

    public MyMaxHeap() {

      this(1000);
    }

    public boolean isEmpty() {

      return (size == 0);
    }

    public boolean push(T data) {

      if (size == array.length)
        return false;

      // add item to bottom of heap -- equiv. to adding item at
      // position indicated by current heapsize

      int i = size++;
      int pi = parent(i);

      array[i] = data; // add item to bottom of heap

      // "bubble" the item up the heap to its final position

      while (pi != -1) {

        // while we're not at the top of the heap, compare the item to
        // its parent and if its smaller, break because the item is
        // already in its final place

        if (array[i].compareTo(array[pi]) <= 0)
          break;

        // otherwise, if the item is larger than its parent, swap them

        array[i] = array[pi];
        array[pi] = data;

        // update position of item to its new position and get
        // position of its (new) parent

        i = pi;
        pi = parent(i);
      }

      return true;
    }

    public T pop() {

      if (isEmpty())
        return null;

      // going to remove the item at the top of the heap, put the item
      // at the bottom of the heap in its place, update heapsize

      int i = 0;
      int[] ci = children(i);

      T data = array[i]; // remove item from top of heap
      array[i] = array[--size]; // move item from bottom of heap to top

      // "bubble" item now at top of heap down to its new position

      while (ci != null) {

        // while we're not at the bottom level (i.e., while this item
        // still has children), find the item's largest child

        // find item's largest child

        int lci = ci[0];

        if ((ci.length == 2) && (array[ci[1]].compareTo(array[ci[0]]) > 0))
          lci = ci[1];

        // if the item is larger (or equal to) its largest child, the
        // item is in its final place, so break

        if (array[i].compareTo(array[lci]) >= 0)
          break;

        // if item is smaller than its largest child, swap them

        T tmp = array[i];
        array[i] = array[lci];
        array[lci] = tmp;

        // update position of item to its new position and get
        // positions of its (new) children

        i = lci;
        ci = children(i);
      }

      return data;
    }

    public T peek() {

      if (isEmpty())
        return null;

      return array[0];
    }

    private int parent(int i) {

      if (i == 0)
        return -1;

      // if j is an odd #, then this is the first child
      // i = 2*j + 1 -> j = (i - 1) / 2

      // if j is an even #, then this is the second child i = 2*j + 2
      // -- subtract off 1 (i - 1) = 2*j + 1 -- this will be an odd
      // number, when we divide it in half -> (i - 1) / 2 = j + 1/2 ->
      // floor will give us j as desired

      return (i - 1) / 2; // cast is equiv. to floor here
    }

    private int[] children(int i) {

      // i's children are at 2*i + 1 (odd #) and 2*i + 2 (even #)

      int lci = 2 * i + 1;
      int rci = lci + 1;

      if ((lci < size) && (rci < size))
        return new int[] { lci, rci };
      else if (lci < size)
        return new int[] { lci };
      else
        return null;
    }

    public boolean isValid() {

      // returns true if heap is valid

      for (int i=1; i<size; i++)
        if (array[i].compareTo(array[parent(i)]) >= 0)
          return false;

      return true;
    }

    public String toString() {

      StringBuffer buf = new StringBuffer("[");

      for (int i=0; i<size; i++) {

        buf.append(array[i]);

        if (i != (size - 1))
          buf.append(", ");
      }

      buf.append("]");

      return buf.toString();
    }
  }

  public static class MyMinHeap<T extends Comparable<T>> {

    private T[] array;
    private int size;

    @SuppressWarnings("unchecked")
    public MyMinHeap(int maxSize) {

      // can't do "this.array = new T[size]" because of "generic array
      // creation" compilation errors; code below generates an
      // "unchecked cast" warning, hence the suppression

      this.array = (T[]) new Comparable[maxSize];
      this.size = 0;
    }

    public MyMinHeap() {

      this(1000);
    }

    public boolean isEmpty() {

      return (size == 0);
    }

    public boolean push(T data) {

      if (size == array.length)
        return false;

      int i = size++;
      int pi = parent(i);

      array[i] = data; // add item to bottom of heap

      // while we're not at the top of the heap

      while (pi != -1) {

        if (array[i].compareTo(array[pi]) >= 0)
          break;

        // if item is smaller than its parent, swap them

        array[i] = array[pi];
        array[pi] = data;

        i = pi;
        pi = parent(i);
      }

      return true;
    }

    public T pop() {

      if (isEmpty())
        return null;

      int i = 0;
      int[] ci = children(i);

      T data = array[i]; // remove item from top of heap
      array[i] = array[--size]; // move item from bottom of heap to top

      // while we're not at the bottom level of the heap

      while (ci != null) {

        // find item's smallest child

        int sci = ci[0];

        if ((ci.length == 2) && (array[ci[1]].compareTo(array[ci[0]]) < 0))
          sci = ci[1];

        if (array[i].compareTo(array[sci]) <= 0)
          break;

        // if item is larger than its smallest child, swap them

        T tmp = array[i];
        array[i] = array[sci];
        array[sci] = tmp;

        i = sci;
        ci = children(i);
      }

      return data;
    }

    public T peek() {

      if (isEmpty())
        return null;

      return array[0];
    }

    private int parent(int i) {

      if (i == 0)
        return -1;

      return (i - 1) / 2; // cast is equiv. to floor here
    }

    private int[] children(int i) {

      int lci = 2 * i + 1;
      int rci = lci + 1;

      if ((lci < size) && (rci < size))
        return new int[] { lci, rci };
      else if (lci < size)
        return new int[] { lci };
      else
        return null;
    }

    public boolean isValid() {

      // returns true if heap is valid

      // runs through heap starting with left child of root, comparing
      // each node to its parent...

      for (int i=1; i<size; i++)
        if (array[i].compareTo(array[parent(i)]) <= 0)
          return false;

      return true;
    }

    public String toString() {

      StringBuffer buf = new StringBuffer("[");

      for (int i=0; i<size; i++) {

        buf.append(array[i]);

        if (i != (size - 1))
          buf.append(", ");
      }

      buf.append("]");

      return buf.toString();
    }
  }
}
