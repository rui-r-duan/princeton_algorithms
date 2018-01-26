final class Coordinate {
    final int x;
    final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object t) {
        if (this == t) return true;
        if (t == null) return false;
        if (this.getClass() != t.getClass()) return false;
        Coordinate that = (Coordinate) t;
        if (this.x != that.x) return false;
        if (this.y != that.y) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = 31 * hash + x;
        hash = 31 * hash + y;
        return hash;
    }
}
