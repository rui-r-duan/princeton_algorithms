/******************************************************************************
 *  Compilation:  javac MutableString.java
 *  Execution:    java MutableString
 *  Dependencies: StdOut.java
 *
 *  Shows that Strings are mutable if you allow reflection.
 * 
 *  Edited by Ryan Duan on 31st Oct, 2017
 *
 ******************************************************************************/

import java.lang.reflect.Field;

public class MutableString {

    public static void main(String[] args) {
        String s = "Immuta";
        String t = "Notreally";

        mutate(s, t);
        StdOut.println(t);

        // strings are interned so this doesn't even print "Immuta" (!)
        StdOut.println("Immuta");
        StdOut.println(s);
        StdOut.println("Immutable");
    }

    // change the first min(|s|, |t|) characters of s to t
    public static void mutate(String s, String t) {
        try {
            Field val = String.class.getDeclaredField("value");
            
            // Comment by Ryan Duan: Why do I comment out "Field off"?
            // Because java.lang.NoSuchFieldException: offset.
            
            // Field off = String.class.getDeclaredField("offset");
            val.setAccessible(true);
            // off.setAccessible(true);
            // int offset   = off.getInt(s);
            int offset = 0;
            char[] value = (char[]) val.get(s);
            for (int i = 0; i < Math.min(s.length(), t.length()); i++)
                value[offset + i] = t.charAt(i);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
