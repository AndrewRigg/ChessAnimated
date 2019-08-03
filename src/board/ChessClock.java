package board;

import java.util.*;

import javafx.application.Platform;
import javafx.scene.control.*;

public class ChessClock {

	private static int defaultMinutes = 20, defaultSeconds = 00;
	private int minutes, seconds;
	Label clock; 
	Timer timer; 
	TimerTask task;
	boolean running;
	
	public ChessClock() {
		this(defaultMinutes, defaultSeconds);
	}
	
	public ChessClock(int minutes, int seconds) {
		this.minutes = minutes;
		this.seconds = seconds;
		clock = new Label(format(minutes, seconds));
		timer = new Timer();
		task = setTimerTask();
		running = false;
		setup();
	}
	
	public void print(String str) {
		Literals.print(str, Literals.CLOCK_DEBUG);
	}
	
	public void setup() {
		timer.scheduleAtFixedRate(task, 1000, 1000);
	}	
	
	private TimerTask setTimerTask() {
		task = new TimerTask() {
			public void run()
			{
				Platform.runLater(new Runnable() {
					public void run() {
						if(running) {
							print(format(minutes, seconds));
							updateLabel();
						}
					}
				});
			}
		};
		return task;
	}	
	
	private void updateLabel() {
		clock.setText(getNewTime());
	}
	
	private String getNewTime() {
		if(seconds == 0) {
			seconds += 60;
			minutes--;
		}
		seconds--;
		return format(minutes, seconds);
	}
	
	public String format(int minutes, int seconds) {
		return String.format("%02d:%02d", minutes, seconds);
	}
	
	public Label getLabel() {
		return clock;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}
