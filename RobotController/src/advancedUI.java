import java.awt.Button;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class advancedUI extends JPanel {
	private Rectangle bounds;
	// button objects
	private Button resetBtn = new Button("RESET");
	private Button menuBtn = new Button("MENU");
	
	// dial objects
	Dial baseDial = new Dial();
	// method to set the control exception
	public void setCtrlException(Exception e) {
		ControllerExceptions obj = new ControllerExceptions("We ran into an issue with the controller input!");
		obj.errorDialog(e);	
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
	private void addActionListeners(UI main) {
        // Action Listener for Reset Button
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Reset Button Pressed");
                // Add functionality for RESET action
            }
        });
    }
	/*
	 * Constructor
	 * 
	 * panelCtrl is set
	 * 
	 * buttons added
	 * 
	 * resize method called
	 * 
	 * button function set
	 */
	public advancedUI(UI main) {
		setLayout(null);
		setBackground(Color.darkGray);
		// setting the bounds of all the buttons
		resetBtn.setBounds(650, 10, 75, 25);
		menuBtn.setBounds(565, 10, 75, 25);
		
		// disables the button outline
		resetBtn.setFocusable(false);
		menuBtn.setFocusable(false);
		
		// adds all the buttons to panel UI
		add(resetBtn);
		add(menuBtn);
		
		// calling buttonPress() method
		btnResize(resetBtn);
		btnResize(menuBtn);
		
		// allocated button to go back to original menu
		addActionListeners(main);
		
	    // Action Listener for Menu Button
        menuBtn.addActionListener(e -> main.showMainPanel());
		
	}
}
