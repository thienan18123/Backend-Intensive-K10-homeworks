package DSA_Homework;
import java.util.ArrayDeque;
import java.util.Queue;

public class FrogJump {
	
	public static int solution(int[] A) {
		
	
	
	
	//fibonacci
	int[] fib =  new int[26];
	fib[0] = 1;
	fib[1] = 2;
	int fs = 2;
	while (fib[fs - 1] < A.length) {
		fib[fs] = fib[fs-1] + fib[fs-2];
		fs++;
	
		
	}
	boolean[] visited = new boolean[A.length];
	Queue<int[]> queue = new ArrayDeque<>();
	queue.add(new int[] {-1,0}); 
	
	
	while (!queue.isEmpty()) {
		int[] current = queue.poll();
		int pos = current[0];
		int jumps = current[1];
		
		for (int i = 0; i < fs; i++) {
			int path = pos + fib[i];
			
			if (path == A.length) {
				return jumps + 1;
				
				
			}
			if (path < A.length && A[path] == 1 && !visited[path]) { 
				visited[path] = true;
				queue.add(new int[] {path, jumps + 1});
			}
		}
		}
	return -1;
	}
	
	
	
	
	
	
	
	
	
	
	
	
		

	public static void main(String[] args) {
//		int[] A = {0, 0, 1, 0, 1, 1, 0, 0}; //3
		int[] A = {1,1,0,0,1,0,0,1,0,0};
		
    
		
		System.out.println(solution(A));

	}

}
