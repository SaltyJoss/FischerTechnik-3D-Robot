import java.awt.*;
import javax.swing.JFrame;

public class Interface extends JFrame{
	
	JFrame frame = new JFrame("3D-ROBOT-INTERFACE");
	
	public Interface() {
		Container toplevel = frame.getContentPane();
		frame.getContentPane().setBackground(new Color(66,179,252));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		
		toplevel.add(this);
		frame.pack();
		frame.setVisible(true);
	}
}
