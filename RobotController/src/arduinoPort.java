import javax.swing.JFrame;

import com.fazecast.jSerialComm.SerialPort;

public class arduinoPort {

	public static void main(String[] args) {
		UI main = new UI();
		
		main.portIdentify();
    }

}
