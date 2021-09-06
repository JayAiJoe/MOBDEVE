package com.mobdeve.group11.assist;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CalendarUtils {

    public static LocalDate selectedDate = LocalDate.now();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<LocalDate> daysInMonthArray(LocalDate date) {
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth ym = YearMonth.from(date);
        int daysInMonth = ym.lengthOfMonth();
        LocalDate firstOfMonth = CalendarUtils.selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        if(dayOfWeek == 7)
            dayOfWeek = 0;

        for(int i = 0; i<=42; i++){
            if(i<dayOfWeek || i>=daysInMonth+dayOfWeek)
                daysInMonthArray.add(null);
            else
                daysInMonthArray.add(LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), i - dayOfWeek + 1));
        }

        return daysInMonthArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<LocalDate> daysInWeekArray(LocalDate date) {
        ArrayList<LocalDate> daysInWeekArray = new ArrayList<>();
        LocalDate current = sundayForDate(selectedDate);
        LocalDate nextWeek = current.plusWeeks(1);

        while(current.isBefore(nextWeek)){
            daysInWeekArray.add(current);
            current = current.plusDays(1);
        }

        return daysInWeekArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static LocalDate sundayForDate(LocalDate date) {
        LocalDate lastWeek = date.minusWeeks(1);

        while(date.isAfter(lastWeek)){
            if(date.getDayOfWeek() == DayOfWeek.SUNDAY)
                return date;
            else
                date = date.minusDays(1);
        }

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String dateToMonthYear(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }
}
