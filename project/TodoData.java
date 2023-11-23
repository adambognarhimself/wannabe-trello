package project;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TodoData extends AbstractTableModel{

    List<Task> tasks = new ArrayList<>();
    String[] header =  {"Name", "Priority", "Date","Category"};

    //public void add()

    @Override
    public int getRowCount() {
        return tasks.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Task task = tasks.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return task.getName();
            case 2: return task.getDate(); 
            default: return task.getCategory();
        }
    }

    @Override
    public String getColumnName(int column) {
        return header[column];
    }
    
}
