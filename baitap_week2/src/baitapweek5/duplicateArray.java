package baitapweek5;

import java.util.HashSet;

public class duplicateArray {
//	public static int removeDuplicates(int[] arr) {
//        
//        // To track seen elements
//        HashSet<Integer> s = new HashSet<>(); //best for unsorted array
//        
//        // To maintain the new size of the array
//        int idx = 0;  
//
//        for (int i = 0; i < arr.length; i++) {
//            if (!s.contains(arr[i])) { 
//                s.add(arr[i]);  
//                arr[idx++] = arr[i];  
//            }
//        }
//
//        // Return the size of the array 
//        // with unique elements
//        return idx;
//    }
	
	//for sorted array - O(n)
	public static int removeDuplicates(int[] arr) {
		int n = arr.length;
		if (n < 1) {
			return n;
		}
		
		int idx = 1;
		
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] != arr[i-1]) {
				arr[idx++] = arr[i];
			}
		}
		return idx;
		
	}

    public static void main(String[] args) {
        int[] arr = {1, 2, 2, 3, 4, 4, 4, 5, 5};
        int newSize = removeDuplicates(arr);

        for (int i = 0; i < newSize; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}

