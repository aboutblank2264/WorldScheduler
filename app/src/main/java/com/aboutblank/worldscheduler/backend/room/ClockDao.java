package com.aboutblank.worldscheduler.backend.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ClockDao {

    @Query("SELECT * from Clock where timeZoneId = :timeZoneId")
    Clock getClockById(String timeZoneId);

    @Query("SELECT * from Clock")
    LiveData<List<Clock>> getAllClocksLive();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertClock(Clock clock);

    @Update
    void update(Clock clock);

    @Query("DELETE from Clock where timeZoneId = :timeZoneId")
    void deleteClock(String timeZoneId);

    @Delete
    void deleteClock(Clock clock);
}
