package DataStructure.Tree.MinHeap;
public class MinHeap {
    private Node[] heap;
    private int size;

    public MinHeap(int capacity){
        heap = new Node[capacity];
        size = 0;
    }

    private int parent(int i) {
        return (i-1)/2;
    }
    private int left(int i) {
        return 2*i +1;
    }
    private int right(int i) {
        return 2*i + 2;
    }

    private void swap(int a, int b) {
        Node tmp = heap[a];
        heap[a] = heap[b];
        heap[b] = tmp;
    }

    public void addNode(Node node) {
        if (size == heap.length) {
            return;
        }
        heap[size] = node;
        size++;
        bubbleUp(size - 1);
    }

    private void bubbleUp(int i) {
        while (i > 0) {
            int p = parent(i);
            if (heap[i].value >= heap[p].value) {
                break;
            }
            swap(i,p);
            i = p;
        }
    }

    public Node removeMinNode() {
        if (size == 0) return null;

        Node min = heap[0];          

        heap[0] = heap[size - 1];           // đưa node CUỐI lên gốc
        heap[size - 1] = null;            // dọn ô cuối
        size--;

        bubbleDown(0);                    //chìm xuống
        return min;
    }

    private void bubbleDown(int i) {
        while (true) {
            int l = left(i), r = right(i);
            int smallest = i;

            if (l < size && heap[l].value < heap[smallest].value)  smallest = l;
            if (r < size && heap[r].value < heap[smallest].value)  smallest = r;

            if (smallest == i) break;     
            swap(i, smallest);
            i = smallest;                   //tương tự bubbleUp, nhưng đi XUỐNG
        }
    }
    
    
}
