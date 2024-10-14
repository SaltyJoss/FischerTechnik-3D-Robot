
public class ControllerExceptions extends ExceptionsUI {
	private String msg;
	public void errorDialog(Exception e) {
		errorDialog(msg, e);
	}
	public ControllerExceptions(String msg) {
		super(msg);
		this.msg = msg;
	}
	
}
