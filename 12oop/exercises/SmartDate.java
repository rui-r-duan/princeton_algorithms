/******************************************************************************
 * Compilation:  javac-algs4 SmartDate.java
 * Execution:    java-algs4 SmartDate
 * Dependencies: StdOut.java
 *
 * 1. Develop an implementation SmartDate of the Date API in the book algs4
 * that raises an exception if the date is not legal.
 *
 * 2. Add a method dayOfTheWeek() to SmartDate that returns a String value
 * Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, or Sunday, giving
 * the appropriate day of the week for the date. You may assume that the date
 * is in the 21st century.
 *
 * An immutable data type for dates.
 *
 *****************************************************************************/

/**
 *  The {@code SmartDate} class is an immutable data type to encapsulate a
 *  date (day, month, and year).
 *  <p>
 *  For additional documentation,
 *  see <a href="https://algs4.cs.princeton.edu/12oop">Section 1.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  This implementation is based on class Date that is implemented by
 *  Robert Sedgewick and Kevin Wayne.
 *
 *  @author Ryan Duan
 */
public class SmartDate implements Comparable<SmartDate> {
    private static final int[] DAYS = {
        0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };
    private static final String[] WEEKDAYS = {
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
        "Saturday", "Sunday"
    };

    private final int month; // between 1 and 12
    private final int day;   // between 1 and DAYS[month]
    private final int year;

    /**
     * Initializes a new date from the month, day, and year.
     * @param m the month (between 1 and 12)
     * @param d the day (between 1 and 28-31, depending on the month)
     * @param y the year
     * @throws IllegalArgumentException if this date is invalid
     */
    public SmartDate(int m, int d, int y) {
        if (!isValid(m, d, y)) {
            throw new IllegalArgumentException("Invalid date: " +
                                               "m=" + m +
                                               ",d=" + d +
                                               ",y=" + y);
        }
        month = m;
        day = d;
        year = y;
    }

    /**
     * Initializes new date specified as a string in form MM/DD/YYYY.
     * @param date the string representation of this date
     * @throws IllegalArgumentException if this date is invalid
     */
    public SmartDate(String date) {
        String[] fields = date.split("/");
        if (fields.length != 3) {
            throw new IllegalArgumentException("Invalid date: " + date);
        }
        month = Integer.parseInt(fields[0]);
        day   = Integer.parseInt(fields[1]);
        year  = Integer.parseInt(fields[2]);
        if (!isValid(month, day, year))
            throw new IllegalArgumentException("Invalid date: " + date);
    }

    /**
     * Returns the month.
     * @return the month (an integer between 1 and 12)
     */
    public int month() {
        return month;
    }

    /**
     * Returns the day.
     * @return the day (an integer between 1 and 31)
     */
    public int day() {
        return day;
    }

    /**
     * Returns the year.
     * @return the year
     */
    public int year() {
        return year;
    }

    // Is the given date valid?
    private static boolean isValid(int m, int d, int y) {
        if (m < 1 || m > 12)      return false;
        if (d < 1 || d > DAYS[m]) return false;
        if (m == 2 && d == 29 && !isLeapYear(y)) return false;
        return true;
    }

    // Is y a leap year?
    private static boolean isLeapYear(int y) {
        if (y % 400 == 0) return true;
        if (y % 100 == 0) return false;
        return y % 4 == 0;
    }

    public String dayOfTheWeek() {
        int d = this.day;
        int m = this.month;
        int y = this.year;

        if (m < 3) {
            m += 12;
            y--;
        }
        // Variance of Zeller's formula
        // http://www.cnblogs.com/mq0036/p/3534314.html
        // https://en.wikipedia.org/wiki/Determination_of_the_day_of_the_week
        int w = (d + 2*m + 3*(m+1)/5 + y + y/4 - y/100 + y/400) % 7;
        return WEEKDAYS[w]; // w = 1 : MONDAY, w = 7 : SUNDAY
    }

    /**
     * Compares two dates chronologically.
     *
     * @return the value {@code 0} if the argument date is equal to this date;
     *         a negative integer if this date is chronologically less than
     *         the argument date; and a positive ineger if this date is chronologically
     *         after the argument date
     */
    @Override
    public int compareTo(SmartDate that) {
        if (this.year  < that.year)  return -1;
        if (this.year  > that.year)  return +1;
        if (this.month < that.month) return -1;
        if (this.month > that.month) return +1;
        if (this.day   < that.day)   return -1;
        if (this.day   > that.day)   return +1;
        return 0;
    }

    /**
     * Returns a string representation of this date.
     *
     * @return the string representation in the format MM/DD/YYYY
     */
    @Override
    public String toString() {
        return month() + "/" + day() + "/" + year();
    }

    /**
     * Unit tests the {@code SmartDate} data type.
     *
     * 1. Enumerate all possible combinations for the tuple (m,d,y)
     * where m ranges [0,16], d ranges [-1,32], y ranges [-1,2017].
     *
     * 2. Test dayOfWeek().
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        SmartDate date;
        for (int y = -1; y <= 2017; y++) {
            for (int m = 0; m <= 16; m++) {
                for (int d = -1; d <= 32; d++) {
                    try {
                        date = new SmartDate(m, d, y);
                    }
                    catch (IllegalArgumentException e) {
                        StdOut.println(e);
                    }
                }
            }
        }

        date = new SmartDate(11, 1, 2017);
        StdOut.println(date + ": " + date.dayOfTheWeek());
    }

}
