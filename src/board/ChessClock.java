package board;

import java.util.*;
import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.paint.*;

public class ChessClock {

	private int minutes, seconds;
	Label clock; 
	Timer timer; 
	TimerTask task;
	Alert alert;
	boolean running;


	public ChessClock(int minutes, int seconds, boolean player) {
		print(format(minutes, seconds));
		this.minutes = minutes;
		this.seconds = seconds;
		clock = new Label(format(minutes, seconds));
		timer = new Timer();
		task = setTimerTask();
		alert = setAlert(player);
		setup();
	}

	/**
	 * Setting up the alert that will be displayed when this 
	 * clock's timer has run out signifying this player has lost
	 * @param player
	 * @return
	 */
	private Alert setAlert(boolean player) {
		Alert alert = new Alert(AlertType.INFORMATION, 
				"Player " + (player? 1 : 2) + 
				" timer has expired.\nPLAYER " + 
				(player? 2 : 1) +  " WINS!");
		return alert;
	}

	public void print(String str) {
		Literals.print(str, Literals.CLOCK_DEBUG);
	}
	
	/**
	 * Setup the schedule of the timer, with the 
	 * associated task, delay, and period
	 */
	public void setup() {
		timer.scheduleAtFixedRate(task, 1000, 1000);
	}	
	
	/**
	 * This is the task which will run every second as long
	 * as the timer is active
	 * @return
	 */
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
	
	/**
	 * Method to set the latest time on the clock label
	 */
	private void updateLabel() {
		if(running) {
			tick();
			clock.setText(format(minutes, seconds));
		}
		if(minutes == 0 && seconds == 0) {
			raiseAlert();
		}
	}
	
	/**
	 * Show game over dialog
	 */
	private void raiseAlert() {
		if(!alert.isShowing()) {
			clock.setTextFill(Color.web("#FF0000"));
			alert.showAndWait();
		}
	}
	
	/**
	 * This method updates the time in minutes and seconds every second,
	 * if the last second is reached the timer is stopped
	 */
	private void tick() {
		if(seconds == 1 && minutes == 0) {
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
	 * minutes and seconds in the format mm:ss
	 * @param minutes
	 * @param seconds
	 * @return
	 */
	public String format(int minutes, int seconds) {
		return String.format("%02d:%02d", minutes, seconds);
	}
	
	/**
	 * Return the label of the clock 
	 * i.e. the time it displays in the format mm:ss 
	 * @return
	 */
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
