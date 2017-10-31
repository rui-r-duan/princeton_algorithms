/*
   sample usage:
   
   execution: java IntersectedIntervals n
   
   example:
% java IntersectedIntervals 4
   input:
3.0 5.0
1.0 3.0
4.0 7.0
2.0 4.0
   output:
[1.0, 3.0] and [2.0, 4.0]
[1.0, 3.0] and [3.0, 5.0]
[2.0, 4.0] and [3.0, 5.0]
[2.0, 4.0] and [4.0, 7.0]
[3.0, 5.0] and [4.0, 7.0]

*/

import java.util.Arrays;
import edu.princeton.cs.algs4.Interval1D;

public class IntersectedIntervals {
    Interval1D[] intervals;

    public IntersectedIntervals(Interval1D[] a) {
        // make a defensive copy (shallow copy, do not copy the elements)
        intervals = new Interval1D[a.length];
        for (int i = 0; i < a.length; i++) {
            intervals[i] = a[i];
        }
        // sort by min endpoints
        Arrays.sort(intervals, Interval1D.MIN_ENDPOINT_ORDER);
    }

    // Pre: s.min() <= t.min()
    static boolean isIntersected(Interval1D s, Interval1D t) {
        assert s.min() <= t.min() : "Precondition violated: s.min() must <= t.min()";
        return s.contains(t.min());
    }

    public void printAllIntersected() {
        int n = intervals.length;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if (isIntersected(intervals[i], intervals[j])) {
                    StdOut.println(intervals[i] + " and " + intervals[j]);
                }
            }
        }
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Interval1D[] a = new Interval1D[n];
        for (int i = 0; i < n; i++) {
            double min = StdIn.readDouble();
            double max = StdIn.readDouble();
            a[i] = new Interval1D(min, max);
        }
        
        IntersectedIntervals obj = new IntersectedIntervals(a);
        obj.printAllIntersected();
    }
}