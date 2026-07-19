package DSA_Homework;
import java.util.Arrays;

public class isTriangle {

	public int solution(int[] A) {
		Arrays.sort(A);
		for (int i = 0; i < A.length - 2; i++) {
			if ((long)A[i] + A[i+1] > A[i + 2]) {
				return 1;
			}
		}
		return 0;
		
	}

	public static void main(String[] args) {
		isTriangle T = new isTriangle();
		System.out.println(T.solution(new int[] {20,2,0,1}));
		System.out.println(T.solution(new int[] {10,1,5,1,8,20}));

	}

}
