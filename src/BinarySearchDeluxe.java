import java.util.Arrays;
import java.util.Comparator;
import stdlib.In;
import stdlib.StdOut;

public class BinarySearchDeluxe {
    // Returns the index of the first key in a that equals the search key, or -1, according to
    // the order induced by the comparator c.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> c) {
        if (a == null || key == null || c == null) {
            throw new NullPointerException("a, key, or c is null");
        }

        // initialize index, lo and hi to their appropriate values
        int index = -1;
        int lo = 0;
        int hi = a.length - 1;

        // as long as lo is less than or equal to hi
        while (lo <= hi) {
            // initialize the middle of the array
            int mid = lo + (hi - lo) / 2;

            // compare key to the element at the middle
            int cmp = c.compare(key, a[mid]);
            // if key is less than the element at the middle
            if (cmp < 0) {
                // update high (throw away the right half)
                hi = mid - 1;
            // if key is greater than the element at the middle
            } else if (cmp > 0) {
                // update lo (throw away the left half)
                lo = mid + 1;

            // if key equals the element at the middle
            } else {
                // set index to middle
                index = mid;
                // set high to middle -1
                hi = mid - 1;

            }
        }
        // return index
        return index;
    }

    // Returns the index of the first key in a that equals the search key, or -1, according to
    // the order induced by the comparator c.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> c) {
        if (a == null || key == null || c == null) {
            throw new NullPointerException("a, key, or c is null");

        }
        // initialize index, lo and high to their appropriate values
        int index = -1;
        int lo = 0;
        int hi = a.length - 1;

        // as long as lo is less than hi
        while (lo <= hi) {
            // set mid to the middle of the array
            int mid = lo + (hi - lo) / 2;
            // compare key to the element at the middle
            int cmp = c.compare(key, a[mid]);
            // if key is less than the element at the middle
            if (cmp < 0) {
                // throw away the right half
                hi = mid - 1;

            // if key is greater than the element at the middle
            } else if (cmp > 0) {
                // throw away the left half
                lo = mid + 1;

            // is key is equal to the element at the middle
            } else {
                // set index equal to middle
                index = mid;

                // update lo to middle + 1
                lo = mid + 1;
            }
        }

        // return index
        return index;
    }

    // Unit tests the library. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        String prefix = args[1];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Arrays.sort(terms);
        Term term = new Term(prefix);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        int count = i == -1 && j == -1 ? 0 : j - i + 1;
        StdOut.println("firstIndexOf(" + prefix + ") = " + i);
        StdOut.println("lastIndexOf(" + prefix + ")  = " + j);
        StdOut.println("frequency(" + prefix + ")    = " + count);
    }
}
