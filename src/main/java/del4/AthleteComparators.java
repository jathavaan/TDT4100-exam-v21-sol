package del4;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AthleteComparators {

	/**
	 * @return a comparator that compares athletes based on their name. Using this
	 *          comparator, Ane should come before Berit
	 */
	public static Comparator<Athlete> getSimpleComparator() {
		Comparator<Athlete> comparator = new Comparator<Athlete>() {
			@Override
			public int compare(Athlete o1, Athlete o2) {
				return o1.getName().compareTo(o2.getName());
			}
		};

		return comparator;
	}

	/**
	 * @return A comparator that compares athletes based on the number of medals of
	 *          different valour. The comparator will be used for sorting athletes
	 *          based on putting the athlete with the highest number of medals of the best valour
	 *          first.
	 * 
	 *          If one athlete has more "Gold" medals than the other athlete it
	 *          should come before that one. If they have equal number of "Gold"
	 *          medals they should be compared on the number of "Silver" medals, and
	 *          if that is equal on the number of "Bronze" medals. If they have the
	 *          same number of medals of all valour, they should be compared based
	 *          on the name similar to getSimpleComparator
	 *          
	 *          The spelling and order of the medals can be seen in the list validMetals in the Medal class. 
	 */
	public static Comparator<Athlete> getAdvancedComparator() {
		// Oppgaven løst med lambda syntaks
		Comparator<Athlete> comparator = new Comparator<Athlete>() {
			@Override
			public int compare(Athlete o1, Athlete o2) {
				int gold1 = (int) o1.getMedals().stream().filter(metal -> metal.getMetal().equals("Gold")).count();
				int gold2 = (int) o2.getMedals().stream().filter(metal -> metal.getMetal().equals("Gold")).count();

				int silver1 = (int) o1.getMedals().stream().filter(metal -> metal.getMetal().equals("Silver")).count();
				int silver2 = (int) o2.getMedals().stream().filter(metal -> metal.getMetal().equals("Silver")).count();

				int bronze1 = (int) o1.getMedals().stream().filter(metal -> metal.getMetal().equals("Bronze")).count();
				int bronze2 = (int) o2.getMedals().stream().filter(metal -> metal.getMetal().equals("Bronze")).count();

				if (gold1 != gold2) {
					return gold2 - gold1;
				} else {
					if (silver1 != silver2) {
						return silver2 - silver1;
					} else {
						return bronze2 - bronze1;
					}
				}
			}
		};

		// Oppgaven løst uten lambda syntaks
		Comparator<Athlete> comparator1 = new Comparator<>() {

			@Override
			public int compare(Athlete o1, Athlete o2) {
				List<Medal> o1Medals = o1.getMedals();
				List<Medal> o2Medals = o2.getMedals();

				int gold1 = AthleteComparators.countMedals("Gold", o1Medals);
				int gold2 = AthleteComparators.countMedals("Gold", o2Medals);

				int silver1 = AthleteComparators.countMedals("Silver", o1Medals);
				int silver2 = AthleteComparators.countMedals("Silver", o2Medals);

				int bronze1 = AthleteComparators.countMedals("Bronze", o1Medals);
				int bronze2 = AthleteComparators.countMedals("Bronze", o2Medals);

				if (gold1 != gold2) {
					return gold2 - gold1;
				} else {
					if (silver1 != silver2) {
						return silver2 - silver1;
					} else {
						return bronze2 - bronze1;
					}
				}
			}
		};

		return comparator1;
	}

	private static int countMedals(String type, List<Medal> medals) {
		if (!Medal.validMetals.contains(type)) throw new IllegalArgumentException("Ugyldig type");
		int count = 0;

		for (int i = 0; i < medals.size(); i++) {
			Medal medal = medals.get(i);
			if (medal.getMetal().equals(type)) count++;
		}

		return count;
	}

	public static void main(String[] args) {
		Medal gold = new Medal("Gold");
		Medal silver = new Medal("Silver");
		Medal bronze = new Medal("Bronze");
		Athlete athlete1 = new Athlete("test", "Athlete", Arrays.asList(gold));
		Athlete athlete2 = new Athlete("test", "Bathlete", Arrays.asList());
		Athlete athlete3 = new Athlete("test", "Cathlete", Arrays.asList(gold, gold));
		Athlete athlete4 = new Athlete("test", "Dathlete", Arrays.asList(gold, silver, silver));
		Athlete athlete5 = new Athlete("test", "Eathlete", Arrays.asList(gold, silver, silver, bronze));
		List<Athlete> athletes = Arrays.asList(athlete1, athlete2, athlete3, athlete4, athlete5);
		// Hint - a more useful toString method of Athletes will yield a more readable
		// output here.
		Collections.sort(athletes, getSimpleComparator());
		System.out.println(athletes);
		Collections.sort(athletes, getAdvancedComparator());
		System.out.println(athletes);

	}

}
