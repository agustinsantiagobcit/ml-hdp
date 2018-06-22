package cl.metlife.conciliacion.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static Date parseDate(String proposedDate) throws ParseException {

        String[] formats = {"dd/MM/yyyy", "dd-MM-yyyy", "dd/MMM/yyyy", "dd-MMM-yyyy", "yyyy-MM-dd", "yyyy/MM/dd"};
        SimpleDateFormat simpleDateFormat;
        Date parse;

        for (String format : formats) {
            simpleDateFormat = new SimpleDateFormat(format);

            try {
                parse = simpleDateFormat.parse(proposedDate);
                return parse;
            } catch (ParseException e) {
                continue;
            }
        }

        throw new ParseException("The date was not parseable to any specified format.", 0);
    }

}
