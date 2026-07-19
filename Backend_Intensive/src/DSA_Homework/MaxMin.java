package DSA_Homework;

import java.util.Scanner;

public class MaxMin {

	static int findMax(int[] nums) {
		int max = nums[0];
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] > max) {
				max = nums[i];
			}
		}
		return max; //return max value
		
	}
	
	static int findMin(int[] nums) {
		int min = nums[0];
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] < min) {
				min = nums[i];
			}
		}
		return min; //return min value
		
	}
	
	static double findAverage(int[] nums) {
		int sum = 0;
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i]; //add numbers to sum
		}
		double average = sum / (double) nums.length; //find average
		return average; 
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("How many numbers? ");
		int size = scanner.nextInt();
		
		int[] nums = new int[size]; //array with size
		
		for (int i = 0; i < nums.length; i++) {
			System.out.print("Enter number " + (i + 1) + ": ");
			nums[i] = scanner.nextInt();
			
		}
		scanner.close();
		System.out.println("Max number is: " + findMax(nums));
		System.out.println("Min number is: " + findMin(nums));
		System.out.printf("Average number is: %.2f%n", findAverage(nums));

	}
	

}
