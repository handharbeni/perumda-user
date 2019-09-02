package com.mhandharbeni.perumda.room.interfaceDao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mhandharbeni.perumda.room.entity.data.DataLoket;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface LoketInterfaceDao {
    @Insert(onConflict = REPLACE)
    void insertAll(List<DataLoket> dataLoket);

    @Insert(onConflict = REPLACE)
    void insert(DataLoket dataLoket);

    @Query("SELECT * FROM DataLoket")
    List<DataLoket> getAll();
}
