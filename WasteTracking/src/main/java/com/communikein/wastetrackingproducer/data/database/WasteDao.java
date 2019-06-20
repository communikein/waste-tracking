package com.communikein.wastetrackingproducer.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.communikein.wastetrackingproducer.data.model.Waste;

import java.util.List;

/**
 * This class provides the methods necessary to access (read/write) the DB.
 */
@Dao
public interface WasteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addWaste(Waste entry);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] addWaste(List<Waste> entries);


    @Update
    int updateWaste(Waste article);


    @Query("DELETE FROM blockchain WHERE waste_id = :id")
    int deleteWaste(String id);

    @Query("DELETE FROM blockchain")
    int deleteWastes();


    @Query("SELECT * FROM blockchain WHERE waste_id = :id")
    Waste getWaste(String id);

    @Query("SELECT * FROM blockchain WHERE waste_id = :id")
    Cursor getWasteAsCursor(String id);

    @Query("SELECT * FROM blockchain WHERE waste_id = :id")
    LiveData<Waste> getWasteAsObservable(long id);


    @Query("SELECT * FROM blockchain ORDER BY waste_id")
    List<Waste> getWastes();

    @Query("SELECT * FROM blockchain ORDER BY waste_id")
    Cursor getWastesAsCursor();

    @Query("SELECT * FROM blockchain ORDER BY waste_id")
    LiveData<List<Waste>> getWastesAsObservable();


    @Query("SELECT COUNT(*) FROM blockchain")
    int getWastesCount();

}