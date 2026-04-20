package org.clkairos;

import javax.swing.*;

public class Main {
    public static JFrame frame; //Global frame instance
    public static boolean debug = false; //Global debug variable

    //Global variables
    public static int WINDOW_WIDTH = 1280;
    public static int WINDOW_HEIGHT = 720;

    public static void main(String[] args){
        Main main = new Main();
        if (args.length > 0){ //if there are args then it will handle them in another function
            main.handleArgs(args);
        }
        main.init(); //initializes the frame then starts the main loop
    }

    public void init(){
        frame = new JFrame("Test JFrame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setVisible(true);

        while (true) {//while the program is running it will render the current scene
            renderScene();
            //if the current frame W+H arent equal to the default global variables
            //then it will set the global variables to the current W+H
            if (frame.getWidth() != WINDOW_WIDTH || frame.getHeight() != WINDOW_HEIGHT){
                WINDOW_WIDTH  = frame.getWidth();
                WINDOW_HEIGHT = frame.getHeight();
            }
            if (debug) System.out.println("Width: " + WINDOW_WIDTH + "\n" + "Height: " + WINDOW_HEIGHT); //if debug mode is enabled then it will print out the current W+H
            frame.repaint();
        }
    }

    public static void renderScene() {
        JPanel panel = new JPanel();
        JButton button = new JButton("Click Me");
        JLabel label = new JLabel("Hello World!");
        panel.add(button);
        panel.add(label);
        frame.add(panel);
    }

    public void handleArgs(String[] args){
        for(int i = 0; i < args.length; i++) {
            switch (args[i]){
                case "debug":
                    debug = true;
            }
        }
    }
}