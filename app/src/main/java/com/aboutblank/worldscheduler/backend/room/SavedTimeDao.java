package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SavedTimeDao {

    @Query("SELECT time from SavedTime where timeZoneId = :timeZoneId")
    List<Long> getTimes(String timeZoneId);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    void addTime(SavedTime savedTime);

    @Query("UPDATE SavedTime SET time = :newTime WHERE timeZoneId = :timeZoneId AND time = :oldTime")
    void updateTime(String timeZoneId, long oldTime, long newTime);

    @Query("DELETE FROM SavedTime where timeZoneId = :timeZoneId AND time = :time")
    void delete(String timeZoneId, long time);
}
