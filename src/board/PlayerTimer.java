package board;

import java.util.*;
import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;

public class PlayerTimer {

	TimerTask task;
	Label countdown;
	Timer timer;
	Alert alert;
	//Color colour;
	private int minutes, seconds;
	boolean running;

	/**
	 * Constructor for the ChessClock
	 * 
	 * @param minutes
	 * @param seconds
	 * @param player
	 */
	public PlayerTimer(int minutes, int seconds, boolean player) {
		print(format(minutes, seconds));
		this.minutes = minutes;
		this.seconds = seconds;
		countdown = new Label(format(minutes, seconds));
		countdown.setFont(Utils.clockFont);
		timer = new Timer();
		task = setTimerTask();
		timer.scheduleAtFixedRate(task, 0, 1000);
		alert = setAlert(player);
	}

	/**
	 * Setting up the alert that will be displayed when this clock's timer has run
	 * out signifying this player has lost
	 * 
	 * @param player
	 * @return
	 */
	private Alert setAlert(boolean player) {
		return new Alert(AlertType.INFORMATION,
				"Player " + (player ? 1 : 2) + " timer has expired.\nPLAYER " + (player ? 2 : 1) + " WINS!");
	}

	public void print(String str) {
		Utils.print(str, Utils.CLOCK_DEBUG);
	}

	/**
	 * This is the task which will run every second as long as the timer is active
	 * 
	 * @return
	 */
	TimerTask setTimerTask() {
		task = new TimerTask() {
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						if (running) {
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
		if (running) {
			tick();
			countdown.setText(format(minutes, seconds));
			if (minutes == 0 && seconds == 0) {
				raiseAlert();
			}
		} else {
			countdown.setTextFill(Utils.wait);
		}

	}

	/**
	 * Show game over dialog
	 */
	private void raiseAlert() {
		if (!alert.isShowing()) {
			countdown.setTextFill(Utils.lost);
			alert.showAndWait();
		}
	}

	/**
	 * This method updates the time in minutes and seconds every second, if the last
	 * second is reached the timer is stopped
	 */
	private void tick() {
		if (seconds == 1 && minutes == 0) {
			running = false;
			timer.cancel();
			timer.purge();
		} else if (seconds <= 10 && minutes == 0) {
			countdown.setTextFill(Utils.losing);
		} else if (seconds == 0) {
			seconds += 60;
			minutes--;
		} else {
			countdown.setTextFill(Utils.run);
		}
		seconds--;
	}

	// TODO : Add logic to deal with NORMAL mode and TIME DELAY mode
	// This requires certain additions of time after each move see here:
	// https://www.fide.com/fide/handbook.html?id=124&view=article

	/**
	 * Returns a formatted String for the timer in minutes and seconds in the format
	 * mm:ss
	 * 
	 * @param minutes
	 * @param seconds
	 * @return
	 */
	public String format(int minutes, int seconds) {
		return String.format("%02d:%02d", minutes, seconds);
	}

	/**
	 * Return the label of the clock i.e. the time it displays in the format mm:ss
	 * 
	 * @return
	 */
	public Label getCountdown() {
		return countdown;
	}

	/**
	 * Sets whether this clock is currently counting down i.e. if it is the
	 * associated player's turn
	 * 
	 * @param running
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
}
