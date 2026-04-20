package org.clkairos;

import javax.swing.*;

public class Main {
    private static boolean debug = false;

    public static int WINDOW_WIDTH = 1280;
    public static int WINDOW_HEIGHT = 720;

    public static void main(String[] args){
        Main main = new Main();

        if (args.length > 0){
            main.handleArgs(args);
        }

        log("Debug mode: " + debug);
        log("Window size: " + WINDOW_WIDTH + "x" + WINDOW_HEIGHT);

        renderingManager rm = new renderingManager(new taskManager());
        rm.init(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public void handleArgs(String[] args){
        log("Handling " + args.length + " argument(s)");
        for (int i = 0; i < args.length; i++) {
            log("Arg[" + i + "]: " + args[i]);
            switch (args[i]){
                case "debug":
                    debug = true;
                    break;
                default:
                    System.err.println("[WARN] Unknown argument: " + args[i]);
            }
        }
    }

    /**
     * Central debug logger. Call this from any class.
     * Only prints when debug mode is enabled.
     */
    public static void log(String message){
        if (debug){
            System.out.println("[DEBUG] " + message);
        }
    }

    public static boolean getDebug(){
        return debug;
    }
}