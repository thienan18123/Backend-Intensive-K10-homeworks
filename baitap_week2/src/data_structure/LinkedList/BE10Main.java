package data_structure.LinkedList;

public class BE10Main {
    public static void main(String[] args) {
        BE10LinkedList list = new BE10LinkedList();

        list.addAtEnd(10);
        list.addAtEnd(20);
        list.addAtEnd(30);
        list.displayAll();        // [10 ⇄ 20 ⇄ 30]  size=3
        list.displayReverse();    // [30 ⇄ 20 ⇄ 10]

        list.addAtIndex(1, 15);
        list.displayAll();        // [10 ⇄ 15 ⇄ 20 ⇄ 30]
        list.displayReverse();    // [30 ⇄ 20 ⇄ 15 ⇄ 10]  ← kiểm tra prev!

        System.out.println(list.get(2));        // 20
        System.out.println(list.get(0));        // 10

        list.remove(1);
        list.displayAll();        // [10 ⇄ 20 ⇄ 30]

        System.out.println(list.removeAtLast()); // 30
        System.out.println(list.poll());         // 10
        list.displayAll();        // [20]  size=1

        System.out.println(list.poll());         // 20
        list.displayAll();        // []  size=0  ← test cạn kiệt
        list.displayReverse();    // []           ← tail phải = null
    }
}
