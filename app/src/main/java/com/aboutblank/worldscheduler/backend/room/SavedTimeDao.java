package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SavedTimeDao {

    @Query("SELECT time from SavedTime where timeZone = :timeZoneId")
    List<Long> getTimes(String timeZoneId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTime(SavedTime savedTime);

    @Query("UPDATE SavedTime SET time = :newTime WHERE timeZone = :timeZoneId AND time = :oldTime")
    void updateTime(String timeZoneId, long oldTime, long newTime);

    @Query("DELETE from SavedTime where timeZone = :timeZoneId AND time = :time")
    void delete(String timeZoneId, long time);

    @Query("DELETE from SavedTime where timeZone = :timeZoneId")
    void delete(String timeZoneId);
}
