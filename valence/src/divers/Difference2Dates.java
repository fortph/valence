package divers;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Difference2Dates {

	public static long getDateDiffEnJours(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}

}
