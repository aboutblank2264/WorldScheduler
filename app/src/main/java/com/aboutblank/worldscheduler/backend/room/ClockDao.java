package com.aboutblank.worldscheduler.backend.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import java.util.List;

@Dao
public interface ClockDao {

    @Query("SELECT * from Clock where id = :timeZoneId")
    Clock getClockById(String timeZoneId);

    @Query("SELECT * from Clock order by id")
    List<Clock> getAllClocks();

    @Query("SELECT * from Clock order by id")
    LiveData<List<Clock>> getAllClocksLive();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertClock(@NonNull Clock clock);

    @Query("DELETE from Clock where id = :timeZoneId")
    void deleteClock(String timeZoneId);
}
