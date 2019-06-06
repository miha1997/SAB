package student.helper;

import java.util.Calendar;

public class Timer {
	
	private Calendar current;
	private static Timer timer = null;
	
	private Timer() {
		
	}
	
	public static Timer getTimer() {
		if(timer == null)
			timer = new Timer();
		
		return timer;
	}
	
	public void setTime(Calendar init) {
		current = (Calendar) init.clone();
	}
	
	//update states of orders
	public void passDay() {
		
	}
	
	public Calendar getTime() {
		return current;
	}
}
