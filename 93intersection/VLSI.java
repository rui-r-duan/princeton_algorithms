/******************************************************************************
 *  Compilation:  javac VLSI.java
 *  Execution:    java VLSI N
 *  Dependencies: IntervalST.java Interval1D.java Interval2D.java
 *
 *  Generate N random 2D intervals and print out all pairwise intersections
 *  between them. Application = VLSI rules check.
 *
 *  Limitations
 *  -----
 *   - Assumes no two y-intervals are identical. This is because
 *     interval search tree disallows duplicates. To fix, create
 *     a helper interval data type that uses x-interval to break ties.
 *
 ******************************************************************************/

public class VLSI {

    // helper class for events in sweep line algorithm
    public static class Event implements Comparable<Event> {
        int time;
        Interval2D rect;

        public Event(int time, Interval2D rect) {
            this.time = time;
            this.rect = rect;
        }

        public int compareTo(Event that) {
            if      (this.time < that.time) return -1;
            else if (this.time > that.time) return +1;
            else                            return  0;
        }
    }


    // the sweep-line algorithm
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);

        // generate N random intervals
        Interval2D[] rects = new Interval2D[N];
        for (int i = 0; i < N; i++) {
            int xmin = (int) (100 * Math.random());
            int ymin = (int) (100 * Math.random());
            int xmax = xmin + (int) (10 * Math.random());
            int ymax = ymin + (int) (20 * Math.random());
            rects[i] = new Interval2D(new Interval1D(xmin, xmax), new Interval1D(ymin, ymax));
            StdOut.println(rects[i]);
        }
        StdOut.println();

        // create events
        MinPQ<Event> pq = new MinPQ<Event>();
        for (int i = 0; i < N; i++) {
            Event e1 = new Event(rects[i].intervalX.min, rects[i]);
            Event e2 = new Event(rects[i].intervalX.max, rects[i]);
            pq.insert(e1);
            pq.insert(e2);
        }


        // run sweep-line algorithm
        IntervalST<Interval2D> st = new IntervalST<Interval2D>();
        while (!pq.isEmpty()) {
            Event e = pq.delMin();
            int time = e.time;
            Interval2D rect = e.rect;

            // next event is the right endpoint of interval i
            if (time == rect.intervalX.max)
                st.remove(rect.intervalY);

            // next event is the left endpoint of interval i
            else {
                for (Interval1D x : st.searchAll(rect.intervalY)) {
                    StdOut.println("Intersection:  " + rect + ", " + st.get(x));
                }
                st.put(rect.intervalY, rect);
            }
        }

    }

}
