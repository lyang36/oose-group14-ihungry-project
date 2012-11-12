package edu.jhu.cs.oose.group14.restaurant.model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import edu.jhu.cs.oose.fall2012.group14.ihungry.internet.CommunicationProtocol;
import edu.jhu.cs.oose.group14.restaurant.gui.ihungryRestaurantGui;
import edu.jhu.cs.oose.project.group14.ihungry.model.*;
import java.util.*
;

/**
 * ihungryRestaurantModel class is the model class for ihungry vendor 
 * application. It uses other helper classes like LoginHandler to handle
 * specific functions.
 */

public class ihungryRestaurantModel {
	
	private ihungryRestaurantGui gui;
	ArrayList<JButton> listOfEdit;
    ArrayList<JButton> listOfDelete;
    ArrayList<JTextField> listOfItemNames;
    ArrayList<JTextField> listOfDescription ;
    ArrayList<JTextField> listOfPrice;
	private int itemNo =0;
	
	/*
	 * Constructor for ihungryVendorController
	 */
	
	public ihungryRestaurantModel(ihungryRestaurantGui gui){
		this.gui = gui;
		listOfEdit = gui.getEditButton();
	    listOfDelete = gui.getDeleteButton();
	    listOfItemNames = gui.getItemNames();
	    listOfDescription = gui.getDescription();
	    listOfPrice = gui.getPrice();
		gui.getLogin().addActionListener(new LoginListener());
	}
	
	/**
	 * LoginListener class implements the actionPerformed method checking for
	 * the user credentials and allowing the user to log into the application.
	 * 
	 * @author parkavi
	 *
	 */
	
	class LoginListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			
			LoginHandler loginHandle = new LoginHandler(gui.getUsernameLogin().getText(),new String(gui.getPasswordLogin().getText()));
			String result = loginHandle.attemptLogin();
			System.out.println(result);
			System.out.println(CommunicationProtocol.NO_SUCH_COMMAND);
			
			gui.displayOrderGui();
           //gui.getmenuScrollBar().addAdjustmentListener(new MenuScrollBarListener());
        
			for(int i=0;i<5;i++)
			{
				listOfEdit.get(i).addActionListener(new EditButtonListener() );
				listOfDelete.get(i).addActionListener(new DeleteButtonListener() );
			}
            
		}
	}
	
	/**
	 * EditButtonListener class implements the actionPerformed method checking for 
	 * the user credentials and allowing the user to log into the application.
	 * 
	 * @author parkavi
	 *
	 */
	
	
	class EditButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) 
		{
			System.out.println(event.getSource());
			int ind = listOfEdit.indexOf(event.getSource());
			
			JTextField name = listOfItemNames.get(ind);
		    JTextField description = listOfDescription.get(ind);
		    JTextField price = listOfPrice.get(ind);
		     
			if(listOfEdit.get(ind).getText()=="Save")
			{
			     //call a method to send this item to server to be stored in the database
			     
			     name.setEditable(false);
			     description.setEditable(false);
			     price.setEditable(false);
			     listOfEdit.get(ind).setText("Edit");
			}
			else if(listOfEdit.get(ind).getText()=="Edit")
			{
				name.setEditable(true);
		        description.setEditable(true);
		        price.setEditable(true);
		        listOfEdit.get(ind).setText("Save");
				
			}
		}
	}
	
	class DeleteButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			
		}
	}
	
	
}
