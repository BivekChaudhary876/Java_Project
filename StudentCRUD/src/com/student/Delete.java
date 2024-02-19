
package com.student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;

public class Delete extends JFrame implements ActionListener {
    JLabel lblRollNo, lblFullName, lblAddress, lblPhoneNo, lblEmail, lblLevel, lblFaculty, lblGender, lblData;
    JTextField txtRollNo, txtFullName, txtAddress, txtPhoneNo, txtEmail;
    JRadioButton rbMale, rbFemale, rbOther;
    ButtonGroup genderButtonGroup;
    JComboBox<String> cbLevel, cdFaculty;
    JButton btnDelete;
    DefaultTableModel tableModel;
    Connection con;
    PreparedStatement pstmt;
    DBConnection connection;
    JButton btnLoadData; // New button for loading data
    JTable table; // Table for displaying records
    DefaultTableModel model; // Table model

    public Delete() {
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 //Setting the form components
		lblRollNo = new JLabel("Roll No");
		lblFullName = new JLabel("Full Name");
		lblAddress = new JLabel("Address");
		lblPhoneNo = new JLabel("Phone No");
		lblEmail = new JLabel("Email");
		lblLevel = new JLabel("Level");
		lblFaculty = new JLabel("Faculty");
		lblGender = new JLabel("Gender");
		lblData = new JLabel("Information Details");
		
		txtRollNo = new JTextField(20);
		txtFullName = new JTextField(20);
		txtAddress = new JTextField(20);
		txtPhoneNo = new JTextField(20);
		txtEmail = new JTextField(20);
	
		setLayout(null);

		// Add a Delete button to delete the record
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(600, 500, 100, 25);
		add(btnDelete);
		btnDelete.addActionListener(this);
		
		
        add(lblData);
        lblData.setBounds(600,20,800,25);
		
        
        // Create a table for displaying records and add it to a JScrollPane
		String[] columns = {"RollNo", "FullName", "Address", "PhoneNo", "Gender", "Email", "Level", "Faculty"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 60, 1230, 400);
        add(scrollPane);
       
      //Load the Data into the table from the database into the Frame
        loadDataFromDatabase();
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (col == 0) {
                    // Get the data from the selected row and pre-fill the form fields
                    String rollNo = table.getValueAt(row, 0).toString();
                    String fullName = table.getValueAt(row, 1).toString();
                    String address = table.getValueAt(row, 2).toString();
                    String phoneNo = table.getValueAt(row, 3).toString();
                    String gender = table.getValueAt(row, 4).toString();
                    String email = table.getValueAt(row, 5).toString();
                    String level = table.getValueAt(row, 6).toString();
                    String faculty = table.getValueAt(row, 7).toString();

                    txtRollNo.setText(rollNo);
                    txtFullName.setText(fullName);
                    txtAddress.setText(address);
                    txtPhoneNo.setText(phoneNo);
                    txtEmail.setText(email);
                    if (gender.equals("Male")) {
                        rbMale.setSelected(true);
                    } else if (gender.equals("Female")) {
                        rbFemale.setSelected(true);
                    } else if (gender.equals("Other")) {
                        rbOther.setSelected(true);
                    }
                    cbLevel.setSelectedItem(level);
                    cdFaculty.setSelectedItem(faculty);
                }
            }
        });
        
      //To make the GUI Visible
  		setVisible(true);
  		//To set the GUI layout Size

  		//setSize(400,420);
  		setSize(screenSize);
  		
  		setTitle("Delete");
  		
        

        // Other initialization and layout code
    }
    public static void main(String[] args) {
		// TODO Auto-generated method stub
    	new Delete();
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLoadData) {
	        // Load data from the database and display it in the table
	        loadDataFromDatabase();
		}else if (e.getSource() == btnDelete) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String rollNo = table.getValueAt(selectedRow, 0).toString();
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    try {
                        connection = new DBConnection();
                        pstmt = connection.con.prepareStatement("DELETE FROM student WHERE RollNo = ?");
                        pstmt.setString(1, rollNo);

                        int result = pstmt.executeUpdate();
                        if (result > 0) {
                            JOptionPane.showMessageDialog(null, "Record Deleted Successfully");
                            model.removeRow(selectedRow);
                        } else {
                            JOptionPane.showMessageDialog(null, "Something went wrong");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to delete.");
            }
        }
    }
    
    private void loadDataFromDatabase() {
        // Clear existing data in the table
        model.setRowCount(0);

        try {
            // Initialize the database connection
            connection = new DBConnection();
            Statement stmt = connection.con.createStatement();

            // Execute a SELECT query to fetch records from the database
            String sql = "SELECT * FROM student";
            ResultSet rs = stmt.executeQuery(sql);

            // Populate the table with the fetched data
            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(rs.getString("RollNo"));
                row.add(rs.getString("FullName"));
                row.add(rs.getString("Address"));
                row.add(rs.getString("PhoneNo"));
                row.add(rs.getString("Gender"));
                row.add(rs.getString("Email"));
                row.add(rs.getString("Level"));
                row.add(rs.getString("Faculty"));
                model.addRow(row);
            }

            // Close the result set and statement
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void clearInputFields() {
        // Clear input fields after saving
        txtRollNo.setText("");
        txtFullName.setText("");
        txtAddress.setText("");
        txtPhoneNo.setText("");
        txtEmail.setText("");
        genderButtonGroup.clearSelection();
        cbLevel.setSelectedIndex(0);
        cdFaculty.setSelectedIndex(0);
    }

    
    public String getSelectedGender() {
        if (rbMale.isSelected()) {
            return "Male";
        } else if (rbFemale.isSelected()) {
            return "Female";
        } else if (rbOther.isSelected()) {
            return "Other";
        } else {
            return ""; // No gender selected
        }
    }

}

