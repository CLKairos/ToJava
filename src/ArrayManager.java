import java.io.*;
import java.util.*;

public class ArrayManager {
    static Scanner scn = new Scanner(System.in);

    static void readtasks(int inputA, int inputB){
        if (inputA == 1 || inputA == 4){
            System.out.println("Todo: ");
            System.out.println(Main.todoList);
        }
        if (inputA == 2 || inputB == 2 || inputA == 4){
            System.out.println("Started: ");
            System.out.println(Main.startedList);
        }
        if (inputA == 3 || inputB == 3 || inputA == 4) {
            System.out.println("Finished: ");
            System.out.println(Main.finishedList);
        }
    }

    static void addtask(){
        try {
            int taskCount = Main.todoList.size();
            System.out.print("What do you need to do? \n");
            String input = scn.nextLine();
            Main.todoList.add((taskCount + 1) + ": " + input);
            FileManager.saveToFile();
        } catch (Exception e) {
            System.out.println("An error occurred while adding a task.");
            e.printStackTrace();
        }
    }

    static void starttask() {
        try {
            int index = -1;
            readtasks(1, 2);
            System.out.print("What do you need to start? \n");
            int input = scn.nextInt();

            for (int i = 0; i < Main.todoList.size(); i++) {
                String task = Main.todoList.get(i);
                String taskNumber = task.split(": ")[0];

                if (taskNumber.equals(String.valueOf(input))) {
                    index = i;
                    break;
                }
            }

            if (index != -1) {
                String taskToMove = Main.todoList.remove(index);
                Main.startedList.add(taskToMove);
                FileManager.saveToFile();
                System.out.println("Task started: " + taskToMove);
            } else {
                System.out.println("Task not found.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while adding a task.");
            e.printStackTrace();
        }
    }
}
