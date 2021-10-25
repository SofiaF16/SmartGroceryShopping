package com.example.f21g3_smartgroceryshopping.storage;

import androidx.room.TypeConverter;

import java.util.Date;

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
