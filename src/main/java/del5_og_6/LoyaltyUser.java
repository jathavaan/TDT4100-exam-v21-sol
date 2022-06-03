package del5_og_6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class LoyaltyUser {
	private String username;
	private int points;
	private String status;
	public static List<String> validStatuses = Arrays.asList("Basic", "Gold", "Silver", "Platinum");
	private final HashMap<StatusListener, String> listeners = new HashMap<StatusListener, String>();

	public LoyaltyUser(String username) {
		this.username = username;
		this.status = "Basic";
	}

	public String getUsername() {
		return username;
	}

	public int getPoints() {
		return points;
	}

	public String getStatus() {
		return status;
	}

	/**
	 * Adds point to this user
	 * @param points the points to add. Can also be a negative number
	 */
	public void addPoints(int points) {
		this.points += points;
		this.checkForStatusUpgrade();
	}

	/**
	 * Checks whether the user qualifies for a status upgrade/downgrade.
	 * 
	 * TODO: If the user qualifies for a new status all observers interested in the new or old
	 * status should be notified
	 */
	public void checkForStatusUpgrade() {
		if (this.points > 10000) {
			if (!this.status.equals("Platinum")) fireStatusChanged(this.status, "Platinum");
			this.status = "Platinum";
		} else if (this.points > 5000) {
			if (!this.status.equals("Gold")) fireStatusChanged(this.status, "Gold");
			this.status = "Gold";
		} else if (this.points > 1000) {
			if (!this.status.equals("Silver")) fireStatusChanged(this.status, "Silver");
			this.status = "Silver";
		} else if (this.points <= 1000) {
			if (!this.status.equals("Basic")) fireStatusChanged(this.status, "Basic");
			this.status = "Basic";
		}
	}

	/**
	 * Adds a listener that listens on when this specific status is obtained or
	 * lost. If the user has been previously added, the old status should be
	 * overridden and the listener should listen on the new status
	 * 
	 * @param listener The listener that will observe
	 * 
	 * @param status   The status the listener will listen to
	 * 
	 * @throws IllegalArgumentException If the status is not valid
	 */
	public void addListener(StatusListener listener, String status) {
		if (!LoyaltyUser.validStatuses.contains(status))
			throw new IllegalArgumentException("Ugyldig status");

		listeners.put(listener, status);
	}

	/**
	 * Remove the listener
	 * 
	 * @param listener The listener to remove
	 */
	public void removeListener(StatusListener listener) {
		if (listeners.containsKey(listener))
			listeners.remove(listener);
	}

	/**
	 * Updates all listeners that were interested in either the old or the new
	 * status that the status of the user has changed. Observers should only be
	 * notified if oldStatus and newStatus is different
	 * 
	 * @param oldStatus The old status of the user
	 * 
	 * @param newStatus The new status of the user
	 */
	private void fireStatusChanged(String oldStatus, String newStatus) {
		this.status = newStatus;
		listeners.forEach((listener, status) -> listener.statusChanged(this.getUsername(), oldStatus, newStatus));
	}
	
	public static void main(String[] args) {
		// Both LoyaltyUser class and RentalCarListener class have to be implemented for the main method to print the correct results
		LoyaltyUser user = new LoyaltyUser("test");
		LoyaltyUser user2 = new LoyaltyUser("test2");
		RentalCarListener listener = new RentalCarListener();
		user.addListener(listener, "Gold");
		user2.addListener(listener, "Gold");
		user.addPoints(6000);
		user2.addPoints(2000);
		// Should be 100
		System.out.println(listener.getDiscount("test"));
		
		// Should be 0
		System.out.println(listener.getDiscount("test2"));
		
	}
}
