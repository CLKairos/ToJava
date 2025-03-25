import java.io.*;
import java.util.*;

public class Main {
    static String[] todo;
    static String[] started;
    static String[] finished;

    public static void main(String[] args) {
        Main main = new Main();
        main.init();
    }

    public void init() {
        FileManager.saveToArray();
        start();
    }

    public void start(){
        while (true) {
            Scanner scn = new Scanner(System.in);
            try {
                System.out.print("What do you want to do \n 1. Read Tasks \n 2. Add Task \n 3. Start Task \n 4. Finish Task \n 5. Delete Task \n");
                int input = scn.nextInt();
                if (input == 1){
                    //readtasks();
                    scn.close();
                } if (input == 2){
                    //addtask();
                    scn.close();
                } if (input == 3){
                    //starttask();
                    scn.close();
                } if (input == 4){
                    //finishtask();
                    scn.close();
                } if (input == 5){
                    //deletetask();
                    scn.close();
                }
            } catch (Exception e) {
                System.out.println("An error occurred while getting input.");
                e.printStackTrace();
            }
        }
    }
}

