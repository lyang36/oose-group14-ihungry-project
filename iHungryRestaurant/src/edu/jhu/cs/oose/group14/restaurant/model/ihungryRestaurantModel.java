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
    ArrayList<String> listOfItemNames, listOfDescription, listOfPrice ;
	private int itemNo =0;
	private int indx=0;
	
	/*
	 * Constructor for ihungryVendorController
	 */
	
	public ihungryRestaurantModel(ihungryRestaurantGui gui){
		this.gui = gui;
		listOfEdit = gui.getEditButton();
	    listOfDelete = gui.getDeleteButton();
	    listOfItemNames = new ArrayList<String>();
	    listOfDescription = new ArrayList<String>();
	    listOfPrice = new ArrayList<String>();
		this.gui.getLogin().addActionListener(new LoginListener());
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
			
			gui.displayOrderGui();
           //gui.getmenuScrollBar().addAdjustmentListener(new MenuScrollBarListener());
        
			for(int i=0;i<5;i++)
			{
				listOfEdit.get(i).addActionListener(new EditButtonListener() );
				listOfDelete.get(i).addActionListener(new DeleteButtonListener() );
			}
			gui.getNext().addActionListener(new NextButtonListener());
			gui.getPrev().addActionListener(new PrevButtonListener());
            
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
			
			JTextField name = gui.getItemNames(ind);
		    JTextField description = gui.getDescription(ind);
		    JTextField price = gui.getPrice(ind);
		    
		    if(name.getText().equals("") || description.getText().equals("") || price.getText().equals("")){
				JLabel errorFields = new JLabel("<HTML><FONT COLOR = Blue>Please enter a value in each field before saving.</FONT></HTML>");	
				JOptionPane.showMessageDialog(null,errorFields);
			}
		    else{
		    	
				if(listOfEdit.get(ind).getText()=="Save")
				{		
				    //Item newItem = new Item(Integer.toString(++itemNo),name.getText(),description.getText(),p);
		     
				     //call a method to send this item to server to be stored in the database
				     
				     name.setEditable(false);
				     description.setEditable(false);
				     price.setEditable(false);
				     listOfEdit.get(ind).setText("Edit");
				     
				     //save the newly entered item and its price
				     if(indx*5+ind < listOfItemNames.size()){
					     listOfItemNames.set(indx*5+ind,name.getText());
					     listOfDescription.set(indx*5+ind,description.getText());
					     listOfPrice.set(indx*5+ind,price.getText());
				     }
				     else{
				    	 listOfItemNames.add(name.getText());
					     listOfDescription.add(description.getText());
					     listOfPrice.add(price.getText());
				     }
				     		
				}
				
				else if(listOfEdit.get(ind).getText()=="Edit")
				{
					name.setEditable(true);
			        description.setEditable(true);
			        price.setEditable(true);
			        listOfEdit.get(ind).setText("Save");
			        
			        /*//replace the newly entered item and its price
			        listOfItemNames.set(indx*5+ind,name.getText());
			        listOfDescription.set(indx*5+ind,description.getText());
			        listOfPrice.set(indx*5+ind,price.getText());*/
					
				}
		    }
		}
	}
	
	class DeleteButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			
		}
	}
	
	class NextButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
				
			boolean nextProceed=true;
			for(int i=0;i<5;i++){
				if (listOfEdit.get(i).getText().equals("Save")){
					nextProceed = false;
					break;
				}
			}
			
			if(nextProceed){
				indx = indx + 1;
				System.out.println("into next index = " + indx);
				System.out.println("into next size = " + listOfItemNames.size());
				for(int i=0;i<5;i++)
				{
					if(indx*5+i<listOfItemNames.size()){
						gui.getItemNames(i).setText(listOfItemNames.get(indx*5+i));
						gui.getDescription(i).setText(listOfDescription.get(indx*5+i));
						gui.getPrice(i).setText(listOfPrice.get(indx*5+i));
						gui.getItemNames(i).setEditable(false);
						gui.getDescription(i).setEditable(false);
						gui.getPrice(i).setEditable(false);
						listOfEdit.get(i).setText("Edit");
						listOfDelete.get(i).setText("Delete");
						gui.getPrev().setVisible(true);
						
					}
					else{
						gui.getItemNames(i).setText("");
						gui.getDescription(i).setText("");
						gui.getPrice(i).setText("");
						gui.getItemNames(i).setEditable(true);
						gui.getDescription(i).setEditable(true);
						gui.getPrice(i).setEditable(true);
						listOfEdit.get(i).setText("Save");
						listOfDelete.get(i).setText("Delete");
						gui.getPrev().setVisible(true);
					}
					
				}
			}
			else{
				JLabel errorFields = new JLabel("<HTML><FONT COLOR = Blue>Please fill all the values before going to next page.</FONT></HTML>");	
				JOptionPane.showMessageDialog(null,errorFields);
			}
				
			
		}
	}
	
	class PrevButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			indx = indx - 1;
			System.out.println("into prev index = " + indx);
			System.out.println("into prev size = " + listOfItemNames.size());
			for (int i=0;i<5;i++){
				listOfEdit.get(i).setText("Edit");
				gui.getItemNames(i).setText(listOfItemNames.get(indx*5+i));
				gui.getDescription(i).setText(listOfDescription.get(indx*5+i));
				gui.getPrice(i).setText(listOfPrice.get(indx*5+i));
				gui.getItemNames(i).setEditable(false);
				gui.getDescription(i).setEditable(false);
				gui.getPrice(i).setEditable(false);
			}
			if (indx == 0){
				System.out.println("exiting prev");
				gui.getPrev().setVisible(false);
			}
			
		}
	}
	
	
}