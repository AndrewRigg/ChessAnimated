package board;

import java.util.*;
import javafx.scene.control.*;

public class ChessClock {

	private static int minutes = 20;
	private static int seconds = 00;
	
	Label clock; 
	Timer timer; 
	TimerTask task;
	
	public ChessClock() {
		this(minutes, seconds);
	}
	
	public ChessClock(int minutes, int seconds) {
		clock = new Label(setText(minutes, seconds));
		timer = new Timer();
		task = setTimerTask();
	}
	
	public void print(String str) {
		Literals.print(str, Literals.CLOCK_DEBUG);
	}
	
	public String setText(int minutes, int seconds) {
		return String.format("%02d:%02d", minutes, seconds);
	}
	
	private TimerTask setTimerTask() {
		TimerTask task = new TimerTask() {
			public void run()
			{
				print("Tick");
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
