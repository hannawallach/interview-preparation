import java.util.ArrayList;
import java.util.HashMap;

public class BinarySearchTrees {

  public static void main(String[] args) {

    Node<String> root = new Node<String>(String.valueOf(2), null);

    System.out.println(root + " " + root.getHeight() + " " + root.isBalanced(new HashMap<Node<String>, Integer>()));

    //   2

    for (int i=0; i<4; i++)
      System.out.println(i + " " + root.insert(String.valueOf(i)));

    System.out.println(root + " " + root.getHeight() + " " + root.isBalanced(new HashMap<Node<String>, Integer>()));

    //   2
    //  / \
    // 0   3
    //  \
    //   1

    System.out.println("Valid: " + root.isValid());

    root.insert(String.valueOf(0.5));
    root.insert(String.valueOf(1.5));

    System.out.println(root + " " + root.getHeight() + " " + root.isBalanced(new HashMap<Node<String>, Integer>()));

    //    2
    //   / \
    //  0   3
    //   \
    //    1
    //   / \
    // 0.5 1.5

    Node<String> node = root.find(String.valueOf(0));

    if (node != null)
      if (node == root)
        root = node.delete();
      else
        node.delete();

    System.out.println(root.find(String.valueOf(0)));

    System.out.println(root + " " + root.getHeight() + " " + root.isBalanced(new HashMap<Node<String>, Integer>()));

    //      2
    //     / \
    //    1   3
    //   / \
    // 0.5 1.5

    System.out.println("Valid: " + root.isValid());

    node = root.find(String.valueOf(1));

    if (node != null)
      if (node == root)
        root = node.delete();
      else
        node.delete();

    System.out.println(root.find(String.valueOf(1)));

    System.out.println(root + " " + root.getHeight() + " " + root.isBalanced(new HashMap<Node<String>, Integer>()));

    //    2
    //   / \
    // 0.5  3
    //   \
    //   1.5

    node = root.find(String.valueOf(0.5));

    if (node != null)
      if (node == root)
        root = node.delete();
      else
        node.delete();

    System.out.println(root + " " + root.getHeight() + " " + root.isBalanced(new HashMap<Node<String>, Integer>()));

    //    2
    //   / \
    // 1.5  3

    node = root.find(String.valueOf(2));

    if (node != null)
      if (node == root)
        root = node.delete();
      else
        node.delete();

    System.out.println(root + " " + root.getHeight() + " " + root.isBalanced(new HashMap<Node<String>, Integer>()));

    // 1.5
    //   \
    //    3

    node = root.find(String.valueOf(1.5));

    if (node != null)
      if (node == root)
        root = node.delete();
      else
        node.delete();

    System.out.println(root + " " + root.getHeight() + " " + root.isBalanced(new HashMap<Node<String>, Integer>()));

    // 3

    node = root.find(String.valueOf(3));

    if (node != null)
      if (node == root)
        root = node.delete();
      else
        node.delete();

    System.out.println(root);

    // null

    root = new Node<String>(String.valueOf(2), null);

    System.out.println(root + " " + root.getHeight() + " " + root.isBalanced(new HashMap<Node<String>, Integer>()));

    //   2

    for (int i=0; i<4; i++)
      System.out.println(i + " " + root.insert(String.valueOf(i)));

    node = root.find(String.valueOf(3));

    node.data = String.valueOf(30);

    System.out.println(root + " " + root.getHeight() + " " + root.isBalanced(new HashMap<Node<String>, Integer>()));

    //   1
    //  / \
    // 0  30

    System.out.println("Valid: " + root.isValid());

    node = root.find(String.valueOf(0));

    node.data = String.valueOf(30);

    System.out.println(root + " " + root.getHeight() + " " + root.isBalanced(new HashMap<Node<String>, Integer>()));

    //   1
    //  / \
    // 30 30

    System.out.println("Valid: " + root.isValid());
  }

  public static class Node<T extends Comparable<T>> {

    // each node has a pointer to its parent, left child & right child

    // every node in the subtree rooted at this node's left child will
    // have a smaller value, while every node in the the subtree
    // rooted at its right child will have a larger value

    T data;
    Node<T> parent, leftChild, rightChild;

    public Node(T data, Node<T> parent) {

      this.data = data;
      this.parent = parent;
    }

    public boolean insert(T data) {

      // to insert a value into the (sub)tree rooted at this node, we
      // compare the value to be inserted with this node's value

      int comp = data.compareTo(this.data);

      if (comp == 0)
        return false; // can't have duplicates
      else if (comp < 0) {

        // if the value to be inserted is less than this node's value,
        // then we have two options: 1) if this node has a left child,
        // recursively insert the value into the subtree rooted at
        // that node, 2) if this node does not have a left child,
        // create a new node and update the current node's left child
        // pointer to point to the new node

        if (leftChild != null)
          return leftChild.insert(data); // insert into left subtree
        else
          leftChild = new Node<T>(data, this);
      }
      else {

        // if the value to be inserted is greater than this node's
        // value then we have two options...

        if (rightChild != null)
          return rightChild.insert(data); // insert into right subtree
        else
          rightChild = new Node<T>(data, this);
      }

      return true;
    }

    public Node<T> find(T data) {

      // to find a value in the subtree rooted at this node, compare
      // the value to this node's value, if they're equal, great

      int comp = data.compareTo(this.data);

      if (comp == 0)
        return this;
      else if (comp < 0) {

        // if the value is less than this node's value, recurse on
        // this node's left child or -- if there is no left child --
        // return null indicating that the value is not present

        if (leftChild != null)
          return leftChild.find(data);
        else
          return null;
      }
      else
        if (rightChild != null)
          return rightChild.find(data);
        else
          return null;
    }

    public Node<T> delete() {

      // if this node has a left child and a right child, replace this
      // node's data with its predecessor or successor's data and
      // delete its predecessor or successor

      if ((leftChild != null) && (rightChild != null)) {

        Node<T> node = getPredecessor(); // get predecessor

        data = node.data; // set this node's data to its predecessor's data
        node.delete(); // delete predecessor

        return this; // TODO: is this right?
      }

      Node<T> node = null;

      // if this node only has a left or right child, replace the node
      // with that child, by updating the child's parent pointer to
      // bypass this node (i.e., point to this node's parent) and this
      // node's parent pointer to point to the child

      if (leftChild != null)
        node = leftChild;
      else if (rightChild != null) {
        node = rightChild;
      }

      // update child' parent pointer

      if (node != null)
        node.parent = parent;

      // update parent's left or right child pointer, as appropriate
      // -- if this node has no children, "node" will be null, so
      // we're effectively just deleting this node by updating its
      // parent's left/right child pointer

      if (parent != null)
        if (parent.leftChild == this)
          parent.leftChild = node;
        else
          parent.rightChild = node;

      return node;
    }

    private Node<T> getPredecessor() {

      // returns right-most child of left subtree

      // TODO: this code is technically wrong, but because it's only
      // ever called AFTER we've checked that both leftChild and
      // rightChild are not null, it will behave okay

      if (leftChild != null) {

        Node<T> node = leftChild;

        while (node.rightChild != null)
          node = node.rightChild;

        return node;
      }
      else {

        return null;

        //    5
        //   / \
        //  3   7
        // / \ / \
        // 2 4 6 8

        // what's 4's in-order predecessor?
      }
    }

    public boolean isValid() {

      // returns true if subtree rooted at this node is valid

      boolean valid = true;

      if (leftChild != null)
        if (leftChild.data.compareTo(data) > 0)
          return false;
        else
          valid &= leftChild.isValid();

      if (valid)
        if (rightChild != null)
          if (rightChild.data.compareTo(data) < 0)
            return false;
          else
            valid &= rightChild.isValid();

      return valid;
    }

    public boolean isBalanced(HashMap<Node<T>, Integer> cache) {

      if ((leftChild == null) && (rightChild == null))
        return true;

      int lch = 0;

      if (leftChild != null)
        if (cache.containsKey(leftChild))
          lch = cache.get(leftChild);
        else {
          lch = leftChild.getHeight();
          cache.put(leftChild, lch);
        }

      int rch = 0;

      if (rightChild != null)
        if (cache.containsKey(rightChild))
          rch = cache.get(rightChild);
        else {
          rch = rightChild.getHeight();
          cache.put(rightChild, rch);
        }

      int diff = Math.abs(lch - rch);

      if (diff > 1)
        return false;
      else {

        boolean isBalanced = true;

        if (leftChild != null)
          isBalanced &= leftChild.isBalanced(cache);

        if (rightChild != null)
          isBalanced &= rightChild.isBalanced(cache);

        return isBalanced;
      }
    }

    public int getHeight() {

      if ((leftChild == null) && (rightChild == null))
        return 1;

      if (leftChild == null)
        return rightChild.getHeight() + 1;

      if (rightChild == null)
        return leftChild.getHeight() + 1;

      return Math.max(leftChild.getHeight(), rightChild.getHeight()) + 1;
    }

    public String toString() {

      StringBuffer buf = new StringBuffer();

      buf.append("[");

      if (leftChild != null)
        buf.append(leftChild.toString() + ", ");

      buf.append(data);

      if (rightChild != null)
        buf.append(", " + rightChild.toString());

      buf.append("]");

      return buf.toString();
    }
  }
}
