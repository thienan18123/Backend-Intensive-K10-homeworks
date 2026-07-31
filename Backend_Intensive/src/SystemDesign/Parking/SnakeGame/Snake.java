package SystemDesign.Parking.SnakeGame;

import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;

public class Snake {
    private LinkedList<Cell> body;      // thân rắn
    private Set<Cell> occupied;         // check tự cắn O(1)

    public Snake(Cell start1, Cell start2) {
        body = new LinkedList<>();
        occupied = new HashSet<>();
        addHead(start2);
        addHead(start1);
    }

    public Cell getHead() { 
        return body.peekFirst(); 
    }
    public Cell getTail() { 
        return body.peekLast(); 
    }
    public int length()   { 
        return body.size(); 
    }

    public boolean contains(Cell cell) {
        return occupied.contains(cell);
    }

    
    public void addHead(Cell cell) {
        body.addFirst(cell);
        occupied.add(cell);
    }

    
    public void removeTail() {
        Cell tail = body.removeLast();
        occupied.remove(tail);
    }
}