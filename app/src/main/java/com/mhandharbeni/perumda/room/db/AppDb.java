package com.mhandharbeni.perumda.room.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;

import com.mhandharbeni.perumda.room.entity.data.DataImageSlider;
import com.mhandharbeni.perumda.room.entity.data.DataLoket;
import com.mhandharbeni.perumda.room.entity.data.DataUnit;
import com.mhandharbeni.perumda.room.interfaceDao.ImageSliderInterfaceDao;
import com.mhandharbeni.perumda.room.interfaceDao.LoketInterfaceDao;
import com.mhandharbeni.perumda.room.interfaceDao.UnitInterfaceDao;
import com.mhandharbeni.perumda.room.utils.DateConverter;
import com.mhandharbeni.perumda.utils.Constant;

@TypeConverters(DateConverter.class)
@Database(
        entities = {
                DataUnit.class,
                DataLoket.class,
                DataImageSlider.class
        },
        version = Constant.versionDb,
        exportSchema = false
)
public abstract class AppDb extends RoomDatabase {
    public abstract UnitInterfaceDao unitInterfaceDao();
    public abstract LoketInterfaceDao loketInterfaceDao();
    public abstract ImageSliderInterfaceDao imageSliderInterfaceDao();

    private static volatile AppDb INSTANCE;
    public static Migration[] migrations = new Migration[]{};
    public static AppDb getInstance(final Context context){
        if (INSTANCE == null){
            synchronized (AppDb.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDb.class,
                            Constant.nameDb
                    )
                            .allowMainThreadQueries()
                            .addMigrations(migrations)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
