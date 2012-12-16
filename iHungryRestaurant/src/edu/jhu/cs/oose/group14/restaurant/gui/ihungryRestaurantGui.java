package edu.jhu.cs.oose.group14.restaurant.gui;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import edu.jhu.cs.oose.group14.restaurant.controller.ihungryRestaurantController;

/*
 * ihungryRestaurantGui class is responsible for displaying the vendor-Gui screens
 *  of the ihungry application.It holds methods to display various Gui screens 
 *  for ihungry vendor application and few methods to return the swing 
 *  components in the current screen to the model class.
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
