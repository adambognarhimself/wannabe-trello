package project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        List<String> table = new ArrayList<>();

        Todo tasks = new Todo();
        tasks.readFromFile();
        if(!tasks.getTasks().isEmpty()){
            for (Task item : tasks.getTasks()) {
                table.add(item.getTable());
            }
        }

        System.out.println("New table: 0");
        System.out.println("New task: 1");
        Scanner sc = new Scanner(System.in);
        String choose = sc.nextLine();
        while (!choose.equals("end")) {
                
            switch (choose) {
                case "0":
                    System.out.println("add table");
                    Scanner tableName = new Scanner(System.in);
                    table.add(tableName.nextLine());
                    break;
                
                case "1":
                    System.out.println("Table;name;priority;date;category");
                    String[] split = sc.nextLine().split(";");
                    tasks.add(new Task(split[0], split[1],Integer.parseInt(split[2]) , null, split[4]));

                    break;    
                default:
                    break;
            }

            choose = sc.nextLine();
        }
        sc.close();

        for (String string : table) {
            System.out.println(string);
            for (Task item : tasks.getTasks()) {
                if(item.getTable().equals(string)){
                    System.out.println(item);
                }
            }
        }
        
        tasks.saveToFile();

    }
}
