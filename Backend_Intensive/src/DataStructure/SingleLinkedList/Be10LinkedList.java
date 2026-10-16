package DataStructure.SingleLinkedList;



public class Be10LinkedList {
    public Be10LinkedListItem head;   

    
    void addAtEnd(Be10LinkedListItem item) {
        if (head == null) {
            head = item;
            return;
        }
        Be10LinkedListItem current = head;
        while (current.next != null) {
            current = current.next;   
        }
        current.next = item;
    }

   
    void addAtIndex(Be10LinkedListItem item, int index) {

        if (index == 0) {
            item.next = head;   
            head = item;        
            return;
        }
        
        Be10LinkedListItem current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        
        item.next = current.next;   
        current.next = item;        
    }

   
    void remove(int index) {
        if (index == 0) {
            head = head.next;   
            return;
        }
        Be10LinkedListItem current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        current.next = current.next.next;
    }

    int get(int index) {
        Be10LinkedListItem current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    void printList() {
        Be10LinkedListItem current = head;
        while (current != null) {
            System.out.print(current.value + " → ");
            current = current.next;
        }
        System.out.println("null");
    }
}