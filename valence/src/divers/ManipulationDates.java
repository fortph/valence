package divers;

import java.util.Calendar;

public class ManipulationDates {
	private static Calendar cal;
	
	/**
	 *   renvoie la date du dernier jour du mois donné en paramètre
	 * @param debut
	 * @return
	 */
	public static java.sql.Date premierJourMoisCourant(){
		cal=Calendar.getInstance();
		cal.set(Calendar.DATE,1);
		java.util.Date date1=cal.getTime();
		return new java.sql.Date(date1.getTime());
	}

	public static java.sql.Date finDumois(java.sql.Date debut){
		cal=Calendar.getInstance();
		cal.setTime(debut);
		int njours=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DATE,njours);
		java.util.Date date2=cal.getTime();
		return new java.sql.Date(date2.getTime());
	}

}
