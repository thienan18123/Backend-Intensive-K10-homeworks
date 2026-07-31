package SystemDesign.SnakeGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Garden {
    private final int rows;
    private final int cols;
    private char[][] grid;         // '.' trống, 'F' trái cây, 'B' bom
    private Random rand = new Random();

    public Garden(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[rows][cols];


         for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = '.';
            }
        }
    }

    public int size() { 
        return rows * cols; 
    }

    // check tường
    public boolean isInside(Cell cell) {
        int r = cell.getRow(), c = cell.getCol();
        if (r >= 0 && r < rows && c >= 0 && c < cols) {
            return true;
        } else {
            return false;
        }
    }

    public char getCell(Cell cell) {
        return grid[cell.getRow()][cell.getCol()];
    }

    public boolean isFruit(Cell cell) { 
        return getCell(cell) == 'F'; 
    }
    public boolean isBomb(Cell cell)  {
        return getCell(cell) == 'B';
    }

    // check ô trống
    public boolean isFree(Cell cell, Snake snake) {
        return getCell(cell) == '.' && !snake.contains(cell);
    }

    
    private void setCell(Cell cell, char type) {
        grid[cell.getRow()][cell.getCol()] = type;
    }

    
    public void clearCell(Cell cell) {
        setCell(cell, '.');
    }

    //for demo
    public void placeFruit(Cell cell) {
        setCell(cell, 'F');
    }

    public void placeBomb(Cell cell) {
        setCell(cell, 'B');
    }

    
    public void clearItems() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = '.';
            }
        }
    }

    public void spawnFruit(Snake snake) {
        Cell cell = randomEmptyCell(snake);
        if (cell == null) {
            return;
        }      
        setCell(cell, 'F');
    }

    public void spawnBomb(Snake snake) {
        Cell cell = randomEmptyCell(snake);
        if (cell == null) {
            return;
        }      
        setCell(cell, 'B');
    }

    
    private Cell randomEmptyCell(Snake snake) {
        List<Cell> empty = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = new Cell(r, c);
                if (isFree(cell, snake)) {
                    empty.add(cell);
                }
            }
        }
        if (empty.isEmpty()) return null;
        return empty.get(rand.nextInt(empty.size()));
    }

    
    public void print(Snake snake) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = new Cell(r, c);
                if (snake.contains(cell)) System.out.print("O ");   // rắn
                else                      System.out.print(grid[r][c] + " ");
            }
            System.out.println();
        }
        System.out.println("Snake Length: " + snake.length() + "\n");
    }
}
