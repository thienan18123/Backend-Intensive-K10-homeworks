package DSA_Homework;

import java.util.Arrays;

public class adjacentPairs {
	
	public static int solution(int[] A) {
		Arrays.sort(A);
		long total = 0;
		long prevCount = 0;
		int i = 0;
		
		while ( i < A.length) {
			int j = i;
			while (j < A.length && A[j] == A[i]) {
				j++;
			}
			long count = j - i;
			
			//cap cung gia tri
			total += count * (count - 1)/2;
			
			//hang xom
			total += prevCount * count;
			
			prevCount = count;
			i = j;
		}
		if (total > 100000000) {
			return -1;
		}
		return (int) total;
		
	}

	public static void main(String[] args) {
		int[] A = {0,3,3,7,5,3,11,1};
		System.out.println(solution(A));

	}

}
