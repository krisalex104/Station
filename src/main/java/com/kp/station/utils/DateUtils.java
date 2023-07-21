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

    public static Map<String, Date> isValidDateRange(String startDateStr, String endDateStr, String operation) {

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = parseDate(startDateStr);
            endDate = parseDate(endDateStr);
            Date currentDate = new Date();

            if (startDate.after(endDate)) {
                throw new ServiceException(operation, Error.INVALID_START_DATE);
            } else if (startDate.after(currentDate)) {
                throw new ServiceException(operation, Error.INVALID_START_DATE);

            } else if (endDate.after(currentDate)) {
                throw new ServiceException(operation, Error.INVALID_END_DATE);
            }

        } catch (ParseException e) {
            throw new ServiceException(operation, Error.INVALID_DATE_FORMAT);
        }
        Map<String, Date> map = new HashMap<>();
        map.put("fromDate", startDate);
        map.put("toDate", endDate);
        return map;
    }

    public static Date getCurrentDate(String key, Date date) {
        Date currentDate = date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(currentDate);
        Date formattedDate = null;

        String hours = "00";
        String minutes = "00";
        String seconds = "00";
        if (key.equals("TO_DATE_TIME")) {
            hours = "23";
            minutes = "59";
            seconds = "59";
        }
        try {
            formattedDate = sdf.parse(dateString);
        } catch (ParseException e) {
            throw new ServiceException("DATE_CONVERSION", Error.ERROR_WHILE_PARSING_DATE);
        }

        return changeTimeOfDate(formattedDate, hours, minutes, seconds);
    }

    private static Date changeTimeOfDate(Date originalDate, String hours, String minutes, String seconds) {
        originalDate.setHours(Integer.parseInt(hours));
        originalDate.setMinutes(Integer.parseInt(minutes));
        originalDate.setSeconds(Integer.parseInt(seconds));
        return originalDate;
    }
}
