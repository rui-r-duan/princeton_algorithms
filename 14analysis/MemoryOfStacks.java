/******************************************************************************
 *  Compilation:  javac -cp .:classmexer.jar MemoryOfStacks.java
 *  Execution:    java -cp  .:classmexer.jar -XX:-UseCompressedOops -javaagent:classmexer.jar MemoryOfStacks
 *  Dependencies: classmexer.jar LinearRegression.java StackOfInts.java
 *                StackOfIntegers.java Stack.java Queue.java Bag.java ResizingArrayStack.java
 *      
 *  Note: the Integer values -128 to 127 are typically cached once created.
 *
 *  % java -cp .:classmexer.jar -XX:-UseCompressedOops -javaagent:classmexer.jar MemoryOfStacks
 *  size of Integer  = 24
 *  Stack of n null references   = 40.00 n + 32.00  (R^2 = 1.000)
 *  Queue of n null references   = 40.00 n + 40.00  (R^2 = 1.000)
 *  Bag   of n null references   = 40.00 n + 32.00  (R^2 = 1.000)
 *  Stack<Integer> of size n     = 64.00 n + 32.00  (R^2 = 1.000)
 *  StackOfInts of size n        = 32.00 n + 32.00  (R^2 = 1.000)
 *  BagOfInts of size n (min)    = 4.00 n + 56.00  (R^2 = 1.000)
 *  BagOfInts of size n (max)    = 8.00 n + 48.00  (R^2 = 1.000)
 *  StackOfIntegers of size n    = 56.00 n + 32.00  (R^2 = 1.000)
 *  ResizingArrayStack of n null references (min) = 8.00 n + 56.00  (R^2 = 1.000)
 *  ResizingArrayStack of n null references (max) = 32.00 n + 24.00  (R^2 = 1.000)
 *  ResizingArrayQueue of n null references (min) = 8.00 n + 64.00  (R^2 = 1.000)
 *  ResizingArrayQueue of n null references (max) = 32.00 n + 32.00  (R^2 = 1.000)
 *  ResizingArrayBag   of n null references (min) = 8.00 n + 56.00  (R^2 = 1.000)
 *  ResizingArrayBag   of n null references (max) = 16.00 n + 40.00  (R^2 = 1.000)
 *
 *  %  java -cp .:classmexer.jar -XX:+UseCompressedOops -javaagent:classmexer.jar MemoryOfStacks
 *  size of Integer  = 16
 *  Stack of n null references   = 24.00 N + 24.00  (R^2 = 1.000)
 *  Queue of n null references   = 24.00 N + 24.00  (R^2 = 1.000)
 *  Bag   of n null references   = 24.00 N + 24.00  (R^2 = 1.000)
 *  Stack<Integer> of size n     = 40.00 N + 24.00  (R^2 = 1.000)
 *  StackOfInts of size n        = 24.00 N + 24.00  (R^2 = 1.000)
 *  BagOfInts of size n (min)    = 4.00 N + 40.00  (R^2 = 1.000)
 *  BagOfInts of size n (max)    = 8.00 N + 32.00  (R^2 = 1.000)
 *  StackOfIntegers of size n    = 40.00 N + 24.00  (R^2 = 1.000)
 *  ResizingArrayStack of n null references (min) = 4.00 n + 40.00  (R^2 = 1.000)
 *  ResizingArrayStack of n null references (max) = 16.00 n + 24.00  (R^2 = 1.000)
 *  ResizingArrayQueue of n null references (min) = 4.00 n + 48.00  (R^2 = 1.000)
 *  ResizingArrayQueue of n null references (max) = 16.00 n + 32.00  (R^2 = 1.000)
 *  ResizingArrayBag   of n null references (min) = 4.00 n + 40.00  (R^2 = 1.000)
 *  ResizingArrayBag   of n null references (max) = 8.00 n + 32.00  (R^2 = 1.000)
 *  
 *  Remark: we fill up the stacks, queues, and bags with null references
 *  to avoid charging the data structure for the memory of the items
 *  themselves.
 *
 ******************************************************************************/

import com.javamex.classmexer.MemoryUtil;
import edu.princeton.cs.algs4.*;

public class MemoryOfStacks {

    public static void nullStack() {
        int[] sizes = { 64, 128, 192, 256, 320, 384, 448, 512, 576,
                        640, 704, 768, 832, 896, 960, 1024 };
        int m = sizes.length;

        double[] x = new double[m];
        double[] memory = new double[m];

        for (int i = 0; i < m; i++) {
            int n = sizes[i];
            Stack<Integer> stack = new Stack<Integer>();
            for (int j = 0; j < n; j++)
                stack.push(null);
            x[i] = stack.size();
            memory[i] = MemoryUtil.deepMemoryUsageOf(stack);
        }

        LinearRegression regression = new LinearRegression(x, memory);
        StdOut.println("Stack of n null references   = " + regression);
    }

    public static void integerStack() {
        int[] sizes = { 64, 128, 192, 256, 320, 384, 448, 512, 576,
                        640, 704, 768, 832, 896, 960, 1024 };
        int m = sizes.length;

        double[] x = new double[m];
        double[] memory = new double[m];

        for (int i = 0; i < m; i++) {
            int n = sizes[i];
            Stack<Integer> stack = new Stack<Integer>();
            for (int j = 0; j < n; j++)
                stack.push(128 + StdRandom.uniform(100000));
            x[i] = stack.size();
            memory[i] = MemoryUtil.deepMemoryUsageOf(stack);
        }

        LinearRegression regression = new LinearRegression(x, memory);
        StdOut.println("Stack<Integer> of size n     = " + regression);
    }

    public static void bagOfInts() {
        // must be power of 2 to make array maximally utilized
        int[] sizes = { 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192 };
        int m = sizes.length;

        double[] x = new double[m];
        double[] memory = new double[m];

        // maximally utilized
        for (int i = 0; i < m; i++) {
            int n = sizes[i];
            BagOfInts bag1 = new BagOfInts();
            for (int j = 0; j < n; j++)
                bag1.add(j);
            x[i] = bag1.size();
            memory[i] = MemoryUtil.deepMemoryUsageOf(bag1);
        }
        LinearRegression regression1 = new LinearRegression(x, memory);
        StdOut.println("BagOfInts of size n (min)    = " + regression1);

        // minimally utilized
        for (int i = 0; i < m; i++) {
            int n = sizes[i];
            BagOfInts bag2 = new BagOfInts();
            for (int j = 0; j <= n; j++)
                bag2.add(j);
            x[i] = bag2.size();
            memory[i] = MemoryUtil.deepMemoryUsageOf(bag2);
        }
        LinearRegression regression2 = new LinearRegression(x, memory);
        StdOut.println("BagOfInts of size n (max)    = " + regression2);
    }

    public static void stackOfInts() {
        int[] sizes = { 64, 128, 192, 256, 320, 384, 448, 512, 576,
                        640, 704, 768, 832, 896, 960, 1024 };
        int m = sizes.length;

        double[] x = new double[m];
        double[] memory = new double[m];

        for (int i = 0; i < m; i++) {
            int n = sizes[i];
            StackOfInts stack = new StackOfInts();
            for (int j = 0; j < n; j++)
                stack.push(128 + StdRandom.uniform(100000));
            x[i] = stack.size();
            memory[i] = MemoryUtil.deepMemoryUsageOf(stack);
        }

        LinearRegression regression = new LinearRegression(x, memory);
        StdOut.println("StackOfInts of size n        = " + regression);
    }

    public static void stackOfIntegers() {
        int[] sizes = { 64, 128, 192, 256, 320, 384, 448, 512, 576,
                        640, 704, 768, 832, 896, 960, 1024 };
        int m = sizes.length;

        double[] x = new double[m];
        double[] memory = new double[m];

        for (int i = 0; i < m; i++) {
            int n = sizes[i];
            StackOfIntegers stack = new StackOfIntegers();
            for (int j = 0; j < n; j++)
                stack.push(128 + StdRandom.uniform(100000));
            x[i] = stack.size();
            memory[i] = MemoryUtil.deepMemoryUsageOf(stack);
        }

        LinearRegression regression = new LinearRegression(x, memory);
        StdOut.println("StackOfIntegers of size n    = " + regression);
    }

    public static void nullQueue() {
        int[] sizes = { 64, 128, 192, 256, 320, 384, 448, 512, 576,
                        640, 704, 768, 832, 896, 960, 1024 };
        int m = sizes.length;

        double[] x = new double[m];
        double[] memory = new double[m];

        for (int i = 0; i < m; i++) {
            int n = sizes[i];
            Queue<Integer> queue = new Queue<Integer>();
            for (int j = 0; j < n; j++)
                queue.enqueue(null);
            x[i] = queue.size();
            memory[i] = MemoryUtil.deepMemoryUsageOf(queue);
        }

        LinearRegression regression = new LinearRegression(x, memory);
        StdOut.println("Queue of n null references   = " + regression);
    }

    public static void nullBag() {
        int[] sizes = { 64, 128, 192, 256, 320, 384, 448, 512, 576,
                        640, 704, 768, 832, 896, 960, 1024 };
        int m = sizes.length;

        double[] x = new double[m];
        double[] memory = new double[m];

        for (int i = 0; i < m; i++) {
            int n = sizes[i];
            Bag<Integer> bag = new Bag<Integer>();
            for (int j = 0; j < n; j++)
                bag.add(null);
            x[i] = bag.size();
            memory[i] = MemoryUtil.deepMemoryUsageOf(bag);
        }

        LinearRegression regression = new LinearRegression(x, memory);
        StdOut.println("Bag   of n null references   = " + regression);
    }

    public static void resizingArrayNullStack() {
        // must be power of 2 to make array maximally utilized
        int[] sizes = { 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192 };
        int m = sizes.length;

        double[] x = new double[m];
        double[] memory = new double[m];

        // maximally utilized
        for (int i = 0; i < m; i++) {
            int n = sizes[i];
            ResizingArrayStack<Integer> stack1 = new ResizingArrayStack<Integer>();
            for (int j = 0; j < n; j++)
                stack1.push(null);
            x[i] = stack1.size();
            memory[i] = MemoryUtil.deepMemoryUsageOf(stack1);
        }
        LinearRegression regression1 = new LinearRegression(x, memory);
        StdOut.println("ResizingArrayStack of n null references (min) = " + regression1);

        // minimally utilized
        for (int i = 0; i < m; i++) {
            int n = sizes[i];
            ResizingArrayStack<Integer> stack2 = new ResizingArrayStack<Integer>();
            for (int j = 0; j < n; j++)
                stack2.push(null);
            for (int j = 0; j < 3*n/4 - 1; j++)
                stack2.pop();
            x[i] = stack2.size();
            memory[i] = MemoryUtil.deepMemoryUsageOf(stack2);
        }
        LinearRegression regression2 = new LinearRegression(x, memory);
        StdOut.println("ResizingArrayStack of n null references (max) = " + regression2);
    }

    public static void resizingArrayNullQueue() {
        // must be power of 2 to make array maximally utilized
        int[] sizes = { 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192 };
        int m = sizes.length;

        double[] x = new double[m];
        double[] memory = new double[m];

        // maximally utilized
        for (int i = 0; i < m; i++) {
            int n = sizes[i];
            ResizingArrayQueue<Integer> queue1 = new ResizingArrayQueue<Integer>();
            for (int j = 0; j < n; j++)
                queue1.enqueue(null);
            x[i] = queue1.size();
            memory[i] = MemoryUtil.deepMemoryUsageOf(queue1);
        }
        LinearRegression regression1 = new LinearRegression(x, memory);
        StdOut.println("ResizingArrayQueue of n null references (min) = " + regression1);

        // minimally utilized
        for (int i = 0; i < m; i++) {
            int n = sizes[i];
            ResizingArrayQueue<Integer> queue2 = new ResizingArrayQueue<Integer>();
            for (int j = 0; j < n; j++)
                queue2.enqueue(null);
            for (int j = 0; j < 3*n/4 - 1; j++)
                queue2.dequeue();
            x[i] = queue2.size();
            memory[i] = MemoryUtil.deepMemoryUsageOf(queue2);
        }
        LinearRegression regression2 = new LinearRegression(x, memory);
        StdOut.println("ResizingArrayQueue of n null references (max) = " + regression2);
    }

    public static void resizingArrayNullBag() {
        // must be power of 2 to make array maximally utilized
        int[] sizes = { 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192 };
        int m = sizes.length;

        double[] x = new double[m];
        double[] memory = new double[m];

        // maximally utilized
        for (int i = 0; i < m; i++) {
            int n = sizes[i];
            ResizingArrayBag<Integer> bag1 = new ResizingArrayBag<Integer>();
            for (int j = 0; j < n; j++)
                bag1.add(null);
            x[i] = bag1.size();
            memory[i] = MemoryUtil.deepMemoryUsageOf(bag1);
        }
        LinearRegression regression1 = new LinearRegression(x, memory);
        StdOut.println("ResizingArrayBag   of n null references (min) = " + regression1);

        // minimally utilized
        for (int i = 0; i < m; i++) {
            int n = sizes[i];
            ResizingArrayBag<Integer> bag2 = new ResizingArrayBag<Integer>();
            for (int j = 0; j <= n; j++)
                bag2.add(null);
            x[i] = bag2.size();
            memory[i] = MemoryUtil.deepMemoryUsageOf(bag2);
        }
        LinearRegression regression2 = new LinearRegression(x, memory);
        StdOut.println("ResizingArrayBag   of n null references (max) = " + regression2);
    }


    public static void main(String[] args) {
        Integer x = new Integer(123456);
        StdOut.println("size of Integer  = " + MemoryUtil.memoryUsageOf(x));

        nullStack();
        nullQueue();
        nullBag();
        integerStack();
        stackOfInts();
        bagOfInts();
        stackOfIntegers();
        resizingArrayNullStack();
        resizingArrayNullQueue();
        resizingArrayNullBag();
    }
                
}
