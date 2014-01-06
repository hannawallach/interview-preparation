import java.util.Iterator;
import java.util.LinkedList;

public class HashMaps {

  public static void main(String[] args) {

    MyHashMap<Integer, String> map = new MyHashMap<Integer, String>();

    map.put(1, "one");
    map.put(2, "two");

    System.out.println(map);

    map.put(1, "five");

    System.out.println(map);

    System.out.println(map.get(1) + " " + map.get(2));

    map.remove(1);

    System.out.println(map);

    map.remove(2);

    System.out.println(map);

    //

    int size = 1000;

    Object key = "foobar";

    // the following gives an integer between -(2^31) and 2^31 - 1
    // (ints are 32 bits long and stored in two's complement)y

    System.out.println(key.hashCode());

    // the following gives an integer between -(size - 1) and (size -
    // 1) rather than an integer between 0 and (size - 1)

    System.out.println(key.hashCode() % size);

    // Math.abs(-N) returns +N, so the following gives an integer
    // between 0 and (size - 1) as desired

    System.out.println(Math.abs(key.hashCode()) % size);

    // But... there's a weird bug, in that Math.abs(Integer.MIN_VALUE)
    // returns Integer.MIN_VALUE, which is a -ve number!

    System.out.println(Math.abs(Integer.MIN_VALUE) % size);

    // And, really, we don't care whether -N gets turned into +N we
    // just need -N to be turned into something positive. Integers are
    // stored in two's complement -- so the MSB is the "sign" bit --
    // if we just "and" the hashCode() with 0111 1111 1111 1111 1111
    // 1111 1111 1111 then we'll flip the sign bit to zero and leave
    // everything else unchanged, which gives us a positive number

    // 0x7FFFFFFF is 0111 1111 1111 1111 1111 1111 1111 1111 -- i.e.,
    // all 1s except the sign bit

    System.out.println((key.hashCode() & 0x7fffffff) % size);
  }

  public static class MyHashMap<K, V> {

    private LinkedList<Entry<K, V>>[] array;

    @SuppressWarnings("unchecked")
    public MyHashMap(int maxSize) {

      // can't do "this.array = new T[maxSize]" because of "generic
      // array creation" compilation errors; code below generates an
      // "unchecked cast" warning, hence the suppression

      this.array = (LinkedList<Entry<K, V>>[]) new LinkedList[maxSize];
    }

    public MyHashMap() {

      this(1000);
    }

    private int index(K key) {

      return (key.hashCode() & 0x7fffffff) % array.length;
    }

    public boolean put(K key, V val) {

      if ((key == null) || (val == null))
        return false;

      int i = index(key);

      if (array[i] != null) {

        Entry<K, V> entry = null;

        for (Entry<K, V> e : array[i])
          if (e.key.equals(key))
            entry = e;

        if (entry != null)
          entry.val = val;
        else
          array[i].addFirst(new Entry<K, V>(key, val));
      }
      else {

        // if there's nothing at this index, create a new linked list
        // and add a new entry (key, val) to the linked list

        array[i] = new LinkedList<Entry<K, V>>();
        array[i].addFirst(new Entry<K, V>(key, val));
      }

      return true;
    }

    public boolean remove(K key) {

      if (key == null)
        return false;

      int i = index(key);

      if (array[i] != null) {

        Entry<K, V> entry = null;

        for (Entry<K, V> e : array[i])
          if (e.key.equals(key))
            entry = e;

        if (entry != null) {

          array[i].remove(entry);

          if (array[i].isEmpty())
            array[i] = null;
        }
      }

      return true;
    }

    public V get(K key) {

      if (key == null)
        return null;

      int i = index(key);

      if (array[i] != null)
        for (Entry<K, V> e : array[i])
          if (e.key.equals(key))
            return e.val;

      return null;
    }

    public String toString() {

      StringBuffer buf = new StringBuffer("[");

      for (int i=0; i<array.length; i++) {
        if (array[i] != null) {
          for (Entry<K, V> e : array[i]) {

            if (!buf.toString().equals("["))
              buf.append(", ");

            buf.append(e);
          }
        }
      }

      buf.append("]");

      return buf.toString();
    }

    public class Entry<K, V> {

      K key;
      V val;

      public Entry(K key, V val) {

        this.key = key;
        this.val = val;
      }

      public String toString() {

        return key.toString() + ":" + val.toString();
      }
    }
  }
}
