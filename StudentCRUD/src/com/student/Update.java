package com.student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Update extends JFrame implements ActionListener{

	JLabel lblRollNo, lblFullName, lblAddress, lblPhoneNo, lblEmail, lblLevel, lblFaculty, lblGender, lblData;
    JTextField txtFullName, txtAddress, txtPhoneNo, txtEmail;
    JRadioButton rbMale, rbFemale, rbOther;
    ButtonGroup genderButtonGroup;
    JComboBox cbroll;
    JComboBox<String> cbLevel, cdFaculty;
    JButton btnUpdate;
	
	JLabel lblroll, lblfname,lblfaculty;
	JTextField txtfname;
	
	DBConnection connection;
	
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	
	
	public Update() {
		
//		setLayout(new FlowLayout());
		setLayout(null);
		
		connection=new DBConnection();
		
		lblRollNo = new JLabel("Roll No");
		lblFullName = new JLabel("Full Name");
		lblAddress = new JLabel("Address");
		lblPhoneNo = new JLabel("Phone No");
		lblEmail = new JLabel("Email");
		lblLevel = new JLabel("Level");
		lblFaculty = new JLabel("Faculty");
		lblGender = new JLabel("Gender");
		lblData = new JLabel("Information Details");
		
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
		
		genderButtonGroup = new ButtonGroup();  //Create a group for the radio buttons
		genderButtonGroup.add(rbMale);  //Add Radio button to the group
		genderButtonGroup.add(rbFemale);
		genderButtonGroup.add(rbOther);
		
		btnUpdate = new JButton("Update");
		
		cbroll=new JComboBox();
		
		
		try {
			
			stmt=connection.con.createStatement();
			rs=stmt.executeQuery("SELECT rollno FROM student");
			while(rs.next()) {
				cbroll.addItem(rs.getInt(1));
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		add(lblRollNo);
		lblRollNo.setBounds(20,20,100,25);
		add(cbroll);
		cbroll.setBounds(120,20,150,25);
		
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
		
		// Add a Update button to update the record
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(150,340,80,25);;
        add(btnUpdate);
		
		cbroll.addActionListener(this);
		btnUpdate.addActionListener(this);
		
		setVisible(true);
		setTitle("Update");
		setSize(370,420);
		//To make the frame on desirable location
		setLocation(500,200);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==cbroll) {
			String selectedGender = getSelectedGender();
			try {
				pstmt=connection.con.prepareStatement("SELECT * FROM student WHERE rollno=?");
				pstmt.setString(1, cbroll.getSelectedItem().toString());
				rs=pstmt.executeQuery();
				
				if(rs.next()) {
					txtFullName.setText(rs.getString("FullName"));
                    txtAddress.setText(rs.getString("Address"));
                    txtPhoneNo.setText(rs.getString("PhoneNo"));
                    txtEmail.setText(rs.getString("Email"));
                    cbLevel.setSelectedItem(rs.getString("Level"));
                    cdFaculty.setSelectedItem(rs.getString("Faculty"));

					// Set the radio button for gender
	                String gender = rs.getString("Gender");
	                if ("Male".equals(gender)) {
	                    rbMale.setSelected(true);
	                } else if ("Female".equals(gender)) {
	                    rbFemale.setSelected(true);
	                } else if ("Other".equals(gender)) {
	                    rbOther.setSelected(true);
	                }
				}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		 if (e.getSource() == btnUpdate) {
	            // Get the updated data from the form fields
	            String selectedGender = getSelectedGender();

	            // Perform an UPDATE query to update the selected record in the database
	            try {
	                connection = new DBConnection();
	                pstmt = connection.con.prepareStatement(
	                        "UPDATE student SET FullName=?, Address=?, PhoneNo=?, Gender=?, Email=?, Level=?, Faculty=? WHERE rollno=?"
	                );
	                pstmt.setString(1, txtFullName.getText());
	                pstmt.setString(2, txtAddress.getText());
	                pstmt.setString(3, txtPhoneNo.getText());
	                pstmt.setString(4, selectedGender);
	                pstmt.setString(5, txtEmail.getText());
	                pstmt.setString(6, cbLevel.getSelectedItem().toString());
	                pstmt.setString(7, cdFaculty.getSelectedItem().toString());
	                pstmt.setString(8, cbroll.getSelectedItem().toString());

	                int result = pstmt.executeUpdate();
	                if (result > 0) {
	                    JOptionPane.showMessageDialog(null, "Record Updated Successfully");

	                } else {
	                    JOptionPane.showMessageDialog(null, "Something went wrong");
	                }
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	     
	        }
	
	}
	
	public static void main(String[] args) {
		new Update();
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