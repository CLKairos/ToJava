import java.io.*;
import java.util.*;

class FileManager {
    static void saveToArray() {
        try {
            File todoFile = new File("src/todo.txt");
            Scanner todoReader = new Scanner(todoFile);
            List<String> todoList = new ArrayList<>();
            while (todoReader.hasNextLine()) {
                String data = todoReader.nextLine();
                todoList.add(data);
            }
            todoReader.close();
            Main.todo = todoList.toArray(new String[0]);

            File startedFile = new File("src/started.txt");
            Scanner startedReader = new Scanner(startedFile);
            List<String> startedList = new ArrayList<>();
            while (startedReader.hasNextLine()) {
                String data = startedReader.nextLine();
                startedList.add(data);
            }
            startedReader.close();
            Main.started = startedList.toArray(new String[0]);

            File finishedFile = new File("src/finished.txt");
            Scanner finishedReader = new Scanner(finishedFile);
            List<String> finishedList = new ArrayList<>();
            while (finishedReader.hasNextLine()) {
                String data = finishedReader.nextLine();
                finishedList.add(data);
            }
            finishedReader.close();
            Main.finished = finishedList.toArray(new String[0]);
        } catch (FileNotFoundException e) {

        }
    }

    public void saveToFile() {
        try {
            FileWriter todoWriter = new FileWriter("src/todo.txt");
            for (String todoItem : Main.todo) {
                todoWriter.write(todoItem + System.lineSeparator());
            }
            todoWriter.close();
            FileWriter startedWriter = new FileWriter("src/started.txt");
            for (String startedItem : Main.started) {
                startedWriter.write(startedItem + System.lineSeparator());
            }
            startedWriter.close();
            FileWriter finishedWriter = new FileWriter("src/finished.txt");
            for (String finishedItem : Main.finished) {
                finishedWriter.write(finishedItem + System.lineSeparator());
            }
            finishedWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An error occurred while saving files.");
            e.printStackTrace();
        }
    }
}
