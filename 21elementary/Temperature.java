import java.util.Arrays;

public class Temperature implements Comparable<Temperature> {
    private final double degrees;
    
    public Temperature(double degrees) {
        if (Double.isNaN(degrees))
            throw new IllegalArgumentException();
        this.degrees = degrees;
    }

    public int compareTo(Temperature that) {
        double EPSILON = Double.MIN_VALUE;
        if (this.degrees < that.degrees - EPSILON) return -1;
        if (this.degrees > that.degrees + EPSILON) return +1;
        return 0;
    }
    
    public double getTemperature() {
        return degrees;
    }
    
    public static void main(String[] args) {
        Temperature[] t = new Temperature[3];
        // t[0] = new Temperature(Math.nextAfter(Double.MIN_VALUE, Double.POSITIVE_INFINITY));
        // t[1] = new Temperature(Double.MIN_VALUE);
        // t[2] = new Temperature(Double.MIN_NORMAL);
        t[0] = new Temperature(10.16);
        t[1] = new Temperature(10.00);
        t[2] = new Temperature(10.08);
        Arrays.sort(t);
        for (Temperature s : t) {
            System.out.println(s.getTemperature() + " ");
        }
    }
}
