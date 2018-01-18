import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wn;
    /**
     * constructor takes a WordNet object
     */
    public Outcast(WordNet wordnet) {
        wn = wordnet;
    }

    /**
     * given an array of WordNet nouns, return an outcast
     *
     * Assume that argument to outcast() contains only valid wordnet nouns (and
     * that it contains at least two such nouns).
     */
    public String outcast(String[] nouns) {
        assert nouns != null;
        assert nouns.length >= 2;
        assert checkAllWordNetNouns(nouns);

        int[] d = new int[nouns.length]; // d[i] == 0
        int i;
        for (i = 0; i < nouns.length; i++) {
            for (int j = 0; j < nouns.length; j++) {
                d[i] += wn.distance(nouns[i], nouns[j]);
            }
        }
        int max = d[0];
        int outcast = 0;
        // StdOut.printf("d[%d]=%d\n", 0, d[0]);
        for (i = 1; i < nouns.length; i++) {
            // StdOut.printf("d[%d]=%d\n", i, d[i]);
            if (d[i] > max) {
                max = d[i];
                outcast = i;
            }
        }
        return nouns[outcast];
    }

    private boolean checkAllWordNetNouns(String[] nouns) {
        for (int i = 0; i < nouns.length; i++) {
            if (!wn.isNoun(nouns[i]))
                return false;
        }
        return true;
    }

    /**
     * see test client below
     */
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
