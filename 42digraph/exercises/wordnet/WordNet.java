import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.SET;

import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.Out;

public class WordNet {
    private final Digraph digraph;
    private final ST<String, SET<Integer>> wordMap;   // <noun, synset_id list>
    private final ST<Integer, SET<String>> synsetMap; // <synset_id, noun list>
    private final SAP sap;

    /**
     * constructor takes the name of the two input files
     */
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("arguments cannot be null");

        wordMap = new ST<String, SET<Integer>>();
        synsetMap = new ST<Integer, SET<String>>();
        int vertexCnt = readSynsets(synsets);
        StdOut.println("vertexCnt=" + vertexCnt);
        digraph = new Digraph(vertexCnt);
        readHypernyms(hypernyms);
        sap = new SAP(digraph);
        StdOut.println("G.V()=" + digraph.V());
        StdOut.println("G.E()=" + digraph.E());
    }

    // Input synsets from a file, store all the nouns and return the vertex
    // number (number of synsets).
    // It should be called only by the constructor.
    // @param synsets the input file name
    // @return number of synsets
    // @pre wordMap != null
    // @pre synsetMap != null
    private int readSynsets(String synsets) {
        assert wordMap != null;
        assert synsetMap != null;
        StdOut.println(synsets);
        In synsetsIn = new In(synsets);
        int vertexCnt = 0;
        while (!synsetsIn.isEmpty()) {
            String line = synsetsIn.readLine();
            String[] fields = line.split(",", 3); // only split by first two ","
            assert fields.length >= 2;

            int synsetID = Integer.parseInt(fields[0]);
            String[] words = fields[1].split(" ");
            // String gloss = fields[2];

            SET<String> wordSet = new SET<String>();
            for (int i = 0; i < words.length; i++) {
                wordSet.add(words[i]);
            }
            synsetMap.put(synsetID, wordSet);

            for (int i = 0; i < words.length; i++) {
                SET<Integer> set = wordMap.get(words[i]);
                if (set == null) {
                    set = new SET<Integer>();
                    set.add(synsetID);
                }
                else {
                    set.add(synsetID);
                }
                wordMap.put(words[i], set);
            }

            vertexCnt++;
        }
        synsetsIn.close();
        return vertexCnt;
    }

    // Input hypernyms from a file, and build up the digraph.
    // It should be called only by the constructor.
    // @param hypernyms the input file name
    // @pre digraph != null
    private void readHypernyms(String hypernyms) {
        assert digraph != null;
        StdOut.println(hypernyms);
        In hypernymsIn = new In(hypernyms);
        while (!hypernymsIn.isEmpty()) {
            String line = hypernymsIn.readLine();
            String[] nums = line.split(",");
            assert nums.length > 0;
            int synsetID = Integer.parseInt(nums[0]);
            int[] parents = null;
            if (nums.length > 1) {
                parents = new int[nums.length - 1];
                for (int i = 0; i < parents.length; i++) {
                    parents[i] = Integer.parseInt(nums[i+1]);
                    digraph.addEdge(synsetID, parents[i]);
                }
            }
        }
        hypernymsIn.close();
    }

    /**
     * returns all WordNet nouns
     */
    public Iterable<String> nouns() {
        return wordMap.keys();
    }

    /**
     * is the word a WordNet noun?
     * @throws IllegalArgumentException if {@code word} is null
     */
    public boolean isNoun(String word) {
        return wordMap.contains(word);
    }

    /**
     * distance between nounA and nounB
     *
     * @return the minimum length of any ancestral path between any synset
     * {@code v} of {@code nounA} and any synset {@code w} of {@code nounB}. -1
     * if {@code nounA} and {@code nounB} do not have any common ancestral
     * path.
     *
     * @throws IllegalArgumentException if any of {@code nounA} or {@code nounB}
     * is null, or if any of {@code nounA} or {@code nounB} is not a WordNet
     * noun.
     */
    public int distance(String nounA, String nounB) {
        // if nounA or nounB is null, ST.get() throws IllegalArgumentException
        SET<Integer> a = wordMap.get(nounA);
        SET<Integer> b = wordMap.get(nounB);
        if (a == null || b == null) {
            throw new IllegalArgumentException(nounA + " or " + nounB + " is not a WordNet noun");
        }
        return sap.length(a, b);
    }

    /**
     * a synset (second field of synsets.txt) that is the common ancestor of
     * nounA and nounB in a shortest ancestral path
     *
     * @return a noun in the closest common ancestor synset; {@code null} if
     * the two nouns do not have any ancestral path
     *
     * @throws IllegalArgumentException if any of {@code nounA} or {@code nounB}
     * is null, or if any of {@code nounA} or {@code nounB} is not a WordNet
     * noun.
     */
    public String sap(String nounA, String nounB) {
        // if nounA or nounB is null, ST.get() throws IllegalArgumentException
        SET<Integer> a = wordMap.get(nounA);
        SET<Integer> b = wordMap.get(nounB);
        if (a == null || b == null) {
            throw new IllegalArgumentException(nounA + " or " + nounB + " is not a WordNet noun");
        }
        int ancestor = sap.ancestor(a, b);
        if (ancestor == -1) {
            return null;
        }
        else {
            SET<String> wordList = synsetMap.get(ancestor);
            if (wordList == null) {
                throw new IllegalArgumentException("data inconsistent, cannot find the closest common ancestor");
            }
            return wordList.min();
        }
    }

    /**
     * do unit testing of this class
     */
    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);

        StdOut.println();
        StdOut.println("isNoun(\"Actifed\")? " + wn.isNoun("Actifed"));
        StdOut.println("isNoun(\"AND_circuit\")? " + wn.isNoun("AND_circuit"));
        StdOut.println("isNoun(\"antihistamine\")? " + wn.isNoun("antihistamine"));
        StdOut.println("isNoun(\"nasal_decongestant\")? " + wn.isNoun("nasal_decongestant"));

        StdOut.println();
        Iterable<String> words = wn.nouns();
        int i = 0;
        for (String s : words) {
            i++;
        }
        StdOut.println("total number of words: " + i);
        // StdOut.println("writing all the words to file \"all_words\"");
        // Out o = new Out("all_words");
        // for (String s : words) {
        //     o.println(s);
        // }
        // o.close();

        StdOut.println();
        StdOut.printf("distance(%s,%s)=%d\n", args[2], args[3],
                       wn.distance(args[2], args[3]));
        StdOut.printf("sap(%s,%s)=%s\n", args[2], args[3],
                       wn.sap(args[2], args[3]));
    }
}
