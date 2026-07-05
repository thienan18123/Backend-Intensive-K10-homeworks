package DSA_Homework;

public class minCoins {
	
	public static int changeCoins(int money) {
		int[] coins = {20,10,5,1};
		int count = 0;
		
		for (int coin : coins) {
			count += money/coin;
			money = money % coin;
		}
		
		return count;
		
	}
	

	public static void main(String[] args) {
		System.out.println(changeCoins(10));//2
		System.out.println(changeCoins(10));
		System.out.println(changeCoins(50));

	}

}
