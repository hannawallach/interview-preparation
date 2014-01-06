import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Strings {

  public boolean containsUniqueCharsBooleanArray(String str) {

    if (str.length() > 256)
      return false;

    boolean[] seen = new boolean[256];
    //Arrays.fill(seen, false);

    for (char c : str.toCharArray()) {

      assert c < 256;

      if (seen[c])
        return false;
      else
        seen[c] = true;
    }

    return true;
  }

  public boolean containsUniqueCharsInt(String str) {

    if (str.length() > 26)
      return false;

    int checker = 0;

    for (char c : str.toCharArray()) {

    // binary representation of integer 1 is 0 ... 0 1 -- check using
    // System.out.println(Integer.toBinaryString(1));

      int mask = 1 << c - 'a';

      if ((checker & mask) > 0)
        return false;
      else
        checker |= mask;
    }

    return true;
  }

  public boolean containsUniqueCharsHashMap(String str) {

    HashMap<Character, Boolean> map = new HashMap<Character, Boolean>();

    for (char c : str.toCharArray()) {
      if (map.containsKey(c))
        return false;
      else
        map.put(c, true);
    }

    return true;
  }

  public boolean containsUniqueCharsHashSet(String str) {

    HashSet<Character> set = new HashSet<Character>();

    for (char c : str.toCharArray()) {
      if (set.contains(c))
        return false;
      else
        set.add(c);
    }

    return true;
  }

  public boolean isPermutationIntArray(String str1, String str2) {

    if (str1.length() != str2.length())
      return false;

    int[] counts = new int[256];

    for (char c : str1.toCharArray()) {

      assert c < 256;
      counts[c]++;
    }

    for (char c : str2.toCharArray()) {

      assert c < 256;
      counts[c]--;
    }

    // loop below can be rolled into loop above (see below)

    for (int count : counts)
      if (count != 0)
        return false;

    return true;
  }

  public boolean isPermutationIntArrayBetter(String str1, String str2) {

    if (str1.length() != str2.length())
      return false;

    int[] counts = new int[256];

    for (char c : str1.toCharArray()) {

      assert c < 256;
      counts[c]++;
    }

    for (char c : str2.toCharArray()) {

      assert c < 256;

      if (--counts[c] < 0)
        return false;
    }

    return true;
  }

  public boolean isPermutationHashMap(String str1, String str2) {

    if (str1.length() != str2.length())
      return false;

    HashMap<Character, Integer> map = new HashMap<Character, Integer>();

    for (char c : str1.toCharArray())
      if (map.containsKey(c))
        map.put(c, map.get(c) + 1);
      else
        map.put(c, 1);

    for (char c : str2.toCharArray())
      if (map.containsKey(c))
        map.put(c, map.get(c) - 1);
      else
        return false;

    // loop below can be rolled into loop above (see above)

    for (int count : map.values())
      if (count != 0)
        return false;

    return true;
  }

  public String replaceSpacesCharArray(String str, int trueLength) {

    if (str != null) {

      int first = trueLength - 1;
      int second = str.length() - 1;

      if (second < 0)
        return str;

      char[] chars = str.toCharArray();

      while (second > first) {

        char c = chars[first--];

        if (c != ' ')
          chars[second--] = c;
        else {
          chars[second--] = '0';
          chars[second--] = '2';
          chars[second--] = '%';
        }
      }

      return String.copyValueOf(chars);
    }

    return str;
  }

  public String replaceSpacesCharArray(String str) {

    if (str != null) {

      int first = str.length() - 1;

      if (first < 0)
        return str;

      int numSpaces = 0;

      for (int i=0; i<=first; i++)
        if (str.charAt(i) == ' ')
          numSpaces++;

      int second = first + numSpaces * 2;

      char[] chars = new char[second + 1];

      // chars is empty: have to copy over all characters, so have to
      // loop over all positions in string

      while (first >= 0) {

        char c = str.charAt(first--);

        if (c != ' ')
          chars[second--] = c;
        else {
          chars[second--] = '0';
          chars[second--] = '2';
          chars[second--] = '%';
        }
      }

      return String.copyValueOf(chars);
    }

    return str;
  }

  public String replaceSpacesStringBuffer(String str, int trueLength) {

    if (str != null) {

      int first = trueLength - 1;
      int second = str.length() - 1;

      if (second < 0)
        return str;

      StringBuffer buf = new StringBuffer(str);

      while (second > first) {

        char c = buf.charAt(first--);

        if (c != ' ')
          buf.setCharAt(second--, c);
        else {
          buf.replace(second - 2, second + 1,  "%20");
          second -= 3;
        }
      }

      return buf.toString();
    }

    return str;
  }

  public String replaceSpacesStringBuffer(String str) {

    if (str != null) {

      int first = str.length() - 1;

      if (first < 0)
        return str;

      int numSpaces = 0;

      for (int i=0; i<=first; i++)
        if (str.charAt(i) == ' ')
          numSpaces++;

      int second = first + numSpaces * 2;

      StringBuffer buf = new StringBuffer(str);
      buf.setLength(second + 1);

      while (second > first) {

        char c = buf.charAt(first--);

        if (c != ' ')
          buf.setCharAt(second--, c);
        else {
          buf.replace(second - 2, second + 1,  "%20");
          second -= 3;
        }
      }

      return buf.toString();
    }

    return str;
  }

  public boolean isRotation(String str1, String str2) {

    if (str1.length() != str2.length())
      return false;

    StringBuffer buf = new StringBuffer(str1);

    buf.append(str1);

    if (buf.indexOf(str2) < 0)
      return false;

    return true;
  }

  public void stringBufferReplaceTest() {

    // StringBuffer's replace() removes the characters at the
    // positions indicated by the indices "start" (incl.) and "end"
    // (excl.) and then inserts the new string starting at the "start"
    // index -- buffer length may change

    StringBuffer buf = new StringBuffer("the cat");
    System.out.println(buf.length()); // 7

    buf.replace(4, 7, "doog");
    System.out.println(buf.length()); // 8

    buf.replace(4, buf.length(), "dg");
    System.out.println(buf.length()); // 6
  }

  public void myStringBufferTest() {

    MyStringBuffer buf = new MyStringBuffer();
    System.out.println(buf.length());

    try {
      System.out.println(buf.charAt(0));
    }
    catch (IndexOutOfBoundsException e) {
      System.out.println(e.getMessage());
    }

    buf.append("foo");

    for (int i=0; i<buf.length(); i++)
      System.out.print(buf.charAt(i));

    System.out.println();
    System.out.println(buf.toString());

    buf.append("bar");

    for (int i=0; i<buf.length(); i++)
      System.out.print(buf.charAt(i));

    System.out.println();
    System.out.println(buf.toString());
  }

  public int[][] zeroRowsAndColumns(int[][] matrix) {

    // assumption: matrix[i].length == matrix[j].length for all i, j

    int M = matrix.length;
    int N = matrix[0].length;

    boolean[] rows = new boolean[M];
    boolean[] cols = new boolean[N];

    for (int m=0; m<M; m++)
      for (int n=0; n<N; n++)
        if (matrix[m][n] == 0) {
          rows[m] = true;
          cols[n] = true;
        }

    for (int m=0; m<M; m++)
      if (rows[m])
        Arrays.fill(matrix[m], 0);
      else {
        for (int n=0; n<N; n++)
          if (cols[n])
            matrix[m][n] = 0;
      }

    return matrix;
  }

  public String reverseStringBuffer(String str) {

    int first = 0;
    int second = str.length() - 1;

    StringBuffer buf = new StringBuffer(str);

    while (second > first) {

      char tmp = buf.charAt(first);

      buf.setCharAt(first++, buf.charAt(second));
      buf.setCharAt(second--, tmp);
    }

    return buf.toString();
  }


  public String compress(String str) {

    if ((str == null) || (str.length() == 0))
      return str;

    int newLength = 0;

    char currentChar = str.charAt(0);
    int currentRunLength = 1;

    for (int i=1; i<str.length(); i++) {

      char c = str.charAt(i);

      if (c == currentChar)
        currentRunLength++;
      else {

        newLength += String.valueOf(currentRunLength).length() + 1;

        currentChar = c;
        currentRunLength = 1;
      }
    }

    newLength += String.valueOf(currentRunLength).length() + 1;

    if (newLength >= str.length())
      return str;

    StringBuffer buf = new StringBuffer();

    currentChar = str.charAt(0);
    currentRunLength = 1;

    for (int i=0; i<str.length(); i++) {

      char c = str.charAt(i);

      if (c == currentChar)
        currentRunLength++;
      else {

        buf.append(currentChar);
        buf.append(currentRunLength);

        currentChar = c;
        currentRunLength = 1;
      }
    }

    buf.append(currentChar);
    buf.append(currentRunLength);

    return buf.toString();
  }

  public void findPairsIndicesHashMap(int k, int[] list) {

    // output indices of each pair that sums to k, such that first
    // index is smaller than the second -- e.g., if k is 5 and list is
    // 2, 1, 3, 5, 1, 4, 2, output is (0, 2), (1, 5), (2, 6), (4, 5)

    // create HashMap that maps values to indices; run through list
    // twice -- first time add each list[i] -> i pair to the map,
    // second time look up key k - list[i] to see if it exists; time
    // complexity is O(N); uses O(N) additional space

    HashMap<Integer, LinkedList<Integer>> map = new HashMap<Integer, LinkedList<Integer>>();

    for (int i=0; i<list.length; i++) {
      if (map.containsKey(list[i]))
        map.get(list[i]).add(i);
      else {

        LinkedList<Integer> tmp = new LinkedList<Integer>();
        tmp.add(i);

        map.put(list[i], tmp);
      }
    }

    for (int i=0; i<list.length; i++)
      if (map.containsKey(k - list[i]))
        for (int j : map.get(k - list[i]))
          if (j > i)
            System.out.println("(" + i + ", " + j + ")");
  }

  public void findPairsIndicesSearch(int k, int[] list) {

    // output indices of each pair that sums to k, such that first
    // index is smaller than the second -- e.g., if k is 5 and list is
    // 2, 1, 3, 5, 1, 4, 2, output is (0, 2), (1, 5), (2, 6), (4, 5)

    // for the ith item, examine items ahead of it to see if any are
    // equal to k - list[i]; time complexity is (N-1) + (N-2) + ... +
    // 1 = (N-1) * N / 2 = O(N^2); uses O(1) additional space

    for (int i=0; i<list.length-1; i++)
      for (int j=(i+1); j<list.length; j++)
        if (list[j] == k - list[i])
          System.out.println("(" + i + ", " + j + ")");
  }

  public void findPairsValuesSearch(int k, int[] list) {

    // output values of each pair that sums to k -- e.g., if k is 5
    // and list is 2, 1, 3, 5, 1, 4, 2, output is (1, 4), (1, 4), (2,
    // 3), (2, 3) where first (1, 4) corresponds to first 1, second
    // corresponds to second 1; first (2, 3) corresponds to first 2;
    // second (2, 3) corresponds to second 2

    // sort list in place -> O(N log N) -- run through list, for yith
    // item, examine items ahead of it, but can stop as soon as
    // list[j] > k - list[i]; time complexity is still O(N^2) but
    // constant is better; uses O(1) additional space if sort in place

    Arrays.sort(list);

    for (int i=0; i<list.length-1; i++)
      for (int j=(i+1); j<list.length; j++)
        if (list[j] == k - list[i])
          System.out.println("(" + list[i] + ", " + list[j] + ")");
        else if (list[j] > k - list[i])
          break;

    // sort list in place -> O(N log N) -- run through list, for the
    // ith item, use binary search to try to find k - list[i]; time
    // complexity of each search is O(log N) and we do N of them =>
    // O(N log N); uses O(1) additional space if we sort in place

    // generalize to binary tree: traverse tree in order, for each
    // node, search the tree for k - value of node -> O(N log N)

    // if we want nodes that sum to <= k, then rather than searching
    // the tree for k - value of current node, traverse tree in order
    // and output anything <= k - value of node -- can also flatten
    // tree to array containing output of in-order traversal, then for
    // each item in array, run through array again outputting
    // everything until hit a value greater than k - the item's value
  }

  public void findPairsValuesStep(int k, int[] list) {

    // output values of each pair that sums to k -- can't use any list
    // item more than once -- e.g., if k is 5 and list is 2, 1, 3, 5,
    // 1, 4, 2, output is just (1, 4), (2, 3)

    // sort in place -> O(N log N), examine items O(N) time complexity
    // is O(N log N); uses O(1) additional space if sort in place

    int start = 0;
    int end = list.length - 1;

    Arrays.sort(list);

    while (start < end) {

      int sum = list[start] + list[end];

      if (sum == k)
        System.out.println("(" + list[start++] + ", " + list[end--] + ")");
      else if (sum < k)
        start++;
      else
        end--;
    }
  }

  public boolean contains(String str1, String str2) {

    // returns true if str1 (e.g., asasat) contains str2 (e.g., asat)

    if (str2.length() > str1.length())
      return false;

    int j;

    for (int i=0; i<str1.length(); i++) {
      for (j=0; j<str2.length(); j++)
        if (str1.charAt(i + j) != str2.charAt(j))
          break;

      if (j == str2.length())
        return true;
    }

    return false;
  }

  public static void main(String[] args) {

    Strings st = new Strings();

    //System.out.println(st.containsUniqueCharsBooleanArray("tee"));
    //System.out.println(st.containsUniqueCharsInt("the"));

    //System.out.println(st.isPermutationHashMap("teh", "the"));

    System.out.println(st.replaceSpacesStringBuffer("the cat"));

    System.out.println(st.isRotation("the", "het"));

    st.myStringBufferTest();

    int[][] matrix = new int[][] { { 1, 0, 1 }, { 1, 1, 1 } };
    matrix = st.zeroRowsAndColumns(matrix);

    for (int m=0; m<matrix.length; m++) {

      for (int n=0; n<matrix[m].length; n++)
        System.out.print(matrix[m][n] + " ");

      System.out.println();
    }

    System.out.println(st.compress("aabccccccccccaaa"));

    System.out.println(st.reverseStringBuffer("food"));

    st.findPairsIndicesHashMap(5, new int[] { 2, 1, 3, 5, 1, 4, 2 });
    st.findPairsIndicesSearch(5, new int[] { 2, 1, 3, 5, 1, 4, 2 });
    st.findPairsValuesSearch(5, new int[] { 2, 1, 3, 5, 1, 4, 2 });
    st.findPairsValuesStep(5, new int[] { 2, 1, 3, 5, 1, 4, 2 });
  }

  public class MyStringBuffer {

    int capacity;

    String[] data;
    int[] lengths;

    int num;

    public MyStringBuffer() {

      capacity = 1000; // default capacity

      data = new String[capacity];
      lengths = new int[capacity];

      num = 0;
    }

    public void append(String str) {

      if (str != null) {

        if (num == capacity) {

          capacity *= 2;

          data = Arrays.copyOf(data, capacity);
          lengths = Arrays.copyOf(lengths, capacity);
        }

        data[num] = str;
        lengths[num] = str.length();

        if (num > 0)
          lengths[num] += lengths[num - 1];

        num++;
      }
    }

    public int length() {

      if (num == 0)
        return 0;

      return lengths[num - 1];
    }

    public char charAt(int i) throws IndexOutOfBoundsException {

      if ((i < 0) || (i >= length()))
        throw new IndexOutOfBoundsException("Index " + i + " out of bounds");

      int idx = 0;

      while (i >= lengths[idx])
        idx++;

      if (idx > 0)
        i -= lengths[idx - 1];

      return data[idx].charAt(i);
    }

    public String toString() {

      if (num == 0)
        return "";

      char[] chars = new char[lengths[num - 1]];

      int i = 0;

      for (int idx=0; idx<num; idx++)
        for (char c : data[idx].toCharArray())
          chars[i++] = c;

      /*
      for (int i=0; i<lengths[num - 1]; i++)
        chars[i] = charAt(i);
      */

      return String.valueOf(chars);
    }
  }
}
