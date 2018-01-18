import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.SET;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Out;

public class WordNet {
    private final Digraph digraph;
    private final ST<String, SET<Integer>> map; // <noun, synset_id list>
    private final SAP sap;

    /**
     * constructor takes the name of the two input files
     */
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("arguments cannot be null");

        map = new ST<String, SET<Integer>>();
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
    // @pre map != null
    private int readSynsets(String synsets) {
        assert map != null;
        StdOut.println(synsets);
        In synsetsIn = new In(synsets);
        int vertexCnt = 0;
        while (!synsetsIn.isEmpty()) {
            String line = synsetsIn.readLine();
            String[] fields = line.split(",", 3);
            assert fields.length >= 2;

            int synsetID = Integer.parseInt(fields[0]);
            String[] words = fields[1].split(" ");
            // String gloss = fields[2];
            for (int i = 0; i < words.length; i++) {
                SET<Integer> set = map.get(words[i]);
                if (set == null) {
                    set = new SET<Integer>();
                    set.add(synsetID);
                }
                else {
                    set.add(synsetID);
                }
                map.put(words[i], set);
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
        return map;
    }

    /**
     * is the word a WordNet noun?
     */
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException("arguments cannot be null");
        return map.contains(word);
    }

    /**
     * distance between nounA and nounB (defined below)
     */
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException("arguments cannot be null");

        return -1;
    }

    /**
     * a synset (second field of synsets.txt) that is the common ancestor of
     * nounA and nounB in a shortest ancestral path
     */
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException("arguments cannot be null");

        return null;
    }

    /**
     * do unit testing of this class
     */
    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);
        StdOut.println("isNoun(\"Actifed\")? " + wn.isNoun("Actifed"));
        StdOut.println("isNoun(\"AND_circuit\")? " + wn.isNoun("AND_circuit"));
        StdOut.println("isNoun(\"antihistamine\")? " + wn.isNoun("antihistamine"));
        StdOut.println("isNoun(\"nasal_decongestant\")? " + wn.isNoun("nasal_decongestant"));
        StdOut.println("writing all the words to file \"all_words\"");
        Iterable<String> words = wn.nouns();
        Out o = new Out("all_words");
        for (String s : words) {
            o.println(s);
        }
        o.close();
    }
}
