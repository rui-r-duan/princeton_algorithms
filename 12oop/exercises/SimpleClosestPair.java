/******************************************************************************
 *  Compilation:  javac ClosestPair.java
 *  Execution:    java ClosestPair n
 *  Dependencies: Point2D.java
 *
 *  Given n random points in the unit square, find the closest pair.
 *
 *  Note: could speed it up by comparing square of Euclidean distances
 *  instead of Euclidean distances.
 *
 ******************************************************************************/

/**
 *  The {@code ClosestPair} data type computes a closest pair of points
 *  in a set of <em>n</em> points in the plane and provides accessor methods
 *  for getting the closest pair of points and the distance between them.
 *  The distance between two points is their Euclidean distance.
 *
 *  @author Ryan Duan
 */

import edu.princeton.cs.algs4.Point2D;

public class SimpleClosestPair {

    // closest pair of points and their Euclidean distance
    private Point2D best1, best2;
    private double bestDistance = Double.POSITIVE_INFINITY;

    /**
     * Computes the closest pair of points in the specified array of points.
     *
     * @param  points the array of points
     * @throws IllegalArgumentException if {@code points} is {@code null} or if any
     *         entry in {@code points[]} is {@code null}
     */
    public SimpleClosestPair(Point2D[] points) {
        if (points == null) throw new IllegalArgumentException("constructor argument is null");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("array element " + i + " is null");
        }

        int n = points.length;
        if (n <= 1) return;


        // check for coincident points
        for (int i = 0; i < n-1; i++) {
            if (points[i].equals(points[i+1])) {
                bestDistance = 0.0;
                best1 = points[i];
                best2 = points[i+1];
                return;
            }
        }

        closest(points);
    }

    // find closest pair of points in points[]
    private double closest(Point2D[] points) {
        int n = points.length;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                double distanceSquared = points[i].distanceSquaredTo(points[j]);
                if (distanceSquared < bestDistance) {
                    bestDistance = distanceSquared;
                    best1 = points[i];
                    best2 = points[j];
                }
            }
        }
        bestDistance = Math.sqrt(bestDistance);
        return bestDistance;
    }

    /**
     * Returns one of the points in the closest pair of points.
     *
     * @return one of the two points in the closest pair of points;
     *         {@code null} if no such point (because there are fewer than 2 points)
     */
    public Point2D either() {
        return best1;
    }

    /**
     * Returns the other point in the closest pair of points.
     *
     * @return the other point in the closest pair of points
     *         {@code null} if no such point (because there are fewer than 2 points)
     */
    public Point2D other() {
        return best2;
    }

    /**
     * Returns the Eucliden distance between the closest pair of points.
     *
     * @return the Euclidean distance between the closest pair of points
     *         {@code Double.POSITIVE_INFINITY} if no such pair of points
     *         exist (because there are fewer than 2 points)
     */
    public double distance() {
        return bestDistance;
    }


   /**
     * Unit tests the {@code ClosestPair} data type.
     * Reads in an integer {@code n} from standard input, generate n random
     * Point2D in the unit square (x is in the range [0, 1), y is in the
     * range [0, 1);
     * computes a closest pair of points; and prints the pair to standard
     * output.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; i++) {
            double x = StdRandom.uniform(); // [0, 1)
            double y = StdRandom.uniform(); // [0, 1)
            points[i] = new Point2D(x, y);
        }
        ClosestPair closest = new ClosestPair(points);
        StdOut.println(closest.distance() + " from " + closest.either() + " to " + closest.other());
    }

}
