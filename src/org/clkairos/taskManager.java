package org.clkairos;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;

public class taskManager {
    private ArrayList<Task> taskList;
    private int nextId = 1;
    private final String FILE_PATH = System.getProperty("user.home") + "/tojava/tasks.json";
    private final Gson gson;

    public taskManager() {
        Main.log("taskManager: initializing");
        Main.log("taskManager: save path = " + FILE_PATH);

        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (date, type, context) ->
                        new JsonPrimitive(date.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, context) ->
                        LocalDate.parse(json.getAsString()))
                .setPrettyPrinting()
                .create();

        this.taskList = loadFromFile();
        Main.log("taskManager: loaded " + taskList.size() + " task(s) from file");

        if (!taskList.isEmpty()) {
            this.nextId = taskList.stream().mapToInt(Task::getID).max().getAsInt() + 1;
        }
        Main.log("taskManager: nextId starts at " + nextId);
    }

    public void createTask(String name, String dueDate) {
        Main.log("createTask: id=" + nextId + " name=\"" + name + "\" due=\"" + dueDate + "\"");
        Task newTask = new Task(nextId, name, dueDate, LocalDate.now());
        nextId++;
        taskList.add(newTask);
        saveToFile();
        Main.log("createTask: task list now has " + taskList.size() + " task(s)");
    }

    public void deleteTask(int id) {
        Main.log("deleteTask: removing task id=" + id);
        int sizeBefore = taskList.size();
        taskList.removeIf(task -> task.getID() == id);
        int removed = sizeBefore - taskList.size();
        Main.log("deleteTask: removed " + removed + " task(s), " + taskList.size() + " remaining");
        saveToFile();
    }

    private void saveToFile() {
        Main.log("saveToFile: writing " + taskList.size() + " task(s) to " + FILE_PATH);

        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs(); // ensure directory exists

        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(taskList, writer);
            Main.log("saveToFile: write successful");
        } catch (IOException e) {
            System.err.println("[ERROR] Could not save tasks: " + e.getMessage());
        }
    }

    private ArrayList<Task> loadFromFile() {
        File file = new File(FILE_PATH);
        Main.log("loadFromFile: checking " + FILE_PATH);

        if (!file.exists()) {
            Main.log("loadFromFile: file not found, starting with empty list");
            return new ArrayList<>();
        }

        Main.log("loadFromFile: file found, parsing JSON");
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<Task>>(){}.getType();
            ArrayList<Task> loadedList = gson.fromJson(reader, listType);
            if (loadedList == null) {
                Main.log("loadFromFile: parsed null (empty or malformed), returning empty list");
                return new ArrayList<>();
            }
            Main.log("loadFromFile: parsed " + loadedList.size() + " task(s)");
            return loadedList;
        } catch (IOException e) {
            System.err.println("[ERROR] Could not read tasks file: " + e.getMessage());
        } catch (JsonParseException e) {
            System.err.println("[ERROR] Could not parse tasks file (corrupt JSON?): " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }
}