package Lab3;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ToDoListApp extends JFrame {
    
    private JTextField taskadd;
    private DefaultListModel<String> List;
    private JList<String> taskList;
    private JRadioButton Universitytasks, Householdtasks;

    public ToDoListApp() {
        setTitle("To Do List");
        setSize(500, 600);  
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Text field for adding new tasks
        taskadd = new JTextField();
        add(taskadd, BorderLayout.NORTH);

        // List model and JList to display tasks
        List = new DefaultListModel<>();
        taskList = new JList<>(List);
        add(new JScrollPane(taskList), BorderLayout.CENTER);

        // Panel for buttons
        JPanel panel = new JPanel();
        JButton addButton = new JButton("Add Task");
        JButton removeButton = new JButton("Remove Task");
        JButton saveButton = new JButton("Save Tasks");
        JButton loadButton = new JButton("Load Tasks");

        // Action listener for adding tasks
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String task = taskadd.getText();
                if (!task.isEmpty()) {
                    List.addElement(task);
                    taskadd.setText("");
                }
            }
        });

        // Action listener for removing selected tasks
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selecttask = taskList.getSelectedIndex();
                if (selecttask != -1) {
                    List.remove(selecttask);
                }
            }
        });

        // Action listener for saving tasks to a file
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTasks();
            }
        });

        // Action listener for loading tasks from a file
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTasks();
            }
        });

        // Adding buttons to the panel
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(saveButton);
        panel.add(loadButton);
        add(panel, BorderLayout.SOUTH);

        // Loading and resizing the image
        try {
            BufferedImage originalImage = ImageIO.read(getClass().getResource("image.jpg"));
            int targetWidth = 200; // Desired width
            int targetHeight = 200; // Desired height

            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = resizedImage.createGraphics();
            graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
            graphics2D.dispose();

            JLabel displayField = new JLabel(new ImageIcon(resizedImage));
            add(displayField, BorderLayout.EAST);
        } catch (IOException e) {
            System.out.println("image cannot be found!");
        }

        // Radio buttons for task categories
        Universitytasks = new JRadioButton("University tasks");
        Householdtasks = new JRadioButton("Household tasks");
        ButtonGroup group = new ButtonGroup();
        group.add(Universitytasks);
        group.add(Householdtasks);

        // Adding radio buttons to a panel
        JPanel radioPanel = new JPanel();
        radioPanel.add(Universitytasks);
        radioPanel.add(Householdtasks);
        add(radioPanel, BorderLayout.WEST);
    }

    // Method to save tasks to a file
    private void saveTasks() {
        String fileName;
        if (Universitytasks.isSelected()) {
            fileName = "University tasks";
        } else if (Householdtasks.isSelected()) {
            fileName = "Household tasks";
        } else {
            JOptionPane.showMessageDialog(this, "Choose where to save the tasks.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < List.size(); i++) {
                writer.write(List.get(i));
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Tasks saved.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error.");
        }
    }

    // Method to load tasks from a file
    private void loadTasks() {
        String fileName;
        if (Universitytasks.isSelected()) {
            fileName = "University tasks";
        } else if (Householdtasks.isSelected()) {
            fileName = "Household tasks";
        } else {
            JOptionPane.showMessageDialog(this, "Choose where to load the tasks.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            List.clear();
            while ((line = reader.readLine()) != null) {
                List.addElement(line);
            }
            JOptionPane.showMessageDialog(this, "Tasks loaded.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ToDoListApp().setVisible(true);
            }
        });
    }
}