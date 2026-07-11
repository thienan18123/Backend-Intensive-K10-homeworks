package DSA_Homework;


public class Be10Main {

    public static void main(String[] args) {
        Be10LinkedList ll = new Be10LinkedList();

        System.out.println("=== TEST addAtEnd ===");
        ll.addAtEnd(makeNode(1));   // [1]
        ll.addAtEnd(makeNode(2));   // [1] → [2]
        ll.addAtEnd(makeNode(3));   // [1] → [2] → [3]
        ll.printList();             // mong đợi: 1 → 2 → 3 → null

        System.out.println("\n=== TEST addAtIndex ===");
        ll.addAtIndex(makeNode(9), 1);   // chèn 9 vào index 1
        ll.printList();                   // mong đợi: 1 → 9 → 2 → 3 → null

        ll.addAtIndex(makeNode(0), 0);   // chèn 0 vào đầu
        ll.printList();                   // mong đợi: 0 → 1 → 9 → 2 → 3 → null

        System.out.println("\n=== TEST get ===");
        System.out.println("get(0) = " + ll.get(0));   // mong đợi: 0
        System.out.println("get(2) = " + ll.get(2));   // mong đợi: 9
        System.out.println("get(4) = " + ll.get(4));   // mong đợi: 3

        System.out.println("\n=== TEST remove ===");
        ll.remove(0);        // xóa đầu (số 0)
        ll.printList();      // mong đợi: 1 → 9 → 2 → 3 → null

        ll.remove(1);        // xóa index 1 (số 9)
        ll.printList();      // mong đợi: 1 → 2 → 3 → null
    }

    // Hàm phụ tạo node nhanh cho gọn
    static Be10LinkedListItem makeNode(int value) {
        Be10LinkedListItem node = new Be10LinkedListItem();
        node.value = value;
        return node;
    }
}