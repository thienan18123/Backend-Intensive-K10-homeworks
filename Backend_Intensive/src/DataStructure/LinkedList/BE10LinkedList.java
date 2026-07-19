package DataStructure.LinkedList;

public class BE10LinkedList {
	public BE10LinkedListItem head;   // node đầu
    public BE10LinkedListItem tail;   // node cuối  
    public int size;                  // đếm số phần tử
    
    
    public void addAtEnd(int value) {
        BE10LinkedListItem item = new BE10LinkedListItem(value);

        if (head == null) {            // list rỗng
            head = item;
            tail = item;
        } else {
            item.prev = tail;          // ① node mới nhìn ngược về tail cũ
            tail.next = item;          // ② tail cũ nhìn xuôi tới node mới
            tail = item;               // ③ tail giờ là node mới
        }
        size++;
    }
    
 // Hàm phụ: tìm NODE tại index (dùng lại cho nhiều hàm khác)
    private BE10LinkedListItem getNode(int index) {
        if (index < 0 || index >= size) return null;    // ngoài phạm vi

        BE10LinkedListItem cur;
        if (index < size / 2) {
            cur = head;                                  // gần đầu → đi xuôi
            for (int i = 0; i < index; i++) {
                cur = cur.next;
            }
        } else {
            cur = tail;                                  // gần cuối → đi NGƯỢC
            for (int i = size - 1; i > index; i--) {
                cur = cur.prev;
            }
        }
        return cur;
    }

    public int get(int index) {
        BE10LinkedListItem node = getNode(index);
        if (node == null) {
            throw new IndexOutOfBoundsException("Index không hợp lệ: " + index);
        }
        return node.value;
    }
    
    public void addAtIndex(int index, int value) {
        if (index < 0 || index > size) return;      // index == size là hợp lệ (thêm cuối)

        if (index == size) {                         // chèn cuối
            addAtEnd(value);
            return;
        }
        if (index == 0) {                            // chèn đầu
            addAtFirst(value);
            return;
        }

        // Chèn vào GIỮA
        BE10LinkedListItem cur = getNode(index);     // node ĐANG ở vị trí index
        BE10LinkedListItem before = cur.prev;        // node đứng trước nó
        BE10LinkedListItem item = new BE10LinkedListItem(value);

        item.prev = before;      // ①
        item.next = cur;         // ②
        before.next = item;      // ③
        cur.prev = item;         // ④
        size++;
    }

    public void addAtFirst(int value) {
        BE10LinkedListItem item = new BE10LinkedListItem(value);
        if (head == null) {
            head = item;
            tail = item;
        } else {
            item.next = head;    // ①
            head.prev = item;    // ②
            head = item;         // ③ head chuyển sang node mới
        }
        size++;
    }
    
    private void unlink(BE10LinkedListItem node) {
        // Nối node TRƯỚC với node SAU, bỏ qua node cần xóa
        if (node.prev != null) {
            node.prev.next = node.next;      // ① người trước trỏ vượt qua
        } else {
            head = node.next;                // node này là head → head lùi
        }

        if (node.next != null) {
            node.next.prev = node.prev;      // ② người sau nhìn ngược vượt qua
        } else {
            tail = node.prev;                // node này là tail → tail lùi
        }

        node.prev = null;                    // ③ cắt đứt hoàn toàn (giúp GC)
        node.next = null;
        size--;
    }

    public void remove(int index) {
        BE10LinkedListItem node = getNode(index);
        if (node == null) return;            // index không hợp lệ
        unlink(node);
    }
    
    public int removeAtLast() {
        if (tail == null) {
            throw new IllegalStateException("List rỗng");
        }
        int value = tail.value;
        unlink(tail);              // tail.prev là ra ngay → O(1) ✓
        return value;
    }
    
    public int poll() {
        if (head == null) {
            throw new IllegalStateException("List rỗng");
        }
        int value = head.value;
        unlink(head);
        return value;
    }
    public void displayAll() {
        System.out.print("Xuôi:  [");
        BE10LinkedListItem cur = head;
        while (cur != null) {
            System.out.print(cur.value);
            if (cur.next != null) System.out.print(" ⇄ ");
            cur = cur.next;
        }
        System.out.println("]  (size = " + size + ")");
    }

    public void displayReverse() {
        System.out.print("Ngược: [");
        BE10LinkedListItem cur = tail;
        while (cur != null) {
            System.out.print(cur.value);
            if (cur.prev != null) System.out.print(" ⇄ ");
            cur = cur.prev;          // đi NGƯỢC — singly không làm được!
        }
        System.out.println("]");
    }

}
