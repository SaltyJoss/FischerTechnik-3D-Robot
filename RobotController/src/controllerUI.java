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
        // Action Listener for Up Button
        upBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                main.portConnection(1, 0, 0, 0);
                System.out.println("Value: " + main.setBase());
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            	main.portConnection(0, 0, 0, 0);
            	System.out.println("Value: " + main.setBase());
            }
        });

        // Action Listener for Down Button
        downBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Down Button Pressed");
                // Add functionality for DOWN action
            }
        });

        // Action Listener for Grip Button
        gripBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Grip Button Pressed");
                // Add functionality for GRAB action
            }
        });

        // Action Listener for Left Button
        leftBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Left Button Pressed");
                // Add functionality for LEFT action
            }
        });

        // Action Listener for Right Button
        rightBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Right Button Pressed");
                // Add functionality for RIGHT action
            }
        });

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
	public controllerUI(UI main) {
		setLayout(null);
		setBackground(Color.darkGray);
		// setting the bounds of all the buttons
		upBtn.setBounds(200, 135, 50, 50); // x,y,w,h for location (x,y) and size(w,h)
		downBtn.setBounds(200, 265, 50, 50);
		leftBtn.setBounds(135, 200, 50, 50);
		rightBtn.setBounds(265, 200, 50, 50);
		gripBtn.setBounds(200, 200, 50, 50);
		resetBtn.setBounds(650, 10, 75, 25);
		menuBtn.setBounds(565, 10, 75, 25);
		
		// disables the button outline
		upBtn.setFocusable(false);
		downBtn.setFocusable(false);
		leftBtn.setFocusable(false);
		rightBtn.setFocusable(false);
		gripBtn.setFocusable(false);
		resetBtn.setFocusable(false);
		menuBtn.setFocusable(false);
		
		// adds all the buttons to panel UI
		add(upBtn);
		add(downBtn);
		add(gripBtn);
		add(leftBtn);
		add(rightBtn);
		add(resetBtn);
		add(menuBtn);
		
		// calling buttonPress() method
		btnResize(upBtn);		// puts the wanted specific btn to be applied to the method
		btnResize(downBtn);
		btnResize(leftBtn);
		btnResize(rightBtn);
		btnResize(gripBtn);
		btnResize(resetBtn);
		btnResize(menuBtn);
		
		// allocated button to go back to original menu
		addActionListeners(main);
		
	    // Action Listener for Menu Button
        menuBtn.addActionListener(e -> main.showMainPanel());
		
	}
}
