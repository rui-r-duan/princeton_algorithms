/*******************************************************************************
 * Compilation:  javac WordNet.java
 * Execution:    java WordNet synsets_file hypernyms_file nounA nounB
 * Dependencies: Digraph.java SAP.java In.java ST.java SET.java
 *               DirectedCycleX.java
 *
 * WordNet representation
 *
 * $ java WordNet synsets.txt hypernyms.txt worm bird
 * isNoun("Actifed")? true
 * isNoun("AND_circuit")? true
 * isNoun("antihistamine")? true
 * isNoun("nasal_decongestant")? true
 * total number of words: 119188
 * distance(worm,bird)=5
 * sap(worm,bird)=animal
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.DirectedCycleX;

import edu.princeton.cs.algs4.StdOut;

/**
 * The {@code WordNet} class is a datatype for representing a WordNet. This
 * class is immutable.
 * <p>
 * WordNet is a semantic lexicon for the English language that is used
 * extensively by computational linguists and cognitive scientists. WordNet
 * groups words into sets of synonyms called synsets and describes semantic
 * relationships between them. One such relationship is the is-a relationship,
 * which connects a hyponym (more specific synset) to a hypernym (more general
 * synset). For example, animal is a hypernym of both bird and fish; bird is a
 * hypernym of eagle, pigeon, and seagull.
 * <p>
 * The wordnet digraph is a rooted DAG: it is acyclic and has one vertex—the
 * root—that is an ancestor of every other vertex. However, it is not
 * necessarily a tree because a synset can have more than one hypernym.
 * <p>
 * <b>Input</b>
 * <p>
 * A synsets file consists of lines of synsets. Each line is a synset that is
 * in the format like:<p>
 * <code>synset_id,noun_1 noun_2 ...,definition (gloss)</code><p>
 * For example,<p>
 * <code>84,Abruzzi Abruzzi_e_Molise,a mountainous region of central Italy on
 * the Adriatic</code>
 * <p>
 * A hypernyms file consists of lines of hypernyms. Each lins is a hypernym set
 * for a WordNet noun:<p>
 * <code>synset_id,parent1,parent2</code><p>
 * For example,<p>
 * <code>164,21012,56099</code><p>
 * It means that the the synset 164 ("Actifed") has two hypernyms: 21012
 * ("antihistamine") and 56099 ("nasal_decongestant"), representing that
 * Actifed is both an antihistamine and a nasal decongestant.
 *
 * <p>
 * <b>Methods</b><p>
 * {@code nouns()} returns all the WordNet nouns.<p>
 * {@code isNoun(s)} judges if the argument is a noun in this WordNet.<p>
 * {@code distance(n1,n2)} returns the distances of the two nouns n1 and n2.<p>
 * {@code ancestor(n1,n2)} returns the common ancestor in the SAP of n1 and n2.
 * <p>
 * For the descrptions of SAP and distances, see SAP.java
 *
 * @author Ryan Duan
 */
public class WordNet {
    private final ST<String, SET<Integer>> wordMap;   // <noun, synset_id list>
    private final ST<Integer, SET<String>> synsetMap; // <synset_id, noun list>
    private final SAP sap;

    /**
     * constructor takes the name of the two input files
     *
     * @param synsets   file name of the synsets
     * @param hypernyms file name of the hypernyms
     *
     * @throws IllegalArgumentException if any of the arguments is null, or if
     * the constructed digraph is not a rooted DAG (a.k.a no cycle, and has
     * only one root), or any vertex value is invalid in hypernyms
     */
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("arguments cannot be null");

        wordMap = new ST<String, SET<Integer>>();
        synsetMap = new ST<Integer, SET<String>>();
        int vertexCnt = readSynsets(synsets);
        // StdOut.println("vertexCnt=" + vertexCnt);
        Digraph digraph = new Digraph(vertexCnt);
        digraph = readHypernyms(hypernyms, digraph);

        if (hasCycle(digraph))
            throw new IllegalArgumentException("digraph has cycle");
        if (hasMultipleRoots(digraph))
            throw new IllegalArgumentException("digraph has more than one root");

        sap = new SAP(digraph);
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
    // @return the constructed digraph
    // @param hypernyms the input file name
    // @param digraph an empty digraph
    // @pre digraph != null
    private Digraph readHypernyms(String hypernyms, Digraph digraph) {
        assert digraph != null;
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
        return digraph;
    }

    // It should be called only by the constructor.
    private boolean hasCycle(Digraph G) {
        DirectedCycleX finder = new DirectedCycleX(G);
        return finder.hasCycle();
    }

    // It should be called only by the constructor.
    private boolean hasMultipleRoots(Digraph G) {
        int rootCnt = 0;
        for (int i = 0; i < G.V(); i++) {
            if (G.outdegree(i) == 0)
                rootCnt++;
        }
        return rootCnt > 1;
    }

    /**
     * returns all WordNet nouns
     */
    public Iterable<String> nouns() {
        return wordMap.keys();
    }

    /**
     * is the word a WordNet noun?
     * @param word a query word
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
     * @param nounA the first query noun
     * @param nounB the second query noun
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
     * @param nounA the first query noun
     * @param nounB the second query noun
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
            // once SAP object is created, no invalid synsetID exists in digraph
            assert wordList != null;
            return wordList.min();
        }
    }

    /**
     * do unit testing of this class
     * @param args args[0] synsets file name, args[1] hypernyms file name,
     * args[2] the first query noun, args[3] the second query noun
     */
    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);

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

        StdOut.println();
        StdOut.printf("distance(%s,%s)=%d\n", args[2], args[3],
                       wn.distance(args[2], args[3]));
        StdOut.printf("sap(%s,%s)=%s\n", args[2], args[3],
                       wn.sap(args[2], args[3]));
    }
}
