import javax.swing.JFrame;
import javax.swing.JOptionPane;

public abstract class ExceptionsUI extends Exception {
	/**
	 * errorDialog message in JFrame box
	 * 
	 */
	public void errorDialog(String msg, Exception e) {
		JOptionPane.showMessageDialog(new JFrame(), msg, "ERROR:", JOptionPane.ERROR_MESSAGE);
		e.printStackTrace();
	}

	public ExceptionsUI (String msg) {
		super(msg);
	}
}
