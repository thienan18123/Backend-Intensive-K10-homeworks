package baitap_week4;

public class numPair {

	public static int solution(int [] A) {
		long evenCount = 0;
		long oddCount =0;
		
		for (int i = 0; i < A.length; i++) {
			if (A[i] % 2 == 0) {
				evenCount++;
			} else {
				oddCount++;
			}
		}
		
		//
		long evenPairs = evenCount * (evenCount - 1)/2;
		long oddPairs = oddCount * (oddCount - 1)/2;
		long total = evenPairs + oddPairs;
		
		//check limit
		if (total > 1_000_000_000L) {
			return -1;
		}
		return (int) total;
	}

	public static void main(String[] args) {
		int[] A1 = {1_000_000_000, -1_000_000_000, 2};
		System.out.print(solution(A1));

	}

}
