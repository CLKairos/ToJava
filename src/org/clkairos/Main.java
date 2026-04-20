package org.clkairos;

import javax.swing.*;

public class Main {
    public static boolean debug = false; //Global debug variable

    //Window size variables
    public static int WINDOW_WIDTH = 1280;
    public static int WINDOW_HEIGHT = 720;

    public static void main(String[] args){
        Main main = new Main();
        renderingManager rm = new renderingManager(new taskManager());
        if (args.length > 0){ //if there are args then it will handle them in another function
            main.handleArgs(args);
        }
        rm.init(WINDOW_WIDTH, WINDOW_HEIGHT); //initializes the frame then starts the main loop
    }

    public void handleArgs(String[] args){
        for(int i = 0; i < args.length; i++) {
            switch (args[i]){
                case "debug":
                    debug = true;
            }
        }
    }

    public static boolean getDebug(){
        return debug;
    }
}