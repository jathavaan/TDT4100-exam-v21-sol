package del5_og_6;

import java.util.ArrayList;
import java.util.HashMap;

/* RentalCar listeners listens to changes in Status for all userNames.*/
public class RentalCarListener implements StatusListener {
	private HashMap<String, String> discounts = new HashMap<>();

	@Override
	/**
	 * Method that should be called when a given userName has updated its status.
	 */
	public void statusChanged(String username, String oldStatus, String newStatus) {
		discounts.put(username, newStatus);
		System.out.println(username + ": Changed status from " + oldStatus + " to " + newStatus + ".");
	}

	/**
	 * Get's the discount of a user. Should be a 100 if the user currently has Gold
	 * status, otherwise should be 0.
	 * 
	 * @param username The username of the user
	 * 
	 * @return The discount the user qualifies for.
	 */
	public int getDiscount(String username) {
		if (discounts.get(username).equals("Gold"))
			return 100;

		return 0;
	}
}
