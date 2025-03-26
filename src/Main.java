import java.io.*;
import java.util.*;

public class Main {
    static List<String> todoList = new ArrayList<>();
    static List<String> startedList = new ArrayList<>();
    static List<String> finishedList = new ArrayList<>();
    boolean debug = true;

    public static void main(String[] args) {
        Main main = new Main();
        main.init();
    }

    public void init() {
        FileManager.saveToArray();
        start();
    }

    public void debug(String string){
        System.out.print(string + " activated debug. \n");
    }

    public void start(){
        Scanner scn = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("What do you want to do \n 1. Read Tasks \n 2. Add Task \n 3. Start Task \n 4. Finish Task \n 5. Delete Task \n");
                int input = scn.nextInt();
                if (input == 1){
                    if(debug){
                        debug("Read tasks");
                    }
                    ArrayManager.readtasks(4, 0);
                } if (input == 2){
                    if(debug){
                        debug("Add task");
                    }
                    ArrayManager.addtask();
                } if (input == 3){
                    if(debug){
                        debug("Start task");
                    }
                    ArrayManager.starttask();
                } if (input == 4){
                    if(debug){
                        debug("Finish Task");
                    }
                    ArrayManager.finishtask();
                } if (input == 5){
                    if(debug){
                        debug("Delete Task");
                    }
                    //deletetask();
                }
            } catch (Exception e) {
                System.out.println("An error occurred while getting input.");
                e.printStackTrace();
            }
        }
    }
}

