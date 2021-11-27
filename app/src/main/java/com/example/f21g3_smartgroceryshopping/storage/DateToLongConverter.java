package com.example.f21g3_smartgroceryshopping.storage;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Class helping to convert long rows in the local storage table to the java.util.Date java objects
 */
public class DateToLongConverter {

    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date){
        return date == null ? null : date.getTime();
    }

}
