package SystemDesign.Parking.SnakeGame;


public class SnakeMain {

    public static void main(String[] args) {
        eatFruit();
        eatBomb();
        hitWall();
        eatItself();
        win();
    }

    
    private static void eatFruit() {
        System.out.println("1. Eat Fruit ");
        SnakeGame game = new SnakeGame(5, 5);
        Snake snake = game.getSnake();
        Garden garden = game.getGarden();

        garden.clearItems();
        garden.placeFruit(new Cell(1, 1));      
        System.out.println("Snake length before eating fruit = " + snake.length());
        garden.print(snake);

        String result = game.move(1, 0);        
        System.out.println("Result: " + result + " — new length = " + snake.length());
        garden.print(snake);
    }

    
    private static void eatBomb() {
        System.out.println("2. Eat Bomb");
        SnakeGame game = new SnakeGame(5, 5);
        Snake snake = game.getSnake();
        Garden garden = game.getGarden();

        garden.clearItems();
        snake.addHead(new Cell(0, 2));          
        garden.placeBomb(new Cell(1, 2));
        System.out.println("Snake length before eating bomb = " + snake.length());
        garden.print(snake);

        String result = game.move(1, 0);        
        System.out.println("Result: " + result + "  — new length = " + snake.length());
        garden.print(snake);
    }

    
    private static void hitWall() {
        System.out.println("3. Hit the wall");
        SnakeGame game = new SnakeGame(5, 5);
        Garden garden = game.getGarden();

        garden.clearItems();
        garden.print(game.getSnake());

        String result = game.move(-1, 0);       
        System.out.println("Move up from the start: " + result + "\n");
    }

    
    private static void eatItself() {
        System.out.println("4. Eat itself");
        SnakeGame game = new SnakeGame(5, 5);
        Snake snake = game.getSnake();
        Garden garden = game.getGarden();

        garden.clearItems();
        snake.addHead(new Cell(0, 2));          
        garden.print(snake);

        String result = game.move(0, -1);       
        System.out.println("Eat itself: " + result + "\n");
    }

    
    private static void win() {
        System.out.println("5. Win");
        SnakeGame game = new SnakeGame(2, 2);   
        Snake snake = game.getSnake();
        Garden garden = game.getGarden();

        garden.clearItems();
        garden.placeFruit(new Cell(1, 1));
        System.out.println("Result: " + game.move(1, 0) + " — snake length = " + snake.length());

        garden.placeFruit(new Cell(1, 0));      
        String result = game.move(0, -1);       
        System.out.println("Result " + result + " — snake length = " + snake.length()
                + "/" + garden.size());
        garden.print(snake);
    }
}
