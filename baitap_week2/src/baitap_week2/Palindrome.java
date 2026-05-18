package baitap_week2;

import java.util.Scanner;

public class Palindrome {

	public static boolean isPalindrome(int[] arr) {
		int left = 0;
		int right = arr.length - 1;
		
		while (left < right) {
			if (arr[left] != arr[right]) {
				System.out.print("Array is not a palindrome");
				return false; // array is not a Palindrome
			}
			left++;
			right--;
		}
		System.out.print("Array is a palindrome");
		return true; //array is palindrome
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("How many numbers? ");
		int size = scanner.nextInt();
		
		int[] arr = new int[size]; //array with size
		
		for (int i = 0; i < arr.length; i++) {
			System.out.print("Enter number " + (i + 1) + ": ");
			arr[i] = scanner.nextInt();
			
		}
		scanner.close();
		isPalindrome(arr);
		
	}
}
