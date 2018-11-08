class SeamFinder {
    private final Matrix energy;

    public SeamFinder(double[][] energyArray, boolean isVertical) {
        assert energyArray != null;
        assert energyArray[0] != null;
        boolean isTransposed = !isVertical;
        energy = new Matrix(energyArray, isTransposed);
    }

    private int width() {
        return energy.cols();
    }

    private int height() {
        return energy.rows();
    }

    /**
     * @return true for vertical, false for horizontal
     */
    public boolean direction() {
        // if not transposed, return true (vertical);
        // if transposed, return false (horizontal);
        return !energy.isTransposed();
    }

    /**
     * energy of pixel at column x and row y
     * @throws IllegalArgumentException if x or y is outside its prescribed range
     */
    public double energy(int x, int y) {
        return energy.get(new Coordinate(x, y));
    }

    private Coordinate[] getChildren(int x, int y) {
        if (y == height() - 1)
            return new Coordinate[0];
        else
            if (width() == 1)
                return new Coordinate[] {
                    new Coordinate(x, y+1)
                };
            else if (x == 0)
                return new Coordinate[] {
                    new Coordinate(x, y+1),
                    new Coordinate(x+1, y+1)
                };
            else if (x == width() - 1)
                return new Coordinate[] {
                    new Coordinate(x-1, y+1),
                    new Coordinate(x, y+1)
                };
            else
                return new Coordinate[] {
                    new Coordinate(x-1, y+1),
                    new Coordinate(x, y+1),
                    new Coordinate(x+1, y+1)
                };
    }

    private void relax(int x, int y, Matrix distTo,
                       CoordMatrix edgeTo) {
        Coordinate v = new Coordinate(x, y);
        Coordinate[] children = getChildren(x, y);
        for (Coordinate w : children) {
            if (distTo.get(w) > distTo.get(v) + energy.get(w)) {
                distTo.set(w, distTo.get(v) + energy.get(w));
                edgeTo.set(w, v);
            }
        }
    }

    /**
     * sequence of indices for vertical seam
     */
    public int[] findVerticalSeam() {
        double[][] arrayDistTo = new double[height()][width()];
        arrayDistTo[0] = new double[width()];
        for (int x = 0; x < width(); x++)
            arrayDistTo[0][x] = energy(x, 0);
        for (int y = 1; y < height(); y++) {
            arrayDistTo[y] = new double[width()];
            for (int x = 0; x < width(); x++) {
                arrayDistTo[y][x] = Double.POSITIVE_INFINITY;
            }
        }
        Matrix distTo = new Matrix(arrayDistTo);

        Coordinate[][] arrayEdgeTo = new Coordinate[height()][width()];
        for (int y = 0; y < height(); y++) {
            arrayEdgeTo[y] = new Coordinate[width()];
            for (int x = 0; x < width(); x++) {
                arrayEdgeTo[y][x] = new Coordinate(-1, -1);
            }
        }
        CoordMatrix edgeTo = new CoordMatrix(arrayEdgeTo);

        // Relax the vertices (pixels) in a topological order.
        // Note that there are more than 3 topological orders for this digraph.
        // We choose the simplest one which is the natual sequence order.
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                relax(x, y, distTo, edgeTo);
            }
        }

        // find the smallest distance in the bottom line
        int minX = 0;
        Coordinate pMin = new Coordinate(minX, height() - 1);
        for (int x = 1, ym = height() - 1; x < width(); x++) {
            Coordinate p = new Coordinate(x, ym);
            if (distTo.get(p) < distTo.get(pMin)) {
                minX = x;
                pMin = new Coordinate(minX, ym);
            }
        }

        ////////////////////////////////////////////////////////////
        // debug
        // StdOut.println("distTo=");
        // for (int y = 0; y < height(); y++) {
        //     for (int x = 0; x < width(); x++)
        //         StdOut.printf("%9.2f ", distTo.get(new Coordinate(x, y)));
        //     StdOut.println();
        // }
        // StdOut.println("edgeTo=");
        // for (int y = 0; y < height(); y++) {
        //     for (int x = 0; x < width(); x++) {
        //         Coordinate c = edgeTo.get(new Coordinate(x, y));
        //         StdOut.printf("(%4d,%4d) ", c.x, c.y);
        //     }
        //     StdOut.println();
        // }
        ////////////////////////////////////////////////////////////

        // trace back the shortest path
        int[] path = new int[height()];
        int i = 0;
        final Coordinate terminator = new Coordinate(-1, -1);
        for (Coordinate v = pMin; !v.equals(terminator); v = edgeTo.get(v)) {
            path[i++] = v.x;
        }

        int[] resultX = new int[height()];
        i = 0;
        for (int j = height() - 1; j >= 0; j--) {
            resultX[i++] = path[j];
        }

        return resultX;
    }
}
