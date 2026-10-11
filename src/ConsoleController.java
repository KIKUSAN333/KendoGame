import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.io.*;

public class ConsoleController implements ActionListener {
	private final static int DELAY = 50; // msec
	private Model model;
	private Timer timer;
	
	public ConsoleController(Model m) {
		model = m;
		//一定時間毎に通知を行うタイマー
		timer = new Timer(DELAY, this);
	}
	
	public void run() throws IOException {
		timer.start();
		
		//入力待ちつづける
		BufferedReader reader
		= new BufferedReader(new InputStreamReader(System.in));
		
		//入力したキーのプロセスを行う
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.isEmpty()) model.process(line);
			}
		} finally {
			timer.stop();
		}
		
	}
	
	//タイマーから通知があるたびにモデルに通知する(一定時間経過)
	public void actionPerformed(ActionEvent e) {
		model.process("TIME_ELAPSED");
	}
	
}
