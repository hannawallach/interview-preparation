import java.util.HashSet;
import java.util.Stack;

public class LinkedLists {

  // generic method, indicated by type parameter section <T>

  public <T> void removeDuplicates(MyDoublyLinkedList<T> list) {

    // assuming that remove is O(1), time complexity is O(N); uses
    // O(N) additional space

    // HashSet uses object equality, not identity

    HashSet<T> set = new HashSet<T>();

    MyDoublyLinkedList<T>.Node<T> node = list.head;

    while (node != null) {

      if (set.contains(node.data))
        list.remove(node);
      else
        set.add(node.data);

      node = node.next;
    }
  }

  public <T> void removeDuplicates(MySinglyLinkedList<T> list) {

    // assuming remove is O(N), time complexity is O(N^2);
    // alternatively, if we maintain a pointer to the previous node,
    // remove is O(1) and we can remove each node, even the tail node
    // -- time complexity is O(N); uses O(N) additional space

    HashSet<T> set = new HashSet<T>();

    MySinglyLinkedList<T>.Node<T> node = list.head;
    MySinglyLinkedList<T>.Node<T> prev = null;

    while (node != null) {

      if (set.contains(node.data))
        list.remove(node, prev);
      else {

        set.add(node.data);
        prev = node;
      }

      node = node.next;
    }
  }

  public <T> void reverse(MyDoublyLinkedList<T> list) {

    // time complexity is O(N); uses O(1) additional space

    MyDoublyLinkedList<T>.Node<T> start = list.head;
    MyDoublyLinkedList<T>.Node<T> end = list.tail;

    while ((start != end) && (start.prev != end)) {

      T tmp = start.data;
      start.data = end.data;
      end.data = tmp;

      start = start.next;
      end = end.prev;
    }
  }

  public <T> void reverse(MySinglyLinkedList<T> list) {

    // time complexity is O(N); uses O(N) additional space

    Stack<T> stack = new Stack<T>();

    MySinglyLinkedList<T>.Node<T> node = list.head;

    while (node != null) {

      stack.push(node.data);
      node = node.next;
    }

    node = list.head;

    while (node != null) {

      node.data = stack.pop();
      node = node.next;
    }
  }

  public <T> boolean weave(MyDoublyLinkedList<T> list) {

    // assuming that insert and remove are both O(1), time complexity
    // is O(N); uses O(1) additional space

    // assumption: list length is even and >= 4

    if (((list.size() % 2) != 0) || (list.size() < 4))
      return false;

    MyDoublyLinkedList<T>.Node<T> slow = list.head;
    MyDoublyLinkedList<T>.Node<T> fast = list.head;

    while (fast != null) {

      slow = slow.next;
      fast = fast.next.next;
    }

    fast = list.head;

    while (fast.next != slow) {

      list.insertAfter(slow.data, fast);
      list.remove(slow);

      fast = fast.next.next; // skip over newly-inserted node
      slow = slow.next; // omit for singly linked list w/ O(1) remove
    }

    return true;
  }

  public <T> T getKthFromEnd(MySinglyLinkedList<T> list, int k) {

    // time complexity is O(N); uses O(1) additional space

    // assumption: we don't know the size of the list

    MySinglyLinkedList<T>.Node<T> first = list.head;
    MySinglyLinkedList<T>.Node<T> second = list.head;

    // second makes k-1 hops -> kth node

    for (int i=1; i<k; i++)
      if (second == list.tail)
        return null;
      else
        second = second.next;

    while (second != list.tail) {

      first = first.next;
      second = second.next;
    }

    return first.data;
  }

  public void partition(MyDoublyLinkedList<Integer> list, int data) {

    // assuming that prepend and remove are both O(1), time complexity
    // is O(N); uses O(1) additional space

    // can also use quickselect!

    // ignore head because it's already at the front of the list

    MyDoublyLinkedList<Integer>.Node<Integer> node = list.head.next;

    while (node != null) {

      if (node.data < data) {

        list.prepend(node.data);
        list.remove(node);
      }

      node = node.next;
    }
  }

  public void partition(MySinglyLinkedList<Integer> list, int data) {

    // assuming remove is O(N), time complexity is O(N^2);
    // alternatively, if we maintain a pointer to the previous node,
    // remove is O(1) and we can remove each node, even the tail node
    // -- time complexity is O(N); uses O(1) additional space

    // can also use quickselect!

    MySinglyLinkedList<Integer>.Node<Integer> node = list.head.next;
    MySinglyLinkedList<Integer>.Node<Integer> prev = list.head;

    while (node != null) {

      if (node.data < data) {

        list.prepend(node.data);
        list.remove(node, prev);
      }
      else
        prev = node;

      node = node.next;
    }
  }

  public <T> boolean isPalindrome(MyDoublyLinkedList<T> list) {

    // time complexity is O(N); uses O(1) additional space

    MyDoublyLinkedList<T>.Node<T> start = list.head;
    MyDoublyLinkedList<T>.Node<T> end = list.tail;

    while ((start != end) && (start.prev != end)) {

      if (!start.data.equals(end.data))
        return false;

      start = start.next;
      end = end.prev;
    }

    return true;
  }

  public <T> boolean isPalindrome(MySinglyLinkedList<T> list) {

    // assumption: we don't know the size of the list

    // time complexity is O(N); uses O(N) additional space

    Stack<T> stack = new Stack<T>();

    MySinglyLinkedList<T>.Node<T> node = list.head;

    while (node != null) {

      stack.push(node.data);
      node = node.next;
    }

    node = list.head;

    int numToCompare = stack.size() / 2;

    for (int i=0; i<numToCompare; i++) {

      if (!node.data.equals(stack.pop()))
        return false;

      node = node.next;
    }

    return true;
  }

  public MySinglyLinkedList<Integer> addReverseOrder(MySinglyLinkedList<Integer> list1, MySinglyLinkedList<Integer> list2) {

    // adds two numbers stored in reverse order, e.g., 617 is stored
    // as 7 -> 1 -> 6, while 395 is stored as 5 -> 9 -> 3

    // time complexity is O(N); uses O(N) space to store result

    MySinglyLinkedList<Integer> result = new MySinglyLinkedList<Integer>();

    int carry = 0;

    MySinglyLinkedList<Integer>.Node<Integer> node1 = list1.head;
    MySinglyLinkedList<Integer>.Node<Integer> node2 = list2.head;

    while (true) {

      if ((node1 == null) && (node2 == null))
        break;

      int sum = carry;

      if (node1 != null) {

        if ((node1.data < 0) || (node1.data > 9))
          return null;

        sum += node1.data;
        node1 = node1.next;
      }

      if (node2 != null) {

        if ((node2.data < 0) || (node2.data > 9))
          return null;

        sum += node2.data;
        node2 = node2.next;
      }

      result.append(sum % 10);
      carry = sum / 10;
    }

    if (carry != 0)
      result.append(carry);

    return result;
  }

  public MySinglyLinkedList<Integer> addForwardOrder(MySinglyLinkedList<Integer> list1, MySinglyLinkedList<Integer> list2) {

    // adds two numbers stored in forward order, e.g., 617 is stored
    // as 6 -> 1 -> 7, while 395 is stored as 3 -> 9 -> 5

    // time complexity is O(N); uses O(N) space to store result and
    // O(1) space for each recursive call to add, of which there are N
    // => O(N) + O(N) = O(N) additional space

    // start by padding the shorter list with zeros

    while (list1.size() < list2.size())
      list1.prepend(0);

    while (list2.size() < list1.size())
      list2.prepend(0);

    if (list1.size() != list2.size())
      return null;

    MySinglyLinkedList<Integer> result = new MySinglyLinkedList<Integer>();

    int carry = add(list1.head, list2.head, result);

    if (carry != 0)
      result.prepend(carry);

    return result;
  }

  private int add(MySinglyLinkedList<Integer>.Node<Integer> node1, MySinglyLinkedList<Integer>.Node<Integer> node2, MySinglyLinkedList<Integer> result) {

    int sum = node1.data + node2.data;

    if ((node1.next != null) && (node2.next != null))
      sum += add(node1.next, node2.next, result);

    result.prepend(sum % 10);

    return sum / 10;
  }

  public <T> MySinglyLinkedList<T>.Node<T> findLoopHead(MySinglyLinkedList<T> list) {

    // assumption: # nodes before loop = k, loop length = l

    // if there's a loop they're guaranteed to collide -- fast cannot
    // hop over slow -- if slow is at i and fast is at i+1 (i.e., fast
    // hopped over slow) then they must previously have both been at
    // i-1 -- also in another l-1 steps, slow will be at i + l - 1 =
    // i-1 and fast will be at (i+1) + 2 * (l-1) = i + 2l - 1 = i-1.

    // slow enters loop after k time steps, at which point fast has
    // taken 2k steps = k steps into loop = k % l = k' steps after
    // start of loop. slow is l - k' steps ahead of fast and fast
    // catches up to slow 1 step per time step => they'll collide in l
    // - k' time steps => since loop is length l, this means slow and
    // fast will both be k' = k steps before start of loop.

    // to find start of loop, move slow back to start, move slow and
    // fast 1 step per time step, they'll collide at start of loop

    MySinglyLinkedList<T>.Node<T> slow = list.head;
    MySinglyLinkedList<T>.Node<T> fast = list.head;

    while ((fast != null) && (fast.next != null)) {

      slow = slow.next;
      fast = fast.next.next;

      if (slow == fast)
        break;
    }

    if ((fast == null) || (fast.next == null))
      return null;

    slow = list.head;

    while (slow != fast) {

      slow = slow.next;
      fast = fast.next;
    }

    return slow;
  }

  public static void main(String[] args) {

    LinkedLists ll = new LinkedLists();

    //

    MyDoublyLinkedList<Integer> dList = new MyDoublyLinkedList<Integer>();

    for (int i=1; i<=10; i++)
      dList.append(i);

    System.out.println(dList);

    System.out.println("Reversing");

    ll.reverse(dList);

    System.out.println(dList);

    //

    MySinglyLinkedList<Integer> sList = new MySinglyLinkedList<Integer>();

    for (int i=1; i<=10; i++)
      sList.append(i);

    System.out.println(sList);

    System.out.println("Reversing");

    ll.reverse(sList);

    System.out.println(sList);

    //

    dList = new MyDoublyLinkedList<Integer>();

    dList.append(1);
    dList.append(3);
    dList.append(5);
    dList.append(2);
    dList.append(4);
    dList.append(6);

    System.out.println(dList);

    System.out.println("Weaving");

    ll.weave(dList);

    System.out.println(dList);

    //

    sList = new MySinglyLinkedList<Integer>();

    for (int i=1; i<=10; i++)
      sList.append(i);

    System.out.println(sList);

    System.out.println("Getting 3rd from end");

    System.out.println(ll.getKthFromEnd(sList, 3));

    //

    dList = new MyDoublyLinkedList<Integer>();

    dList.append(8);
    dList.append(4);
    dList.append(2);
    dList.append(7);
    dList.append(5);
    dList.append(9);

    System.out.println(dList);

    System.out.println("Partitioning around 5");

    ll.partition(dList, 5);

    System.out.println(dList);

    //

    sList = new MySinglyLinkedList<Integer>();

    sList.append(8);
    sList.append(4);
    sList.append(2);
    sList.append(7);
    sList.append(5);
    sList.append(9);

    System.out.println(sList);

    System.out.println("Partitioning around 5");

    ll.partition(sList, 5);

    System.out.println(sList);

    //

    dList = new MyDoublyLinkedList<Integer>();

    dList.append(1);
    dList.append(2);
    dList.append(3);
    dList.append(2);
    dList.append(1);

    System.out.println(dList);

    System.out.println("Is palindrome: " + ll.isPalindrome(dList));

    //

    sList = new MySinglyLinkedList<Integer>();

    sList.append(1);
    sList.append(2);
    sList.append(3);
    sList.append(2);
    sList.append(1);

    System.out.println(sList);

    System.out.println("Is palindrome: " + ll.isPalindrome(sList));

    //

    dList = new MyDoublyLinkedList<Integer>();

    dList.append(3);
    dList.append(5);
    dList.append(3);
    dList.append(3);

    System.out.println(dList);

    System.out.println("Removing duplicates");

    ll.removeDuplicates(dList);

    System.out.println(dList);

    //

    sList = new MySinglyLinkedList<Integer>();

    sList.append(3);
    sList.append(5);
    sList.append(3);
    sList.append(3);

    System.out.println(sList);

    System.out.println("Removing duplicates");

    ll.removeDuplicates(sList);

    System.out.println(sList);

    MySinglyLinkedList<Integer> sList1 = new MySinglyLinkedList<Integer>();

    sList1.append(7);
    sList1.append(1);
    sList1.append(6);

    MySinglyLinkedList<Integer> sList2 = new MySinglyLinkedList<Integer>();

    sList2.append(5);
    sList2.append(9);
    sList2.append(3);
    sList2.append(9);

    System.out.println(ll.addReverseOrder(sList1, sList2));
    System.out.println(ll.addForwardOrder(sList1, sList2));

    //

    sList = new MySinglyLinkedList<Integer>();

    for (int i=0; i<=10; i++)
      sList.append(i);

    sList.tail.next = sList.find(3);
    sList.tail = null;

    System.out.println("Finding loop head");

    System.out.println(ll.findLoopHead(sList));

    /*
    MySinglyLinkedList<Integer> list = new MySinglyLinkedList<Integer>();

    System.out.println("Appending 10");

    System.out.println(list.append(10)); // Java will box the int

    System.out.println(list);

    System.out.println("Prepending 5 then 1");

    System.out.println(list.prepend(5));

    System.out.println(list);

    System.out.println(list.prepend(1));

    System.out.println(list);

    // can't do "MySinglyLinkedList.Node<Integer> node = list.head;"
    // but can do "MySinglyLinkedList.Node node = list.head;" -- also
    // can't do "Node<Integer> node = list.head" because Node isn't
    // nested directly w/in LinkedLists so it's not found

    MySinglyLinkedList<Integer>.Node<Integer> node = list.head;

    System.out.println("Removing node after head");

    System.out.println(list.remove(node.next));

    System.out.println(list);

    System.out.println("Removing tail");

    System.out.println(list.remove(list.tail));

    System.out.println(list);

    System.out.println("Finding first node equal to 1");

    System.out.println(list.find(1));

    System.out.println("Inserting 2 after null node");

    System.out.println(list.insertAfter(2, null));

    System.out.println("Inserting 2 after first node equal to 1");

    System.out.println(list.insertAfter(2, n)); // Java will box the int

    System.out.println(list);

    System.out.println("Inserting 2 after first node equal to 1 again");

    System.out.println(list.insertAfter(2, n));

    System.out.println(list);

    System.out.println("Removing nodes equal to 2");

    System.out.println(list.remove(2));

    System.out.println(list);

    System.out.println("Removing nodes equal to 10");

    System.out.println(list.remove(10));

    System.out.println(list);

    System.out.println("Removing nodes equal to 1");

    System.out.println(list.remove(1));

    System.out.println(list);
    */
  }

  // static nested class so I can create an instance of it w/out
  // creating an enclosing instance of LinkedLists -- otherwise I'd
  // have to do: "MySinglyLinkedList<Integer> list = new
  // (LinkedLists()).new MySinglyLinkedList<Integer>();"

  public static class MySinglyLinkedList<T> {

    Node<T> head, tail;

    private int size = 0;

    public boolean append(T data) {

      Node<T> newNode = new Node<T>(data, null);

      if (tail != null)
        tail.next = newNode;
      else {

        // check list really is empty

        if (head != null)
          return false;

        head = newNode;
      }

      tail = newNode;

      size++;

      return true;
    }

    public boolean prepend(T data) {

      Node<T> newNode = new Node<T>(data, head);

      if (head == null) {

        // check list really is empty

        if (tail != null)
          return false;

        tail = newNode;
      }

      head = newNode;

      size++;

      return true;
    }

    public boolean insertAfter(T data, Node<T> node) {

      if (node == tail)
        return append(data);

      if (node == null)
        return false;

      Node<T> newNode = new Node<T>(data, node.next);

      node.next = newNode;

      size++;

      return true;
    }

    public boolean remove(Node<T> node, Node<T> prev) {

      if (node == null)
        return false;

      if ((prev == null) && (node != head))
        return false;

      if ((prev != null) && (node == head))
          return false;

      if (prev.next != node)
        return false;

      if (node == head)
        head = node.next;
      else
        prev.next = node.next;

      if (node == tail)
        tail = prev;

      return true;
    }

    public boolean remove(Node<T> node) {

      if (node == null)
        return false;

      if (node == tail)
        return false;

      if (node.next == tail)
        tail = node;

      // to delete a node, have to change prev.next to point to
      // node.next, but we don't have access to the previous node
      // here, so we copy data from the next node over to this one and
      // delete the next node (this node is previous to that one)

      node.data = node.next.data;
      node.next = node.next.next;

      size--;

      return true;
    }

    public boolean remove(T data) {

      boolean success = false;

      Node<T> node = head;

      while (node != null) {

        // after remove(node) node.next's data is now stored in node

        if (node.data.equals(data))
          if (remove(node))
            success = true;
          else
            return false;
        else
          node = node.next;
      }

      return success;
    }

    public Node<T> find(T data) {

      // returns the first node containing the specified data

      Node<T> node = head;

      while (node != null) {

        if (node.data.equals(data))
          return node;

        node = node.next;
      }

      return null;
    }

    public int size() {

      return size;
    }

    public String toString() {

      StringBuffer buf = new StringBuffer("[");

      Node<T> node = head;

      while (node != null) {

        buf.append(node);

        if (node != tail)
          buf.append(", ");

        node = node.next;
      }

      buf.append("]");

      return buf.toString();
    }

    // inner class (i.e., non-static nested class) -- I don't want to
    // create instances of it w/out an instance of MySinglyLinkedList

    public class Node<T> {

      T data;
      Node<T> next;

      public Node(T data, Node<T> next) {

        this.data = data;
        this.next = next;
      }

      public String toString() {

        return data.toString();
      }
    }
  }

  public static class MyDoublyLinkedList<T> {

    Node<T> head, tail;

    private int size = 0;

    public boolean append(T data) {

      Node<T> newNode = new Node<T>(data, tail, null);

      if (tail != null)
        tail.next = newNode;
      else {

        // check list really is empty

        if (head != null)
          return false;

        head = newNode;
      }

      tail = newNode;

      size++;

      return true;
    }

    public boolean prepend(T data) {

      Node<T> newNode = new Node<T>(data, null, head);

      if (head != null)
        head.prev = newNode;
      else {

        // check list really is empty

        if (tail != null)
          return false;

        tail = newNode;
      }

      head = newNode;

      size++;

      return true;
    }

    public boolean insertAfter(T data, Node<T> node) {

      if (node == tail)
        return append(data);

      if (node == null)
        return false;

      Node<T> newNode = new Node<T>(data, node, node.next);

      node.next.prev = newNode;
      node.next = newNode;

      size++;

      return true;
    }

    public boolean insertBefore(T data, Node<T> node) {

      if (node == head)
        return prepend(data);

      if (node == null)
        return false;

      Node<T> newNode = new Node<T>(data, node.prev, node);

      node.prev.next = newNode;
      node.prev = newNode;

      size++;

      return true;
    }

    public boolean remove(Node<T> node) {

      if (node == null)
        return false;

      if ((head == null) && (tail == null))
        return false;

      if (node == head)
        head = node.next;
      else
        node.prev.next = node.next;

      if (node == tail)
        tail = node.prev;
      else
        node.next.prev = node.prev;

      size--;

      return true;
    }

    public boolean remove(T data) {

      boolean success = false;

      Node<T> node = head;

      while (node != null) {

        if (node.data.equals(data))
          if (remove(node))
            success = true;
          else
            return false;

        node = node.next;
      }

      return success;
    }

    public Node<T> find(T data) {

      // returns the first node containing the specified data

      Node<T> node = head;

      while (node != null) {

        if (node.data.equals(data))
          return node;

        node = node.next;
      }

      return null;
    }

    public int size() {

      return size;
    }

    public String toString() {

      StringBuffer buf = new StringBuffer("[");

      Node<T> node = head;

      while (node != null) {

        buf.append(node);

        if (node != tail)
          buf.append(", ");

        node = node.next;
      }

      buf.append("]");

      return buf.toString();
    }

    public class Node<T> {

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
}
