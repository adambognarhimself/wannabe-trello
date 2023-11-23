package project;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class Window extends JFrame {
	JButton addTask;
	JButton addTable;
	Todo todo;
	TodoData data;
	JTabbedPane tabbedpane;
	List<String> table;

	public Window() {
		todo = new Todo();
		table = new ArrayList<>();
		addTable = new JButton("Add Table");
		addTask = new JButton("Add task");

		initComponents();
	}

	private void initComponents() {
		this.setLayout(new BorderLayout());
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		tabbedpane = new JTabbedPane();
		add(tabbedpane, BorderLayout.CENTER);

		JPanel bottom = new JPanel();

		bottom.setLayout(new GridLayout());

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
						table.add(name);
						int index = tabbedpane.getTabCount();
						JPanel tabPanel = new JPanel();
						tabbedpane.add(name, tabPanel);
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
				JFrame dialogFrame = new JFrame("Add Task");
				dialogFrame.setSize(300, 200);
				dialogFrame.setLayout(new GridLayout(5, 2));

				JTextField nameField = new JTextField();
				JTextField priorityField = new JTextField();
				JTextField dateField = new JTextField();
				JTextField categoryField = new JTextField();

				JButton addButton = new JButton("Add Task");

				addButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							String name = nameField.getText();
							int priority = Integer.parseInt(priorityField.getText());
							LocalDate date = LocalDate.parse(dateField.getText());
							String category = categoryField.getText();

							Task newTask = new Task("Category " + category, name, priority, date, category);
							todo.add(newTask);

							dialogFrame.dispose();
						} catch (NumberFormatException | java.time.format.DateTimeParseException ex) {
							JOptionPane.showMessageDialog(dialogFrame, "Invalid input. Please check your data.");
						}
					}
				});

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

	}

}