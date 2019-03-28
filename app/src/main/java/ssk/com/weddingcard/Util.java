package ssk.com.weddingcard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by santosh.kathait on 21-11-2017.
 */

public class Util {

    public static String firstLetterToUpperCase(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String getVenueDate(String date){
        try {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return new SimpleDateFormat("EEEE MMMM dd, yyyy").format(formatter.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String pad(String val) {
        if (val.length() == 1) {
            return "0" + val;
        }
        return val;
    }
}
