package baitapweek5;

public class zeroArray {

	static void pushZerosToEnd(int[] arr) {
        int j =0;
        
        for (int i = 0; i < arr.length; i++) {
        	if (arr[i] != 0) {
        		int temp = arr[i];
        		arr[i] = arr[j];
        		arr[j] = temp;
        		j++;
        		
        	}
        	}
        }
	
    public static void main(String[] args) {
        int[] arr = {1, 2, 0, 4, 3, 0, 5, 0};
        pushZerosToEnd(arr);

        for (int num : arr) {
            System.out.print(num + " ");
        }
    }
}
