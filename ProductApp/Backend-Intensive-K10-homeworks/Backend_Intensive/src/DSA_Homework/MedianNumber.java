package DSA_Homework;

import java.util.Collections;
import java.util.PriorityQueue;

public class MedianNumber {
    private final PriorityQueue<Integer> low = new PriorityQueue<>(Collections.reverseOrder());
    private final PriorityQueue<Integer> high = new PriorityQueue<>();

    public void add(int num) {
        low.offer(num);
        high.offer(low.poll());
        if (high.size() > low.size()) {
            low.offer(high.poll());
        }
    }

    public double median() {
        if (low.isEmpty()) {
            throw new IllegalStateException("no element");
        }
        if (low.size() > high.size()) {
            return low.peek();
        } else {
            return (low.peek() + high.peek()) / 2.0;
        }
    }
    public static void main(String[] args) {
        MedianNumber mn = new MedianNumber();
        for (int n : new int[]{5, 2, 9, 1, 7}) {
            mn.add(n);
            System.out.println("added " + n + " → median " + mn.median());
        }
    }
    
}
