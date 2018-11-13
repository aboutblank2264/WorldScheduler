package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Clock.class, TimeZone.class}, version = 4)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract ClockDao clockDao();
    public abstract TimeZoneDao timeZoneDao();

    public static LocalDatabase getDatabase(Context context) {
        return Room.databaseBuilder(context, LocalDatabase.class, LocalDatabase.class.getName())
                .fallbackToDestructiveMigration()
                .build();
    }

//    public Clock getClockById(String timeZoneId) {
//        return clockDao().getClockById(timeZoneId);
//    }
//
//    public List<Clock> getAllClocks() {
//        return clockDao().getAllClocks();
//    }
//
//    public LiveData<List<Clock>> getAllClocksLive() {
//        return clockDao().getAllClocksLive();
//    }
//
//    public void saveClockWithId(String timeZoneId) {
//        clockDao().insertClock(new Clock(timeZoneId));
//    }
//
//    public void deleteClock(String timeZoneId) {
//        clockDao().deleteClock(timeZoneId);
//    }
//
//    public List<TimeZone> getAllTimeZones() {
//        return timeZoneDao().getTimeZones();
//    }
//
//    public TimeZone getTimeZoneById(String timeZoneId) {
//        return timeZoneDao().getTimeZoneById(timeZoneId);
//    }
//
//    public TimeZone getTimeZoneIdByName(String name) {
//        return timeZoneDao().getTimeZoneIdByName(name);
//    }
//
//    public List<String> getTimeZoneNames() {
//        return timeZoneDao().getTimeZoneNames();
//    }
//
//    public void saveTimeZone(String timeZoneId) {
//        timeZoneDao().insert(new TimeZone(timeZoneId, TimeFormatter.formatTimeZoneId(timeZoneId)));
//    }
//
//    public int countTimeZones() {
//        return timeZoneDao().count();
//    }
}
