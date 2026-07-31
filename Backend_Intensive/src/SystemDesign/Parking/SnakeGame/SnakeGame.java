package SystemDesign.Parking.SnakeGame;

public class SnakeGame {
    private Snake snake;
    private Garden garden;

    public SnakeGame(int rows, int cols) {
        garden = new Garden(rows, cols);
        snake = new Snake(new Cell(0, 1), new Cell(0, 0));  
        garden.spawnFruit(snake);
        garden.spawnBomb(snake);
    }

    public Garden getGarden() {
        return garden;
    }

    public Snake getSnake() {
        return snake;
    }

    
    public String move(int dRow, int dCol) {
        Cell head = snake.getHead();
        Cell newHead = new Cell(head.getRow() + dRow, head.getCol() + dCol);

        // Đâm tường
        if (!garden.isInside(newHead)) return "LOSE";

        boolean eatFruit = garden.isFruit(newHead);

        // Tự cắn
        
        if (snake.contains(newHead)) {
            boolean tailMovesAway = !eatFruit && newHead.equals(snake.getTail());
            if (!tailMovesAway) return "LOSE";
        }

        // ăn quả
        if (eatFruit) {
            garden.clearCell(newHead);       
            snake.addHead(newHead);                         
            if (snake.length() == garden.size()) return "WIN";
            garden.spawnFruit(snake);
        }
        // Ăn bom
        else if (garden.isBomb(newHead)) {
            garden.clearCell(newHead);       
            snake.addHead(newHead);                         
            snake.removeTail();                          
            snake.removeTail();                         
            if (snake.length() == 0) {
                return "LOSE";
            }
            garden.spawnBomb(snake);
        }
        
        else {
            snake.removeTail();                            
            snake.addHead(newHead);                        
        }

        return "CONTINUE";
    }
}
