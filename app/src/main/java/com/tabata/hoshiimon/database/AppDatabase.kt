package com.tabata.hoshiimon.database

import android.content.Context
import androidx.room.*

@Database(entities = [Item::class, Group::class, Value::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val dao: AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}