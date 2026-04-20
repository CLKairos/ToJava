package org.clkairos;

import com.google.gson.annotations.Expose;
import java.time.LocalDate;

public class Task {
    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private String dueDate;
    @Expose
    private LocalDate addedDate;

    // Remove all JPanel, JLabel, and JButton fields from here!
    // They belong in the renderingManager's 'rebuild' method.

    public Task(int id, String name, String dueDate, LocalDate addedDate) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.addedDate = (addedDate != null) ? addedDate : LocalDate.now();
    }

    // Getters
    public String getName() { return name; }
    public int getID() { return id; }
    public String getDueDate() { return dueDate; }
    public LocalDate getAddedDate() { return addedDate; }
}