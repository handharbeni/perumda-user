package com.mhandharbeni.perumda.room.interfaceDao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.mhandharbeni.perumda.room.entity.SampleEntity;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface SampleInterfaceDao {
    @Insert(onConflict = REPLACE)
    public void insertSample(SampleEntity sampleEntity);
}
