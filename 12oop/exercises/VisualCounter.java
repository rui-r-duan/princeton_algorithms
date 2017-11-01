public class VisualCounter implements Comparable<VisualCounter> {
    
    private final String name; // counter name
    private int count = 0;     // current value
    private int opCnt = 0;     // current operation count
    private int maxOps;        // max operations (increment or decrement)
    private int maxCnt;        // maximum absolute value for this counter
    
    public VisualCounter(int n, int max, String name) {
        maxOps = n;
        maxCnt = max;
        this.name = name;
        StdDraw.setXscale(-1, n+1);
        StdDraw.setYscale(-max-1, max+1);
        StdDraw.setPenRadius(0.005);
    }
    
    public void increment() {
        if (opCnt > maxOps) return;
        
        // draw previous value point in BLACK
        clearOldPoint();
        drawOldPoint();
        
        // increment
        count++;
        opCnt++;
        if (count > maxCnt) {
            count = maxCnt;
        }
        
        // draw new value in RED
        drawNewPoint();
    }
    
    public void decrement() {
        if (opCnt > maxOps) return;
        
        // draw previous value point in BLACK
        clearOldPoint();
        drawOldPoint();
        
        // decrement
        count--;
        opCnt++;
        if (count < -maxCnt) {
            count = -maxCnt;
        }
        
        // draw new value in RED
        drawNewPoint();
    }
    
    public int tally() {
        return count;
    }
    
    // draw current value
    public void draw() {
        drawNewPoint();
    }
    
    private void clearOldPoint() {
        StdDraw.setPenRadius(0.009);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.point(opCnt, count);
    }
    
    private void drawOldPoint() {
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(opCnt, count);
    }
    
    private void drawNewPoint() {
        StdDraw.setPenRadius(0.006);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.point(opCnt, count);
    }
    
    public String toString() {
        return count + ", " + opCnt + "operation, " +
            " [maxOps:" + maxOps + "," + " |maxCnt|:" + maxCnt + "]: "
            + name;
    }
    
    @Override
    public int compareTo(VisualCounter that) {
        if      (this.count < that.count) return -1;
        else if (this.count > that.count) return +1;
        else                              return 0;
    }
    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int max = Integer.parseInt(args[1]);
        VisualCounter counter = new VisualCounter(n, max, "default counter");
        counter.draw(); // draw current value
        for (int i = 0; i < n; i++) {
            boolean b = StdRandom.bernoulli(0.5);
            if (b) {
                counter.increment();
            }
            else {
                counter.decrement();
            }
            StdDraw.pause(20);
        }
        StdOut.println("tally: " + counter.tally());
    }
}