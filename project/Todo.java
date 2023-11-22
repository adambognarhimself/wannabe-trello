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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Todo(){
        tasks = new ArrayList<>();
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

    public List<Task> filterByPriority(int priority){
        List<Task> filtered = new ArrayList<>();

        for (Task item : tasks) {
            if(priority == item.getPriority()){
                filtered.add(item);
            }
        }
        return filtered;
    }

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

    public void moveTask(Task t, String category){
        tasks.remove(t);
        t.setCategory(category);
        tasks.add(t);

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
