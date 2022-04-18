package com.openclassrooms.realestatemanager;

import org.junit.Test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class UtilsUnitTest {
    @Test
    public void convertEuroDollarTest() {
        assertEquals(Utils.convertDollarToEuro(1000), 812, 1);
        assertEquals(Utils.convertEuroToDollar(812), 1000, 1);
    }

    @Test
    public void changeDateFormatTest() {
        //get today date with the good format
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(date);


        assertEquals(Utils.getTodayDate(), strDate);
    }
}