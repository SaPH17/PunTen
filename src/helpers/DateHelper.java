package helpers;

import java.util.Date;

public class DateHelper {
	public static String convertDateToRelativeTime(Date date) {
		long days = (new Date().getTime() - date.getTime()) / 86400000;

	    if(days == 0) {
	    	return "Today";
	    }
	    else if(days == 1) {
	    	return "Yesterday";
	    }
	    else if(days <= 30) {
	    	return days + " days ago";
	    }
	    else if(days <= 365){
	    	return days / 30 + " month(s) ago";
	    }
	    else {
	    	return days / 365 + " year(s) ago";
	    }
	}
}
