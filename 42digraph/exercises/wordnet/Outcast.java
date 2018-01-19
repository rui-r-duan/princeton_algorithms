/*******************************************************************************
 * Compilation:  javac Outcast.java
 * Execution:    java Outcast synsets_file hypernyms_file word_set_1 ... word_set_n
 * Dependencies: WordNet.java
 *
 * Outcast of a set of WordNet nouns. An outcast is the noun that is the least
 * related to the others.
 *
 * $ more outcast5.txt
 * horse zebra cat bear table
 *
 * $ more outcast8.txt
 * water soda bed orange_juice milk apple_juice tea coffee
 *
 * $ more outcast11.txt
 * apple pear peach banana lime lemon blueberry strawberry mango watermelon potato
 *
 * $ java Outcast synsets.txt hypernyms.txt outcast5.txt outcast8.txt outcast11.txt
 * outcast5.txt: table
 * outcast8.txt: bed
 * outcast11.txt: potato
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code Outcast} class can find the outcast in a set of WordNet nouns
 * according to the information provided by the given WordNet. An outcast is
 * the noun that is the least related to the others. This class is immutable.
 */
public class Outcast {
    private final WordNet wn;
    /**
     * constructor takes a WordNet object
     * @param wordnet the input WordNet object
     */
    public Outcast(WordNet wordnet) {
        wn = wordnet;
    }

    /**
     * Given an array of WordNet nouns, return an outcast.<p>
     * An outcast is the noun that is the least related to the others.<p>
     *
     * Given a list of wordnet nouns <em>A</em><sub>1</sub>,
     * <em>A</em><sub>2</sub>, ..., <em>A</em><sub><em>n</em></sub>, which noun
     * is the least related to the others? To identify <em>an outcast</em>,
     * compute the sum of the distances between each noun and every other one:
     *
     * <blockquote>
     * <em>d</em><sub><em>i</em></sub> &nbsp; = &nbsp;
     * dist(<em>A</em><sub><em>i</em></sub>, <em>A</em><sub>1</sub>) &nbsp; + &nbsp;
     * dist(<em>A</em><sub><em>i</em></sub>, <em>A</em><sub>2</sub>) &nbsp; + &nbsp; ... &nbsp; + &nbsp;
     * dist(<em>A</em><sub><em>i</em></sub>, <em>A</em><sub><em>n</em></sub>)
     * </blockquote>
     *
     * and return a noun <em>A</em><sub><em>t</em></sub>
     * for which <em>d</em><sub><em>t</em></sub> is maximum.
     * <p>
     * Assume that argument to outcast() contains only valid wordnet nouns (and
     * that it contains at least two such nouns).
     *
     * @param nouns the query noun list
     * @return the outcast in the query noun list
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
        for (i = 1; i < nouns.length; i++) {
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
     * unit test client
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
