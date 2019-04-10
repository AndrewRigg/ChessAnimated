package board;

import java.util.*;

import javafx.scene.control.Label;

public class ChessClock {

	int minutes = 20;
	int seconds = 00;
	
	Label clock; 
	Timer timer; 
	
	public ChessClock() {
		clock = new Label(String.format("%02d:%02d", minutes, seconds));
		timer = new Timer();
		TimerTask task = setTimerTask();
	}
	
	public void setText(String hours, String minutes) {
		//return String.format("%02d:%02d", minutes, seconds);
	}
	
	private TimerTask setTimerTask() {
		TimerTask task = new TimerTask() {
			public void run()
			{
				System.out.println("Tick");
				updateLabel(clock);
			}
		};
		return task;
	}	

	public void setup() {
		timer.schedule(setTimerTask(), 1000);
	}
	
	private void updateLabel(Label clock) {
		clock.setText(getNewTime(minutes, seconds));
	}
	
	private String getNewTime(int minutes, int seconds) {
		if(seconds == 0) {
			minutes--;
		}
		seconds -= 1 % 60;
		return null;
	}
	
	public Label getLabel() {
		return clock;
	}
}
