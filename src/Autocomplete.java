import java.util.Arrays;
import java.util.Comparator;
import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

public class Autocomplete {
    private Term[] terms; // Array of terms

    // Constructs an autocomplete data structure from an array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) {
            throw new NullPointerException("terms is null");
        }

        // create a new Term object
        this.terms = new Term[terms.length];
        for (int i = 0; i < terms.length; i++) {
            // create a defensive copy of terms
            this.terms[i] = terms[i];
        }

        // sort this.terms in the lexicographic order
        Arrays.sort(this.terms);
    }

    // Returns all terms that start with prefix, in descending order of their weights.
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }

        // create a new term object with prefix as the query
        Term term = new Term(prefix);
        // create a new comparator that compares with prefix order
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        // set i to the first index of term in terms that starts with prefix
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);

        // set n to the number of terms  that start with prefix
        int n = numberOfMatches(prefix);

        // Construct an array matches containing n elements
        Term[] matches = new Term[n];

        // copy n elements into matches from terms starting at index i
        for (int k = 0; k < n; k++) {
            matches[k] = terms[i++];
        }

        // sort matches in reverse order of weight
        Arrays.sort(matches, Term.byReverseWeightOrder());
        // return matches
        return matches;
    }

    // Returns the number of terms that start with prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }

        // crate a new Term object
        Term term = new Term(prefix);
        // create a new comparator that compares using prefix order
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        // set i to the first index of term in terms starting with prefix
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        // set j to the last index of terms starting with prefix
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);

        // if terms is empty, return 0
        if (i == -1) {
            return 0;
        }

        // calculate n
        int n = j - i + 1;

        // return n
        return n;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Autocomplete autocomplete = new Autocomplete(terms);
        StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            String msg = " matches for \"" + prefix + "\", in descending order by weight:";
            if (results.length == 0) {
                msg = "No matches";
            } else if (results.length > k) {
                msg = "First " + k + msg;
            } else {
                msg = "All" + msg;
            }
            StdOut.printf("%s\n", msg);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println("  " + results[i]);
            }
            StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        }
    }
}
