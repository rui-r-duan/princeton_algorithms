import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.Digraph;

public class WordNet {
    private final Digraph digraph;

    /**
     * constructor takes the name of the two input files
     */
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("arguments cannot be null");

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
            // for (int i = 0; i < words.length; i++) {
            // }
            vertexCnt++;
        }
        synsetsIn.close();

        digraph = new Digraph(vertexCnt);

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

        // StdOut.println(digraph);
        StdOut.println("V=" + digraph.V());
        StdOut.println("E=" + digraph.E());
    }

    /**
     * returns all WordNet nouns
     */
    public Iterable<String> nouns() {
        return null;
    }

    /**
     * is the word a WordNet noun?
     */
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException("arguments cannot be null");
        return false;
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
     * a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
     * in a shortest ancestral path (defined below)
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
    }
}
