package DSA_Homework;
import java.util.HashMap;

public class DominatorValue {
	
//	public int solution(int[] A) {
//		
//		//Boyer Moore
//		int candidate = 0;
//
//		int  count = 0;
//		
//		for (int i = 0; i < A.length; i++) {
//			if(count == 0) {
//			candidate = A[i];
//			count = 1;
//		} else if (A[i] == candidate) {
//			count++;
//		} else {
//			count--;
//			}
//		}
//		int occurences = 0;
//		int candidateIndex = -1;
//		for (int i = 0; i < A.length; i++) {
//			if (A[i] == candidate) {
//				occurences++;
//				candidateIndex = i;
//				
//			}
//			
//		}
//		if (occurences > A.length /2) {
//			return candidateIndex;
//		}
//		return -1;
//		
//	}
	
	//hashmap
	public int solution(int[] A) {
		if (A.length ==0) return -1;
		
		HashMap<Integer, Integer> counts = new HashMap<>();
		
		for (int i = 0; i < A.length; i++) {
			int val = A[i];
			int c = counts.getOrDefault(val, 0) + 1;
			counts.put(val, c);
			if (c > A.length / 2) {
				return i;
			}
		}
		return -1;
	}
	
	

	public static void main(String[] args) {
		
		int[] A = {3,3,3,3,3,3,1};
		
		DominatorValue dominator = new DominatorValue();
		
		System.out.print(dominator.solution(A));

	}
}
