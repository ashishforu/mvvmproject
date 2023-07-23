package com.example.demospeedtest.database;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


@Dao
public interface SpeedDao {
    @Query("DELETE FROM Speed")
    void deleteAll();

    @Query("SELECT MAX(actualSpeed) FROM Speed")
    String getMax();

    @Query("SELECT MIN(actualSpeed) FROM Speed")
    String getMin();


    @Query("SELECT SUM(actualSpeed) FROM Speed")
    String  getSum();

    @Query("SELECT COUNT(actualSpeed) FROM Speed")
    String getALlcount();

    @Insert
    void insertSpeed(Speed... speeds);
}

