package del1;

import java.util.ArrayList;
import java.util.Collection;

public class VaccineTrial {

	// Add any needed fields here
	private ArrayList<VaccineTrialVolunteer> volunteers = new ArrayList<>();

	/**
	 * Adds a new VaccineTrialVolunteer to the trial
	 * 
	 * @param id      The id of the volunteer
	 * 
	 * @param placebo Whether the volunteer was given a placebo, or the actual
	 *                vaccine
	 */
	public void addVolunteer(String id, boolean placebo) {
		// Sjekker om en frivillig med samme ID har blitt lagt til før
		/**
		 * Bruk av streams
		 */
		if (volunteers.stream().anyMatch(v -> v.getId().equals(id))) return;

		/**
		 * Bruk av for-løkke
		 */
		for (int i = 0; i < volunteers.size(); i++) {
			VaccineTrialVolunteer volunteer = volunteers.get(i);
			if (volunteer.getId().equals(id))
				return;
		}

		volunteers.add(new VaccineTrialVolunteer(id, placebo));
	}

	/**
	 * Returns whether the vaccine's effectiveness rate is higher than the provided
	 * limit. The effectiveness of the vaccine is calculated as follows: 
	 * 
	 * 1- (number of people that received the vaccine and got sick/
	 *         number of people that got sick)
	 * 
	 * If there is no sick people, the vaccine is not effective
	 * 
	 * @param limit A limit to compare against
	 * 
	 * @throws IllegalArgumentException If limit is not between (including) 0 and 1.
	 * 
	 * @return Whether the vaccine effectiveness rate is higher than the limit
	 */
	public boolean isMoreEffectiveThanLimit(double limit) {
		if (0 > limit || limit > 1)
			throw new IllegalArgumentException("Grensen må være mellom 0 og 1");

		/**
		 * Med lambda syntaks
		 */
		double vaccineAndSick = (double) volunteers.stream().filter(v -> v.gotSick() && !v.isPlacebo()).count();
		double sick = (double) volunteers.stream().filter(v -> v.gotSick()).count();

		/**
		 * Uten lambda syntaks

		int vaccineAndSick1 = 0;
		int sick1 = 0;

		for (int i = 0; i < volunteers.size(); i++) {
			VaccineTrialVolunteer vol = volunteers.get(i);

			if (vol.gotSick() && !vol.isPlacebo())
				vaccineAndSick1++;

			if (vol.gotSick()) sick1++;
		}
		 */

		double effectiveness = 1 - vaccineAndSick / sick;
		return effectiveness > limit;
	}

	/**
	 * Updates the sick state of a VaccineTrialVolunteer
	 * 
	 * @param id The id of the volunteer to set sick.
	 * @throws IllegalArgumentException if there is no volunteer with the given id
	 */
	public void setSick(String id) {
		VaccineTrialVolunteer volunteer = getVolunteer(id);
		volunteer.setGotSick(true);
	}

	/**
	 * Get's the volunteer with the given ID
	 * 
	 * @param id The id of the volunteer to set sick.
	 * 
	 * @return The vaccine trial volunteer with the given ID. If the ID is not valid
	 *         for any volunteer, return null
	 */
	public VaccineTrialVolunteer getVolunteer(String id) {
		return volunteers.stream().filter(v -> v.getId().equals(id)).findFirst().get(); // Lambda syntaks
		/**
		 * Uten lambda syntaks
		VaccineTrialVolunteer volunteer = null;

		for (int i = 0; i < volunteers.size(); i++) {
			VaccineTrialVolunteer vol = volunteers.get(i);

			if (vol.getId().equals(id)) {
				volunteer = vol;
				break;
			}
		}

		return volunteer;
		 */
	}

	public static void main(String[] args) {
		VaccineTrial trial = new VaccineTrial();
		trial.addVolunteer("1", false);
		trial.addVolunteer("2", false);
		trial.addVolunteer("3", true);
		trial.addVolunteer("4", true);
		trial.volunteers.stream().forEach(v -> System.out.println(v.toString()));
		System.out.println();

		trial.setSick("4");
		trial.volunteers.stream().forEach(v -> System.out.println(v.toString()));

		// Should now be true
		System.out.println(trial.isMoreEffectiveThanLimit(0.5));
	}
}
