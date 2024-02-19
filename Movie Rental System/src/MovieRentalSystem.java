
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MovieRentalSystem extends JFrame{
	
    JButton btnAdd, btnRetrieve, btnUpdate,btnDisplayData;
    
	DBConnection connection;
    Statement statement;
    PreparedStatement pstmt;
    ResultSet rs;

    public MovieRentalSystem() {
    	setLayout(null);
    	Dimension scrsize = Toolkit.getDefaultToolkit().getScreenSize();
        btnAdd = new JButton("Add Movie");
        btnAdd.setBounds(100,40,300,25);;
        add(btnAdd);
        btnRetrieve = new JButton("Retrieve Movie");
        btnRetrieve.setBounds(500,40,300,25);;
        add(btnRetrieve);
        btnUpdate = new JButton("Update Movie");
        btnUpdate.setBounds(900,40,300,25);;
        add(btnUpdate);
        btnDisplayData = new JButton("Display Movie Data");
        btnDisplayData.setBounds(500,80,300,25);;
        add(btnDisplayData);
        
     // Initialize GUI components
        setVisible(true);
        setTitle("Movie Rental System");
        setSize(scrsize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Add action listeners
        btnAdd.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                new StoreMovieDetails();
            }
        });

        btnRetrieve.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                new RetrieveMovieDetails();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                new UpdateMovieInformation();
            }
        });
        
        btnDisplayData.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                new DisplayAllData();
            }
        });
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MovieRentalSystem();
	}

}
