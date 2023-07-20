package com.kp.station.utils;

import com.kp.station.exception.Error;
import com.kp.station.exception.ServiceException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static Date parseDate(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.parse(dateStr);
    }
    public static Map<String,String> isValidDateRange(String startDateStr, String endDateStr,String operation) {

        Date startDate=null;
        Date endDate=null;
        try {
             startDate = parseDate(startDateStr);
             endDate = parseDate(endDateStr);
            Date currentDate = new Date();

           if(startDate.after(endDate)){
             throw   new ServiceException(operation, Error.INVALID_START_DATE);
           }else if(startDate.after(currentDate)){
              throw  new ServiceException(operation,Error.INVALID_START_DATE);

           } else if (endDate.after(currentDate)) {
               throw new ServiceException(operation,Error.INVALID_END_DATE);
           }

        } catch (ParseException e) {
            throw new ServiceException(operation,Error.INVALID_DATE_FORMAT);
        }

        HashMap<String,String> map=new HashMap<>();
        map.put("startDate", String.valueOf(startDate));
        map.put("endDate",String.valueOf(endDate));
        return map;
    }

}
