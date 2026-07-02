package DSA_Homework;

public class DominatorValue {
	
	public int solution(int[] A) {
		
		//Boyer Moore
		int candidate = 0;

		int  count = 0;
		
		for (int i = 0; i < A.length; i++) {
			if(count == 0) {
			candidate = A[i];
			count = 1;
		} else if (A[i] == candidate) {
			count++;
		} else {
			count--;
			}
		}
		int occurences = 0;
		int candidateIndex = -1;
		for (int i = 0; i < A.length; i++) {
			if (A[i] == candidate) {
				occurences++;
				candidateIndex = i;
				
			}
			
		}
		if (occurences > A.length /2) {
			return candidateIndex;
		}
		return -1;
		
	}
	
	

	public static void main(String[] args) {
		
		int[] A = {3,1,3,1,3,2,3};
		
		DominatorValue dominator = new DominatorValue();
		
		System.out.print(dominator.solution(A));

	}
}
