import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.fazecast.jSerialComm.SerialPort;

public class UI extends JFrame {
	public int TT, Z, Arm, Grip;
	
	public SerialPort[] ports = SerialPort.getCommPorts();
    public SerialPort comPort = ports[0]; // Change the index based on available ports
    
	
	// variables
	private Rectangle bounds;
	public JFrame frame = new JFrame("3D-ROBOT");
	// Cardlayout object
	protected CardLayout cardLayout = new CardLayout();
	protected JPanel panelContainer = new JPanel(cardLayout);
	
	// JFrame objects
	JPanel panelMain = new JPanel();
	controllerUI controlPanel;
	advancedUI advancedPanel;
	
	// font objects
	Font roboto = new Font("Roboto", Font.BOLD, 12);
	
	// button object
	Button controlBtn = new Button("CONTROL PANEL");
	Button advBtn = new Button("ADVANCED CONTROLS");
	/**
	 * 
	 */
	public void controlPanel(){
		controlBtn.addActionListener(new ActionListener() {	// .addMouseListener to the parameter btn, with that method having new param of MouseAdapter
			@Override
			public void actionPerformed(ActionEvent e) {	// overrides the actionPerformed
				try {
					cardLayout.show(panelContainer, "Control Panel");
				} catch(Exception g) {
					setFrmeException(g);
				}
			}
		});
	}
	/**
	 * buttonPress method to listen when a button is pressed
	 * 
	 * if button pressed resizes the image
	 * 
	 * @param btn
	 */
	public void btnResize(Button btn) {
		bounds = btn.getBounds();	// sets the bounds of the parameter to Rectangle variable
		int x = bounds.x;			// splits the specific value of bounds(x,y,w,h) to a specific variable
		int y = bounds.y;
		int w = bounds.width;
		int h = bounds.height;
		
		btn.addMouseListener(new MouseAdapter() {	// .addMouseListener to the parameter btn, with that method having new param of MouseAdapter
			@Override
			public void mousePressed(MouseEvent e) {	// Overrides method mousePressed
				btn.setBounds(x-5, y-5, w+10, h+10); 	// button is placed at the cords x and y, we calculate position based on width and height (think axis)
													 	// if your height changes from 50 to 100, your x need to go BACK by 25, same applies for y, height
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btn.setBounds(x, y, w, h);
			}
		});
	}
	/**
	 * method to indentify and connect to port
	 * 
	 * uses portConnection() method to deal with motor input
	 * 
	 * focuses on the port connection and Exceptions
	 * 
	 */
	public void portIdentify() {
		// Print the number of available ports
        System.out.println("Number of available ports: " + ports.length);
        
        // Check if there are any ports available
        if (ports.length == 0) {
            System.out.println("No serial ports found.");
            return; // Exit if no ports are found
        }
        
        // Select a port safely
        System.out.println("Using port: " + comPort.getSystemPortName());

        // Set parameters for the serial port
        comPort.setComPortParameters(9600, 8, 1, 0); // Adjust as needed
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 1000, 0);

        // Attempt to open the port
        if (comPort.openPort()) {
            System.out.println("Port opened successfully."); // gives info port is opened
            try {
               portConnection(TT,Z,Arm,Grip);
            } catch (Exception e) {
                System.err.println("Error during communication: " + e.getMessage());
                e.printStackTrace();
            } finally {
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) { // wanted the exit of the port to be inLine with user exiting
                    	comPort.closePort();
                        System.out.println("Port closed.");
                    }
                });
            }
                
        } else {
            System.err.println("Failed to open port: " + comPort.getLastErrorLocation());
        }
	}
	
	/*
	 * Method for outputting data to arduino
	 * 
	 * focuses on what button pressed and why
	 */
	void portConnection(int TT, int Z, int Arm, int Grip) {
		this.TT = TT;
		this.Z = Z;
		this.Arm = Arm;
		this.Grip = Grip;
        
		try {
			if (TT == 0 && Z == 0 & Arm == 0 & Grip == 0) {
				comPort.getOutputStream().write('0');
			} else if (TT == 1) { // using the param of the method to talk to the arduino
				comPort.getOutputStream().write('1'); // char characters easiest to pass
				System.out.println("Values (int,int,int,int): " + TT + ", " + Z + ", " + Arm + ", " + Grip); // debugging output
			} else if (TT == 2){
				// Example: Turn off the LED
				comPort.getOutputStream().write('2');
				System.out.println("Values (int,int,int,int): " + TT + ", " + Z + ", " + Arm + ", " + Grip);  
			} else if (Z == 1) { // using the param of the method to talk to the arduino
				comPort.getOutputStream().write('3'); // char characters easiest to pass
				System.out.println("Values (int,int,int,int): " + TT + ", " + Z + ", " + Arm + ", " + Grip);
			} else if (Z == 2){
				// Example: Turn off the LED
				comPort.getOutputStream().write('4');
				System.out.println("Values (int,int,int,int): " + TT + ", " + Z + ", " + Arm + ", " + Grip);  
			} else if (Arm == 1) { // using the param of the method to talk to the arduino
				comPort.getOutputStream().write('5'); // char characters easiest to pass
				System.out.println("Values (int,int,int,int): " + TT + ", " + Z + ", " + Arm + ", " + Grip);
			} else if (Arm == 2){
				// Example: Turn off the LED
				comPort.getOutputStream().write('6');
				System.out.println("Values (int,int,int,int): " + TT + ", " + Z + ", " + Arm + ", " + Grip);  
			} else if (Grip == 1) { // using the param of the method to talk to the arduino
				comPort.getOutputStream().write('7'); // char characters easiest to pass
				System.out.println("Values (int,int,int,int): " + TT + ", " + Z + ", " + Arm + ", " + Grip);
			}
		} catch(Exception e) {
		}
	      
	}
	// method to set the frame exception
	public void setFrmeException(Exception e) {
		FrameExceptions obj = new FrameExceptions("We ran into an issue loading new panel!");
		obj.errorDialog(e);	
	}
    // Method to return the panelContain
    public JPanel getPanelContainer() {
        return panelContainer;
    }
    
    // Method to return the CardLayout
    public CardLayout getCardLayout() {
        return cardLayout;
    }
    // method to return back to main
    public void showMainPanel() {
    	 System.out.println("Switching to Main Menu");
    	cardLayout.show(panelContainer, "Main Menu");
    }
    
    // Method to show CONTROL panel
    public void showControlPanel() {
        System.out.println("Switching to Control Panel");
        cardLayout.show(panelContainer, "Control Panel");
    }
    
    // Method to show advanced panel
    public void showAdvancedPanel() {
        System.out.println("Switching to Advanced Panel");
        cardLayout.show(panelContainer, "Advanced Panel");
    }
    /**
     * setupMain() method
     * 
     * sets up the 
     */
    private void setupMain() {
    	// Frame setup
    	frame.setSize(750, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(getPanelContainer());
    	// button listener
    	controlBtn.addActionListener(e -> showControlPanel());
    	advBtn.addActionListener(e -> showAdvancedPanel());
    	
		// sets bounds for the buttons
		controlBtn.setBounds(275,195,150,50);
		advBtn.setBounds(275,255,150,50);

		// disables the button outline
		controlBtn.setFocusable(false);
		advBtn.setFocusable(false);
		
		// sets the font on buttons
		controlBtn.setFont(roboto);
		advBtn.setFont(roboto);
		
		// adds buttons to panel
		panelMain.setLayout(null);
		panelMain.setBackground(Color.darkGray);
		panelMain.add(controlBtn);
		panelMain.add(advBtn);
		
		// resize method called
		btnResize(controlBtn);
		btnResize(advBtn);
		
		frame.setVisible(true);
    }
	/*
	 * constructor for UI
	 * 
	 * sets the same object
	 * 
	 * sets the panelUI object
	 * 
	 * sets the buttons
	 */
	public UI() {
		// set layout
		setLayout(new BorderLayout());
		
		// setup general panels
		panelMain.setLayout(null);
		controlPanel = new controllerUI(this);
		advancedPanel = new advancedUI(this);
		
		// adds panels to cardlayout
		panelContainer.add(panelMain, "Main Menu");
		panelContainer.add(controlPanel, "Control Panel");
		panelContainer.add(advancedPanel, "Advanced Panel");
		
		// setup main called to finish rest of setup
		setupMain();
	}
}
