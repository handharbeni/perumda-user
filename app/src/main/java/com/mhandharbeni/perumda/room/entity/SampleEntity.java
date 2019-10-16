package com.mhandharbeni.perumda.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.mhandharbeni.perumda.room.utils.DateConverter;

@Entity
@TypeConverters(DateConverter.class)
public class SampleEntity {
    @PrimaryKey
    public int id;
}
