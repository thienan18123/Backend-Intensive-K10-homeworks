package DSA_Homework;

public class DepthPit {
	
	public int solution(int[] A) {
		int maxDepth = -1;
		
		for (int i = 1; i < A.length - 1; i++) {
			if (A[i-1] > A[i] && A[i] < A[i+1]) {
				int Q = i; //bottom pit
				int P = i; //left triplet
				int R = i; //right
				
				while (P > 0 && A[P-1] > A[P]) {
					P--;
				}
				while (R < A.length -1 && A[R+1] > A[R]) {
					R++;
				}
				int depth = Math.min(A[P] - A[Q], A[R] - A[Q]);
				maxDepth = Math.max(maxDepth, depth);
			}
		}
		return maxDepth;
		
	}
	
	//prefix array
	
//	public int[] buildLeftPeaks(int[]A) {
//		int n = A.length;
//		int[] left = new int[n];
//		
//		left[0] = 0;
//		for (int i = 1; i < n; i++) {
//			if (A[i-1] > A[i]) {
//				left[i] = left[i - 1];
//		} else {
//			left [i] =i;
//			
//		} 
//		}
//		return left;
//	}
//	
//	public int[] buildrightPeaks(int[]A) {
//		int n = A.length;
//		int[] right = new int[n];
//		
//		right[n-1] = n-1;
//		for (int i = n - 2; i >= 0; i--) {
//			if (A[i] < A[i+1]) {
//				right[i] = right[i + 1];
//		} else {
//			right [i] =i;
//			
//		} 
//		}
//		return right;
//	}
	
//	public int solution(int[] A) {
//		int[] leftPeak = buildLeftPeaks(A);
//		int [] rightPeak = buildrightPeaks(A);
//		
//		int maxDepth = -1;
//		
//		for (int i = 1; i < A.length -1; i++) {
//			if (A[i-1] > A[i] && A[i] < A[i+1]) {
//				int P = leftPeak[i];
//				int R = rightPeak[i];
//				
//				int depth = Math.min(A[P] - A[i], A[R] - A[i]);
//				maxDepth = Math.max(maxDepth, depth);
//			}
//		}
//		
//		return maxDepth;
//        
//    }


	public static void main(String[] args) {
		int[] A = {0,1,3,-2,0,1,0,-3,2,3};
		
		DepthPit depth = new DepthPit();
		
		System.out.print(depth.solution(A));

	}

}
