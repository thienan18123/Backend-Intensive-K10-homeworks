package baitap_week2;

public class check_prime_number {

	public static boolean isPrimeNumber(int n) {
		// prime number must be greater than 1
		if (n <= 1 || n % 2 ==0) {
			return false;
		}
		if (n == 2) {
			return true;
		}
		// check divisibility from from 2 to square root n
		for (int i = 3; i <= Math.sqrt(n); i+=2) {
			if (n % i ==0) {
				return false; //found divisor
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int n = 4;
		if(isPrimeNumber(n)) {
			System.out.print(n + " is a prime number");
		}
		else {
			System.out.print(n + " is not a prime number");
		}

	}

}
