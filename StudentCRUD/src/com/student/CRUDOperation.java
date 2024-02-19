package com.student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CRUDOperation extends JFrame {

	JMenuBar mb;
	
	//Record Operation(mnuro)
	JMenu mnuro, mnure, mnuhelp;
	JMenuItem miCreate, miRetrieve, miUpdate, miDelete, miexit;
	
	public CRUDOperation() {
		
		//To set the Frame to full screen
		Dimension scrsize = Toolkit.getDefaultToolkit().getScreenSize();
		
		mb =new JMenuBar();
		mnuro = new JMenu("Record Operation");
		
		miCreate = new JMenuItem("Create New Record");
		miRetrieve = new JMenuItem("Retrieve Record");
		miUpdate = new JMenuItem("Update Record");
		miDelete = new JMenuItem("Delete Record");
		miexit = new JMenuItem("Exit");
		
		//Setting the MenuBar to the Frame
		setJMenuBar(mb);
		
		//Adding Menu to MenuBar
		mb.add(mnuro);
		
		//Adding MenuItem to the Record OPeration
		mnuro.add(miCreate);
		mnuro.add(miRetrieve);
		mnuro.add(miUpdate);
		mnuro.add(miDelete);
		mnuro.add(miexit);
		
		setVisible(true);
		setTitle("CRUD Operation");
		setSize(scrsize);
		
		
		miCreate.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Create();	
			}
		});
		
		miRetrieve.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Retrieve();	
			}
		});
		
		miUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Update();
			}
		});
		
		miDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Delete();
			}
		});
		
		miexit.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
		        if (option == JOptionPane.YES_OPTION) {
		            System.exit(0); // Exit the application
		        }
		    }
		});			
	}

}
