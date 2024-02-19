
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class StoreMovieDetails extends JFrame implements ActionListener{
	
	JLabel lblTitle, lblGenre, lblLanguage, lblLength;
    JTextField txtTitle, txtGenre, txtLanguage, txtLength;
    JButton btnAdd;

    DBConnection connection;
    Statement statement;
    PreparedStatement pstmt;
    ResultSet rs;

    public StoreMovieDetails() {
  
    	setLayout(null);
        
        lblTitle = new JLabel("Title");
		lblGenre = new JLabel("Genre");
		lblLanguage = new JLabel("Language");
		lblLength = new JLabel("Length");
		
		txtTitle = new JTextField(20);
		txtGenre = new JTextField(20);
		txtLanguage = new JTextField(20);
		txtLength = new JTextField(20);
        
        add(lblTitle);
		lblTitle.setBounds(20,20,100,25);
		add(txtTitle);
		txtTitle.setBounds(110,20,150,25);
		
		add(lblGenre);
		lblGenre.setBounds(20,60,100,25);
		add(txtGenre);
		txtGenre.setBounds(110,60,150,25);
		
		add(lblLanguage);
		lblLanguage.setBounds(20,100,100,25);
		add(txtLanguage);
		txtLanguage.setBounds(110,100,150,25);
		
		add(lblLength);
		lblLength.setBounds(20,140,100,25);
		add(txtLength);
		txtLength.setBounds(110,140,150,25);
		
		// Add a Update button to update the record
        btnAdd = new JButton("Add");
        btnAdd.setBounds(100,200,80,25);;
        add(btnAdd);
        
        btnAdd.addActionListener(this);
        
        setVisible(true);
        setTitle("Store Movie Details");
		setSize(300,300);
		//To disable the frame
		setResizable(false);
		//To make the frame on desirable location
		setLocation(500,200);
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new StoreMovieDetails();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btnAdd) {
		
			try {
				//Intialise the dbconnection
				connection = new DBConnection();
				
				//prepareStatement is the method of PreparedStatement
				pstmt = connection.con.prepareStatement(
						"INSERT INTO movies values(?,?,?,?)"
						);
				pstmt.setString(1,txtTitle.getText());
				pstmt.setString(2,txtGenre.getText());
				pstmt.setString(3,txtLanguage.getText());
				pstmt.setString(4,txtLength.getText());
				
				//Execute the update
				int result = pstmt.executeUpdate();
				if(result >0) {
					JOptionPane.showMessageDialog(null, "Movie details added successfully!");

				}else {
					JOptionPane.showMessageDialog(null, "Error adding movie details");
				}
						
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			// After saving, clear the input fields
	        clearInputFields();
	        
		}
		
	}
	
	private void clearInputFields() {
        // Clear input fields after saving
        txtTitle.setText("");
        txtGenre.setText("");
        txtLanguage.setText("");
        txtLength.setText("");
    }

}
