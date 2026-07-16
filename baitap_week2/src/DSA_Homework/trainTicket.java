package DSA_Homework;

public class trainTicket {
	
	public static int solution(int[] Dates) {
		if (Dates.length == 0) {
			return 0;
		}
		
		boolean[] travel = new boolean[31];
		for (int day: Dates) {
			travel[day] = true;
		}
		
		int[]dp = new int[31];
		dp[0] = 0;
		
		for (int i = 1; i <= 30; i ++) {
			if (!travel[i]) {
				dp[i] = dp[i -1];
			} else {
				dp[i] = Math.min(dp[i - 1] + 2, Math.min(dp[Math.max(0, i - 7)] + 7, dp[Math.max(0, i - 30)] + 25));
			}
		}
		return dp[30];
		
	}

	public static void main(String[] args) {
		int[] testDates = {1,2,4,5,7,29,30};
		
		System.out.print(solution(testDates));

	}

}
