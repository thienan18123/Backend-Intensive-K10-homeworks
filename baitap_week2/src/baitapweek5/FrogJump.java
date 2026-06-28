package baitapweek5;

public class FrogJump {
	
	//O(n)
	
	public int solution(int[] A) {
		for (int i = 0; i < A.length; i++) {
			if (A[i] == 1) {
				return i;
				
			}
		}
		return -1;
		
	}

	public static void main(String[] args) {
		int[] A = {0, 0, 0, 0, 1, 0, 0, 1};
		
        
		FrogJump jump = new FrogJump();
		System.out.println("Shortest jump is " + jump.solution(A));

	}

}
