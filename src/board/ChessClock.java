package board;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.*;

public class ChessClock {

	private int minutes, seconds;
	Label clock; 
	Timer timer; 
	TimerTask task;
	boolean running;
	Alert alert;

	public ChessClock(int minutes, int seconds, boolean player) {
		print("creating clock");
		print(format(minutes, seconds));
		this.minutes = minutes;
		this.seconds = seconds;
		clock = new Label(format(minutes, seconds));
		timer = new Timer();
		task = setTimerTask();
		alert = new Alert(AlertType.INFORMATION, "Player " + (player? 1 : 2) + " timer has expired.\nPLAYER " + (player? 2 : 1) +  " WINS!");
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
							updateLabel();
							print(format(minutes, seconds));
						}
					}
				});
			}
		};
		return task;
	}	
	
	private void updateLabel() {
		if(running) {
			tick();
			clock.setText(format(minutes, seconds));
		}
		if(minutes == 0 && seconds == 0) {
			if(!alert.isShowing()) {
				clock.setTextFill(Color.web("#FF0000"));
				alert.showAndWait();
			}
		}
	}
	
	/**
	 * This method acts as the tick to update time in minutes and seconds each second
	 * If the last second is reached the timer is stopped
	 */
	private void tick() {
		if(seconds == 1 && minutes == 0 && running) {
			running = false;
			timer.cancel();
			timer.purge();
		}
		else if(seconds == 0) {
			seconds += 60;
			minutes--;
		}
		seconds--;
	}
	
	/**
	 * Returns a formatted String for the timer in 
	 * minutes and seconds in the format MM:SS
	 * @param minutes
	 * @param seconds
	 * @return
	 */
	public String format(int minutes, int seconds) {
		return String.format("%02d:%02d", minutes, seconds);
	}
	
	public Label getLabel() {
		return clock;
	}

	/**
	 * Sets whether this clock is currently counting down
	 * i.e. if it is the associated player's turn
	 * @param running
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
}
