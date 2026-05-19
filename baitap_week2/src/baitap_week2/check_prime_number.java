package baitap_week2;

public class check_prime_number {

	public static boolean isPrimeNumber(int n) {
		// prime number must be greater than 1
		if (n <= 1) {
			return false;
		}
		// check divisibility from from 2 to square root n
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i ==0) {
				return false; //found divisor
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int n = 28;
		if(isPrimeNumber(n)) {
			System.out.print(n + " is a prime number");
		}
		else {
			System.out.print(n + " is not a prime number");
		}

	}

}
