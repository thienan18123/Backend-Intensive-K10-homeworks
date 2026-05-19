package baitap_week2;

import java.util.Scanner;

public class SingleNumber {

	public int SingleNumber(int [] nums) {
		int[] count = new int[nums.length + 1];
		//loop through nums, count each number
		for (int i = 0; i < nums.length; i++) {
			count[nums[i]]++; //nums[i] is the index into count
		}
		for (int i = 0; i < count.length; i++) {
			if (count[i] == 1) {
				return i;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("How many numbers? ");
		int size = scanner.nextInt();
		int[] nums = new int[size];
		for (int i = 0; i < nums.length; i++) {
		    System.out.print("Enter number " + (i + 1) + ": ");
		    nums[i] = scanner.nextInt();
		    while (nums[i] <= 0) {
		        System.out.println("Only positive numbers!");
		        System.out.print("Enter number " + (i + 1) + ": ");
		        nums[i] = scanner.nextInt();
		    }
		}
		scanner.close();
		SingleNumber sn = new SingleNumber();
		System.out.print("Single number is: " + sn.SingleNumber(nums));
		

	}
	

}
