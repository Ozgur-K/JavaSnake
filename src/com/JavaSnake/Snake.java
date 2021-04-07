package com.JavaSnake;

import javax.swing.*;

public class Snake {

    static int width = 630;
    static int height = 470;


    public static void main(String[] args) {
        JFrame frame1 = new JFrame("Snake. No Library.");
        frame1.setBounds(348, 160, width, height);
        frame1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame1.setResizable(false);

        SnakeJPanel panel1 = new SnakeJPanel();

        frame1.add(panel1);

        frame1.setVisible(true);
        frame1.addKeyListener(panel1);
    }
}
