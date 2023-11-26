package project.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // String[] tables = {"Project 1","Project 2","Project 3"};
        // String[] taskNames = {"Task1", "Task2"};
        // Integer[] prio = {0,1,2};
        // LocalDate[] dates = {LocalDate.now(), LocalDate.of(2002, 07, 30)};
        // String[] category = {"Ongoing", "Done"};

        // List<Task> ize = new ArrayList<>();

        // for (String item1 : tables) {
        //     for (String item2 : taskNames) {
        //         for (int item3 : prio) {
        //             for (LocalDate item4 : dates) {
        //                 for (String item5 : category) {
        //                     ize.add(new Task(item1, item2, item3, item4, item5));
        //                 }
        //             }
        //         }
                
        //     }
        // }

        // Todo todo = new Todo();
        // todo.setTasks(ize);
        // todo.saveToFile();

        Window win = new Window();
        win.setVisible(true);

    }
}
