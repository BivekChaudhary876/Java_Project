import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DisplayAllData extends JFrame implements ActionListener {
    JTable table;
    DefaultTableModel model;
    JLabel lblGenre;
    JComboBox<String> cdGenre;

    DBConnection connection;
    Statement stmt;
    ResultSet rs;

    public DisplayAllData() {
        connection = new DBConnection();

        setLayout(new FlowLayout());
        model = new DefaultTableModel();

        JPanel compPanel = new JPanel();
        JPanel tablePanel = new JPanel();

        lblGenre = new JLabel("Choose Genre");
        cdGenre = new JComboBox<>();
        cdGenre.addItem("All"); // Add "All" option
        try {
            stmt = connection.con.createStatement();
            rs = stmt.executeQuery("SELECT DISTINCT genre FROM movies");
            while (rs.next()) {
                cdGenre.addItem(rs.getString("genre"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        table = new JTable(model);

        compPanel.add(lblGenre);
        compPanel.add(cdGenre);
        add(compPanel);

        model.addColumn("Title");
        model.addColumn("Genre");
        model.addColumn("Language");
        model.addColumn("Length");

        cdGenre.addActionListener(this);

        LoadAllData(); // Load all data initially

        int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

        JScrollPane jsp = new JScrollPane(table, v, h);
        add(jsp);

        tablePanel.add(jsp);
        add(tablePanel);

        setVisible(true);
        setTitle("Display All Data in JTable");
        setSize(600, 400);
        //To make the frame on desirable location
      	setLocation(350,200);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cdGenre) {
            LoadDataByGenre();
        }
    }

    public void LoadAllData() {
        model.setRowCount(0);

        try {
            stmt = connection.con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM movies");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("title"), rs.getString("genre"), rs.getString("language"), rs.getString("length")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void LoadDataByGenre() {
        model.setRowCount(0);

        try {
            stmt = connection.con.createStatement();
            String selectedGenre = (String) cdGenre.getSelectedItem();
            if (selectedGenre.equals("All")) {
                LoadAllData();
            } else {
                rs = stmt.executeQuery("SELECT * FROM movies WHERE genre = '" + selectedGenre + "'");
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getString("title"), rs.getString("genre"), rs.getString("language"), rs.getString("length")
                    });
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
    	new DisplayAllData();
    }
}
