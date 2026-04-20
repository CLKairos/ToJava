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
    private final String FILE_PATH = "tasks.json";
    private final Gson gson;

    public taskManager() {
        // Fix: Explicitly tell Gson how to handle LocalDate without using reflection
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (date, type, context) ->
                        new JsonPrimitive(date.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, context) ->
                        LocalDate.parse(json.getAsString()))
                .setPrettyPrinting()
                .create();

        // Load existing tasks on startup
        this.taskList = loadFromFile();

        // Ensure nextId starts after the highest existing ID
        if (!taskList.isEmpty()) {
            this.nextId = taskList.stream().mapToInt(Task::getID).max().getAsInt() + 1;
        }
    }

    public void createTask(String name, String dueDate) {
        Task newTask = new Task(nextId, name, dueDate, LocalDate.now());
        nextId++;
        taskList.add(newTask);
        saveToFile();
    }

    public void deleteTask(int id) {
        taskList.removeIf(task -> task.getID() == id);
        saveToFile();
    }

    private void saveToFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(taskList, writer);
        } catch (IOException e) {
            System.err.println("Could not save tasks: " + e.getMessage());
        }
    }

    private ArrayList<Task> loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();

        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<Task>>(){}.getType();
            ArrayList<Task> loadedList = gson.fromJson(reader, listType);
            return (loadedList != null) ? loadedList : new ArrayList<>();
        } catch (Exception e) { // Caught broad Exception to handle potential parsing errors
            System.err.println("Could not load tasks: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }
}