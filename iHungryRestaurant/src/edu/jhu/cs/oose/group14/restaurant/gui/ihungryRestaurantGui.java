package edu.jhu.cs.oose.group14.restaurant.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.SwingUtilities;

import edu.jhu.cs.oose.group14.restaurant.controller.ihungryRestaurantController;
import edu.jhu.cs.oose.project.group14.ihungry.model.Order;

import java.util.*;

/*
 * ihungryRestaurantGui class is responsible for displaying the vendor-Gui screens
 *  of the ihungry application.It holds methods to display various Gui screens 
 *  for ihungry vendor application and few methods to return the swing 
 *  components in the current screen to the model class.
 */








/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class ihungryRestaurantGui extends javax.swing.JFrame {
	
	private LoginGui loginGui;
	private OrderGui orderGui;
	private SignupGui signupGui;
	

	/*
	 * Main method creates an object of ihungryVendorGui class and an object 
	 * of model class and passes the Gui class object to model object thus 
	 * creating a connection between them.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ihungryRestaurantGui gui = new ihungryRestaurantGui();
				ihungryRestaurantController control = new ihungryRestaurantController(gui);
				gui.setLocationRelativeTo(null);
				gui.setVisible(true);
			}
		});
	}
	
	
	/*
	 * Constructor of ihungryVendorGui just sets up the components and containers
	 * ready for displaying the login screen.
	 */
	
	public ihungryRestaurantGui() {
		super();
		initGUI();
	}
	
	
	/*
	 * initGUI method displays the login screen of the vendor application. 
	 * Registered users can login by typing their credentials.New users 
	 * can signup by giving a username and password.
	 */
	
	public void initGUI() {
		try {
			
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setTitle("iHungry Vendor");
			setSize(800, 600);
			
			loginGui = new LoginGui(getContentPane());
			signupGui = new SignupGui(getContentPane());
			orderGui = new OrderGui(getContentPane());
			loginGui.displayLoginScreen();
			
			/*pack();
			setSize(800, 600);*/
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	
	public LoginGui getLoginGui(){
		return loginGui;
	}
	
	public SignupGui getSignupGui(){
		return signupGui;
	}
	
	public OrderGui getOrderGui(){
		return orderGui;
	}

}
