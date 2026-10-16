package DSA_Homework;

import java.util.Arrays;

public class minCoins {
	
	//top down          
	public static int coinChange(int[] coins, int amount) {
		int[] memo = new int[amount + 1];
		return helper(coins,amount,memo);
	}
	
	public static int helper(int[] coins, int amount, int[] memo) {
		if (amount == 0) return 0;
		if (amount < 0) return -1;
		if (memo[amount] !=0) return memo[amount]; //memorize
		
		
		int min = Integer.MAX_VALUE;
		for (int coin : coins) {
			int coinRes = helper(coins, amount - coin, memo);
			if (coinRes >= 0 && coinRes < min) {
				min = Math.min(min, coinRes+ 1); // cộng số còn lại với xu đã dùng
			}
			if (min == Integer.MAX_VALUE) {
				memo[amount] = -1;
			} else {
				memo[amount] = min;
			}
		}
		return memo[amount];
		
	}
	
	//bottom up
//	public static int coinChange(int[] coins, int amount) {
//		int[] dp = new int[amount + 1];
//		Arrays.fill(dp, amount + 1); // vô cực
//		dp[0] = 0;
//		
//		
//		Arrays.sort(coins);
//		for (int i = 1; i <= amount; i++) {
//			for (int coin : coins) {
//				if (coin >  i) break; 
//				dp[i] = Math.min(dp[i], dp[i - coin] + 1);
//					
//				
//			}
//		}
//		if (dp[amount] > amount) {
//			return -1;
//		} else {
//			return dp[amount];
//		}
//	}
	
	

	public static void main(String[] args) {
		int[] testCoins = {3,1,5,10,7};
		
		System.out.print(coinChange(testCoins,35));

	}

}
