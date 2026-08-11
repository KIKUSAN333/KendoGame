
public class Time {
	private final static int TIME_X = 39;
	private final static int TIME_Y = 3;
	
	private int maxTime = 99;
	private int time;
	
	public Time() {
		super();
		// TODO Auto-generated constructor stub
		time = maxTime;
	}
	
	public void oneSecondHasPassed() {
		time--;
	}

	public void showTime(ConsoleView view) {
		String timeString = Integer.toString(time);
		view.drawString(timeString,TIME_X,TIME_Y);
		
		if(isTimeEnd()) {
			view.drawFramedString("  TIME UP  ", '#', Result.RESULT_BAR_X, Result.RESULT_BAR_Y);
		}
	}
	
	public boolean isTimeEnd() {
		if(time <= 0) {
			return true;
		}
		return false;
	}
	
	public int getTime() {
		return time;
	}
}
