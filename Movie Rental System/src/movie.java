import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class movie extends JFrame {
    private JTextField titleField, genreField, languageField, lengthField;
    private JButton addButton, retrieveButton, updateButton;
    private JTable movieTable;
    private DefaultTableModel tableModel;

    private Connection connection;
    private Statement statement;

    public movie() {
        // Initialize GUI components
        setTitle("Movie Rental System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        titleField = new JTextField(20);
        genreField = new JTextField(20);
        languageField = new JTextField(20);
        lengthField = new JTextField(20);

        addButton = new JButton("Add Movie");
        retrieveButton = new JButton("Retrieve Movie");
        updateButton = new JButton("Update Movie");

        tableModel = new DefaultTableModel();
        movieTable = new JTable(tableModel);

        // Set layout
        setLayout(new BorderLayout());

        // Create panel for input fields and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Genre:"));
        inputPanel.add(genreField);
        inputPanel.add(new JLabel("Language:"));
        inputPanel.add(languageField);
        inputPanel.add(new JLabel("Length:"));
        inputPanel.add(lengthField);
        inputPanel.add(addButton);
        inputPanel.add(retrieveButton);
        inputPanel.add(updateButton);

        // Create panel for displaying data in JTable
//        JPanel tablePanel = new JPanel();
//        tablePanel.setLayout(new BorderLayout());
//        tablePanel.add(new JScrollPane(movieTable), BorderLayout.CENTER);

        // Add panels to the frame
        add(inputPanel, BorderLayout.NORTH);
//        add(tablePanel, BorderLayout.CENTER);

        // Set up database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/movierental", "username", "password");
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addMovie();
            }
        });

        retrieveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                retrieveMovie();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateMovie();
            }
        });
    }

    // Method to add a movie to the database
    private void addMovie() {
        // Retrieve data from text fields
        String title = titleField.getText();
        String genre = genreField.getText();
        String language = languageField.getText();
        String length = lengthField.getText();

        // Insert data into the database
        try {
            statement.executeUpdate("INSERT INTO movies (title, genre, language, length) VALUES ('" + title + "', '" + genre + "', '" + language + "', '" + length + "')");
            // Refresh the JTable
            displayAllMovies();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve a movie from the database
    private void retrieveMovie() {
        // Retrieve data from text fields
        String title = titleField.getText();

        // Retrieve data from the database
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM movies WHERE title='" + title + "'");
            // Process the result set and update the text fields
            if (resultSet.next()) {
                genreField.setText(resultSet.getString("genre"));
                languageField.setText(resultSet.getString("language"));
                lengthField.setText(resultSet.getString("length"));
            } else {
                JOptionPane.showMessageDialog(this, "Movie not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update a movie in the database
    private void updateMovie() {
        // Retrieve data from text fields
        String title = titleField.getText();
        String genre = genreField.getText();
        String language = languageField.getText();
        String length = lengthField.getText();

        // Update data in the database
        try {
            statement.executeUpdate("UPDATE movies SET genre='" + genre + "', language='" + language + "', length='" + length + "' WHERE title='" + title + "'");
            // Refresh the JTable
            displayAllMovies();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to display all movies in the JTable
    private void displayAllMovies() {
        // Clear the table
        tableModel.setRowCount(0);

        // Retrieve data from the database and populate the JTable
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM movies");
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String genre = resultSet.getString("genre");
                String language = resultSet.getString("language");
                String length = resultSet.getString("length");

                // Add a new row to the table
                tableModel.addRow(new Object[]{title, genre, language, length});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new movie().setVisible(true);
            }
        });
    }
}
