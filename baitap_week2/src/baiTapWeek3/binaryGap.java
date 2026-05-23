package baiTapWeek3;

public class binaryGap {

	public int solution(int N) {
		String binary = Integer.toBinaryString(N);
		int currentGap = 0;
		int maxGap = 0;
		boolean counting = false;
		
		//counting 0s between 1, stop when count 1
		for (int i = 0; i <binary.length(); i++) {
			if (binary.charAt(i) == '1') {
				if (counting) {
					if (currentGap > maxGap) {
						maxGap = currentGap;
						
					}
				}
				counting = true;
				currentGap = 0; //reset
			} else {
				if (counting) {
					currentGap++;
				}
			}
		}
		return maxGap;
	}

	public static void main(String[] args) {
		binaryGap bg = new binaryGap();
		System.out.println(bg.solution(1041));
		System.out.println(bg.solution(4132));
		System.out.println(bg.solution(5242));
		System.out.println(bg.solution(3145));
		

	}

}
