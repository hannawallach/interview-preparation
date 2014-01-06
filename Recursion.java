import java.util.LinkedList;
import java.util.ArrayList;

public class Recursion {

  public static void main(String[] args) {

    Recursion rec = new Recursion();

    for (LinkedList str : rec.generateStrings(4, new char[] { 'a', 'b' }))
      System.out.println(str);

    String word = "the";

    for (LinkedList str : rec.generatePerms(word))
      System.out.println(str);
  }

  public ArrayList<LinkedList<Character>> generateStrings(int length, char[] alphabet) {

    // generates all strings of specified length constructed using
    // characters from the specified alphabet

    // each string will be represented as a linked list

    ArrayList<LinkedList<Character>> strings = new ArrayList<LinkedList<Character>>();

    if (length == 1) {

      // base case: just create a string of length 1 for each
      // character in the alphabet and add it to our collection

      for (int i=0; i<alphabet.length; i++) {

        LinkedList<Character> str = new LinkedList<Character>();
        str.add(alphabet[i]);

        strings.add(str);
      }
    }
    else {

      // otherwise, we need to recurse and generate all strings of
      // length "length - 1" -- then for each one of those, create A
      // new strings of length "length" (where A is the size of the
      // alphabet) by appending each alphabet letter to the front of
      // the "length - 1" string...

      for (LinkedList<Character> str : generateStrings(length - 1, alphabet))
        for (int i=0; i<alphabet.length; i++) {

          LinkedList<Character> newStr = new LinkedList<Character>(str);
          newStr.add(alphabet[i]);

          strings.add(newStr);
        }
    }

    return strings;
  }

  public ArrayList<LinkedList<Character>> generatePerms(String word) {

    // returns all permutations of specified word

    ArrayList<LinkedList<Character>> strings = new ArrayList<LinkedList<Character>>();

    if (word.length() == 1) {

      LinkedList<Character> str = new LinkedList<Character>();
      str.add(word.charAt(0));

      strings.add(str);
    }
    else {

      for (int i=0; i<word.length(); i++) {

        // run through each character

        StringBuffer buf = new StringBuffer(word);
        buf.deleteCharAt(i);

        // create a new word with that character deleted, and generate
        // all permutations of that word...

        for (LinkedList<Character> str : generatePerms(buf.toString())) {

          // for each of those permutations, add back in the character

          LinkedList<Character> newStr = new LinkedList<Character>(str);
          newStr.add(word.charAt(i));

          strings.add(newStr);
        }
      }
    }

    return strings;
  }
}
