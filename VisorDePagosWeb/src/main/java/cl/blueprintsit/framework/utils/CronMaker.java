package cl.blueprintsit.framework.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BluePrints Developer on 22-02-2017.
 */
public class CronMaker implements Serializable {

    public String getMinutesExpression(int minutes){
        return "0 0/" + minutes + " * 1/1 * ? *";
    }

    public String getHourExpression(String hour, String minute) {
        return "0 " + minute + " " + hour + " ? * MON-FRI *";
    }

    public String getWeeklyExpression(String hourWeekly, String minuteWeekly, String[] selectedDays) {
        return "0 " + minuteWeekly + " " + hourWeekly + " ? * " + getFormattedSelectedDays(selectedDays) + " *";
    }

    public List<String> getHours() {
        List<String> hours = new ArrayList<String>();

        for(int i=0;i<24;i++){
            if(i <= 9){
                hours.add("0" + (String.valueOf(i)));
                continue;
            }
            hours.add((String.valueOf(i)));
        }

        return hours;
    }

    public List<String> getMinutes() {
        List<String> minutes = new ArrayList<String>();

        for(int i=0;i<60;i++){
            if(i <= 9){
                minutes.add("0" + (String.valueOf(i)));
                continue;
            }
            minutes.add((String.valueOf(i)));
        }

        return minutes;
    }

    public List<String> getDaysOfWeek() {
        List<String> days = new ArrayList<String>();

        days.add("Lunes");
        days.add("Martes");
        days.add("Miércoles");
        days.add("Jueves");
        days.add("Viernes");
        days.add("Sábado");
        days.add("Domingo");

        return days;
    }

    public String getFormattedSelectedDays(String[] selectedDays) {
        String finalString = "";

        for (String selectedDay : selectedDays) {
            if(selectedDay.equals("Lunes")){
                finalString += "MON,";
            } else if(selectedDay.equals("Martes")){
                finalString += "TUE,";
            } else if(selectedDay.equals("Miércoles")){
                finalString += "WED,";
            } else if(selectedDay.equals("Jueves")){
                finalString += "THU,";
            } else if(selectedDay.equals("Viernes")){
                finalString += "FRI,";
            } else if(selectedDay.equals("Sábado")){
                finalString += "SAT,";
            } else if(selectedDay.equals("Domingo")){
                finalString += "SUN,";
            }
        }

        return finalString.substring(0, finalString.length()-1);
    }
}
