package dk.tbertelsen.android.castdr.api.model.subtypes;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class MuBase {
	public abstract boolean isValid();
	
	protected Timestamp getTimestamp(String date) {
		Timestamp timestamp = null;
    	try{
    		String regex = "([0-9]{4})-([0-9]{2})-([0-9]{2})T([0-9]{2}):([0-9]{2}):([0-9]{2}).*?Z";
    		Matcher m = Pattern.compile(regex).matcher(date);
    		if (m.matches()) {
    			String time = m.group(1) + "-" + m.group(2) + "-" + m.group(3) + " " + m.group(4) + ":" + m.group(5) + ":" + m.group(6);
    			
        	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
//    			DateFormat dateFormat = SimpleDateFormat.getDateInstance();
//    			dateFormat.format("yyyy-MM-dd hh:mm:ss");
        	    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        	    Date parsedDate = dateFormat.parse(time);
        	    
        	    dateFormat.setTimeZone(TimeZone.getDefault());
        	    
        	    timestamp = new Timestamp(parsedDate.getTime());
    		} else {
    			timestamp = new Timestamp(0);
    		}
    	} catch(Exception e) {
    		timestamp = new Timestamp(0);
    	}
		return timestamp;
	}
}
