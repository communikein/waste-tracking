package com.example.xyzreader.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.xyzreader.data.model.Waste;

@Database(entities = {Waste.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class WasteDatabase extends RoomDatabase {

    public static final String NAME = "waste";

    public abstract WasteDao wasteDao();
}
