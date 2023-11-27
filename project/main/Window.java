package project.main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class Window extends JFrame {
	JButton addTask;
	JButton addTable;
	JButton removeTask;
	JButton editTask;

	Todo todo;
	TodoData data;
	List<TodoData> datas;
	JTabbedPane tabbedpane;
	List<String> table;
	List<JTable> jTables;
	Object[][] adat;
	String[] col;

	public Window() {
		todo = new Todo();
		table = new ArrayList<>();
		addTable = new JButton("Add Table");
		addTask = new JButton("Add task");
		removeTask = new JButton("Remove task");
		jTables = new ArrayList<>();
		col = new String[] { "Category", "Name", "Date", "Priority" };
		tabbedpane = new JTabbedPane();
		editTask = new JButton("Edit task");
		datas = new ArrayList<>();

		todo.readFromFile();
		if (!todo.getTasks().isEmpty()) {
			for (Task item : todo.getTasks()) {
				if (!table.contains(item.getTable())) {
					table.add(item.getTable());
					TodoData uj = new TodoData();
					datas.add(uj);
				}
			}
		}

		for (String item : table) {
			int index = table.indexOf(item);
			for (Task item2 : todo.tasks) {
				if (item2.getTable().equals(item)) {
					datas.get(index).add(item2);
				}
			}

			JTable jtable = new JTable(datas.get(index));
			JPanel tabPanel = new JPanel();
			tabPanel.add(new JScrollPane(jtable));
			jtable.setAutoCreateRowSorter(true);
			jTables.add(jtable);

			tabbedpane.add(item, tabPanel);
		}

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				todo.saveToFile();
			}
		});

		initComponents();

		pack();
		setTitle("Todo");
	}

	private void initComponents() {
		this.setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(tabbedpane, BorderLayout.CENTER);

		JPanel bottom = new JPanel();

		bottom.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));

		addTable.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame addTableFrame = new JFrame();
				addTableFrame.setLayout(new BorderLayout());
				JTextField tableName = new JTextField();
				JButton add = new JButton("Add table");
				add.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						String name = tableName.getText();

						if(!table.contains(name)){
						table.add(name);
						JTable jtable = new JTable();
						JPanel tabPanel = new JPanel();

						TodoData uj = new TodoData();
						datas.add(uj);
						jtable.setModel(uj);
						tabPanel.add(new JScrollPane(jtable));
						jtable.setAutoCreateRowSorter(true);
						jTables.add(jtable);

						tabbedpane.add(name, tabPanel);
						}else{
							JOptionPane.showMessageDialog(addTableFrame, "This table already exists");
						}

						addTableFrame.dispose();
					}

				});

				addTableFrame.add(tableName, BorderLayout.NORTH);
				addTableFrame.add(add, BorderLayout.SOUTH);
				addTableFrame.setSize(200, 200);
				addTableFrame.setVisible(true);
			}

		});
		bottom.add(addTable);

		addTask.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Task task =
				JFrame dialogFrame = new JFrame("Add Task");
				dialogFrame.setSize(400, 300);
				dialogFrame.setLayout(new GridLayout(6, 2));

				JTextField tablefField = new JTextField();
				JTextField nameField = new JTextField();
				JTextField priorityField = new JTextField();
				JTextField dateField = new JTextField();
				JTextField categoryField = new JTextField();

				JButton addButton = new JButton("Add Task");

				addButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {

							String tablename = tablefField.getText();
							String name = nameField.getText();
							int priority = Integer.parseInt(priorityField.getText());
							LocalDate date = LocalDate.parse(dateField.getText());
							String category = categoryField.getText();

							if(table.contains(tablename)){

							Task newTask = new Task(tablename, name, priority, date, category);

							todo.add(newTask);

							int index = table.indexOf(tablename);
							datas.get(index).add(newTask);
						}else{
							JOptionPane.showMessageDialog(dialogFrame, "There is no such table");
						}

							dialogFrame.dispose();
						} catch (NumberFormatException | java.time.format.DateTimeParseException ex) {
							JOptionPane.showMessageDialog(dialogFrame, "Invalid input. Please check your data.");
						} catch(IndexOutOfBoundsException ex){
							JOptionPane.showMessageDialog(dialogFrame, "Invalid input. Please check your data.");
						}
					}

				});

				dialogFrame.add(new JLabel("Table:"));
				dialogFrame.add(tablefField);
				dialogFrame.add(new JLabel("Name:"));
				dialogFrame.add(nameField);
				dialogFrame.add(new JLabel("Priority:"));
				dialogFrame.add(priorityField);
				dialogFrame.add(new JLabel("Date (YYYY-MM-DD):"));
				dialogFrame.add(dateField);
				dialogFrame.add(new JLabel("Category:"));
				dialogFrame.add(categoryField);
				dialogFrame.add(new JLabel(""));
				dialogFrame.add(addButton);

				dialogFrame.setVisible(true);
			}

		});
		bottom.add(addTask);

		add(bottom, BorderLayout.SOUTH);

		tabbedpane.getSelectedIndex();

		removeTask.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int selectedTabIndex = tabbedpane.getSelectedIndex();
				if (selectedTabIndex == -1) {
					JOptionPane.showMessageDialog(Window.this, "Please select a table.");
					return;
				}

				JTable selectedTable = jTables.get(selectedTabIndex);
				int selectedRow = selectedTable.getSelectedRow();

				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(Window.this, "Please select a task to edit.");
					return;
				}

				int selectedindex = tabbedpane.getSelectedIndex();
				String tablename = table.get(selectedindex);
				Task selectedTask = getSelectedTask(tablename, selectedindex);

				if (selectedTask != null) {
					todo.remove(selectedTask);

					int index = table.indexOf(tablename);
					datas.get(index).remove(selectedTask);

				}
			}

		});

		bottom.add(removeTask);

		editTask.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedTabIndex = tabbedpane.getSelectedIndex();
				if (selectedTabIndex == -1) {
					JOptionPane.showMessageDialog(Window.this, "Please select a table.");
					return;
				}

				JTable selectedTable = jTables.get(selectedTabIndex);
				int selectedRow = selectedTable.getSelectedRow();

				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(Window.this, "Please select a task to edit.");
					return;
				}

				String tableName = table.get(selectedTabIndex);
				Task selectedTask = todo.getTasks().stream()
						.filter(task -> task.getTable().equals(tableName))
						.skip(selectedRow)
						.findFirst()
						.orElse(null);

				if (selectedTask != null) {
					// Create a copy of the selected task to edit
					Task editedTask = new Task(
							selectedTask.getTable(),
							JOptionPane.showInputDialog("Enter updated name:", selectedTask.getName()),
							Integer.parseInt(
									JOptionPane.showInputDialog("Enter updated priority:", selectedTask.getPriority())),
							selectedTask.getDate(),
							selectedTask.getCategory());

					// Call the edit method
					todo.edit(selectedTask, editedTask);

					int index = table.indexOf(tableName);
					datas.get(index).edit(selectedTask, editedTask);
				}

			}

		});

		bottom.add(editTask);

		// Add a new button for moving tasks
		JButton moveTask = new JButton("Move Task");
		bottom.add(moveTask);

		moveTask.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedTabIndex = tabbedpane.getSelectedIndex();
				if (selectedTabIndex == -1) {
					JOptionPane.showMessageDialog(Window.this, "Please select a table.");
					return;
				}

				JTable selectedTable = jTables.get(selectedTabIndex);
				int selectedRow = selectedTable.getSelectedRow();

				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(Window.this, "Please select a task to move.");
					return;
				}

				String tableName = table.get(selectedTabIndex);
				Task selectedTask = getSelectedTask(tableName, selectedTabIndex);

				String newCategory = JOptionPane.showInputDialog("Enter the new category:");

				if (newCategory != null && !newCategory.trim().isEmpty()) {
					// Remove the task from the current category
					datas.get(selectedTabIndex).remove(selectedTask);
					todo.remove(selectedTask);

					// Update the task with the new category
					selectedTask.setCategory(newCategory);

					// Add the task to the new category

					datas.get(selectedTabIndex).add(selectedTask);
				}
			}
		});

		

		JMenuBar menubar = new JMenuBar();

		JMenu helpMenu = new JMenu("Help");
        JMenuItem userManualItem = new JMenuItem("User Manual");
        helpMenu.add(userManualItem);

        // Set up the action listener for the "User Manual" menu item
        userManualItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUserManual();
            }
        });

        

		JMenu actionsMenu = new JMenu("Actions");
		JMenuItem getOverdueTasks = new JMenuItem("Get overdue tasks");

		menubar.add(actionsMenu);
		// Add the "Help" menu to the menu bar
        menubar.add(helpMenu);
		actionsMenu.add(getOverdueTasks);

		getOverdueTasks.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Create a new window for listing overdue tasks
				JFrame overdueFrame = new JFrame("Overdue Tasks");
				overdueFrame.setSize(600, 400);

				// Create data and column arrays for the JTable
				Object[][] overdueData = new Object[todo.getOverdueTasks().size()][5];
				String[] overdueCol = new String[] { "Table", "Category", "Name", "Date", "Priority" };

				// Populate the data array with overdue tasks
				for (int i = 0; i < todo.getOverdueTasks().size(); i++) {
					overdueData[i][0] = todo.getOverdueTasks().get(i).getTable();
					overdueData[i][1] = todo.getOverdueTasks().get(i).getCategory();
					overdueData[i][2] = todo.getOverdueTasks().get(i).getName();
					overdueData[i][3] = todo.getOverdueTasks().get(i).getDate();
					overdueData[i][4] = todo.getOverdueTasks().get(i).getPriority();
				}

				// Create a new JTable for overdue tasks
				JTable overdueTable = new JTable(overdueData, overdueCol);
				overdueTable.setAutoCreateRowSorter(true);

				// Add the JTable to the new window
				overdueFrame.add(new JScrollPane(overdueTable));

				// Set the window properties and make it visible
				overdueFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				overdueFrame.setLocationRelativeTo(Window.this);
				overdueFrame.setVisible(true);
			}
			
		});


		JMenuItem showTasksByPriority = new JMenuItem("Show Tasks by Priority");
actionsMenu.add(showTasksByPriority);

showTasksByPriority.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Prompt the user to enter a priority
        String priorityInput = JOptionPane.showInputDialog("Enter the priority:");

        if (priorityInput != null && !priorityInput.trim().isEmpty()) {
            try {
                int selectedPriority = Integer.parseInt(priorityInput);

                // Create a new window for showing tasks by priority
                JFrame priorityFrame = new JFrame("Tasks with Priority " + selectedPriority);
                priorityFrame.setSize(600, 400);

                // Create data and column arrays for the JTable
                Object[][] priorityData = new Object[todo.filterByPriority(selectedPriority).size()][4];
                String[] priorityCol = new String[] { "Table", "Category", "Name", "Date" };

                // Populate the data array with tasks by priority
                for (int i = 0; i < todo.filterByPriority(selectedPriority).size(); i++) {
                    priorityData[i][0] = todo.filterByPriority(selectedPriority).get(i).getTable();
                    priorityData[i][1] = todo.filterByPriority(selectedPriority).get(i).getCategory();
                    priorityData[i][2] = todo.filterByPriority(selectedPriority).get(i).getName();
                    priorityData[i][3] = todo.filterByPriority(selectedPriority).get(i).getDate();
                }

                // Create a new JTable for tasks by priority
                JTable priorityTable = new JTable(priorityData, priorityCol);
                priorityTable.setAutoCreateRowSorter(true);

                // Add the JTable to the new window
                priorityFrame.add(new JScrollPane(priorityTable));

                // Set the window properties and make it visible
                priorityFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                priorityFrame.setLocationRelativeTo(Window.this);
                priorityFrame.setVisible(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(Window.this, "Invalid input. Please enter a valid priority.");
            }
        }
    }
});


JMenuItem searchByKeyword = new JMenuItem("Search Tasks by Keyword");
actionsMenu.add(searchByKeyword);

searchByKeyword.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Prompt the user to enter a keyword
        String keyword = JOptionPane.showInputDialog("Enter the keyword:");

        if (keyword != null && !keyword.trim().isEmpty()) {
            // Create a new window for showing tasks by keyword
            JFrame searchFrame = new JFrame("Tasks with Keyword: " + keyword);
            searchFrame.setSize(600, 400);

            // Create data and column arrays for the JTable
            Object[][] searchData = new Object[todo.listByKeyWord(keyword).size()][4];
            String[] searchCol = new String[] { "Table", "Category", "Name", "Date" };

            // Populate the data array with tasks containing the keyword
            for (int i = 0; i < todo.listByKeyWord(keyword).size(); i++) {
                searchData[i][0] = todo.listByKeyWord(keyword).get(i).getTable();
                searchData[i][1] = todo.listByKeyWord(keyword).get(i).getCategory();
                searchData[i][2] = todo.listByKeyWord(keyword).get(i).getName();
                searchData[i][3] = todo.listByKeyWord(keyword).get(i).getDate();
            }

            // Create a new JTable for tasks by keyword
            JTable searchTable = new JTable(searchData, searchCol);
            searchTable.setAutoCreateRowSorter(true);

            // Add the JTable to the new window
            searchFrame.add(new JScrollPane(searchTable));

            // Set the window properties and make it visible
            searchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            searchFrame.setLocationRelativeTo(Window.this);
            searchFrame.setVisible(true);
        }
    }
});


JMenuItem showTodayTasks = new JMenuItem("Show Today's Tasks");
actionsMenu.add(showTodayTasks);

showTodayTasks.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Create a new window for showing today's tasks
        JFrame todayTasksFrame = new JFrame("Today's Tasks");
        todayTasksFrame.setSize(600, 400);

        // Create data and column arrays for the JTable
        Object[][] todayData = new Object[todo.getTodayTasks().size()][4];
        String[] todayCol = new String[] { "Table", "Category", "Name", "Priority" };

        // Populate the data array with today's tasks
        for (int i = 0; i < todo.getTodayTasks().size(); i++) {
            todayData[i][0] = todo.getTodayTasks().get(i).getTable();
            todayData[i][1] = todo.getTodayTasks().get(i).getCategory();
            todayData[i][2] = todo.getTodayTasks().get(i).getName();
            todayData[i][3] = todo.getTodayTasks().get(i).getPriority();
        }

        // Create a new JTable for today's tasks
        JTable todayTable = new JTable(todayData, todayCol);
        todayTable.setAutoCreateRowSorter(true);

        // Add the JTable to the new window
        todayTasksFrame.add(new JScrollPane(todayTable));

        // Set the window properties and make it visible
        todayTasksFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        todayTasksFrame.setLocationRelativeTo(Window.this);
        todayTasksFrame.setVisible(true);
    }
});

JButton showTasksByDateRangeButton = new JButton("Extend");
bottom.add(showTasksByDateRangeButton);

showTasksByDateRangeButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Prompt the user to enter the number of days
        String daysInput = JOptionPane.showInputDialog("Enter the number of days:");

        if (daysInput != null && !daysInput.trim().isEmpty()) {
            try {
                int numberOfDays = Integer.parseInt(daysInput);

                // Get the selected task
                int selectedTabIndex = tabbedpane.getSelectedIndex();
                if (selectedTabIndex == -1) {
                    JOptionPane.showMessageDialog(Window.this, "Please select a table.");
                    return;
                }

                JTable selectedTable = jTables.get(selectedTabIndex);
                int selectedRow = selectedTable.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(Window.this, "Please select a task.");
                    return;
                }

                String tableName = table.get(selectedTabIndex);
                Task selectedTask = todo.getTasks().stream()
                        .filter(task -> task.getTable().equals(tableName))
                        .skip(selectedRow)
                        .findFirst()
                        .orElse(null);

				if (selectedTask != null) {
					int indexof = todo.getTasks().indexOf(selectedTask);
					todo.extendDate(selectedTask, numberOfDays);
					int index = table.indexOf(tableName);
					datas.get(index).remove(selectedTask);
					datas.get(index).add(todo.getTasks().get(indexof));

				}
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(Window.this, "Invalid input. Please enter a valid number of days.");
            }
        }
    }
});

JButton deleteTableButton = new JButton("Delete Table");
bottom.add(deleteTableButton);

deleteTableButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the selected index of the tabbed pane
        int selectedTabIndex = tabbedpane.getSelectedIndex();
        if (selectedTabIndex == -1) {
            JOptionPane.showMessageDialog(Window.this, "Please select a table.");
            return;
        }

        // Prompt the user to confirm the deletion
        int confirm = JOptionPane.showConfirmDialog(Window.this,
                "Are you sure you want to delete the selected table?\nThis will also delete all associated tasks.",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Remove the selected table and its associated tasks
            String tableName = table.get(selectedTabIndex);
            table.remove(selectedTabIndex);

			for (var item : datas.get(selectedTabIndex).tasks) {
				todo.remove(item);
			}
            datas.remove(selectedTabIndex);
            jTables.remove(selectedTabIndex);
            tabbedpane.remove(selectedTabIndex);

        }
    }
});

		setJMenuBar(menubar);

	}

	private Task getSelectedTask(String tablename, int selectedindex) {

		JTable selectedJTable = jTables.get(selectedindex);
		int selectedrow = selectedJTable.getSelectedRow();

		return todo.getTasks().stream().filter(task -> task.getTable().equals(tablename))
				.skip(selectedrow).findFirst().orElse(null);

	}

	private void openUserManual() {

        // Provide the path to your user documentation PDF file
        String userManualPath = "C:\\Users\\bgnra\\Desktop\\prog hazi\\wannabe-trello\\Felhasználói Dokumentáció.pdf";

        try {
            File userManualFile = new File(userManualPath);

            // Check if the PDF file exists
            if (userManualFile.exists()) {
                // Open the PDF file with the default system PDF viewer
                Desktop.getDesktop().open(userManualFile);
            } else {
                JOptionPane.showMessageDialog(this, "User manual not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error opening user manual.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

}