package project;

import java.io.Serializable;
import java.time.LocalDate;

class Task implements Serializable{
    private String table;
    //The name of the task
    private String name;
    //The priotity level of the task. 0 being the least and 2 being the most important
    //0 by default
    private int priority;
    //The finish date
    //Empty by default
    private LocalDate date;
    //The category you wish to save your task to
    private String category;

    public Task(String table, String task, int priority, LocalDate date, String category) {
        this.table = table;
        this.name = task;
        this.priority = priority;
        this.date = date;
        this.category = category;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public void setName(String task) {
        this.name = task;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return category + ": " + name + ",";
    }


    
    

}