import java.io.*;
import java.util.*;

class FileManager {
    static void saveToArray() {
        try {
            File todoFile = new File("src/todo.txt");
            Scanner todoReader = new Scanner(todoFile);
            while (todoReader.hasNextLine()) {
                String data = todoReader.nextLine();
                Main.todoList.add(data);
            }
            todoReader.close();

            File startedFile = new File("src/started.txt");
            Scanner startedReader = new Scanner(startedFile);
            while (startedReader.hasNextLine()) {
                String data = startedReader.nextLine();
                Main.startedList.add(data);
            }
            startedReader.close();

            File finishedFile = new File("src/finished.txt");
            Scanner finishedReader = new Scanner(finishedFile);
            while (finishedReader.hasNextLine()) {
                String data = finishedReader.nextLine();
                Main.finishedList.add(data);
            }
            finishedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while loading to the array.");
            e.printStackTrace();
        }
    }

    static void saveToFile() {
        try {
            FileWriter todoWriter = new FileWriter("src/todo.txt");
            for (String todoItem : Main.todoList) {
                todoWriter.write(todoItem + System.lineSeparator());
            }
            todoWriter.close();
            FileWriter startedWriter = new FileWriter("src/started.txt");
            for (String startedItem : Main.startedList) {
                startedWriter.write(startedItem + System.lineSeparator());
            }
            startedWriter.close();
            FileWriter finishedWriter = new FileWriter("src/finished.txt");
            for (String finishedItem : Main.finishedList) {
                finishedWriter.write(finishedItem + System.lineSeparator());
            }
            finishedWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}
