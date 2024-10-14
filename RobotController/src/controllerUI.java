import java.awt.Button;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class controllerUI extends JPanel {
	private Rectangle bounds;
	// JFrame objects
	JPanel panelCtrl = new JPanel();
	// button objects
	private Button upBtn = new Button("UP");
	private Button downBtn = new Button("DOWN");
	private Button gripBtn = new Button("GRAB");
	private Button leftBtn = new Button("LEFT");
	private Button rightBtn = new Button("RIGHT");
	private Button leftBaseBtn = new Button("BASE LEFT");
	private Button rightBaseBtn = new Button("BASE RIGHT");
	private Button resetBtn = new Button("RESET");
	private Button menuBtn = new Button("MENU");
	private Button releaseBtn = new Button("DROP");
	
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
	/**
	 * addActionListeners method to listen for different actions
	 * 
	 * #includes mousePressed,mosueReleased,actionPerformed
	 * 
	 * Helpful way to organise the large amount of functions needed for the motors
	 *
	 * 
	 * @param main
	 */
	private void addActionListeners(UI main) {
        // MouseListener for Up Button (Z - Vertical)
        upBtn.addMouseListener(new MouseAdapter() {				// adds mouseListener to button
            @Override											// Overrides the 
            public void mousePressed(MouseEvent e) {			// mousePressed with param of MouseEvent (variable e)
                main.portConnection(0, 1, 0, 0);				// uses UI's portConnection method to update param for arduino
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            	main.portConnection(0, 0, 0, 0);
            }
        });

        // MouseListener for Down Button (Z - Vertical)
        downBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                main.portConnection(0, 2, 0, 0);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            	main.portConnection(0, 0, 0, 0);
            }
        });
        // MouseListener for Grip Button (Gripper)
        gripBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	main.portConnection(0, 0, 0, 1);
            }
            public void mouseReleased(MouseEvent e) {
            	main.portConnection(0, 0, 0, 0);
            }
        });
        releaseBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	main.portConnection(0, 0, 0, 2
            			);
            }
            public void mouseReleased(MouseEvent e) {
            	main.portConnection(0, 0, 0, 0);
            }
        });
        

        // MouseListener for Left Button (Arm)
        leftBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                main.portConnection(0, 0, 1, 0);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            	main.portConnection(0, 0, 0, 0);
            }
        });

        // MouseListener for Right Button (Arm)
        rightBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                main.portConnection(0, 0, 2, 0);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            	main.portConnection(0, 0, 0, 0);
            }
        });
        
        // MouseListener for Left Base Button (TT)
        leftBaseBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                main.portConnection(1, 0, 0, 0);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            	main.portConnection(0, 0, 0, 0);
            }
        });

        // MouseListener for Right Base Button (TT)
        rightBaseBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                main.portConnection(2, 0, 0, 0);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            	main.portConnection(0, 0, 0, 0);
            }
        });

        // Action Listener for Reset Button (UI specific)
        resetBtn.addActionListener(new ActionListener() { // plan is to have this button reset robot to middle
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
	public controllerUI(UI main) {
		setLayout(null);
		setBackground(Color.darkGray);
		// setting the bounds of all the buttons
		upBtn.setBounds(200, 135, 50, 50); // x,y,w,h for location (x,y) and size(w,h)
		downBtn.setBounds(200, 265, 50, 50);
		leftBtn.setBounds(135, 200, 50, 50);
		rightBtn.setBounds(265, 200, 50, 50);
		leftBaseBtn.setBounds(460, 200, 75, 50);
		rightBaseBtn.setBounds(615, 200, 75, 50);
		gripBtn.setBounds(200, 200, 50, 50);
		resetBtn.setBounds(650, 10, 75, 25);
		menuBtn.setBounds(565, 10, 75, 25);
		releaseBtn.setBounds(550, 200, 50, 50);
		
		// disables the button outline
		upBtn.setFocusable(false);
		downBtn.setFocusable(false);
		leftBtn.setFocusable(false);
		rightBtn.setFocusable(false);
		leftBaseBtn.setFocusable(false);
		rightBaseBtn.setFocusable(false);
		gripBtn.setFocusable(false);
		resetBtn.setFocusable(false);
		menuBtn.setFocusable(false);
		releaseBtn.setFocusable(false);
		
		// adds all the buttons to panel UI
		add(upBtn);
		add(downBtn);
		add(gripBtn);
		add(leftBtn);
		add(rightBtn);
		add(leftBaseBtn);
		add(rightBaseBtn);
		add(resetBtn);
		add(menuBtn);
		add(releaseBtn);
		
		// calling buttonPress() method
		btnResize(upBtn);		// puts the wanted specific btn to be applied to the method
		btnResize(downBtn);
		btnResize(leftBtn);
		btnResize(rightBtn);
		btnResize(leftBaseBtn);
		btnResize(rightBaseBtn);
		btnResize(gripBtn);
		btnResize(resetBtn);
		btnResize(menuBtn);
		btnResize(releaseBtn);
		
		// allocated button to go back to original menu
		addActionListeners(main);
		
	    // Action Listener for Menu Button
        menuBtn.addActionListener(e -> main.showMainPanel());
		
	}
}
