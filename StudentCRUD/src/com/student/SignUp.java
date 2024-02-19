package com.student;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;
import java.sql.*;

public class SignUp extends JFrame implements ActionListener {
	JLabel lbluser, lblpassword, lblText, lblLogin;
	JTextField txtuser;
	JPasswordField txtpass;
	JButton btnsignup;
	
	DBConnection connection;
	PreparedStatement pstmt;
	ResultSet rs;
	
	
	public SignUp() {
		lbluser = new JLabel("Username");
		lblpassword = new JLabel("Password");
		txtuser = new JTextField(20);
		txtpass = new JPasswordField(20);
		btnsignup = new JButton("Sign Up");
		lblText = new JLabel("Already Sign Up?");
		lblLogin = new JLabel("<html><u>Login</u></html>"); 
		
		setLayout(null);
		add(lbluser);
		lbluser.setBounds(20,20,100,25);
		add(txtuser);
		txtuser.setBounds(120,20,150,25);
		add(lblpassword);
		lblpassword.setBounds(20,60,100,25);
		add(txtpass);
		txtpass.setBounds(120,60,150,25);
		add(btnsignup);
		btnsignup.setBounds(150,100,100,25);
		
		btnsignup.addActionListener(this);
		
		add(lblText); // Add the Text
        lblText.setBounds(120, 130, 200, 25);
        add(lblLogin); // Add the login link
        lblLogin.setBounds(230, 130, 250, 25);
        lblLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Login();
                dispose();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                // Change the color or style when the mouse enters the label (optional)
                lblLogin.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Change the color or style back when the mouse exits the label (optional)
                lblLogin.setForeground(Color.BLACK);
            }
        });	
		
		//To make the GUI Visible
		setVisible(true);
		//To set the GUI layout Size
		setSize(400,200);
		//To close the GUI while clicking on the cross button
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//To set the Title
		setTitle("Admin Sign Up");
		//To disable the frame
		setResizable(false);
		//To make the frame on desirable location
		setLocation(500,200);
		
		
	}

	public static void main(String[] args) {
		//To set the different look and design
		setDefaultLookAndFeelDecorated(true);
		new SignUp();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnsignup) {
			
			try {
				
				connection = new DBConnection();
	
				//prepareStatement is the method of PreparedStatement
				pstmt = connection.con.prepareStatement(
						"INSERT INTO users VALUES(?,?)"
						);
				pstmt.setString(1,txtuser.getText());
				pstmt.setString(2,txtpass.getText());
				
				//Execute the update
				int result = pstmt.executeUpdate();
				if(result >0) {
					JOptionPane.showMessageDialog(null, "Sign Up Successfully");
				}else {
					JOptionPane.showMessageDialog(null, "Something went wrong");
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
