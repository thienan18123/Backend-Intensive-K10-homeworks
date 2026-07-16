package DSA_Homework;

public class smallestIndex {

	public int solution(int [] A) {
		//count unique values
		int[] count = new int[A.length];
		int totalUnique = 0;
		for (int i = 0; i < A.length; i++) {
			if (count[A[i]] == 0) {
				totalUnique++;
			}
			count[A[i]]++;
		}
		
		//Scan to find all values
		int[] seen = new int[A.length];
		int seenCount = 0;
		for (int i = 0; i < A.length; i++) {
			if (seen[A[i]] == 0) {
				seenCount++;
			}
			seen[A[i]]++;
			if (seenCount == totalUnique) {
				return i;
			}
		}
		return A.length - 1; 
	}
	

	public static void main(String[] args) {
		smallestIndex s = new smallestIndex();
		System.out.println(s.solution(new int[] {2,0,1,1,2,0}));
		System.out.println(s.solution(new int[] {2,1,3,0,2,3,0}));
		System.out.println(s.solution(new int[] {2,1,3,2}));

	}

}
