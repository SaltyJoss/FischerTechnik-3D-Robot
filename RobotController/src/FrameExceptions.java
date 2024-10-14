
public class FrameExceptions extends ExceptionsUI {
	private String msg;
	public void errorDialog(Exception e) {
		errorDialog(msg, e);
	}
	
	public FrameExceptions(String msg) {
		super(msg);
		this.msg = msg;
	}
	
}
