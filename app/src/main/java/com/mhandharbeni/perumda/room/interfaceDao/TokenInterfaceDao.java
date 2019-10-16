package com.mhandharbeni.perumda.room.interfaceDao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.mhandharbeni.perumda.room.entity.data.DataToken;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TokenInterfaceDao {
    @Insert(onConflict = REPLACE)
    void insertAll(List<DataToken> listToken);

}
