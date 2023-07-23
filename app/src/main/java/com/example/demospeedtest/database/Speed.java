package com.example.demospeedtest.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Speed {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "actualSpeed")
    public String actualSpeed;


}