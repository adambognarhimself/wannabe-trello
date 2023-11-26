package project.main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TodoData extends AbstractTableModel{

    List<Task> tasks = new ArrayList<>();
    String[] header =  {"Name", "Priority", "Date","Category"};

    public void add(Task t){
        tasks.add(t);
        //fireTableRowsUpdated(0, tasks.size()-1);
        fireTableDataChanged();
    }

    public void remove(Task t){
        tasks.remove(t);
        fireTableDataChanged();
    }

    public void edit(Task t1, Task t2){
        tasks.remove(t1);
        tasks.add(t2);
        fireTableDataChanged();

    }

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
            case 1:return task.getPriority();
            case 2: return task.getDate(); 
            default: return task.getCategory();
        }
    }

    @Override
    public String getColumnName(int column) {
        return header[column];
    }
    
}
