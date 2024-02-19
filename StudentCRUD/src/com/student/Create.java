
package com.student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;

public class Create extends JFrame implements ActionListener {
	
	JLabel lblRollNo,lblFullName, lblAddress,lblPhoneNo,lblEmail,lblLevel,lblFaculty,lblGender,lblData;
	JTextField txtRollNo,txtFullName,txtAddress,txtPhoneNo,txtEmail;
	JRadioButton rbMale, rbFemale, rbOther; //Radio Button for the gender
	ButtonGroup genderButtonGroup; //To group the radio buttons
	JComboBox<String> cbLevel, cdFaculty;
	JButton btnCreate;
    JTable table; // Table for displaying records
    DefaultTableModel model; // Table model
	
	//Create Connection object con
	Connection con;
	PreparedStatement pstmt;
	DBConnection connection;
	
	
	public Create() {
		
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
//		lblData = new JLabel("Information Details");
		
		txtRollNo = new JTextField(20);
		txtFullName = new JTextField(20);
		txtAddress = new JTextField(20);
		txtPhoneNo = new JTextField(20);
		txtEmail = new JTextField(20);
		
		String[] Levels = {"Intermediate","Undergraduate","Postgraduate"};
		cbLevel = new JComboBox<>(Levels);
		
		String[] Faculties = {"Science","Commerce","Education","Humanity","BSc.CSIT","BIM","BBM","BCA","BBS","BSW","BBA","Arts","MBBS","BDS","B.Pharma","BSc.Nursing","BMLT","B.Opthalmology"};
		cdFaculty = new JComboBox<>(Faculties);
		
		rbMale = new JRadioButton("Male");
		rbFemale = new JRadioButton("Female");
		rbOther = new JRadioButton("Other");
		
		genderButtonGroup = new ButtonGroup();   //Create a group for the radio buttons
		genderButtonGroup.add(rbMale);  //Add Radio button to the group
		genderButtonGroup.add(rbFemale);
		genderButtonGroup.add(rbOther);
		
		btnCreate = new JButton("Create");
		
		setLayout(null);
		
		add(lblRollNo);
		lblRollNo.setBounds(20,20,100,25);
		add(txtRollNo);
		txtRollNo.setBounds(120,20,150,25);
		
		add(lblFullName);
		lblFullName.setBounds(20,60,100,25);
		add(txtFullName);
		txtFullName.setBounds(120,60,150,25);
		
		add(lblAddress);
		lblAddress.setBounds(20,100,100,25);
		add(txtAddress);
		txtAddress.setBounds(120,100,150,25);
		
		add(lblPhoneNo);
		lblPhoneNo.setBounds(20,140,100,25);
		add(txtPhoneNo);
		txtPhoneNo.setBounds(120,140,150,25);
		
		add(lblEmail);
		lblEmail.setBounds(20,180,100,25);
		add(txtEmail);
		txtEmail.setBounds(120,180,150,25);
		
		add(lblGender);
		lblGender.setBounds(20,220,100,25);
		add(rbMale);
		rbMale.setBounds(120,220,80,25);
		add(rbFemale);
		rbFemale.setBounds(200,220,80,25);
		add(rbOther);
		rbOther.setBounds(280,220,80,25);
		
		add(lblLevel);
		lblLevel.setBounds(20,260,100,25);
		add(cbLevel);
		cbLevel.setBounds(120,260,150,25);
		
		add(lblFaculty);
		lblFaculty.setBounds(20,300,100,25);
		add(cdFaculty);
		cdFaculty.setBounds(120,300,150,25);
		
		
		add(btnCreate);
		btnCreate.setBounds(150,340,80,25);
		btnCreate.addActionListener(this);
		
		setVisible(true);
		setSize(370,420);
		setTitle("Create");
		//To make the frame on desirable location
		setLocation(500,200);
	}
	
	public static void main(String args[]) {
		new Create();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btnCreate) {		
			
			try {
				String selectedGender = getSelectedGender();
//				//Intialise the dbconnection
				connection = new DBConnection();
				
				//prepareStatement is the method of PreparedStatement
				pstmt = connection.con.prepareStatement(
						"INSERT INTO student VALUES(?,?,?,?,?,?,?,?)"
						);
				pstmt.setString(1,txtRollNo.getText());
				pstmt.setString(2,txtFullName.getText());
				pstmt.setString(3,txtAddress.getText());
				pstmt.setString(4,txtPhoneNo.getText());
				pstmt.setString(5,txtEmail.getText());
				pstmt.setString(6,selectedGender);
				pstmt.setString(7,cbLevel.getSelectedItem().toString());
				pstmt.setString(8,cdFaculty.getSelectedItem().toString());
				
				//Execute the update
				
				int result = pstmt.executeUpdate();
				if(result >0) {
					JOptionPane.showMessageDialog(null, "Record Saved Successfully");
				}else {
					JOptionPane.showMessageDialog(null, "Something went wrong");
				}
						
			}catch(Exception ex) {
				
			}
	        clearInputFields();
	        
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
	        return "";
	    }
	}
	
}


