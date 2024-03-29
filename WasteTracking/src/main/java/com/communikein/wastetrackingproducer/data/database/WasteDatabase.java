package com.communikein.wastetrackingproducer.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.communikein.wastetrackingproducer.data.model.Waste;

/**
 * Class necessary to create the DB object, listing which entries, version and converters to use
 */
@Database(entities = {Waste.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class WasteDatabase extends RoomDatabase {

    public static final String NAME = "waste";

    public abstract WasteDao wasteDao();
}
