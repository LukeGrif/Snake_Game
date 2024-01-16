package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Graphics extends JPanel implements ActionListener {


    static final int WIDTH = 800;
    static final int HEIGHT = 800;
    static final int TICK_SIZE = 50;
    static final int BOARD_SIZE = (WIDTH * HEIGHT) / (TICK_SIZE * TICK_SIZE);

    final Font font = new Font("TimesRoman", Font.BOLD, 30);

    int[] snakePosX = new int[BOARD_SIZE];
    int[] snakePosY = new int[BOARD_SIZE];
    int snakeLength;

    Food food;
    int foodEaten;

    char direction = 'R';
    boolean isMoving = false;
    final Timer timer = new Timer(150, this);

    private final ImageIcon leftmouth = new ImageIcon(getClass().getResource("leftmouth.png"));
    private final ImageIcon rightmouth = new ImageIcon(getClass().getResource("rightmouth.png"));
    private final ImageIcon upmouth = new ImageIcon(getClass().getResource("upmouth.png"));
    private final ImageIcon downmouth = new ImageIcon(getClass().getResource("downmouth.png"));
    private final ImageIcon bodysnake = new ImageIcon(getClass().getResource("bodysnakle.png"));
    private final ImageIcon apple = new ImageIcon(getClass().getResource("apple.png"));

    public Graphics(){



        this.setPreferredSize((new Dimension(WIDTH, HEIGHT)));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener((new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (isMoving) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                            if (direction != 'R') {
                                direction = 'L';
                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (direction != 'L') {
                                direction = 'R';
                            }
                            break;
                        case KeyEvent.VK_UP:
                            if (direction != 'D') {
                                direction = 'U';
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            if (direction != 'U') {
                                direction = 'D';
                            }
                            break;
                    }
                }else
                    start();
            }
        }));

        start();
    }

    protected void start() {
        snakePosX = new int[BOARD_SIZE];
        snakePosY = new int[BOARD_SIZE];
        snakeLength = 5;
        foodEaten = 0;
        direction = 'R';
        isMoving = true;
        spawnfood();
        timer.start();
    }

    @Override
    protected void paintComponent(java.awt.Graphics graphics) {
        super.paintComponent(graphics);



        if(isMoving) {

            apple.paintIcon(this, graphics, food.getPosX(), food.getPosY());

            if(direction == 'L') {
                leftmouth.paintIcon(this, graphics, snakePosX[0], snakePosY[0]);
            }

            if(direction == 'R') {
                rightmouth.paintIcon(this, graphics, snakePosX[0], snakePosY[0]);
            }

            if(direction == 'D') {
                downmouth.paintIcon(this, graphics, snakePosX[0], snakePosY[0]);
            }
            if(direction == 'U') {
                upmouth.paintIcon(this, graphics, snakePosX[0], snakePosY[0]);
            }

            graphics.setColor(Color.GREEN);
            for (int i = 1; i < snakeLength; i++) {
                bodysnake.paintIcon(this, graphics, snakePosX[i], snakePosY[i]);
            }


        } else {
            String scoreTest = String.format("The End... Score %d... Press a Direction Key to play again", foodEaten);
            graphics.setColor(Color.BLUE);
            graphics.setFont(font);
            graphics.drawString(scoreTest, (WIDTH - getFontMetrics(graphics.getFont()).stringWidth(scoreTest))/2, HEIGHT / 2);
        }
    }

    protected  void move() {
        for (int i = snakeLength; i > 0; i--) {
            snakePosX[i] = snakePosX[i-1];
            snakePosY[i] = snakePosY[i-1];
        }

        switch (direction) {
            case 'U' -> snakePosY[0] -= TICK_SIZE;
            case 'D' -> snakePosY[0] += TICK_SIZE;
            case 'L' -> snakePosX[0] -= TICK_SIZE;
            case 'R' -> snakePosX[0] += TICK_SIZE;
        }
    }

    protected void spawnfood()
    {
        food = new Food();
    }

    protected void eatFood() {
        if ((snakePosX[0] == food.getPosX()) && (snakePosY[0] == food.getPosY())){
            snakeLength++;
            foodEaten++;
            spawnfood();
        }
    }

    protected void collisionTest() {
        for (int i = snakeLength; i > 0; i--) {
            if ((snakePosX[0] == snakePosX[i]) && (snakePosY[0] == snakePosY[i])) {
                isMoving = false;
                break;
            }
        }

        if (snakePosX[0] < 0 || snakePosX[0] > WIDTH - TICK_SIZE || snakePosY[0] < 0 || snakePosY[0] > HEIGHT - TICK_SIZE){
            isMoving = false;
        }

        if (!isMoving){
            timer.stop();
        }
    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (isMoving) {
            move();
            collisionTest();
            eatFood();
        }
        repaint();
    }
}
