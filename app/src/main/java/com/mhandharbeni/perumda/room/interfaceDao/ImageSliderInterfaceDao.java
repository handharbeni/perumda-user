package com.mhandharbeni.perumda.room.interfaceDao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mhandharbeni.perumda.room.entity.data.DataImageSlider;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ImageSliderInterfaceDao {
    @Insert(onConflict = REPLACE)
    void insertAll(List<DataImageSlider> dataImageSliders);

    @Insert(onConflict = REPLACE)
    void insert(DataImageSlider dataImageSlider);

    @Query("SELECT * FROM DataImageSlider WHERE aktif = 1")
    List<DataImageSlider> getAll();
}
