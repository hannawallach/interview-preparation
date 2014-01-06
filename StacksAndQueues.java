import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class StacksAndQueues {

  public static void main(String[] args) {

    MyLinkedListStack<Integer> lls = new MyLinkedListStack<Integer>();

    for (int i=0; i<5; i++)
      lls.push(i); // Java will box the ints

    System.out.println(lls);

    while (!lls.isEmpty())
      System.out.println(lls.peek() + " " + lls.pop());

    System.out.println(lls);

    //

    MyArrayStack<Integer> as = new MyArrayStack<Integer>();

    for (int i=0; i<5; i++)
      as.push(i);

    while (!as.isEmpty())
      System.out.println(as.peek() + " " + as.pop());

    System.out.println(as);

    //

    MyNodeStack<Integer> ns = new MyNodeStack<Integer>();

    for (int i=0; i<5; i++)
      ns.push(i);

    while (!ns.isEmpty())
      System.out.println(ns.peek() + " " + ns.pop());

    System.out.println(ns);

    //

    MyNodeQueue<Integer> nq = new MyNodeQueue<Integer>();

    for (int i=0; i<5; i++)
      nq.push(i);

    while (!nq.isEmpty())
      System.out.println(nq.peek() + " " + nq.pop());

    System.out.println(nq);

    //

    MyStackQueue<Integer> sq = new MyStackQueue<Integer>();

    for (int i=0; i<4; i++)
      sq.push(i);

    System.out.println(sq);

    System.out.println("Pop: " + sq.pop());

    System.out.println(sq);

    sq.push(4);

    System.out.println(sq);

    System.out.println("Pop: " + sq.pop());

    System.out.println(sq);

    sq.push(5);

    System.out.println(sq);

    System.out.println("Pop: " + sq.pop());
    System.out.println("Pop: " + sq.pop());
    System.out.println("Pop: " + sq.pop());

    System.out.println(sq);

    System.out.println("Pop: " + sq.pop());

    MyStackWithMin swm = new MyStackWithMin();

    swm.push(3);

    System.out.println("Push 3: " + swm.min() + " " + swm);

    swm.push(2);

    System.out.println("Push 2: " + swm.min() + " " + swm);

    swm.push(5);

    System.out.println("Push 5: " + swm.min() + " " + swm);

    swm.push(1);

    System.out.println("Push 1: " + swm.min() + " " + swm);

    System.out.println("Pop: " + swm.pop() + " " + swm.min() + " " + swm);
    System.out.println("Pop: " + swm.pop() + " " + swm.min() + " " + swm);
    System.out.println("Pop: " + swm.pop() + " " + swm.min() + " " + swm);
    System.out.println("Pop: " + swm.pop() + "   " + swm);
  }

  public static class MyStackWithMin {

    // Create an Item class that keeps track of the item to be added
    // to the stack as well as the current minimum. Could also have
    // just used two stacks...

    private Stack<Item> stack = new Stack<Item>();

    public boolean isEmpty() {

      return stack.empty();
    }

    public void push(int data) {

      int min = data;

      if (!stack.empty())
        min = Math.min(stack.peek().min, min);

      stack.push(new Item(data, min));
    }

    public int pop() {

      return stack.pop().data;
    }

    public int peek() {

      return stack.peek().data;
    }

    public int min() {

      return stack.peek().min;
    }

    public String toString() {

      StringBuffer buf = new StringBuffer("[");

      Iterator<Item> itr = stack.iterator();

      while (itr.hasNext()) {

        buf.append(itr.next());

        if (itr.hasNext())
          buf.append(", ");
      }

      buf.append("]");

      return buf.toString();
    }

    public class Item {

      int data;
      int min;

      public Item(int data, int min) {

        this.data = data;
        this.min = min;
      }

      public String toString() {

        return "(" + data + ", " + min + ")";
      }
    }
  }

  public static class MyLinkedListStack<T> {

    // Store items in linked list -- efficient because removing the
    // head is O(1) as is prepending items to the front

    private LinkedList<T> list = new LinkedList<T>();

    public boolean isEmpty() {

      return list.isEmpty();
    }

    public void push(T data) {

      // adding an item to the front of a linked list is O(1)

      list.addFirst(data);
    }

    public T pop() {

      if (isEmpty())
        return null;

      return list.removeFirst(); // O(1)
    }

    public T peek() {

      return list.peekFirst();
    }

    public String toString() {

      StringBuffer buf = new StringBuffer("[");

      Iterator<T> itr = list.iterator();

      while (itr.hasNext()) {

        buf.append(itr.next().toString());

        if (itr.hasNext())
          buf.append(", ");
      }

      buf.append("]");

      return buf.toString();
    }
  }

  public static class MyArrayStack<T> {

    // Items stored in an array from oldest to newest. Keep track of
    // location of top of stack (equal to num items in stack) -- add
    // new item to that location

    private T[] array;
    private int size;

    @SuppressWarnings("unchecked")
    public MyArrayStack(int maxSize) {

      // can't do "this.array = new T[maxSize]" because of "generic
      // array creation" compilation errors; code below generates an
      // "unchecked cast" warning, hence the suppression

      this.array = (T[]) new Object[maxSize];
      this.size = 0;
    }

    public MyArrayStack() {

      this(1000);
    }

    public boolean isEmpty() {

      return (size == 0);
    }

    public boolean push(T data) {

      if (size == array.length)
        return false;

      array[size++] = data;

      return true;
    }

    public T pop() {

      if (isEmpty())
        return null;

      return array[--size];
    }

    public T peek() {

      if (isEmpty())
        return null;

      return array[size - 1];
    }

    public String toString() {

      StringBuffer buf = new StringBuffer("[");

      for (int i=(size - 1); i>=0; i--) {

        buf.append(array[i]);

        if (i != 0)
          buf.append(", ");
      }

      buf.append("]");

      return buf.toString();
    }
  }

  public static class MyNodeStack<T> {

    // store items in linked list (nodes have previous & next
    // pointers, but we're not really using previous pointers) -- new
    // items added by creating a new head node

    private Node<T> head;

    public boolean isEmpty() {

      return (head == null);
    }

    public void push(T data) {

      Node<T> node = new Node<T>(data, null, head); // create new head node

      head.prev = node; // update old head's "prev" pointer
      head = node; // set new head to new node
    }

    public T pop() {

      if (isEmpty())
        return null;

      T data = head.data; // data to return

      // 2 cases: either this is the only item, in which case
      // head.next == null, or there is another item after this one,
      // in which case that item will become the head

      // if there is a node after the current head (i.e., head.next is
      // not null), have to set that node's "prev" pointer to null

      if (head.next != null)
        head.next.prev = null;

      head = head.next; // update the head

      return data;
    }

    public T peek() {

      if (isEmpty())
        return null;

      return head.data;
    }

    public String toString() {

      StringBuffer buf = new StringBuffer("[");

      Node<T> node = head;

      while (node != null) {

        buf.append(node.data);

        if (node.next != null)
          buf.append(", ");

        node = node.next;
      }

      buf.append("]");

      return buf.toString();
    }
  }

  public static class MyStackQueue<T> {

    // Queue using two stacks -- push onto one stack (newest) and pop
    // off of the other (oldest) -- don't have to do anything fancy
    // when pushing, but when popping/peeking have to make sure oldest
    // is not empty -- if it is, have to refill it first -- do so by
    // popping items off of newest and pushing them onto oldest

    private Stack<T> newest = new Stack<T>();
    private Stack<T> oldest = new Stack<T>();

    public boolean isEmpty() {

      return newest.empty() && oldest.empty();
    }

    public void push(T data) {

      newest.push(data);
    }

    private void fillOldest() {

      assert oldest.empty();

      while (!newest.empty())
        oldest.push(newest.pop());
    }

    public T pop() {

      if (oldest.empty())
        fillOldest();

      return oldest.pop();
    }

    public T peek() {

      if (oldest.empty())
        fillOldest();

      return oldest.peek();
    }

    public String toString() {

      // FIX THIS

      return "Newest " + newest.toString() + " oldest " + oldest.toString();
    }
  }

  public static class MyNodeQueue<T> {

    // store items in linked list (nodes have previous & next
    // pointers) -- need pointer to tail of list

    private Node<T> head, tail;

    public boolean isEmpty() {

      return (head == null) && (tail == null);
    }

    public void push(T data) {

      // add item end of list

      Node<T> node = new Node<T>(data, tail, null);

      if (isEmpty())
        head = node; // if list is empty, new node will be head also
      else
        tail.next = node; // update old tail's "next" pointer

      tail = node; // set new tail to new node
    }

    public T pop() {

      // return item from front of list

      if (isEmpty())
        return null;

      T data = head.data; // data to return;

      // 2 cases: either this is the only item, in which case
      // head.next == null and removing this item (which is both the
      // head and the tail) should result in head == tail == null, or
      // there is another item after this one, in which case that item
      // will become the head (the tail remains unchanged)

      // if there is a node after the current head (i.e., head.next is
      // not null), have to set that node's "prev" pointer to null

      // if there isn't (i.e., head.next is null) then removing this
      // node must cause both head and tail to become null

      if (head.next != null)
        head.next.prev = null;
      else
        tail = null;

      head = head.next;

      return data;
    }

    public T peek() {

      if (isEmpty())
        return null;

      return head.data;
    }

    public String toString() {

      StringBuffer buf = new StringBuffer("[");

      Node<T> node = head;

      while (node != null) {

        buf.append(node.data);

        if (node.next != null)
          buf.append(", ");

        node = node.next;
      }

      buf.append("]");

      return buf.toString();
    }
  }

  public static class Node<T> {

    T data;
    Node<T> prev, next;

    public Node(T data, Node<T> prev, Node<T> next) {

      this.data = data;
      this.prev = prev;
      this.next = next;
    }

    public String toString() {

      return data.toString();
    }
  }
}
