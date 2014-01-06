import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class SortingAlgorithms {

  public void bubbleSort(int[] list) {

    // comparison sort: only uses comparisons to operate items

    // best case time complexity is O(N) because in that case the list
    // is sorted and we only make 1 pass; worst case list is sorted in
    // reverse order and time complexity is O(N^2); average case time
    // complexity is also O(N^2); uses O(1) additional space

    // pros: can easily detect when a list is sorted -> O(N) but this
    // is true of insertion sort too, which performs much better in
    // the case where a list is mostly sorted

    // cons: bad worst and averge time complexity esp. for large lists

    // builds sorted list from end to start -- i.e., largest item is
    // "bubbled" to end on first pass, second largest on second pass,
    // etc. can optimize bubble sort as follows: on the ith pass, we
    // find the ith largest item and put it in its final place (i.e.,
    // on the first pass, we find the largest item and put it in its
    // final place; on the second pass, we find the second largest
    // item, etc.) -- this means that on the ith pass, i-1 items are
    // already in their final places, so we don't have to examine them

    int numToExamine = list.length;

    // every pass all items after the last swap are already sorted,
    // don't need to examine those

    while (true) {

      boolean sorted = true;

      for (int i=1; i<numToExamine; i++)
        if (list[i] < list[i-1]) {

          // if current item is less than one before it, swap them and
          // indicate that we made a swap

          int tmp = list[i];

          list[i] = list[i-1];
          list[i-1] = tmp;

          sorted = false;
        }

      // list is sorted when we complete a pass with no swaps

      if (sorted)
        break;

      numToExamine--;
    }

    return;
  }

  public void iterativeMergeSort(int[] list) {

  }

  public void recursiveMergeSort(int[] list) {

    // worst, best, and average time complexity: T(N) = N + 2 * T(N/2)
    // but T(N/2) = N/2 + 2 * T(N/4) and T(N/4) = N/4 + 2 * T(N/8),
    // etc. so T(N) = N + N + N + 8 * T(N/8) = ... = O(N log(N))

    // space complexity is O(log(N)) + O(N) = O(N)

    recursiveMergeSort(list, 0, list.length);
  }

  private void recursiveMergeSort(int[] list, int start, int end) {

    // uses O(1) additional space, but called log(N) times

    if (end - start <=1) // list of length 0 or 1 is already sorted
      return;

    int mid = (end - start) / 2 + start; // cast is equiv. to floor here

    recursiveMergeSort(list, start, mid);
    recursiveMergeSort(list, mid, end);

    merge(list, start, mid, end);
  }

  private void merge(int[] list, int start, int mid, int end) {

    // merge in place is non-trivial

    // time complexity is O(N); uses O(N) additional space

    ArrayList<Integer> sorted = new ArrayList<Integer>();

    int start1 = start, end1 = mid, start2 = mid, end2 = end;

    while (true) {

      if ((start1 < end1) && (start2 < end2)) {

        // both lists have items remaining

        if (list[start1] <= list[start2])
          sorted.add(list[start1++]);
        else
          sorted.add(list[start2++]);

        continue;
      }

      if (start1 < end1) {

        // only list 1 has items remaining

        while (start1 < end1)
          sorted.add(list[start1++]);

        continue;
      }

      if (start2 < end2) {

        // only list 2 has items remaining

        while (start2 < end2)
          sorted.add(list[start2++]);

        continue;
      }

      break; // both lists are empty
    }

    for (int i=start; i<end; i++)
      list[i] = sorted.get(i - start);
  }

  public void quicksort(int[] list) {

    quicksort(list, 0, list.length, new Random());
  }

  private void quicksort(int[] list, int start, int end, Random rng) {

    // best, average time complexity: we compare each item to the
    // pivot and then divide the list in two and recurse -> T(N) = N +
    // 2 * T(N/2) but T(N/2) = N/2 + 2 * T(N/4) = N + N + ... + N --
    // we're diving in two each time, so number of divisions is height
    // of binary tree w/ N items = log(N) -> O(N log(N)); worst case
    // time complexity: pivot is min or max each time -> O(N^2)

    if (end - start <= 1)
      return;

    int pivot = rng.nextInt(end - start) + start; // randomly choose pivot
    int pivotValue = list[pivot]; // get its value

    list[pivot] = list[end-1]; // switch last item w/ pivot
    list[end-1] = pivotValue;

    // position immediately AFTER sorted list -- initially sorted list
    // is empty, so position is at the start

    int idx = start;

    for (int i=start; i<end-1; i++) // run through unsorted items
      if ((list[i] < pivotValue) && (idx != i)) {

        // if value of current item is less than pivot, switch item
        // with item immediately after sorted list, and update
        // position immediately after sorted list to reflect this

        int tmp = list[i];
        list[i] = list[idx];
        list[idx++] = tmp;
      }

    list[end-1] = list[idx]; // put pivot back in place
    list[idx] = pivotValue;

    // end index (exclusive) for "less than" list -- equal to start
    // index + number of items less than pivot

    quicksort(list, start, idx, rng);
    quicksort(list, idx + 1, end, rng);
  }

  public void insertionSort(int[] list) {

    // in-place comparison sort; sorted sublist built left-to-right

    // best case time complexity is O(N) because in that case the list
    // is sorted and we only compare each item to the largest value in
    // the sorted list; worst case list is sorted in reverse order and
    // time complexity is O(N^2); average case is also O(N^2)

    for (int i=1; i<list.length; i++) { // run through positions 1 onwards

      int value = list[i]; // get value of item at that point

      int j = i-1; // get index of item before it

      while ((j>=0) && (list[j] > value)) {

        // if we're not at the start of the list and if the item
        // before it is larger than the current item, march back down
        // the list sliding items along to the right to make space for
        // the current (smaller) item

        list[j+1] = list[j];
        j--;
      }

      // when we reach the start of the list (j = -1) or an item that
      // is less than the current one, insert current item ahead of it

      list[j+1] = value;
    }
  }

  public void selectionSort(int[] list) {

    // in-place comparison sort; sorted sublist built left-to-right;
    // initially empty, so first unsorted item is at index i=0; find
    // smallest unsorted item (search from first unsorted item to end
    // of list), exchange with first unsorted item, increment i

    // generally performs worse than insertion sort

    // worst, best, and average time complexities are O(N^2); uses
    // O(1) additional space (because it's in-place)

    for (int i=0; i<list.length-1; i++) {

      int minIdx = i;
      int minVal = list[i];

      for (int j=i+1; j<list.length; j++)
        if (list[j] < minVal) {
          minIdx = j;
          minVal = list[j];
        }

      list[minIdx] = list[i];
      list[i] = minVal;
    }
  }

  public void radixSortMSD(int[] list) {

    // e.g., 0 1 5 112 111 10 45 -> 0 1 10 111 112 45 5

    // treat items as if they're left-justified and padded with
    // blanks, e.g., 0 1 5 112 111 10 45 -> 0.. 1.. 5.. 112 111
    // 10. 45. (k = 3); sort items based on MSD, grouping into buckets
    // -> [0..]  [1.. 112 111 10.] [45.] [5..]; recursively sort each
    // bucket based on 2nd MSD -> [0..] [[1..] [10.] [112 111]] [45.]
    // [5..]; recursively sort each of these buckets based on 3rd MSD
    // -> [0..]  [[1..] [10.] [[111] [112]]] [45.]  [5..]; concatenate
    // all buckets back together in order

  }

  public void radixSortLSD(int[] list) {

    // e.g. 0 1 5 112 111 10 45 ->

    // treat items as if they're right-justified and zero- (or blank-)
    // padded, e.g., 0 1 5 112 111 10 45 -> 000 001 112 111 010 045 (k
    // = 3); sort items by LSD, grouping into buckets -> [000 010]
    // [001 111] [112] [005 045]; recursively sort each bucket based
    // on 2nd LSD -> [[000] [010]] [[001] [111]] [112] [[005] [045]];
    // recursively sort each bucket based on 3rd LSD if necessary;
    // concatenate all buckets back together in order
  }

  public int binarySearch(int k, int[] list) {

    // if list contains k, returns index of first instance of k; if
    // list does not contain k, returns -1; list MUST be sorted

    return binarySearch(k, list, 0, list.length);
  }

  private int binarySearch(int k, int[] list, int start, int end) {

    // O(log(N)) -- height of binary tree

    if (end - start < 1)
      return -1;

    // find middle item in list

    int mid = (end - start) / 2 + start; // cast is equiv. to floor here

    if (list[mid] == k) {

      // if it's the value we're looking for, great! but now need to
      // march left to find the first index of k

      while ((mid > 0) && (list[mid - 1] == list[mid]))
        mid--;

      return mid;
    }
    else if (list[mid] > k)
      return binarySearch(k, list, 0, mid); // item bigger than k -> left
    else
      return binarySearch(k, list, mid + 1, end); // item less than k -> right
  }

  public static void main(String[] args) {

    int[] list = new int[] { 5, 1, 2, 4, 3 };

    SortingAlgorithms sa = new SortingAlgorithms();

    sa.insertionSort(list);

    System.out.println(Arrays.toString(list));

    list = new int[] { 1, 1, 1, 1, 2, 3 };

    System.out.println(Arrays.toString(list));

    System.out.println(sa.binarySearch(1, list));
    System.out.println(sa.binarySearch(4, list));
  }
}
