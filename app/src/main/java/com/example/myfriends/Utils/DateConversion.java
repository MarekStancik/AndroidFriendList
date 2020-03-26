package com.example.myfriends.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConversion {
    static final DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

    public static String toString(Date date){
        return dateFormat.format(date);
    }

    public static Date toDate(String str) {
        try {
            return dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

}
