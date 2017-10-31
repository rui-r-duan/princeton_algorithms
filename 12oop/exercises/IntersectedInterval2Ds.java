import edu.princeton.cs.algs4.Interval1D;
import edu.princeton.cs.algs4.Interval2D;

class Interval2DExt extends Interval2D {
    Interval1D xRef;
    Interval1D yRef;
    
    public Interval2DExt(Interval1D x, Interval1D y) {
        super(x, y);
        // Interval2D.x and Interval2D.y are not accessible, so make copies
        xRef = x;
        yRef = y;
    }
    
    boolean contains(Interval2DExt that) {
        return doesAcontainsB(this.xRef, that.xRef) &&
            doesAcontainsB(this.yRef, that.yRef);
    }
    
    static boolean doesAcontainsB(Interval1D a, Interval1D b) {
        return a.min() <= b.min() && a.max() >= b.max();
    }
}

public class IntersectedInterval2Ds {
    Interval2DExt[] intervals;

    public IntersectedInterval2Ds(Interval2DExt[] a) {
        // make a defensive copy (shallow copy, do not copy the elements)
        intervals = new Interval2DExt[a.length];
        for (int i = 0; i < a.length; i++) {
            intervals[i] = a[i];
        }
    }

    public void countAllIntersected() {
        int n = intervals.length;
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if (intervals[i].intersects(intervals[j])) {
                    // StdOut.println(intervals[i] + " and " + intervals[j]);
                    cnt++;
                }
            }
        }
        StdOut.println("There are " + cnt + " pairs of intersected intervals");
    }

    public void countAllContained() {
        int cnt = 0;
        for (int i = 0; i < intervals.length; i++) {
            for (int j = 0; j < intervals.length; j++) {
                if (i == j) continue;
                if (intervals[i].contains(intervals[j])) {
                    cnt++;
                    // StdOut.println(intervals[i] + " and " + intervals[j]);
                }
            }
        }
        StdOut.println("There are " + cnt + " pairs of contained intervals");
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        double min = Double.parseDouble(args[1]); // minimum box width or height
        double max = Double.parseDouble(args[2]); // maximum bax width or height
        Interval2DExt[] a = new Interval2DExt[n];
        double length = max - min;
        for (int i = 0; i < n; i++) {
            double xmin = StdRandom.uniform(0.0, 1.0 - length);
            double xmax = xmin + StdRandom.uniform(min, max);
            assert xmax <= 1.0;
            double ymin = StdRandom.uniform(0.0, 1.0 - length);
            double ymax = ymin + StdRandom.uniform(min, max);
            assert ymax <= 1.0;
            Interval1D xInterval = new Interval1D(xmin, xmax);
            Interval1D yInterval = new Interval1D(ymin, ymax);
            Interval2DExt box = new Interval2DExt(xInterval, yInterval);
            box.draw();
            // StdOut.println(box);
            a[i] = box;
        }
        
        IntersectedInterval2Ds obj = new IntersectedInterval2Ds(a);
        obj.countAllIntersected();
        obj.countAllContained();
    }
}