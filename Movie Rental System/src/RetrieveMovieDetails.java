import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RetrieveMovieDetails extends JFrame implements ActionListener {
    JLabel lblTitle, lblGenre, lblLanguage, lblLength;
    JTextField txtTitle, txtGenre, txtLanguage, txtLength;
    JButton btnRetrieve;

    DBConnection connection;
    Statement statement;
    ResultSet rs;

    public RetrieveMovieDetails() {
        setLayout(null);

        lblTitle = new JLabel("Title");
        lblGenre = new JLabel("Genre");
        lblLanguage = new JLabel("Language");
        lblLength = new JLabel("Length");

        txtTitle = new JTextField(20);
        txtGenre = new JTextField(20);
        txtLanguage = new JTextField(20);
        txtLength = new JTextField(20);

        btnRetrieve = new JButton("Retrieve Movie");

        add(lblTitle);
        lblTitle.setBounds(20, 20, 100, 25);
        add(txtTitle);
        txtTitle.setBounds(110, 20, 150, 25);

        add(lblGenre);
        lblGenre.setBounds(20, 60, 100, 25);
        add(txtGenre);
        txtGenre.setBounds(110, 60, 150, 25);

        add(lblLanguage);
        lblLanguage.setBounds(20, 100, 100, 25);
        add(txtLanguage);
        txtLanguage.setBounds(110, 100, 150, 25);

        add(lblLength);
        lblLength.setBounds(20, 140, 100, 25);
        add(txtLength);
        txtLength.setBounds(110, 140, 150, 25);

        add(btnRetrieve);
        btnRetrieve.setBounds(100, 200, 120, 25);

        btnRetrieve.addActionListener(this);

        // Initialize DBConnection and Statement
        connection = new DBConnection();
        try {
            statement = connection.con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setVisible(true);
        setTitle("Retrieve Movie Details");
        setSize(300,300);
		//To disable the frame
		setResizable(false);
		//To make the frame on desirable location
		setLocation(500,200);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String title = txtTitle.getText();

        try {
            rs = statement.executeQuery("SELECT * FROM movies WHERE title='" + title + "'");
            if (rs.next()) {
                txtGenre.setText(rs.getString("genre"));
                txtLanguage.setText(rs.getString("language"));
                txtLength.setText(rs.getString("length"));
            } else {
                JOptionPane.showMessageDialog(null, "Movie not found");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving movie details");
        } 
        
    }

    public static void main(String[] args) {
        new RetrieveMovieDetails();
    }
}
