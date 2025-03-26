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
                System.out.println("Task not found.\n");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while adding a task.\n");
            e.printStackTrace();
        }
    }

    static void finishtask() {
        try {
            int index = -1;
            readtasks(2, 3);
            System.out.print("What do you need to finish? \n");
            int input = scn.nextInt();
            for (int i = 0; i < Main.startedList.size(); i++) {
                String task = Main.startedList.get(i);
                String taskNumber = task.split(": ")[0];
                if (taskNumber.equals(String.valueOf(input))) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                String taskToMove = Main.startedList.remove(index);
                Main.finishedList.add(taskToMove);
                FileManager.saveToFile();
                System.out.println("Task finished: " + taskToMove);
            } else {
                System.out.println("Task not found.\n");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while adding a task.\n");
            e.printStackTrace();
        }
    }
    static void deletetask() {
        try {
            int index = -1;
            System.out.print("What list do you need to delete from? \n 1. Todo \n 2. Started \n 3. Finished \n");
            int listselect = scn.nextInt();
            if (listselect == 1){
                readtasks(1, 0);
                System.out.print("What do you need to delete? \n");
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
                    Main.todoList.remove(index);
                    FileManager.saveToFile();
                    System.out.println("Task deleted\n");
                } else {
                    System.out.println("Task not found.\n");
                }
            }
            if (listselect == 2){
                readtasks(2, 0);
                System.out.print("What do you need to delete? \n");
                int input = scn.nextInt();
                for (int i = 0; i < Main.startedList.size(); i++) {
                    String task = Main.startedList.get(i);
                    String taskNumber = task.split(": ")[0];
                    if (taskNumber.equals(String.valueOf(input))) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    Main.startedList.remove(index);
                    FileManager.saveToFile();
                    System.out.println("Task deleted\n");
                } else {
                    System.out.println("Task not found.\n");
                }
            }
            if (listselect == 3){
                readtasks(3, 0);
                System.out.print("What do you need to delete? \n");
                int input = scn.nextInt();
                for (int i = 0; i < Main.finishedList.size(); i++) {
                    String task = Main.finishedList.get(i);
                    String taskNumber = task.split(": ")[0];
                    if (taskNumber.equals(String.valueOf(input))) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    Main.finishedList.remove(index);
                    FileManager.saveToFile();
                    System.out.println("Task deleted");
                } else {
                    System.out.println("Task not found.");
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while adding a task. \n");
            e.printStackTrace();
        }
    }
}
