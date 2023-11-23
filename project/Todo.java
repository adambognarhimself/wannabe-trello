package project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Todo {
    List<Task> tasks;
    List<Task> completed;

    public List<Task> getCompleted() {
        return completed;
    }

    public void setCompleted(List<Task> completed) {
        this.completed = completed;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Todo(){
        tasks = new ArrayList<>();
        completed = new ArrayList<>();
    }

    public void add(Task t){
        tasks.add(t);
    }

    public void remove(Task t){
        tasks.remove(t);
    }

    public void edit(Task t1, Task t2){
        remove(t1);
        add(t2);
    }

    //filter by priority, shows the tasks with the corresponding priorities
    public List<Task> filterByPriority(int priority){
        List<Task> filtered = new ArrayList<>();

        for (Task item : tasks) {
            if(priority == item.getPriority()){
                filtered.add(item);
            }
        }
        return filtered;
    }

    //sort by date in a given categotry
    public List<Task> sortByDate(String category){
        List<Task> sorted = new ArrayList<>();

        for (Task item : tasks) {
            if(item.getCategory().equals(category)){
                sorted.add(item);
            }
        }

        sorted.sort((t1,t2) -> t1.getDate().compareTo(t2.getDate()));

        return sorted;
    }

    //lists the tasks with expired dates
    public List<Task> getOverdueTasks(){
        List<Task> overdue = new ArrayList<>();

        LocalDate current = LocalDate.now();

        for (Task item : tasks) {
            if(item.getDate().isAfter(current)){
                overdue.add(item);
            }
        }

        return overdue;
    }
    //move from one catgory to another
    public void moveTask(Task t, String category){
        tasks.remove(t);
        t.setCategory(category);
        tasks.add(t);

    }

    public void markAsCompleted(Task t){
        completed.add(t);
    }

    public void deleteCompleted(){
        for (Task item : completed) {
            for (Task item2 : tasks) {
                if(item.equals(item2)){
                    tasks.remove(item2);
                    completed.remove(item);
                }
            }
        }
    }

    public void undoCompleted(Task t){
        completed.remove(t);
    }

    public List<Task> listByKeyWord(String word){
        List<Task> contains = new ArrayList<>();

        for (Task task : tasks) {
            if(task.getName().contains(word)){
                contains.add(task);
            }
        }
        return contains;
    }

    public void saveToFile(){
        try {
            FileOutputStream f = new FileOutputStream("save.xml");
            ObjectOutputStream out = new ObjectOutputStream(f);
            out.writeObject(tasks);
            out.close();
        
        } catch (IOException e) {
        }
    }

    public void readFromFile(){
        try{
        FileInputStream f = new FileInputStream("save.xml");
        ObjectInputStream in = new ObjectInputStream(f);
        tasks = (ArrayList<Task>)(in.readObject());
        in.close();
        }catch(IOException ex){

        }catch(ClassNotFoundException ex){

        }
    }


}
