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

    @Query("SELECT * from Clock where timeZoneId = :timeZoneId")
    LiveData<Clock> getClockById(String timeZoneId);

    //Not to be used outside of LocalDatabase
    @Query("SELECT * from Clock where timeZoneId = :timeZoneId")
    Clock getClockByIdNonLive(String timeZoneId);

    @Query("SELECT * from Clock")
    LiveData<List<Clock>> getAllClocks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertClock(@NonNull Clock clock);

    @Query("DELETE from Clock where timeZoneId = :timeZoneId")
    void deleteClock(String timeZoneId);
}
