package baitapweek5;
import java.util.ArrayDeque;
import java.util.Queue;

public class FrogJump {
	
	//O(nlogn)- Breadth first search
	
	public int solution(int[] A) {
		int n = A.length;
		
		//fibonacci
		int [] fib = new int[30];
		fib[0] = 1;
		fib[1] = 2;
		int fs = 2;
		while (fib[fs-1] <= n) {
			fib[fs] = fib[fs - 1] + fib[fs - 2];
			fs++;
		}
		
		boolean[] visited = new boolean[n + 2];
		Queue<int[]> queue = new ArrayDeque<>();
		queue.add(new int[] {-1,0});
		visited[0] = true;
		
		while (!queue.isEmpty()) {
			int[] cur = queue.poll();
			int pos = cur[0];
			int jumps = cur[1];
			
			for (int j = 0; j < fs; j++) {
				int next = pos + fib[j];
				
				if (next == n) {
					return jumps + 1; //reach the far bank
				}
				if (next < n && A[next] == 1 && !visited[next + 1]) {
					visited[next + 1] = true;
					queue.add(new int[] {next, jumps + 1});
				}
			}
		}
		return -1;
		
	}
	
	//dynamic programming..
	
		

	public static void main(String[] args) {
		int[] A = {0, 0, 0, 0, 1, 0, 0, 1};
		
        
		FrogJump jump = new FrogJump();
		System.out.println("Shortest jump is " + jump.solution(A));

	}

}
