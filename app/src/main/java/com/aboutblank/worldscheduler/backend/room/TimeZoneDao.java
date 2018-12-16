package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import java.util.List;

@Dao
public interface TimeZoneDao {

    @Query("SELECT * from TimeZone where id = :timeZoneId")
    TimeZone getTimeZoneById(String timeZoneId);

    @Query("SELECT * from TimeZone")
    List<TimeZone> getTimeZones();

    @Query("SELECT COUNT(*) from TimeZone")
    int count();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(@NonNull TimeZone timeZone);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(@NonNull List<TimeZone> timeZones);

    @Query("DELETE from TimeZone where id = :timeZoneId")
    void delete(String timeZoneId);
}
