package com.night.navdrawer.db;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.night.navdrawer.model.QrCode;

import java.util.List;

@Dao
public interface QrCodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(QrCode qrCode);

    @Query("delete from qrCodes")
    void deleteAll();

    @Delete
    void deleteItem(QrCode qrCode);

    @Query("select * from qrCodes")
    LiveData<List<QrCode>> getAll();
}
