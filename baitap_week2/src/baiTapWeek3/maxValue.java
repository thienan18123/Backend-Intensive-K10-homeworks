package baiTapWeek3;

public class maxValue {

	public int solution(int[] A) {
		int maxEven = 0;
		int maxOdd = 0;
		
		//loop
		for (int i = 0; i < A.length; i++) {
			//giu hoac skip de ra gia tri lon nhat
			int newEven = Math.max(maxEven, maxOdd + A[i]);
			int newOdd = Math.max(maxOdd, maxEven - A[i]);
			maxEven = newEven;
			maxOdd = newOdd;
		}
		return maxEven % 1000000000;
	}

	public static void main(String[] args) {
		maxValue m = new maxValue();
		System.out.println(m.solution(new int[] {4, 1, 2, 3}));
		System.out.println(m.solution(new int[] {1000000000, 1, 2, 300000000}));
		System.out.println(m.solution(new int[] {10, 3, 20, 1}));

	}

}
