import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateMovieInformation extends JFrame implements ActionListener {
    JLabel lblTitle, lblGenre, lblLanguage, lblLength;
    JTextField txtGenre, txtLanguage, txtLength;
    JComboBox<String> cdTitle;
    JButton btnUpdate;

    DBConnection connection;
    PreparedStatement pstmt;
    ResultSet rs;

    public UpdateMovieInformation() {
        setLayout(null);
        connection = new DBConnection();

        lblTitle = new JLabel("Title");
        lblGenre = new JLabel("Genre");
        lblLanguage = new JLabel("Language");
        lblLength = new JLabel("Length");

        txtGenre = new JTextField(20);
        txtLanguage = new JTextField(20);
        txtLength = new JTextField(20);

        btnUpdate = new JButton("Update Movie");

        add(lblTitle);
        lblTitle.setBounds(20, 20, 100, 25);

        cdTitle = new JComboBox<>();
        cdTitle.setBounds(120, 20, 150, 25);

        add(cdTitle);

        add(lblGenre);
        lblGenre.setBounds(20, 60, 100, 25);
        add(txtGenre);
        txtGenre.setBounds(120, 60, 150, 25);

        add(lblLanguage);
        lblLanguage.setBounds(20, 100, 100, 25);
        add(txtLanguage);
        txtLanguage.setBounds(120, 100, 150, 25);

        add(lblLength);
        lblLength.setBounds(20, 140, 100, 25);
        add(txtLength);
        txtLength.setBounds(120, 140, 150, 25);

        add(btnUpdate);
        btnUpdate.setBounds(150, 220, 80, 25);

        try {
            Statement statement = connection.con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT title FROM movies");
            while (rs.next()) {
                cdTitle.addItem(rs.getString("title"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        cdTitle.addActionListener(this);
        btnUpdate.addActionListener(this);

        setVisible(true);
        setTitle("Update Movie Information");
        setSize(300, 300);
        setResizable(false);
        setLocation(500, 200);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cdTitle) {
            try {
                String selectedTitle = cdTitle.getSelectedItem().toString();
                pstmt = connection.con.prepareStatement("SELECT * FROM movies WHERE title=?");
                pstmt.setString(1, selectedTitle);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    txtGenre.setText(rs.getString("genre"));
                    txtLanguage.setText(rs.getString("language"));
                    txtLength.setText(rs.getString("length"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == btnUpdate) {
            try {
                // Perform an UPDATE query to update the selected record in the database
                pstmt = connection.con.prepareStatement(
                        "UPDATE movies SET title=?, genre=?, language=?, length=? WHERE title=?"
                );
                pstmt.setString(1, txtGenre.getText());
                pstmt.setString(2, txtGenre.getText());
                pstmt.setString(3, txtLanguage.getText());
                pstmt.setString(4, txtLength.getText());
                pstmt.setString(5, cdTitle.getSelectedItem().toString());

                int result = pstmt.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Movie information updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Error updating movie information");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new UpdateMovieInformation();
    }
}
