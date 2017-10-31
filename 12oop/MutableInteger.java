/******************************************************************************
 *  Compilation:  javac MutableInteger.java
 *  Execution:    java MutableInteger
 *  Dependencies: StdOut.java
 *
 *  Shows that Integerss are mutable if you allow reflection.
 *
 *  Edited by Ryan Duan on 31st Oct, 2017
 * 
 ******************************************************************************/

import java.lang.reflect.Field;

public class MutableInteger {

    public static void main(String[] args) {
        final Integer x = 17;
        StdOut.println(x);
        mutate(x);
        StdOut.println(x);
    }

    // change the Integer to 9999999
    public static void mutate(Integer x) {
        try {
            Field value = Integer.class.getDeclaredField("value");
            value.setAccessible(true);
            value.setInt(x, 999999999);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
