package com.mhandharbeni.perumda.room.interfaceDao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mhandharbeni.perumda.room.entity.data.DataUnit;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UnitInterfaceDao {
    @Insert(onConflict = REPLACE)
    void insertAll(List<DataUnit> dataUnit);

    @Insert(onConflict = REPLACE)
    void insert(DataUnit dataUnit);

    @Query("SELECT * FROM DataUnit")
    List<DataUnit> getAllUnit();

    @Query("SELECT * FROM DataUnit WHERE unit = :unit")
    DataUnit getUnit(String unit);
}
