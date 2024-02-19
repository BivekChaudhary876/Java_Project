package com.student;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
	JLabel lbluser, lblpassword, lblText, lblSignUp;
	JTextField txtuser;
	JPasswordField txtpass;
	JButton btnlogin;
	
	DBConnection connection;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public Login() {
		lbluser = new JLabel("Username");
		lblpassword = new JLabel("Password");
		txtuser = new JTextField(20);
		txtpass = new JPasswordField(20);
		btnlogin = new JButton("Login");
		lblText = new JLabel("Don't have account?");
		lblSignUp = new JLabel("<html><u>Sign Up</u></html>");
		
		setLayout(null);
		add(lbluser);
		lbluser.setBounds(20,20,100,25);
		add(txtuser);
		txtuser.setBounds(120,20,150,25);
		add(lblpassword);
		lblpassword.setBounds(20,60,100,25);
		add(txtpass);
		txtpass.setBounds(120,60,150,25);
		add(btnlogin);
		btnlogin.setBounds(150,100,100,25);
		
		add(lblText); // Add the Text
        lblText.setBounds(120, 130, 200, 25);
        add(lblSignUp); // Add the login link
        lblSignUp.setBounds(240, 130, 250, 25);
        lblSignUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new SignUp();
                dispose();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                // Change the color or style when the mouse enters the label (optional)
                lblSignUp.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Change the color or style back when the mouse exits the label (optional)
                lblSignUp.setForeground(Color.BLACK);
            }
        });
		
		btnlogin.addActionListener(this);
		//To make the GUI Visible
		setVisible(true);
		//To set the GUI layout Size
		setSize(400,200);
		//To close the GUI while clicking on the cross button
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//To set the Title
		setTitle("Admin Login");
		//To disable the frame
		setResizable(false);
		//To make the frame on desirable location
		setLocation(500,200);
	}

	public static void main(String[] args) {
		//To set the different look and design
		setDefaultLookAndFeelDecorated(true);
		new Login();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnlogin) {
			
			try {
				
				connection = new DBConnection();
				pstmt = connection.con.prepareStatement(
						"SELECT * FROM users WHERE username = ? and password = ?");
				pstmt.setString(1, txtuser.getText());
				pstmt.setString(2, txtpass.getText());
				rs = pstmt.executeQuery();
			
				if(rs.next()) {
					JOptionPane.showMessageDialog(null, "Login Success");
					new CRUDOperation();
					this.dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Invalid Details");
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
