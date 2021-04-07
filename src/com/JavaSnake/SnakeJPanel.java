package com.JavaSnake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class SnakeJPanel extends JPanel implements KeyListener, ActionListener {

    Rectangle rect;
    Rectangle forageRect;
    Rectangle screenBounds;
    LinkedList<Rectangle> rects;
    Timer timer1;
    Random randX;
    Random randY;

    int snakeSize = 3;
    int partWidth = 20;
    int partHeight = 20;
    int snakeStartingCoordX = 20;
    int snakeStartingCoordY = 40;
    int delay = 500;
    int direction = 1;// default direction is right
    int randIntX = -1;
    int randIntY = -1;

    boolean directionCanBeChanged = false;

    public SnakeJPanel() {
        super();
        rect = new Rectangle();
        rects = new LinkedList<Rectangle>();
        timer1 = new Timer(delay, this);
        timer1.start();
        randX = new Random();
        randY = new Random();
        randIntX = randX.nextInt(30) * partWidth + partWidth - 1;
        randIntY = randY.nextInt(21) * partHeight + partHeight - 1;
        forageRect = new Rectangle(randIntX, randIntY, partWidth + 2, partHeight + 2);
        screenBounds = new Rectangle(1, 0, 621, 439);

        create();
    }

    public void create() {
        //restart
        if (!rects.isEmpty()) {

            timer1.stop();

            rects.remove();

            snakeSize = 3;
            partWidth = 20;
            partHeight = 20;
            snakeStartingCoordX = 20;
            snakeStartingCoordY = 40;
            delay = 500;
            direction = 1;// default direction is right

            directionCanBeChanged = false;

            randIntX = randX.nextInt(30) * partWidth + partWidth - 1;
            randIntY = randY.nextInt(21) * partHeight + partHeight - 1;
            forageRect = new Rectangle(randIntX, randIntY, partWidth + 2, partHeight + 2);

            rect = new Rectangle();
            rects = new LinkedList<Rectangle>();
            timer1 = new Timer(delay, this);
            timer1.start();
        }

        //Snake creating
        for (int i = 0; i < snakeSize; i++) {
            rect = new Rectangle(snakeStartingCoordX, snakeStartingCoordY, partWidth, partHeight);
            rects.add(i, rect);
            snakeStartingCoordX += partWidth;
        }

    }

    public void moveAction() {
        /*
        int fiveX = (int) rects.get(4).getX();
        int fiveY = (int) rects.get(4).getY();
        int fourX = (int) rects.get(3).getX();
        int fourY = (int) rects.get(3).getY();
        int threeX = (int) rects.get(2).getX();
        int threeY = (int) rects.get(2).getY();
        int twoX = (int) rects.get(1).getX();
        int twoY = (int) rects.get(1).getY();

        Rectangle four = new Rectangle();
        four = rects.get(3);
        Rectangle three = new Rectangle();
        three = rects.get(2);
        Rectangle two = new Rectangle();
        two = rects.get(1);
        Rectangle one = new Rectangle();
        one = rects.get(0);

        four.setLocation(fiveX, fiveY);
        three.setLocation(fourX, fourY);
        two.setLocation(threeX, threeY);
        one.setLocation(twoX, twoY);
        */

        // Let's convert the above codes to for loop

        for (int i = 0; i < rects.size() - 1; i++) {
            int tempX = (int) rects.get(i + 1).getX();
            int tempY = (int) rects.get(i + 1).getY();

            Rectangle tempRect;
            tempRect = rects.get(i);
            tempRect.setLocation(tempX, tempY);

        }
        directionCanBeChanged = true;
    }


    public void snakeIsGrowingUp(int howManyParts) {
        for (int j = 0; j < howManyParts; j++) {
            Rectangle temp = rects.getLast();
            switch (direction) {
                case 0: {
                    rect = new Rectangle(temp.x, temp.y - partHeight, partWidth, partHeight);
                }
                break;
                case 1: {
                    rect = new Rectangle(temp.x + partWidth, temp.y, partWidth, partHeight);
                }
                break;
                case 2: {
                    rect = new Rectangle(temp.x, temp.y + partHeight, partWidth, partHeight);
                }
                break;
                case 3: {
                    rect = new Rectangle(temp.x - partWidth, temp.y, partWidth, partHeight);
                }
                break;
                default: {
                    direction = 1;
                    System.out.println("The direction has been right.");
                }
            }

            rects.addLast(rect);
        }
    }

    public void snakeIsShorting(int howManyParts) {
        for (int j = 0; j < howManyParts; j++) {
            if (rects.size() > snakeSize) {
                rects.removeFirst();
            }
        }
    }

    public void createForage() {
        randIntX = randX.nextInt(30) * partWidth + partWidth - 1;
        randIntY = randY.nextInt(21) * partHeight + partHeight - 1;
        forageRect.setLocation(randIntX, randIntY);
    }

    public void intersectionControl() {
        //intersection with itself
        interSectionWithItself();

        //intersection with forage
        if (rects.getLast().intersects(forageRect)) {
            createForage();
            snakeIsGrowingUp(1);
        }

        //intersection with border
        if (!rects.getLast().intersects(screenBounds)) {
            create();
        }
    }

    public void interSectionWithItself() {
        Iterator<Rectangle> it = rects.iterator();
        Rectangle temp2 = rects.getLast();
        boolean intersection = false;

        while (it.hasNext()) {
            Rectangle temp = it.next();
            if (temp != temp2) {
                intersection = temp.intersects(temp2);
            }

            try{
                if (intersection) {
                    create();
                    System.out.println("Crashed");
                }
            } catch (Exception e){
                System.out.println("Error");
            }
        }

    }

    public void paint(Graphics g) {
        super.paint(g);

        Iterator<Rectangle> it = rects.iterator();
        while (it.hasNext()) {
            Rectangle temp = it.next();
            g.drawRect(temp.x, temp.y, temp.width, temp.height);
        }
        //forage
        g.setColor(new Color(0, 155, 255));
        g.fillRect((int) forageRect.getX(), (int) forageRect.getY(),
                (int) forageRect.getWidth(), (int) forageRect.getHeight());
        g.setColor(Color.BLACK);


        //border
        g.drawRect((int) screenBounds.getX(), (int) screenBounds.getY(),
                (int) screenBounds.getWidth(), (int) screenBounds.getHeight());

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'a') {
            snakeIsGrowingUp(1);
        } else if (e.getKeyChar() == 's') {
            snakeIsShorting(1);
        } else if (e.getKeyChar() == 'd') {
            create();
        } else if (e.getKeyChar() == 'f') {
            createForage();
        } else if (e.getKeyCode() == e.VK_UP) {
            if (direction != 2 && directionCanBeChanged == true) {
                direction = 0;
                directionCanBeChanged = false;
            }
        } else if (e.getKeyCode() == e.VK_RIGHT) {
            if (direction != 3 && directionCanBeChanged == true) {
                direction = 1;
                directionCanBeChanged = false;
            }
        } else if (e.getKeyCode() == e.VK_DOWN) {
            if (direction != 0 && directionCanBeChanged == true) {
                direction = 2;
                directionCanBeChanged = false;
            }
        } else if (e.getKeyCode() == e.VK_LEFT) {
            if (direction != 1 && directionCanBeChanged == true) {
                direction = 3;
                directionCanBeChanged = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        intersectionControl();

        moveAction();

        switch (direction) {
            case 0: {
                rects.getLast().setLocation((int) rects.getLast().getX(),
                        (int) rects.getLast().getY() - partHeight);
            }
            break;
            case 1: {
                rects.getLast().setLocation((int) rects.getLast().getX() + partWidth,
                        (int) rects.getLast().getY());
            }
            break;
            case 2: {
                rects.getLast().setLocation((int) rects.getLast().getX(),
                        (int) rects.getLast().getY() + partHeight);
            }
            break;
            case 3: {
                rects.getLast().setLocation((int) rects.getLast().getX() - partWidth,
                        (int) rects.getLast().getY());
            }
            break;
            default: {
                direction = 1;
                System.out.println("The direction has been right.");
            }
        }
        repaint();
    }


}
