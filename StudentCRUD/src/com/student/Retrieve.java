package com.student;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class Retrieve extends JFrame implements ActionListener{
	JTable table;
	DefaultTableModel model;
	JLabel lblFaculty;
	JComboBox cdFaculty;
	
	DBConnection connection;
	Statement stmt;
	ResultSet rs;

	JPanel compPanel, tablePanel;
	
	public Retrieve() {
		connection = new DBConnection();
		
		setLayout(new FlowLayout());
		model = new DefaultTableModel();
		
		compPanel = new JPanel();
		tablePanel = new JPanel();
		
		lblFaculty = new JLabel("Choose Faculty");
		cdFaculty = new JComboBox();
		
		cdFaculty.addItem("All"); // Add "All" option
		try {
			stmt=connection.con.createStatement();
			rs=stmt.executeQuery("SELECT distinct Faculty FROM student");
			while(rs.next()) {
				cdFaculty.addItem(rs.getString("Faculty"));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			
		}
		table = new JTable(model);
		
		compPanel.add(lblFaculty);
		compPanel.add(cdFaculty);
		add(compPanel);
		
		model.addColumn("RollNo");
		model.addColumn("FullName");
		model.addColumn("Address");
		model.addColumn("PhoneNo");
		model.addColumn("Gender");
		model.addColumn("Email");
		model.addColumn("Level");
		model.addColumn("Faculty");
		
		cdFaculty.addActionListener((ActionListener) this);		
		
		LoadAllData();
		
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		
		JScrollPane jsp = new JScrollPane(table,v,h);
		add(jsp);
		
		tablePanel.add(jsp);
		add(tablePanel);
		
		setVisible(true);
		setTitle("Retrieve Data");
		setSize(800,800);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cdFaculty) {
            // When faculty selection changes, load data for the selected faculty
            LoadData();
        }
	}

	public void LoadAllData() {
        model.setRowCount(0);

        try {
            stmt = connection.con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM student");
            while (rs.next()) {
                model.addRow(new Object[]{
                		rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	public void LoadData() {
		model.setRowCount(0);
		
		try {
			stmt = connection.con.createStatement();
			String selectedFaculty = (String) cdFaculty.getSelectedItem();
			if (selectedFaculty.equals("All")) {
                LoadAllData();
            } else {
				rs = stmt.executeQuery("select * from student where Faculty='" + selectedFaculty + "'");
				while(rs.next()) {
					model.addRow(new Object[] {
							rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)
					});
				}	
            }
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Retrieve();
	}

}
