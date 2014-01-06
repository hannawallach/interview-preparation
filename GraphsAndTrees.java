import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

public class GraphsAndTrees {

  public static void main(String[] args) {

    Node<Integer> three = new Node<Integer>(3, null);
    Node<Integer> five = new Node<Integer>(5, null);
    Node<Integer> seven = new Node<Integer>(7, null);
    Node<Integer> ten = new Node<Integer>(10, null);
    Node<Integer> fifteen = new Node<Integer>(15, null);
    Node<Integer> seventeen = new Node<Integer>(17, null);
    Node<Integer> twenty = new Node<Integer>(20, null);
    Node<Integer> thirty = new Node<Integer>(30, null);

    twenty.addAdjacent(ten);
    twenty.addAdjacent(thirty);

    ten.addAdjacent(five);
    ten.addAdjacent(fifteen);

    five.addAdjacent(three);
    five.addAdjacent(seven);

    fifteen.addAdjacent(seventeen);

    //

    System.out.println("Iterative BFS");

    iterativeBFS(twenty);

    System.out.println("Iterative DFS");

    iterativeDFS(twenty);

    System.out.println("Recursive DFS");

    recursiveDFS(twenty, new HashSet<Node<Integer>>());

    //

    Node<String> a = new Node<String>("a", null);
    Node<String> b = new Node<String>("b", null);
    Node<String> c = new Node<String>("c", null);
    Node<String> d = new Node<String>("d", null);

    //    B
    //  / ^^
    // v  | \
    // A->C<-D

    d.addAdjacent(b);
    d.addAdjacent(c);

    c.addAdjacent(b);

    b.addAdjacent(a);

    a.addAdjacent(c);

    ArrayList<Node<String>> path = new ArrayList<Node<String>>();

    boolean pathExists = pathExists1(d, a, new HashSet<Node<String>>(), path);

    if (pathExists)
      System.out.println(pathExists + " " + path);
    else
      System.out.println(pathExists);

    System.out.println(shortestPath(d, c));

    /*
    ArrayList<Node<String>> graph = new ArrayList<Node<String>>();

    graph.add(a);
    graph.add(b);
    graph.add(c);
    graph.add(d);

    System.out.println(numConnectedComponents(graph));

    //    B
    //   /|\
    //  / | \
    // A--C--D

    c.addAdjacent(a);
    c.addAdjacent(d);

    b.addAdjacent(c);
    b.addAdjacent(d);

    a.addAdjacent(b);

    System.out.println(numConnectedComponents(graph));
    */

    // TODO: implement recursive/iterative Make
  }

  public static <T> void iterativeBFS(Node<T> node) {

    LinkedList<Node<T>> todo = new LinkedList<Node<T>>();
    HashSet<Node<T>> mark = new HashSet<Node<T>>();

    if (node != null) {

      todo.add(node);
      mark.add(node);
    }

    while (!todo.isEmpty()) {

      node = todo.remove();

      // examine node

      System.out.println(node);

      for (Node<T> n : node.adjacent)
        if (!mark.contains(n)) {

          todo.add(n);
          mark.add(n);
        }
    }
  }

  public static <T> void iterativeDFS(Node<T> node) {

    // "marking" a node means that we've encountered it for the first
    // time, not that we've explored its children, etc.

    Stack<Node<T>> todo = new Stack<Node<T>>();
    HashSet<Node<T>> mark = new HashSet<Node<T>>();

    if (node != null) {

      todo.push(node);
      mark.add(node);
    }

    while (!todo.empty()) {

      node = todo.pop();

      // examine node

      System.out.println(node);

      for (Node<T> n : node.adjacent)
        if (!mark.contains(n)) {

          todo.push(n);
          mark.add(n);
        }
    }
  }

  public static <T> void recursiveDFS(Node<T> node, HashSet<Node<T>> mark) {

    if (node == null)
      return;

    mark.add(node);

    // examine node

    System.out.println(node);

    for (Node<T> n : node.adjacent)
      if (!mark.contains(n))
        recursiveDFS(n, mark);
  }

  public static <T> boolean pathExists1(Node<T> src, Node<T> dst, HashSet<Node<T>> mark, ArrayList<Node<T>> path) {

    if (src == null)
      return false;

    mark.add(src);

    path.add(src);

    if (src == dst)
      return true;

    for (Node<T> n : src.adjacent)
      if (!mark.contains(n))
        if (pathExists1(n, dst, mark, path))
          return true;

    path.remove(src);

    return false;
  }

  public static <T> boolean pathExists2(Node<T> src, Node<T> dst, HashSet<Node<T>> mark, ArrayList<Node<T>> path) {

    if (src == null)
      return false;

    mark.add(src);

    boolean isPath = false;

    if (src == dst)
      isPath = true;
    else {
      for (Node<T> n : src.adjacent)
        if (!mark.contains(n)) {

          isPath = pathExists2(n, dst, mark, path);

          if (isPath)
            break; // don't need more than one path
        }
    }

    if (isPath)
      path.add(src);

    return isPath;
  }

  public static <T> int shortestPathLength(Node<T> src, Node<T> dst) {

    // uses BFS

    LinkedList<Node<T>> todo = new LinkedList<Node<T>>();
    HashMap<Node<T>, Integer> lengths = new HashMap<Node<T>, Integer>();

    if ((src == null) || (dst == null))
      return -1;

    todo.add(src);
    lengths.put(src, 0); // src is at distance 0 from src

    while (!todo.isEmpty()) {

      Node<T> node = todo.remove(); // grab a node from the queue

      int dist = lengths.get(node); // grab its distance from src

      if (node == dst)
        return dist; // if its the destination, great we're done

      // otherwise increment "distance from src" and examine its
      // children (who are all 1 step further from src)

      dist++;

      for (Node<T> n : node.adjacent)
        if (!lengths.containsKey(n)) {

          todo.add(n);
          lengths.put(n, dist);
        }
    }

    return -1;
  }

  public static <T> ArrayList<Node<T>> shortestPath(Node<T> src, Node<T> dst) {

    // uses BFS

    // if we had edge costs, then we'd need to keep track of path
    // "lengths" (i.e., costs) as above -- also rather than adding 1
    // to "dist" each time, we'd add the cost for that edge -- would
    // also need to create an Edge<T> object that had 2 fields
    // ("Node<T> dst" and "double cost") and we'd need to modify
    // Node<T> such that it kept a list of adjacent edges (rather than
    // nodes) -- then we'd just loop through these adjacent edges

    // Dijkstra: queue -> min-heap priority queue; when we add nodes
    // to the queue, we also have to add priorities = "lengths"

    LinkedList<Node<T>> todo = new LinkedList<Node<T>>();

    HashMap<Node<T>, ArrayList<Node<T>>> paths = new HashMap<Node<T>, ArrayList<Node<T>>>();

    if ((src == null) || (dst == null))
      return null;

    todo.add(src);
    paths.put(src, new ArrayList<Node<T>>()); // path from src to src is empty

    while (!todo.isEmpty()) {

      Node<T> node = todo.remove(); // grab a node from the queue

      ArrayList<Node<T>> path = paths.get(node); // get its path from src

      if (node == dst) {

        // if this is dst, add dst to path...

        path.add(node);

        // ... and return it

        return path;
      }

      for (Node<T> n : node.adjacent)
        if (!paths.containsKey(n)) {

          todo.add(n);

          // clone node's path from src to pass along

          @SuppressWarnings("unchecked")
          ArrayList<Node<T>> newPath = (ArrayList<Node<T>>) path.clone();
          newPath.add(node);
          paths.put(n, newPath);
        }
    }

    return null;
  }

  public static <T> int numConnectedComponents(ArrayList<Node<T>> graph) {

    HashSet<Node<T>> mark = new HashSet<Node<T>>();

    int num = 0;

    for (Node<T> node : graph)
      if (!mark.contains(node)) {

        recursiveDFS(node, mark);
        num++;
      }

    return num;
  }

  public static class Node<T> {

    T data;
    ArrayList<Node<T>> adjacent;

    public Node(T data, ArrayList<Node<T>> adjacent) {

      this.data = data;

      if (adjacent == null)
        this.adjacent = new ArrayList<Node<T>>();
      else
        this.adjacent = adjacent;
    }

    public void addAdjacent(Node<T> node) {

      adjacent.add(node);
    }

    public int getDegree() {

      return adjacent.size();
    }

    public String toString() {

      return data.toString();
    }
  }
}
