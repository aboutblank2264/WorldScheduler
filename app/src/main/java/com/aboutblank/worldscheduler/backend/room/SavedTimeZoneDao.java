package com.aboutblank.worldscheduler.backend.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

@Dao
public interface SavedTimeZoneDao {

    @Transaction
    @Query("SELECT * from SavedTimeZone")
    LiveData<List<Clock>> getAllClocksLive();

    @Transaction
    @Query("SELECT * from SavedTimeZone where timeZoneId = :timeZoneId")
    Clock getClockById(String timeZoneId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SavedTimeZone savedTimeZone);

    @Delete
    void delete(SavedTimeZone savedTimeZone);

    @Query("DELETE from SavedTimeZone where timeZoneId = :timeZoneId")
    void delete(String timeZoneId);
}
