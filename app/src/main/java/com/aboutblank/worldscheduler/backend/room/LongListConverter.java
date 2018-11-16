package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class LongListConverter {

    private static Gson gson = new Gson();

    @TypeConverter
    public static List<Long> stringToList(String val) {
        if(val == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Long>>() {}.getType();

        return gson.fromJson(val, listType);
    }

    @TypeConverter
    public static String listToString(List<Long> val) {
        return gson.toJson(val);
    }
}
