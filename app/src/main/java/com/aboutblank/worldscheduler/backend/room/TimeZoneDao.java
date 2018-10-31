package com.aboutblank.worldscheduler.backend.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

@Dao
public interface TimeZoneDao {

    @Query("SELECT * from TimeZone where id = :timeZoneId")
    TimeZone getTimeZoneById(String timeZoneId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(@NonNull TimeZone timeZone);

    @Query("DELETE from TimeZone where id = :timeZoneId")
    void delete(String timeZoneId);
}
