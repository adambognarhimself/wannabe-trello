package project.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import project.main.Todo;
import project.main.Task;

public class test {


    private Todo todo;

    @Before
    public void setUp() {
        todo = new Todo();
    }

    @Test
    public void testAddTask() {
        
        Task task = new Task("Table1", "Task1", 1, LocalDate.now(), "Category1");
        todo.add(task);
        List<Task> tasks = todo.getTasks();
        assertTrue(tasks.contains(task));
    }

    @Test
    public void testRemoveTask() {
        Task task = new Task("Table1", "Task1", 1, LocalDate.now(), "Category1");
        todo.add(task);
        todo.remove(task);
        List<Task> tasks = todo.getTasks();
        assertFalse(tasks.contains(task));
    }

    @Test
    public void testEditTask() {
        Task task1 = new Task("Table1", "Task1", 1, LocalDate.now(), "Category1");
        Task task2 = new Task("Table1", "Task2", 2, LocalDate.now(), "Category2");
        todo.add(task1);
        todo.edit(task1, task2);
        List<Task> tasks = todo.getTasks();
        assertTrue(tasks.contains(task2));
        assertFalse(tasks.contains(task1));
    }

    @Test
    public void testFilterByPriority() {
        Task task1 = new Task("Table1", "Task1", 1, LocalDate.now(), "Category1");
        Task task2 = new Task("Table2", "Task2", 1, LocalDate.now(), "Category2");
        Task task3 = new Task("Table3", "Task3", 2, LocalDate.now(), "Category3");
        todo.add(task1);
        todo.add(task2);
        todo.add(task3);
        List<Task> filteredTasks = todo.filterByPriority(1);
        assertEquals(2, filteredTasks.size());
        assertTrue(filteredTasks.contains(task1));
        assertTrue(filteredTasks.contains(task2));
        assertFalse(filteredTasks.contains(task3));
    }

    @Test
    public void testGetOverdueTasks() {
        Task overdueTask = new Task("Table1", "OverdueTask", 1, LocalDate.now().minusDays(1), "Category1");
        Task futureTask = new Task("Table2", "FutureTask", 2, LocalDate.now().plusDays(1), "Category2");
        todo.add(overdueTask);
        todo.add(futureTask);
        List<Task> overdueTasks = todo.getOverdueTasks();
        assertEquals(1, overdueTasks.size());
        assertTrue(overdueTasks.contains(overdueTask));
        assertFalse(overdueTasks.contains(futureTask));
    }

    @Test
    public void testMoveTask() {
        Task task = new Task("Table1", "Task1", 1, LocalDate.now(), "Category1");
        todo.add(task);
        todo.moveTask(task, "Category2");
        List<Task> tasks = todo.getTasks();
        assertEquals("Category2", tasks.get(0).getCategory());
    }

    @Test
    public void testExtendDate() {
        Task task = new Task("Table1", "Task1", 1, LocalDate.now(), "Category1");
        todo.add(task);
        todo.extendDate(task, 5);
        assertEquals(LocalDate.now().plusDays(5), task.getDate());
    }

    @Test
    public void testDeleteTable() {
        Task task1 = new Task("Table1", "Task1", 1, LocalDate.now(), "Category1");
        Task task2 = new Task("Table2", "Task2", 2, LocalDate.now(), "Category2");
        todo.add(task1);
        todo.add(task2);
        todo.deleteTable("Table1");
        List<Task> tasks = todo.getTasks();
        assertFalse(tasks.contains(task1));
        assertTrue(tasks.contains(task2));
    }

    @Test
    public void testGetTodayTasks() {
        Task todayTask = new Task("Table1", "TodayTask", 1, LocalDate.now(), "Category1");
        Task futureTask = new Task("Table2", "FutureTask", 2, LocalDate.now().plusDays(1), "Category2");
        todo.add(todayTask);
        todo.add(futureTask);
        List<Task> todayTasks = todo.getTodayTasks();
        assertEquals(1, todayTasks.size());
        assertTrue(todayTasks.contains(todayTask));
        assertFalse(todayTasks.contains(futureTask));
    }

    @Test
    public void testListByKeyWord() {
        Task task1 = new Task("Table1", "Task1", 1, LocalDate.now(), "Category1");
        Task task2 = new Task("Table2", "Task2", 2, LocalDate.now(), "Category2");
        Task task3 = new Task("Table3", "KeywordTask", 3, LocalDate.now(), "Category3");
        todo.add(task1);
        todo.add(task2);
        todo.add(task3);
        List<Task> tasksWithKeyword = todo.listByKeyWord("Keyword");
        assertEquals(1, tasksWithKeyword.size());
        assertTrue(tasksWithKeyword.contains(task3));
        assertFalse(tasksWithKeyword.contains(task1));
        assertFalse(tasksWithKeyword.contains(task2));
    }
}