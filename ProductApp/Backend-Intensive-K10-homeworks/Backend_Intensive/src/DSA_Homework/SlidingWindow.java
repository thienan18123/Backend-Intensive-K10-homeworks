package DSA_Homework;

public class SlidingWindow {

	public int solution(int[] A) {
		//find unique values
		int[] count = new int[A.length + 1];
		int totalUnique = 0;
		for (int i = 0; i < A.length; i++) {
			if(count[A[i]] == 0) {
				totalUnique++;
			}
			count[A[i]]++;
		}
		//sliding window
		int[] window = new int[A.length + 1];
		int left = 0;
		int seenCount = 0;
		int minSize = A.length;
		
		//move from right
		for (int right = 0; right < A.length; right ++) {
			if(window[A[right]] == 0) seenCount++;
			window[A[right]]++;
			
			//shrink left
			while(seenCount == totalUnique) {
				minSize = Math.min(minSize, right - left + 1);
				window[A[left]]--;
				if(window[A[left]] == 0) {
					seenCount--;
				}
				left++;
			}
			
		}
		return minSize;
		
	}

	public static void main(String[] args) {
		SlidingWindow sw = new SlidingWindow();
        System.out.println(sw.solution(new int[]{2, 1, 0, 2, 1}));  
        System.out.println(sw.solution(new int[]{1, 2, 3, 4}));      
        System.out.println(sw.solution(new int[]{1, 1, 1, 1}));     
        System.out.println(sw.solution(new int[]{0, 1, 0, 1, 0})); 
        System.out.println(sw.solution(new int[]{0, 1, 2, 2, 0,1}));

	}

}
